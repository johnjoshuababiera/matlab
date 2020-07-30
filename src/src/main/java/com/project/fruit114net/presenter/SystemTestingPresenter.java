package com.project.fruit114net.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.project.fruit114net.ActivityLogger;
import com.project.fruit114net.util.EngineUtil;
import com.project.fruit114net.util.PresenterUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class SystemTestingPresenter extends ActivityLogger {

    @FXML private TextArea txtAreaFacts;
    @FXML private ImageView imgView;
    @FXML private JFXButton btnChooseFile;
    @FXML private JFXButton btnClassify;
    @FXML private JFXButton btnClear;
    @FXML private JFXTextField txtFile;
    @FXML private JFXTextField txtAlexPredicted;
    @FXML private JFXTextField txtGooglePredicted;
    @FXML private JFXTextField txtResNetPredicted;
    @FXML private JFXTextField txtFruit114Predicted;
    @FXML private JFXTextField txtAlexAccuracy;
    @FXML private JFXTextField txtGoogleAccuracy;
    @FXML private JFXTextField txtResNetAccuracy;
    @FXML private JFXTextField txtFruit114Accuracy;

    MatlabEngine matEng;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matEng = EngineUtil.getMatlabEngineInstance();
        btnChooseFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png"));
            File imageFile = fileChooser.showOpenDialog(null);
            if (imageFile != null) {
                Image image = new Image(imageFile.toURI().toString());
                imgView.setImage(image);
                txtFile.setText(imageFile.getAbsolutePath());
            }
        });
        btnClassify.setOnAction(event -> {
            String command= String.format("image = imread('%s');", txtFile.getText());
            try{
                matEng.eval("clear;", null, null);
                matEng.eval("close all;", null, null);

                //read workspaces
                matEng.eval("userProfile = getenv('USERPROFILE');", null, null);
                matEng.eval("userProfile = userProfile + \"\\AppData\\Local\\Fruit114Net\\app\\admin\\\";", null, null);
                matEng.eval("alexnet=load(userProfile+'Fruit114AlexNet.mat');", null, null);
                matEng.eval("googlenet=load(userProfile+'Fruit114GoogLeNet.mat');", null, null);
                //matEng.eval("resnet=load(userProfile+'Fruit114ResNet.mat');", null, null);

                //matEng.eval("googlenet=load('D:\\output\\Fruit114GoogLeNet.mat');", null, null);
                matEng.eval("resnet=load(userProfile+'Fruit114ResNet.mat');", null, null);
                matEng.eval("fruit=load(userProfile+'Fruit114Net.mat');", null, null);
                //read image
                matEng.eval(command, null, null);
                //classify images
                matEng.eval("alexnetClassify = imresize(image,[227 227]);",null, null);
                matEng.eval("googlenetClassify = imresize(image,[224 224]);",null, null);
                matEng.eval("resnetClassify = imresize(image,[224 224]);",null, null);
                matEng.eval("fruitClassify = imresize(image,[100 100]);",null, null);

                matEng.eval("[YPredT,probsT] = classify(alexnet.Fruit114AlexNet,alexnetClassify);",null, null);
                matEng.eval("alexPredicted = string(YPredT);",null, null);
                matEng.eval("alexProbability = maxk(probsT,1);",null, null);
                matEng.eval("alexProbability = string(round(alexProbability*100, 2));",null, null);

                matEng.eval("[YPredT,probsT] = classify(googlenet.Fruit114GoogLeNet,googlenetClassify);",null, null);
                matEng.eval("googlePredicted = string(YPredT);",null, null);
                matEng.eval("googleProbability = string(probsT);",null, null);
                matEng.eval("googleProbability = maxk(probsT,1);",null, null);
                matEng.eval("googleProbability = string(round(googleProbability*100, 2));",null, null);

                matEng.eval("[YPredT,probsT] = classify(resnet.Fruit114ResNet,resnetClassify);",null, null);
                matEng.eval("resnetPredicted = string(YPredT);",null, null);
                matEng.eval("resnetProbability = maxk(probsT,1);",null, null);
                matEng.eval("resnetProbability = string(round(resnetProbability*100, 2));",null, null);

                matEng.eval("[YPredT,probsT] = classify(fruit.Fruit114Net,fruitClassify);",null, null);
                matEng.eval("fruitPredicted = string(YPredT);",null, null);
                matEng.eval("fruitProbability = maxk(probsT,1);",null, null);
                matEng.eval("fruitProbability = string(round(fruitProbability*100, 2));",null, null);

                //get results
                /*matEng.eval("alexResults = string(alex);", null, null);
                matEng.eval("googleResults = string(google);", null, null);
                matEng.eval("resnetResults = string(res);", null, null);
                matEng.eval("fruitResults = string(fruit);", null, null);*/

                StringWriter alexPredicted = new StringWriter();
                StringWriter alexProbability = new StringWriter();
                StringWriter googlePredicted = new StringWriter();
                StringWriter googleProbability = new StringWriter();
                StringWriter resnetPredicted = new StringWriter();
                StringWriter resnetProbability = new StringWriter();
                StringWriter fruitPredicted = new StringWriter();
                StringWriter fruitProbability = new StringWriter();

                matEng.eval("disp(alexPredicted);", alexPredicted,null);
                matEng.eval("disp(alexProbability);", alexProbability,null);
                matEng.eval("disp(googlePredicted);", googlePredicted,null);
                matEng.eval("disp(googleProbability);", googleProbability,null);
                matEng.eval("disp(resnetPredicted);", resnetPredicted,null);
                matEng.eval("disp(resnetProbability);", resnetProbability,null);
                matEng.eval("disp(fruitPredicted);", fruitPredicted,null);
                matEng.eval("disp(fruitProbability);", fruitProbability,null);

                //store result of the value to String variable
                String AlexNetResults=alexPredicted.toString();
                String AlexNetProbability=alexProbability.toString();
                String GoogLeNetResults=googlePredicted.toString();
                String GoogLeNetProbability=googleProbability.toString();
                String ResNetResults=resnetPredicted.toString();
                String ResNetProbability=resnetProbability.toString();
                String FruitNetResults=fruitPredicted.toString();
                String FruitNetProbability=fruitProbability.toString();

                txtAlexPredicted.setText(AlexNetResults);
                txtGooglePredicted.setText(GoogLeNetResults);
                txtResNetPredicted.setText(ResNetResults);
                txtFruit114Predicted.setText(FruitNetResults);
                txtAlexAccuracy.setText(AlexNetProbability);
                txtGoogleAccuracy.setText(GoogLeNetProbability);
                txtResNetAccuracy.setText(ResNetProbability);
                txtFruit114Accuracy.setText(FruitNetProbability);
                //Apple Braeburn
                if(txtFruit114Predicted.getText().equals("Apple Braeburn")) {
                    txtAreaFacts.setText("Braeburn apples are medium to large in size and are oval in shape. The thin, bi-colored skin has a yellow base and is covered with red to pink blush and red striping. The striping and blush will vary in degree of visibility depending on the climate in which the fruit has matured. Its firm flesh is pale yellow to gold and contains a few dark brown seeds in the center of the fibrous core.\n" +
                            "\n" +
                            "Braeburn apples are a good source of fiber, vitamins A, and C, and contain trace amounts of boron and potassium, most of which are located in the apple's skin.\n");
                }
                //Apple Crimson Snow
                else if(txtFruit114Predicted.getText().equals("Apple Crimson Snow")) {
                    txtAreaFacts.setText("Snow apples are round, conical, to ovate shaped fruits with a symmetrical appearance. The skin is smooth, matte, and has a yellow-green base, covered in bright red blush and striping. Underneath the surface, the flesh is soft, white, and crisp, encasing a small central core filled with black-brown seeds. The flesh may also be streaked with red and pink hues, depending on growing conditions. Snow apples are aromatic and are said to emit a strawberry-like scent. The apples also have a very sweet, subtly tart flavor with notes of spice, caramel, and wine.\n");
                }
                //Apple Golden
                else if(txtFruit114Predicted.getText().equals("Apple Golden")) {
                    txtAreaFacts.setText("Golden Delicious apples are pale green to golden yellow in color and speckled with small spots. They are small to medium in size, and tend to be conical or oblong in shape. Golden Delicious apples are firm, crisp, and white-fleshed. These apples have a balanced sweet-tart aromatic flavor, which has been described as honeyed. The flavor varies depending on where these apples are grown; in a cool climate, the amount of acid increases, actually creating a sweeter flavor. When grown in warmer areas, the acid content is lower, creating a milder flavor.\n" +
                        "\n" +
                        "Golden Delicious apples are low in calories and are a good source of soluble fiber, which has been proven to help lower cholesterol, control weight, and regulate blood sugar. They also contain vitamins A and C, as well as a trace amount of boron and potassium, most of which is located in the apple’s skin.\n");
                }
                //Apple Golden 2
                else if(txtFruit114Predicted.getText().equals("Apple Golden 2")) {
                    txtAreaFacts.setText("Golden Delicious apples are pale green to golden yellow in color and speckled with small spots. They are small to medium in size, and tend to be conical or oblong in shape. Golden Delicious apples are firm, crisp, and white-fleshed. These apples have a balanced sweet-tart aromatic flavor, which has been described as honeyed. The flavor varies depending on where these apples are grown; in a cool climate, the amount of acid increases, actually creating a sweeter flavor. When grown in warmer areas, the acid content is lower, creating a milder flavor.\n" +
                            "\n" +
                            "Golden Delicious apples are low in calories and are a good source of soluble fiber, which has been proven to help lower cholesterol, control weight, and regulate blood sugar. They also contain vitamins A and C, as well as a trace amount of boron and potassium, most of which is located in the apple’s skin.\n");
                }
                //Apple Golden 3
                else if(txtFruit114Predicted.getText().equals("Apple Golden 3")) {
                    txtAreaFacts.setText("Golden Delicious apples are pale green to golden yellow in color and speckled with small spots. They are small to medium in size, and tend to be conical or oblong in shape. Golden Delicious apples are firm, crisp, and white-fleshed. These apples have a balanced sweet-tart aromatic flavor, which has been described as honeyed. The flavor varies depending on where these apples are grown; in a cool climate, the amount of acid increases, actually creating a sweeter flavor. When grown in warmer areas, the acid content is lower, creating a milder flavor.\n" +
                            "\n" +
                            "Golden Delicious apples are low in calories and are a good source of soluble fiber, which has been proven to help lower cholesterol, control weight, and regulate blood sugar. They also contain vitamins A and C, as well as a trace amount of boron and potassium, most of which is located in the apple’s skin.\n");
                }
                //Apple Granny Smith
                else if(txtFruit114Predicted.getText().equals("Apple Granny Smith")) {
                    txtAreaFacts.setText("Granny Smith apples have a bright green skin that is often speckled with faint white lenticels (spots). Medium to large in size and round in shape, they are a firm and juicy apple with thick skin. Their flesh is bright white and crisp in texture with a tart, acidic, yet subtly sweet flavor. Granny Smith apples grown in colder climates will often take on a yellow to pink blush.");
                }
                //Apple Pink Lady
                else if(txtFruit114Predicted.getText().equals("Apple Pink Lady")) {
                    txtAreaFacts.setText("Pink Lady apples are elongated and have an asymmetrical shape. The skin is a vivid green covered in a pinkish blush which becomes a deeper shade of red where the apple was exposed to more sun. Pink Lady apples have a crunchy texture and a tart taste with a sweet finish. The white flesh is juicy and crisp, and offers a “fizz-like” burst of flavor.");

                }
                //Apple Red 1
                else if(txtFruit114Predicted.getText().equals("Apple Red 1")) {
                    txtAreaFacts.setText("Apples are among the world’s most popular fruits.\n" +
                            "\n" +
                            "Apples are high in fiber, vitamin C, and various antioxidants. They are also very filling, considering their low calorie count. Studies show that eating apples can have multiple benefits for your health.\n");

                }
                //Apple Red 2
                else if(txtFruit114Predicted.getText().equals("Apple Red 2")) {
                    txtAreaFacts.setText("Apples are among the world’s most popular fruits.\n" +
                            "\n" +
                            "Apples are high in fiber, vitamin C, and various antioxidants. They are also very filling, considering their low calorie count. Studies show that eating apples can have multiple benefits for your health.\n");

                }
                //Apple Red 3
                else if(txtFruit114Predicted.getText().equals("Apple Red 3")) {
                    txtAreaFacts.setText("Apples are among the world’s most popular fruits.\n" +
                            "\n" +
                            "Apples are high in fiber, vitamin C, and various antioxidants. They are also very filling, considering their low calorie count. Studies show that eating apples can have multiple benefits for your health.\n");

                }
                //Apple Red Delicious
                else if(txtFruit114Predicted.getText().equals("Apple Red Delicious")) {
                    txtAreaFacts.setText("Red Delicious apples are medium-sized and have a conical shape. They are broadly round at the top and taper at the base. The thick skin turns bright red well before the fruits are fully ripe, which can lead to early harvests. As they mature, the skin becomes a darker red with white lenticels visibly dotting the surface. The fine-grained flesh is creamy white, crisp and juicy and offers a mildly sweet taste with flavors of melon.\n" +
                            "\n" +
                            "Red Delicious apples are high in dietary fiber and contain vitamin C. They offer a small amount of vitamin A and sodium and traces of calcium and iron. Red Delicious apples are higher in antioxidants than many other apple varieties, most of which are contained in the skin.\n");

                }
                //Apple Red Yellow 1
                else if(txtFruit114Predicted.getText().equals("Apple Red Yellow 1")) {
                    txtAreaFacts.setText("Apples are among the world’s most popular fruits.\n" +
                            "\n" +
                            "Apples are high in fiber, vitamin C, and various antioxidants. They are also very filling, considering their low calorie count. Studies show that eating apples can have multiple benefits for your health.\n");
                }
                //Apple Red Yellow 2
                else if(txtFruit114Predicted.getText().equals("Apple Red Yellow 2")) {
                    txtAreaFacts.setText("Apples are among the world’s most popular fruits.\n" +
                            "\n" +
                            "Apples are high in fiber, vitamin C, and various antioxidants. They are also very filling, considering their low calorie count. Studies show that eating apples can have multiple benefits for your health.\n");
                }
                //Apricot
                else if(txtFruit114Predicted.getText().equals("Apricot")) {
                    txtAreaFacts.setText("Apricot, Prunus armeniaca is a deciduous tree in the family Rosaceae grown for its edible fruit. The apricot tree is has an erect growth habit and a spreading canopy. The leaves of the tree are ovate with a rounded base, pointed tip and serrated margin. The tree produces white to pink flowers, singly or in pairs, and a fleshy yellow to orange fruit. The apricot fruit is a drupe with skin that can be smooth or covered in tiny hairs depending on the variety and a single seed enclosed within a protective outer shell (stone). Apricot trees can reach 8–12 m (26–39 ft) and can live anywhere between 20 and 40 years depending on variety and growth conditions. Apricots may have as many as three centers of origin in China, Central Asia and the Near East.");
                }
                //Avocado
                else if(txtFruit114Predicted.getText().equals("Avocado")) {
                    txtAreaFacts.setText("Avocado fruit, a popular food, is a good source of potassium and healthy fats. The fruit, leaves, and seeds are sometimes used to make medicine. The oil from the fruit is also used as a medicine.\n" +
                            "\n" +
                            "Avocado is used for high cholesterol, psoriasis, arthritis, sexual desire, obesity, and many other conditions, but there is no good scientific evidence to support these uses.\n");
                }
                //Avocado ripe
                else if(txtFruit114Predicted.getText().equals("Avocado ripe")) {
                    txtAreaFacts.setText("Avocado fruit, a popular food, is a good source of potassium and healthy fats. The fruit, leaves, and seeds are sometimes used to make medicine. The oil from the fruit is also used as a medicine.\n" +
                            "\n" +
                            "Avocado is used for high cholesterol, psoriasis, arthritis, sexual desire, obesity, and many other conditions, but there is no good scientific evidence to support these uses.\n");
                }
                //Banana
                else if(txtFruit114Predicted.getText().equals("Banana")) {
                    txtAreaFacts.setText("Banana, fruit of the genus Musa, of the family Musaceae, one of the most important fruit crops of the world. The banana is grown in the tropics, and, though it is most widely consumed in those regions, it is valued worldwide for its flavour, nutritional value, and availability throughout the year.\n" +
                            "\n" +
                            "A ripe fruit contains as much as 22 percent of carbohydrate and is high in dietary fibre, potassium, manganese, and vitamins B6 and C.\n");
                }
                //Banana Lady Finger
                else if(txtFruit114Predicted.getText().equals("Banana Lady Finger")) {
                    txtAreaFacts.setText("Lady Finger bananas are good for fresh eating, as well as adding to baked goods or smoothies. The thin fruits do not oxidize as quickly as the more common banana, so they are perfect for fruit salads. If Lady Finger bananas are slightly overripe, they are ideal for a loaf of banana bread. Lady Finger bananas are used for creamy banana puddings, or in banana foster.");
                }
                //Banana Red
                else if(txtFruit114Predicted.getText().equals("Banana Red")) {
                    txtAreaFacts.setText("Red bananas are smaller in size than a common banana and the peel is a deep red or purple. It has a creamy white to pink flesh, with a slight raspberry-banana flavor. The overall taste is similar to a common yellow banana. They are imported from Costa Rica and are a favorite in Central America.");
                }
                //Blueberry
                else if(txtFruit114Predicted.getText().equals("Blueberry")) {
                    txtAreaFacts.setText("Blueberries are sweet, nutritious and wildly popular fruit. It is often labeled as a superfood, and is low in calories.\n" +
                            "\n" +
                            "Blueberry fruit is high in fiber which could help normal digestive function. It also contains vitamin C and other antioxidants. Blueberry also contains chemicals that might reduce swelling and destroy cancer cells.\n");
                }
                //Cactus fruit
                else if(txtFruit114Predicted.getText().equals("Cactus fruit")) {
                    txtAreaFacts.setText("Cactus fruit is delicious and loaded with nutrients. It is also known as prickly pear, tuna fruit or cactus pear, it boasts a naturally sweet flavor and can be used in various desserts.It is very low in calories and sugars, which makes it ideal for ketogenic and low-carb diets.");
                }
                //Cantaloupe 1
                else if(txtFruit114Predicted.getText().equals("Cantaloupe 1")) {
                    txtAreaFacts.setText("Colourful and sweet, cantaloupe is low in calories yet packed with essential nutrients that help lower the risk for certain diseases and help keep you looking and feeling great.\n" +
                            "\n" +
                            "Cantaloupe’s most potent vitamins—A and C—are essential to helping us maintain strong, healthy hair and just one serving (about one-quarter of a melon) contains 100 per cent of the recommended daily allowances of both.\n");
                }
                //Cantaloupe 2
                else if(txtFruit114Predicted.getText().equals("Cantaloupe 2")) {
                    txtAreaFacts.setText("Colourful and sweet, cantaloupe is low in calories yet packed with essential nutrients that help lower the risk for certain diseases and help keep you looking and feeling great.\n" +
                            "\n" +
                            "Cantaloupe’s most potent vitamins—A and C—are essential to helping us maintain strong, healthy hair and just one serving (about one-quarter of a melon) contains 100 per cent of the recommended daily allowances of both.\n");
                }
                //Carambula
                else if(txtFruit114Predicted.getText().equals("Carambula")) {
                    txtAreaFacts.setText("Carambula, also known as star fruit, is a star-shaped tropical fruit with sweet and sour flavor. The fruit is recognized as belimbing manis or balimbing (filipino) in many South-East Asian regions and kamrakh in India.\n" +
                            "\n" +
                            "Carambula contains good quantities of vitamin-C, and is rich in antioxidant phytonutrient polyphenolic flavonoids. They are  a good source of B-complex vitamins such as folates, riboflavin, and pyridoxine.\n");
                }
                //Cherry 1
                else if(txtFruit114Predicted.getText().equals("Cherry 1")) {
                    txtAreaFacts.setText("Cherries are small, round, deep red stone fruit. Depending on the variety, of which there are hundreds, their size and flavour can vary but usually fall into one of two categories: sweet or tart (sour).\n" +
                            "\n" +
                            "Cherries provide a wide range of rich nutrients that include in potassium, calcium, fiber, carotenoids, and vitamin C. The fruit also contains magnesium, iron, zinc, thiamin, riboflavin, niacin, and vitamin E and B6.\n");
                }
                //Cherry 2
                else if(txtFruit114Predicted.getText().equals("Cherry 2")) {
                    txtAreaFacts.setText("Cherries are small, round, deep red stone fruit. Depending on the variety, of which there are hundreds, their size and flavour can vary but usually fall into one of two categories: sweet or tart (sour).\n" +
                            "\n" +
                            "Cherries provide a wide range of rich nutrients that include in potassium, calcium, fiber, carotenoids, and vitamin C. The fruit also contains magnesium, iron, zinc, thiamin, riboflavin, niacin, and vitamin E and B6.\n");
                }
                //Cherry Rainier
                else if(txtFruit114Predicted.getText().equals("Cherry Rainier")) {
                    txtAreaFacts.setText("Rainier cherries distinguish themselves from all other cherry varieties by the color of their skin and their unparalleled high sugar levels. Their coloring exhibits layers of golden hues blushed with tones of pink and red, an unequivocally unique facade. Their shape is quintessential cherry: plump, rounded and slightly heart-shaped with a dimple at the stem end. The flesh is a pale golden color with red streaks near the skin and seed. The flavor of Rainier cherries is memorably sweet and low acid with a caramel-like finish on the palate.");
                }
                //Cherry Wax Black
                else if(txtFruit114Predicted.getText().equals("Cherry Wax Black")) {
                    txtAreaFacts.setText("Cherries are small, round, deep red stone fruit. Depending on the variety, of which there are hundreds, their size and flavour can vary but usually fall into one of two categories: sweet or tart (sour).\n" +
                            "\n" +
                            "Cherries provide a wide range of rich nutrients that include in potassium, calcium, fiber, carotenoids, and vitamin C. The fruit also contains magnesium, iron, zinc, thiamin, riboflavin, niacin, and vitamin E and B6.\n");
                }
                //Cherry Wax Red
                else if(txtFruit114Predicted.getText().equals("Cherry Wax Red")) {
                    txtAreaFacts.setText("Cherries are small, round, deep red stone fruit. Depending on the variety, of which there are hundreds, their size and flavour can vary but usually fall into one of two categories: sweet or tart (sour).\n" +
                            "\n" +
                            "Cherries provide a wide range of rich nutrients that include in potassium, calcium, fiber, carotenoids, and vitamin C. The fruit also contains magnesium, iron, zinc, thiamin, riboflavin, niacin, and vitamin E and B6.\n");
                }
                //Cherry Wax Yellow
                else if(txtFruit114Predicted.getText().equals("Cherry Wax Yellow")) {
                    txtAreaFacts.setText("Cherries are small, round, deep red stone fruit. Depending on the variety, of which there are hundreds, their size and flavour can vary but usually fall into one of two categories: sweet or tart (sour).\n" +
                            "\n" +
                            "Cherries provide a wide range of rich nutrients that include in potassium, calcium, fiber, carotenoids, and vitamin C. The fruit also contains magnesium, iron, zinc, thiamin, riboflavin, niacin, and vitamin E and B6.\n");
                }
                //Chestnut
                else if(txtFruit114Predicted.getText().equals("Chestnut")) {
                    txtAreaFacts.setText("Chestnut (Castanea sativa) is the fruit of the chestnut tree which is native to Southern Europe and Asia Minor.  Its nutrient-dense fruits are high-energy food and present many health benefits and therapeutic value. It would replace cereals and grains during meals before visitors to the Americas brought the potato back home.");
                }
                //Clementine
                else if(txtFruit114Predicted.getText().equals("Clementine")) {
                    txtAreaFacts.setText("Clementines — commonly known by the brand names Cuties or Halos — are a hybrid of mandarin and sweet oranges. These tiny fruits are bright orange, easy to peel, sweeter than most other citrus fruits, and typically seedless.\n" +
                            "\n" +
                            "They’re a great source of vitamin C and antioxidants. However, like grapefruit, they contain compounds that may interact with certain medications.\n");
                }
                //Cocos
                else if(txtFruit114Predicted.getText().equals("Cocos")) {
                    txtAreaFacts.setText("Commonly called the “coconut tree” and is the most naturally widespread fruit plant on Earth. All parts of the fruit of the coconut tree can be used. Both the green coconut water and solid albumen ripe fruits are used industrially and in home cooking in many ways.");
                }
                //Dates
                else if(txtFruit114Predicted.getText().equals("Dates")) {
                    txtAreaFacts.setText("Dates are the fruit of the date palm tree, which is grown in many tropical regions of the world. Almost all dates sold in Western countries are dried. A wrinkled skin indicates they are dried, whereas a smooth skin indicates freshness.\n" +
                            "\n" +
                            "Dates contain several vitamins and minerals, in addition to fiber and antioxidants. However, they are high in calories since they are a dried fruit. Dates contain several types of antioxidants that may help prevent the development of certain chronic illnesses, such as heart disease, cancer, Alzheimer’s and diabetes\n");
                }
                //Ginger Root
                else if(txtFruit114Predicted.getText().equals("Ginger Root")) {
                    txtAreaFacts.setText("Ginger is among the healthiest (and most delicious) spices on the planet. Ginger can be used fresh, dried, powdered, or as an oil or juice, and is sometimes added to processed foods and cosmetics. It is a very common ingredient in recipes.\n" +
                            "\n" +
                            "Ginger has been shown to lower blood sugar levels and improve various heart disease risk factors in patients with type 2 diabetes. Ginger contains a substance called 6-gingerol, which may have protective effects against cancer. However, this needs to be studied a lot more.\n");
                }
                //Granadilla
                else if(txtFruit114Predicted.getText().equals("Granadilla")) {
                    txtAreaFacts.setText("The orange granadilla has a sweet and sour flavour and belongs to the family of passion fruits, along with the yellow maracuja and the traditional purple passion fruit.It has the size and shape of a plum and contains a yellow, jelly-like pulp with a scattering of black edible seeds.\n" +
                            "Granadilla is commonly eaten by itself. However it makes a wonderful jelly, pie filling or cake frosting. Seeds with the surrounding juice sacs are also often added to fruit salads.\n");
                }
                //Grape Blue
                else if(txtFruit114Predicted.getText().equals("Grape Blue")) {
                    txtAreaFacts.setText("Grapes come in different colors and forms. There are red, green, and purple grapes, seedless grapes, grape jelly, grape jam and grape juice, raisins, currents, and sultanas, not to mention wine.\n" +
                            "\n" +
                            "The nutrients in grapes offer a number of possible health benefits. They have been associated with prevention of cancer, heart disease, high blood pressure, and constipation.\n");
                }
                //Grape Pink
                else if(txtFruit114Predicted.getText().equals("Grape Pink")) {
                    txtAreaFacts.setText("Grapes come in different colors and forms. There are red, green, and purple grapes, seedless grapes, grape jelly, grape jam and grape juice, raisins, currents, and sultanas, not to mention wine.\n" +
                            "\n" +
                            "The nutrients in grapes offer a number of possible health benefits. They have been associated with prevention of cancer, heart disease, high blood pressure, and constipation.\n");
                }
                //Grape White
                else if(txtFruit114Predicted.getText().equals("Grape White")) {
                    txtAreaFacts.setText("Grapes come in different colors and forms. There are red, green, and purple grapes, seedless grapes, grape jelly, grape jam and grape juice, raisins, currents, and sultanas, not to mention wine.\n" +
                            "\n" +
                            "The nutrients in grapes offer a number of possible health benefits. They have been associated with prevention of cancer, heart disease, high blood pressure, and constipation.\n");
                }
                //Grape White 2
                else if(txtFruit114Predicted.getText().equals("Grape White 2")) {
                    txtAreaFacts.setText("Grapes come in different colors and forms. There are red, green, and purple grapes, seedless grapes, grape jelly, grape jam and grape juice, raisins, currents, and sultanas, not to mention wine.\n" +
                            "\n" +
                            "The nutrients in grapes offer a number of possible health benefits. They have been associated with prevention of cancer, heart disease, high blood pressure, and constipation.\n");
                }
                //Grape White 3
                else if(txtFruit114Predicted.getText().equals("Grape White 3")) {
                    txtAreaFacts.setText("Grapes come in different colors and forms. There are red, green, and purple grapes, seedless grapes, grape jelly, grape jam and grape juice, raisins, currents, and sultanas, not to mention wine.\n" +
                            "\n" +
                            "The nutrients in grapes offer a number of possible health benefits. They have been associated with prevention of cancer, heart disease, high blood pressure, and constipation.\n");
                }
                //Grape White 4
                else if(txtFruit114Predicted.getText().equals("Grape White 4")) {
                    txtAreaFacts.setText("Grapes come in different colors and forms. There are red, green, and purple grapes, seedless grapes, grape jelly, grape jam and grape juice, raisins, currents, and sultanas, not to mention wine.\n" +
                            "\n" +
                            "The nutrients in grapes offer a number of possible health benefits. They have been associated with prevention of cancer, heart disease, high blood pressure, and constipation.\n");
                }
                //Grapefruit Pink
                else if(txtFruit114Predicted.getText().equals("Grapefruit Pink")) {
                    txtAreaFacts.setText("Grapefruit is a tropical citrus fruit known for its sweet and somewhat sour taste.\n" +
                            "\n" +
                            "It's rich in nutrients, antioxidants and fiber, making it one of the healthiest citrus fruits you can eat.Research shows that it may have some powerful health benefits, including weight loss and a reduced risk of heart disease.\n");
                }
                //Grapefruit White
                else if(txtFruit114Predicted.getText().equals("Grapefruit White")) {
                    txtAreaFacts.setText("Grapefruit is a tropical citrus fruit known for its sweet and somewhat sour taste.\n" +
                            "\n" +
                            "It's rich in nutrients, antioxidants and fiber, making it one of the healthiest citrus fruits you can eat.Research shows that it may have some powerful health benefits, including weight loss and a reduced risk of heart disease.\n");
                }
                //Guava
                else if(txtFruit114Predicted.getText().equals("Guava")) {
                    txtAreaFacts.setText("Guavas are tropical trees originating in Central America.\n" +
                            "Their fruits are oval in shape with light green or yellow skin and contain edible seeds. What’s more, guava leaves are used as an herbal tea and the leaf extract as a supplement.\n" +
                            "\n" +
                            "Guava fruits are amazingly rich in antioxidants, vitamin C, potassium, and fiber. This remarkable nutrient content gives them many health benefits.\n");
                }
                //Hazelnut
                else if(txtFruit114Predicted.getText().equals("Hazelnut")) {
                    txtAreaFacts.setText("The hazelnut, also known as the filbert, is a type of nut that comes from the Corylus tree. Hazelnuts have a sweet flavor and can be eaten raw, roasted or ground into a paste.\n" +
                            "\n" +
                            "Like other nuts, hazelnuts are rich in nutrients and have a high content of protein, fats, vitamins and minerals. Here are seven evidence-based health benefits of hazelnuts.\n");
                }
                //Huckleberry
                else if(txtFruit114Predicted.getText().equals("Huckleberry")) {
                    txtAreaFacts.setText("Often confused with the blueberry due to its close resemblance, huckleberries are a wild blue-black berry.  Although very similar in taste, the big difference is the seeds within the huckleberry that give it a crunchy texture when fresh and its thicker skin.  The flavor is a little more tart than blueberries, with an intense blueberry flavor.  Huckleberries are not cultivated commercially, and are usually found in the wild.");
                }
                //Kaki
                else if(txtFruit114Predicted.getText().equals("Kaki")) {
                    txtAreaFacts.setText("The persimmon is an edible fruit that comes from the persimmon tree. The tree is a member of the Ericales order of plants, which also includes Brazil nuts, blueberries and tea.\n" +
                            "\n" +
                            "Kaki are loaded with antioxidants, promotes regularity, supports healthy vision, reduces cholesterol levels, decreases inflammation and lowers blood pressure.\n");
                }
                //Kiwi
                else if(txtFruit114Predicted.getText().equals("Kiwi")) {
                    txtAreaFacts.setText("Kiwis are small fruits that pack a lot of flavor and plenty of health benefits. Their green flesh is sweet and tangy. It’s also full of nutrients like vitamin C, vitamin K, vitamin E, folate, and potassium. They also have a lot of antioxidants and are a good source of fiber. Their small black seeds are edible, as is the fuzzy brown peel, though many prefer to peel the kiwi before eating it.");
                }
                //Kohlrabi
                else if(txtFruit114Predicted.getText().equals("Kohlrabi")) {
                    txtAreaFacts.setText("Also known as German turnip or cabbage turnip, is very popular in Northern and Eastern European countries like Germany and Hungary as well as northern Vietnam and eastern India.  It can be white, green, or purple with little difference in flavor, and has a mild taste that has made it popular in dishes from salads to soups. When raw, kohlrabi has a flavor similar to raw cabbage with a lightly spicy kick like a radish or turnip. Kohlrabi is low in fat and high in fiber, providing 19 percent of the daily recommended intake in a 1-cup serving. The vegetable is high in calcium and even provides a noticeable amount of potassium (14 percent per 1-cup serving). Raw or cooked, it's a healthy addition to any meal as a side dish or ingredient.");
                }
                //Kumquats
                else if(txtFruit114Predicted.getText().equals("Kumquats")) {
                    txtAreaFacts.setText("A kumquat isn’t much bigger than a grape, yet this bite-sized fruit fills your mouth with a big burst of sweet-tart citrus flavor.\n" +
                            "In Chinese, kumquat means “golden orange.” They were originally grown in China. In contrast with other citrus fruits, the peel of the kumquat is sweet and edible, while the juicy flesh is tart.\n" +
                            "\n" +
                            "Kumquats are an excellent source of vitamin C. They’re also rich in fiber and water, making them a weight loss friendly food. Because kumquat peels are edible, you can tap into their rich reservoirs of plant compounds. These have antioxidant, anti-inflammatory and cholesterol-lowering properties.\n");
                }
                //Lemon
                else if(txtFruit114Predicted.getText().equals("Lemon")) {
                    txtAreaFacts.setText("Lemons (Citrus limon) are among the world's most popular citrus fruits. They grow on lemon trees and are a hybrid of the original citron and lime.\n" +
                            "\n" +
                            "These yellow fruits also have many potential health benefits. Eating lemons may lower your risk of heart disease, cancer, and kidney stones. A great source of vitamin C and fiber, lemons contain many plant compounds, minerals, and essential oils.\n");
                }
                //Lemon Meyer
                else if(txtFruit114Predicted.getText().equals("Lemon Meyer")) {
                    txtAreaFacts.setText("Meyer lemons are named for Frank N. Meyer. Meyer lemons are believed to be a cross between a regular lemon and a mandarin orange. The fruit is about the size of a lemon, sometimes slightly smaller, with a smooth, deep yellow peel. When mature, the thin peel can be almost orange. The flesh and juice are sweeter than a regular lemon and can be used raw or cooked. Because the peel is thin and lacking in a thick, bitter pith, the whole lemon (minus the seeds) can be used.\n" +
                            "\n" +
                            "The fruits are packed with vitamin C, which fights scurvy (a now-rare disease) and aids in boosting immunity and repairing cells and tissue. The fruit is also low in calories and fat and high in water.\n");
                }
                //Limes
                else if(txtFruit114Predicted.getText().equals("Limes")) {
                    txtAreaFacts.setText("Limes are sour, round, and bright green citrus fruits.\n" +
                            "There are many species of limes like the Key lime (Citrus aurantifolia), Persian lime (Citrus latifolia), desert lime (Citrus glauca) and kaffir lime (Citrus hystrix).\n" +
                            "\n" +
                            "Limes are high in vitamin C, providing over 20% of your daily needs. They also contain small amounts of iron, calcium, vitamin B6, thiamine, potassium, and more.\n");
                }
                //Lychee
                else if(txtFruit114Predicted.getText().equals("Lychee")) {
                    txtAreaFacts.setText("The lychee (Litchi chinensis) — also known as litchi or lichee — is a small tropical fruit from the soapberry family. Lychees have an inedible, pink-red, leathery skin, which is removed before consumption. The flesh is white and surrounds a dark seed in the center.\n" +
                            "\n" +
                            "Lychees are primarily composed of water and carbs, most of which are sugars. Compared to many other fruits, they’re low in fiber. They’re also high in vitamin C and offer decent amounts of copper and potassium. Like most fruits and vegetables, lychees are a good source of antioxidants and other healthy plant compounds. These include epicatechin and rutin.\n");
                }
                //Mandarine
                else if(txtFruit114Predicted.getText().equals("Mandarine")) {
                    txtAreaFacts.setText("Mandarine oranges are a small, loose-skinned variety of the common orange, typically sweeter and less acidic than the larger oranges. Thought to have originated in India, they travelled across China where they picked up the name “mandarin”.\n" +
                            "\n" +
                            "They’re a great source of vitamin C and antioxidants such as fiber, protein, Vitamin C, Vitamin A,and potassium.\n");
                }
                //Mango
                else if(txtFruit114Predicted.getText().equals("Mango")) {
                    txtAreaFacts.setText("In some parts of the world, mango (Mangifera indica) is called the “king of fruits.” It’s a drupe, or stone fruit, which means that it has a large seed in the middle.\n" +
                            "\n" +
                            "Studies link mango and its nutrients to health benefits, such as improved immunity, digestive health and eyesight, as well as a lower risk of certain cancers. Mango is low in calories yet high in nutrients — particularly vitamin C, which aids immunity, iron absorption and growth and repair.\n");
                }
                //Mango Red
                else if(txtFruit114Predicted.getText().equals("Mango Red")) {
                    txtAreaFacts.setText("In some parts of the world, mango (Mangifera indica) is called the “king of fruits.” It’s a drupe, or stone fruit, which means that it has a large seed in the middle.\n" +
                            "\n" +
                            "Studies link mango and its nutrients to health benefits, such as improved immunity, digestive health and eyesight, as well as a lower risk of certain cancers. Mango is low in calories yet high in nutrients — particularly vitamin C, which aids immunity, iron absorption and growth and repair.\n");
                }
                //Mangostan
                else if(txtFruit114Predicted.getText().equals("Mangostan")) {
                    txtAreaFacts.setText("Mangostan or mangosteen (Garcinia mangostana) is an exotic, tropical fruit with a slightly sweet and sour flavor. The fruit is sometimes referred to as purple mangosteen because of the deep purple color its rind develops when ripe. In contrast, the juicy inner flesh is bright white.\n" +
                            "\n" +
                            "Mangosteen provides a variety of essential vitamins, minerals, and fiber while being low in calories. These nutrients are important for maintaining many functions in your body. Mangosteen contains vitamins with antioxidant capacity, as well as a unique class of antioxidant compounds known as xanthones.\n");
                }
                //Maracuja
                else if(txtFruit114Predicted.getText().equals("Maracuja")) {
                    txtAreaFacts.setText("Maracujá is the Portuguese name for the superfruit more commonly known as passion fruit. The name maracujá comes from a word in Paraguayan Guaraní that means “nursery for flies” - probably because the juicy pulp of the fruit contains around 250 black seeds.\n" +
                            "\n" +
                            "Maracujá is rich with some wonderful vitamins and minerals, a good source of vitamin C, fiber, and the B vitamins riboflavin and niacin. It also has a high content of essential fatty acids, calcium and phosphorus. Maracujá oil also has an astounding number of benefits when consumed or applied.  It's best when cold-pressed, as other methods of extracting the oil will often destroy or reduce the many vitamins and minerals that are found in the passion fruit seed. \n");
                }
                //Melon Piel de Sapo
                else if(txtFruit114Predicted.getText().equals("Melon Piel de Sapo")) {
                    txtAreaFacts.setText("Also known as the Christmas or Santa Claus melon. \n" +
                            "The Piel de Sapo has a thick green outer rind resembling the skin of a toad, a juicy white flesh, and a taste somewhere between a honeydew and a pear. It can last on the counter for weeks, explaining the melon’s pseudonyms. It gives off little to no odor, even when ripe. A ripe Christmas melon will be heavy for its size with a firm flesh that has more yellow appearing through the green speckles.\n" +
                            "\n" +
                            "Like other melons, the Christmas melon is low in calories and high in vitamin C and potassium, making it great for meals, snacks or desserts. \n");
                }
                //Mulberry
                else if(txtFruit114Predicted.getText().equals("Mulberry")) {
                    txtAreaFacts.setText("Mulberries are the fruits of mulberry trees (Morus sp.) and related to figs and breadfruit. They carry colorful berries — most commonly black, white, or red — that are often made into wine, fruit juice, tea, jam, or canned foods, but can also be dried and eaten as a snack.\n" +
                            "\n" +
                            "Fresh mulberries have about 10% carbs in the form of simple sugars, starch, and soluble and insoluble fibers. They’re fairly high in water and low in calories.Mulberries contain high amounts of both iron and vitamin C, as well as decent amounts of potassium and vitamins E and K.\n");
                }
                //Nectarine
                else if(txtFruit114Predicted.getText().equals("Nectarine")) {
                    txtAreaFacts.setText("Nectarines are nearly identical to peaches in both their genetic makeup and nutritional profile. The most obvious differences are their skin and flavor. Peaches are covered with fuzz, while nectarines have thin, smooth skin. Nectarines also have slightly firmer flesh and a more sweet-tart flavor. Although their flavors are distinctive, nectarines and peaches can often be interchanged in recipes.\n" +
                            "\n" +
                            "In addition to being low in calories and rich in fiber, nectarines are a good source of vitamin A, vitamin C, and potassium. These nutrients offer health benefits in terms of improved metabolism, digestion, and heart health. \n");
                }
                //Nectarine Flat
                else if(txtFruit114Predicted.getText().equals("Nectarine Flat")) {
                    txtAreaFacts.setText("Nectarines are nearly identical to peaches in both their genetic makeup and nutritional profile. The most obvious differences are their skin and flavor. Peaches are covered with fuzz, while nectarines have thin, smooth skin. Nectarines also have slightly firmer flesh and a more sweet-tart flavor. Although their flavors are distinctive, nectarines and peaches can often be interchanged in recipes.\n" +
                            "\n" +
                            "In addition to being low in calories and rich in fiber, nectarines are a good source of vitamin A, vitamin C, and potassium. These nutrients offer health benefits in terms of improved metabolism, digestion, and heart health. \n");
                }
                //Nut Forest
                else if(txtFruit114Predicted.getText().equals("Nut Forest")) {
                    txtAreaFacts.setText("The hazelnut, also known as the filbert, is a type of nut that comes from the Corylus tree. Hazelnuts have a sweet flavor and can be eaten raw, roasted or ground into a paste.\n" +
                            "\n" +
                            "Like other nuts, hazelnuts are rich in nutrients and have a high content of protein, fats, vitamins and minerals. Here are seven evidence-based health benefits of hazelnuts\n");
                }
                //Nut Pecan
                else if(txtFruit114Predicted.getText().equals("Nut Pecan")) {
                    txtAreaFacts.setText("Pecan is a brown nut with an edible kernel similar to walnut. Pecans have a sweet, buttery taste as compared to other nuts.\n" +
                            "\n" +
                            "Pecans contain energy, fiber, amino acids, starch, and sugars. [4] These nuts are the best source of plant-based protein. Other nutrients in this fruit include thiamin, riboflavin, niacin, pantothenic acid, beta-carotene, folate, folic acid, and vitamin A, vitamin B6, vitamin C, vitamin E, and vitamin K.\n");
                }
                //Onion Red
                else if(txtFruit114Predicted.getText().equals("Onion Red")) {
                    txtAreaFacts.setText("Onions vary in size, shape, color, and flavor. The most common types are red, yellow, and white onions. The taste of these vegetables can range from sweet and juicy to sharp, spicy, and pungent, often depending on the season in which people grow and consume them.\n" +
                            "\n" +
                            "Onions may also provide potential health benefits. These may include reducing the risk of several types of cancer, improving mood, and maintaining skin and hair health.\n");
                }
                //Onion Red Peeled
                else if(txtFruit114Predicted.getText().equals("Onion Red Peeled")) {
                    txtAreaFacts.setText("Onions vary in size, shape, color, and flavor. The most common types are red, yellow, and white onions. The taste of these vegetables can range from sweet and juicy to sharp, spicy, and pungent, often depending on the season in which people grow and consume them.\n" +
                            "\n" +
                            "Onions may also provide potential health benefits. These may include reducing the risk of several types of cancer, improving mood, and maintaining skin and hair health.\n");
                }
                //Onion White
                else if(txtFruit114Predicted.getText().equals("Onion White")) {
                    txtAreaFacts.setText("Onions vary in size, shape, color, and flavor. The most common types are red, yellow, and white onions. The taste of these vegetables can range from sweet and juicy to sharp, spicy, and pungent, often depending on the season in which people grow and consume them.\n" +
                            "\n" +
                            "Onions may also provide potential health benefits. These may include reducing the risk of several types of cancer, improving mood, and maintaining skin and hair health.\n");
                }
                //Orange
                else if(txtFruit114Predicted.getText().equals("Orange")) {
                    txtAreaFacts.setText("Oranges are small to medium in size, averaging 5-10 centimeters in diameter, and are globular, oblate, or oval in shape. The rind ranges in color from orange, yellow, to green and is smooth with a leathery texture, dotted with many small oil glands across the surface that produce fragrant essential oil. Underneath the rind, there is a thin layer of white pith that is bitter and spongy. The flesh is juicy, also ranges in color from red, orange, to yellow, is filled with tightly packed pulp and juice sacs, and is divided into 10-14 segments by thin membranes. The flesh, depending on the variety, may also contain a few cream-colored seeds. Oranges are aromatic, juicy, and vary in flavor from sweet, acidic, tart, to sour.\n" +
                            "\n" +
                            "Oranges are an excellent source of vitamin C, fiber, potassium, and have anti-inflammatory properties. They also contain some calcium, vitamin A, manganese, iron, copper, and magnesium.\n");
                }
                //Papaya
                else if(txtFruit114Predicted.getText().equals("Papaya")) {
                    txtAreaFacts.setText("Papayas are a soft, fleshy fruit that can be used in a wide variety of culinary ways. Here we will explore more on the health benefits, uses, how to incorporate more of them into your diet, and what nutritional value papayas have.\n" +
                            "\n" +
                            "The possible health benefits of consuming papaya include a reduced risk of heart disease, diabetes, cancer, aiding in digestion, improving blood glucose control in people with diabetes, lowering blood pressure, and improving wound healing.\n");
                }
                //Passion Fruit
                else if(txtFruit114Predicted.getText().equals("Passion Fruit")) {
                    txtAreaFacts.setText("Passion fruit is a tropical fruit grown all over the world. It has a hard, colorful rind and juicy, seed-filled center. Purple and yellow varieties are the most common.\n" +
                            "\n" +
                            "Passion fruit is a good source of fiber, vitamin C, and vitamin A. Calorie for calorie, it’s a nutrient-dense fruit. Passion fruit is also rich in antioxidants and dietary fiber. Diets high in these nutrients have been linked to a lower risk of conditions like heart disease and diabetes.\n");
                }
                //Peach
                else if(txtFruit114Predicted.getText().equals("Peach")) {
                    txtAreaFacts.setText("Peaches (Prunus persica) are small fruit with a fuzzy peel and a sweet white or yellow flesh. Peaches are related to plums, apricots, cherries, and almonds. They’re considered drupes or stone fruit because their flesh surrounds a shell that houses an edible seed. They’re thought to have originated in China more than 8,000 years ago.\n" +
                            "\n" +
                            "Peaches is nutritious and may offer an array of health benefits, including improved digestion, smoother skin, and allergy relief.\n");
                }
                //Peach 2
                else if(txtFruit114Predicted.getText().equals("Peach 2")) {
                    txtAreaFacts.setText("Peaches (Prunus persica) are small fruit with a fuzzy peel and a sweet white or yellow flesh. Peaches are related to plums, apricots, cherries, and almonds. They’re considered drupes or stone fruit because their flesh surrounds a shell that houses an edible seed. They’re thought to have originated in China more than 8,000 years ago.\n" +
                            "\n" +
                            "Peaches is nutritious and may offer an array of health benefits, including improved digestion, smoother skin, and allergy relief.\n");
                }
                //Peach Flat
                else if(txtFruit114Predicted.getText().equals("Peach Flat")) {
                    txtAreaFacts.setText("The unusual Saturn peach boasts a whitish-yellow skin brushed with colorful red highlights. Short and squat and about three and a half inches in diameter and only about an inch and one-half high, the tender skin is covered with a very fine fuzz. The juicy white flesh offers a delicious sweet flavor, a smooth texture and a low acid content. Resembling a donut per se, this peach's other descriptive name is donut peach.");
                }
                //Pear
                else if(txtFruit114Predicted.getText().equals("Pear")) {
                    txtAreaFacts.setText("In structure, pear fruit features a bell or “pyriform” shape; around 5-6 inches long, and weigh about 200 gm. Fresh fruit is firm in texture with mild ‘apple’ flavor. Externally, its skin is very thin; and depending upon the cultivar type, it can be green, red-orange or yellow-orange in color. Inside, it's off-white color flesh is soft and juicy.\n" +
                            "\n" +
                            "Pears fruit is packed with health benefiting nutrients such as dietary fiber, antioxidants, minerals, and vitamins, which are necessary for optimum health.\n");
                }
                //Pear Abate
                else if(txtFruit114Predicted.getText().equals("Pear Abate")) {
                    txtAreaFacts.setText("Abate Fetel pears are medium to large in size and are long and slender with a slightly curved shape. The smooth skin has a yellow-green base with red blushing, freckling, and brown russeting connecting to a thick, light-brown stem. The white flesh is semi-crisp with a melting, velvety consistency and the entire pear is edible, including the seedbed. Abate Fetel pears are aromatic and very sweet with rich notes of honey.\n" +
                            "\n" +
                            "Abate Fetel pears contain some vitamin C, vitamin A, calcium, and iron.\n");
                }
                //Pear Kaiser
                else if(txtFruit114Predicted.getText().equals("Pear Kaiser")) {
                    txtAreaFacts.setText("Pear Kaisers are medium to large in size and are oblong in shape with a rounded bottom that gradually tapers to an elongated neck and a slender green-brown stem. The thick skin is golden tan and is covered in rough, brown russeting with some mottling. The ivory to off-white flesh is firm, dense, and crisp with an intense honeyed aroma and has a central, soft core encasing a few small black-brown seeds. When ripe, Bosc pears are juicy, crunchy, and have a very sweet flavor with notes of woodsy spice.\n" +
                            "\n" +
                            "Pear Kaisers contain vitamin C, calcium, potassium, and dietary fiber.\n");
                }
                //Pear Monster
                else if(txtFruit114Predicted.getText().equals("Pear Monster")) {
                    txtAreaFacts.setText("In structure, pear fruit features a bell or “pyriform” shape; around 5-6 inches long, and weigh about 200 gm. Fresh fruit is firm in texture with mild ‘apple’ flavor. Externally, its skin is very thin; and depending upon the cultivar type, it can be green, red-orange or yellow-orange in color. Inside, it's off-white color flesh is soft and juicy.\n" +
                            "\n" +
                            "Pears fruit is packed with health benefiting nutrients such as dietary fiber, antioxidants, minerals, and vitamins, which are necessary for optimum health.\n");
                }
                //Pear Red
                else if(txtFruit114Predicted.getText().equals("Pear Red")) {
                    txtAreaFacts.setText("Red Anjou pears are medium in size, averaging eight centimeters in diameter, and are short, squat, and egg-shaped with a wide base that gradually tapers to a rounded top with a thick, dark-brown stem. The smooth, firm skin can range in color from deep red, maroon, rust-colored, to a lighter, crimson red with occasional striations and is covered in lenticels or pores. The flesh is white to cream-colored, dense, and buttery with a slightly gritty texture. When ripe, Red Anjou pears are juicy and soft with subtle, sweet flavors and mild notes of lemon and lime.\n" +
                            "\n" +
                            "Red Anjou pears contain vitamin C, vitamin K, vitamin B6, iron, magnesium, and some riboflavin.\n");
                }
                //Pear Williams
                else if(txtFruit114Predicted.getText().equals("Pear Williams")) {
                    txtAreaFacts.setText("Williams pears are medium to large in size and have a true pyriform, or pear shape, which has a large rounded base that tapers to a smaller curved neck with a light brown stem. The thin skin brightens as it ripens, transforming from green to a golden yellow, and is smooth and firm with some blushing and russeting. The flesh is aromatic, moist, cream-colored to ivory, and is fine-grained encasing a central core containing a few small, black-brown seeds. When mature but not fully ripe, Williams pears are crunchy, tart, and slightly gritty, but when fully ripe, they develop a juicy, smooth, buttery texture with a sweet flavor.\n" +
                            "\n" +
                            "Williams pears contain vitamin C, potassium, iron, and dietary fiber.\n");
                }
                //Pepino
                else if(txtFruit114Predicted.getText().equals("Pepino")) {
                    txtAreaFacts.setText("The Pepino has a light-yellow to light-green skin, streaked with purple vertical striping. The flesh, when ripe is golden yellow with a narrow seed cavity. The Pepino is entirely edible: skin, flesh, pulp and seeds. The yellow interior is fine-grained and sweetly aromatic, intensifying as it ripens. Its flavor can be described as a mix of banana and pear, with a slightly bitter bite. Its size is inconsistent and can be as small as a plum or as large as a papaya. For optimum sweetness Pepino should be picked at peak of ripeness. Care must be taken when handling Pepino fruits as once ripe they are delicate and easily prone to bruising.\n");
                }
                //Pepper Green
                else if(txtFruit114Predicted.getText().equals("Pepper Green")) {
                    txtAreaFacts.setText("Bell peppers (Capsicum annuum) are fruits that belong to the nightshade family. Also called sweet peppers or capsicums, bell peppers can be eaten either raw or cooked.\n" +
                            "\n" +
                            "Like their close relatives, chili peppers, bell peppers are sometimes dried and powdered. In that case, they are referred to as paprika.\n" +
                            "\n" +
                            "They are low in calories and exceptionally rich in vitamin C and other antioxidants, making them an excellent addition to a healthy diet.\n" +
                            "\n" +
                            "Bell peppers come in various colors, such as red, yellow, orange, and green — which are unripe.\n" +
                            "\n" +
                            "Green, unripe peppers have a slightly bitter flavor and are not as sweet as fully ripe ones.\n");
                }
                //Pepper Red
                else if(txtFruit114Predicted.getText().equals("Pepper Red")) {
                    txtAreaFacts.setText("Bell peppers (Capsicum annuum) are fruits that belong to the nightshade family. Also called sweet peppers or capsicums, bell peppers can be eaten either raw or cooked.\n" +
                            "\n" +
                            "Like their close relatives, chili peppers, bell peppers are sometimes dried and powdered. In that case, they are referred to as paprika.\n" +
                            "\n" +
                            "They are low in calories and exceptionally rich in vitamin C and other antioxidants, making them an excellent addition to a healthy diet.\n" +
                            "\n" +
                            "Bell peppers come in various colors, such as red, yellow, orange, and green — which are unripe.\n");
                }
                //Pepper Yellow
                else if(txtFruit114Predicted.getText().equals("Pepper Yellow")) {
                    txtAreaFacts.setText("Bell peppers (Capsicum annuum) are fruits that belong to the nightshade family. Also called sweet peppers or capsicums, bell peppers can be eaten either raw or cooked.\n" +
                            "\n" +
                            "Like their close relatives, chili peppers, bell peppers are sometimes dried and powdered. In that case, they are referred to as paprika.\n" +
                            "\n" +
                            "They are low in calories and exceptionally rich in vitamin C and other antioxidants, making them an excellent addition to a healthy diet.\n" +
                            "\n" +
                            "Bell peppers come in various colors, such as red, yellow, orange, and green — which are unripe.\n");
                }
                //Physalis
                else if(txtFruit114Predicted.getText().equals("Physalis")) {
                    txtAreaFacts.setText("Physalis bear a smooth berry which looks like a small, round yellowish tomato. It is sub-globose berry, 1.25–2 cm diameter, each enclosed within a bladder like husk that will become papery on maturation. The fruit is green while young turning to a smooth, glossy, golden–yellow to orange when ripe with many tiny (2 mm diameter) yellowish, flat seeds surrounded in the juicy palatable pulp. It has mildly tart flavor and sweet with a pleasing grape-like tang taste making it ideal for snacks, pies, or jams.\n" +
                            "\n" +
                            "Physalis is a good source of nutrients, minerals, vitamins. Consuming  140 gram of this fruit supplies, 3.92 mg of Vitamin B3, 1.4 mg of Iron, 15.4 mg of Vitamin C, 0.154 mg of Vitamin B1, 15.68 g of Carbohydrate, 56 mg of Phosphorus, 50 µg of Vitamin A, 2.66 g of Protein and 0.056 mg of Vitamin B2, 0.98 g of Total Fat and 13 mg of Calcium.\n");
                }
                //Physalis with Husk
                else if(txtFruit114Predicted.getText().equals("Physalis with Husk")) {
                    txtAreaFacts.setText("Physalis bear a smooth berry which looks like a small, round yellowish tomato. It is sub-globose berry, 1.25–2 cm diameter, each enclosed within a bladder like husk that will become papery on maturation. The fruit is green while young turning to a smooth, glossy, golden–yellow to orange when ripe with many tiny (2 mm diameter) yellowish, flat seeds surrounded in the juicy palatable pulp. It has mildly tart flavor and sweet with a pleasing grape-like tang taste making it ideal for snacks, pies, or jams.\n" +
                            "\n" +
                            "Physalis is a good source of nutrients, minerals, vitamins. Consuming  140 gram of this fruit supplies, 3.92 mg of Vitamin B3, 1.4 mg of Iron, 15.4 mg of Vitamin C, 0.154 mg of Vitamin B1, 15.68 g of Carbohydrate, 56 mg of Phosphorus, 50 µg of Vitamin A, 2.66 g of Protein and 0.056 mg of Vitamin B2, 0.98 g of Total Fat and 13 mg of Calcium.\n");
                }
                //Pineapple
                else if(txtFruit114Predicted.getText().equals("Pineapple")) {
                    txtAreaFacts.setText("Pineapples have the shape of a pinecone, and can reach up to 30 centimeters in length. They have a rough, waxy, hexagonal-patterned rind that is covered in small, soft spikes and topped with a compact grouping of narrow, green, pointed-tipped leaves that extend upright. The rind can range in color from green to yellow or reddish-orange when ripe. The flesh varies in shades of white or yellow, depending on the variety, and modern cultivated varieties are known to be seedless. The loosely fibrous and juicy flesh offers a sweet flavor with mild acidity, while the edible core is firmer, more leathery, and less sweet.\n" +
                            "\n" +
                            "Pineapple is a good source of dietary fiber and vitamin C, as well as some iron and calcium. The pineapple’s core contains high levels of an enzyme called bromelain, which is said to be an effective anti-inflammatory, muscle relaxant, and digestive aid. Bromelain even has chemicals that have been shown to interfere with the growth of tumor cells.\n");
                }
                //Pineapple Mini
                else if(txtFruit114Predicted.getText().equals("Pineapple Mini")) {
                    txtAreaFacts.setText("Peach pineapples are a small variety, but because of the high level of sweetness and juiciness, can weigh anywhere from 14 and 28 ounces. When ripe, its exterior turns a red-orange hue with pale eyes. The flesh is a bright yellowish-white, with a soft edible core. The texture of the fruit is creamy, tender and juicy, offering a balanced sweet-tart tropical flavor with nuances of peach. Slightly sharp in flavor when first harvested, the acidity of the Peach pineapple will mellow after a few days in storage. Highly aromatic, the Peach pineapple will have a sweet scent when ripe and ready to eat.\n" +
                            "\n" +
                            "Peach pineapples, like other pineapple varieties, are an excellent source of manganese. They are one of the most important sources of the essential mineral. Manganese contributes to overall brain and nervous system functions, as well as regulating the body’s metabolism. It helps control the levels of sugar in the blood, helps the body absorb essential vitamins and minerals, and is used as an anti-inflammatory. Peach pineapples are a good source vitamin C and dietary fiber. They are also a source of potassium, vitamins B1 and B6, beta carotene and antioxidants. They also contain the prebiotic enzyme bromelain, which also gives under ripe pineapple an irritating quality.\n");
                }
                //Pitahaya Red
                else if(txtFruit114Predicted.getText().equals("Pitahaya Red")) {
                    txtAreaFacts.setText("Dragon fruit grows on the Hylocereus cactus, also known as the Honolulu queen, whose flowers only open at night. It goes by many names, including pitaya, pitahaya, and strawberry pear.\n" +
                            "\n" +
                            "Dragon fruit contains small amounts of several nutrients. It’s also a decent source of iron, magnesium, and fiber.\n");
                }
                //Plum
                else if(txtFruit114Predicted.getText().equals("Plum")) {
                    txtAreaFacts.setText("Plum is a soft round smooth-skinned sweet fruit with sweet flesh and a flattish pointed stone. It has a lot of varieties that vary in season, size, colour and taste. Plums are large 3-6 cm andThey are dried often and can be kept for a few days. Keep them on a fruit dish in a cool and dark place.\n" +
                            "\n" +
                            "Plums contain an assortment of healthy components, vitamins, and minerals. [5] According to the USDA National Nutrient Database, they are an excellent source of vitamins such as vitamin A, vitamin C (ascorbic acid), folate, and vitamin K (phylloquinone). [6] They are also a good source of vitamin B1 (thiamine), B2 (riboflavin), B3 (niacin), B-6, and vitamin E (alpha-tocopherol). The minerals present in them include potassium, fluoride, phosphorus, magnesium, iron, calcium, and zinc.\n");
                }
                //Plum 2
                else if(txtFruit114Predicted.getText().equals("Plum 2")) {
                    txtAreaFacts.setText("Plum is a soft round smooth-skinned sweet fruit with sweet flesh and a flattish pointed stone. It has a lot of varieties that vary in season, size, colour and taste. Plums are large 3-6 cm andThey are dried often and can be kept for a few days. Keep them on a fruit dish in a cool and dark place.\n" +
                            "\n" +
                            "Plums contain an assortment of healthy components, vitamins, and minerals. [5] According to the USDA National Nutrient Database, they are an excellent source of vitamins such as vitamin A, vitamin C (ascorbic acid), folate, and vitamin K (phylloquinone). [6] They are also a good source of vitamin B1 (thiamine), B2 (riboflavin), B3 (niacin), B-6, and vitamin E (alpha-tocopherol). The minerals present in them include potassium, fluoride, phosphorus, magnesium, iron, calcium, and zinc.\n");
                }
                //Plum 3
                else if(txtFruit114Predicted.getText().equals("Plum 3")) {
                    txtAreaFacts.setText("Plum is a soft round smooth-skinned sweet fruit with sweet flesh and a flattish pointed stone. It has a lot of varieties that vary in season, size, colour and taste. Plums are large 3-6 cm andThey are dried often and can be kept for a few days. Keep them on a fruit dish in a cool and dark place.\n" +
                            "\n" +
                            "Plums contain an assortment of healthy components, vitamins, and minerals. [5] According to the USDA National Nutrient Database, they are an excellent source of vitamins such as vitamin A, vitamin C (ascorbic acid), folate, and vitamin K (phylloquinone). [6] They are also a good source of vitamin B1 (thiamine), B2 (riboflavin), B3 (niacin), B-6, and vitamin E (alpha-tocopherol). The minerals present in them include potassium, fluoride, phosphorus, magnesium, iron, calcium, and zinc.\n");
                }
                //Pomegranate
                else if(txtFruit114Predicted.getText().equals("Pomegranate")) {
                    txtAreaFacts.setText("The tough, leathery skin or rind is typically yellow overlaid with light or deep pink or rich red. The interior is separated by membranous walls and white, spongy, bitter tissue into compartments packed with sacs filled with sweetly acid, juicy, red, pink or whitish pulp or aril. In each sac there is one angular, soft or hard seed. High temperatures are essential during the fruiting period to get the best flavor. The pomegranate may begin to bear in 1 year after planting out, but 2-1/2 to 3 years is more common. Under suitable conditions the fruit should mature some 5 to 7 months after bloom.\n" +
                            "\n" +
                            "Pomegranate contains a variety of chemicals that might have antioxidant effects. Some preliminary research suggests that chemicals in pomegranate juice might slow the progression of atherosclerosis (hardening of the arteries) and possibly fight cancer cells. But it is not known if pomegranate has these effects when people drink the juice.\n");
                }
                //Pomelo Sweetie
                else if(txtFruit114Predicted.getText().equals("Pomelo Sweetie")) {
                    txtAreaFacts.setText("Oroblancos are either round- or oval-shaped with a thicker rind than grapefruit. When eaten, an oroblanco lacks bitterness associated with grapefruits and is rather sweet, even when the outer peel is still green, but the white membranes separating the fleshy segments are bitter and usually discarded.\n" +
                            "\n" +
                            "A similar fruit has been commonly cultivated in Israel since 1984, from where the name \"Sweetie\" originated.\n");
                }
                //Potato Red Washed
                else if(txtFruit114Predicted.getText().equals("Potato Red Washed")) {
                    txtAreaFacts.setText("Red Pontiac potatoes are round, thin-skinned red potatoes with deep-set eyes. Grown for their delicate flavor as a “new potato,\" Pontiacs maintain their shape when boiled in the skin and seldom burst or crumble. Their firm texture makes them ideal for “new potato hash”--the New England equivalent of home fries.\n" +
                            "\n" +
                            "Red potato nutrition includes essential vitamins, minerals and energy you need to fuel your busy day. \n");
                }
                //Potato White
                else if(txtFruit114Predicted.getText().equals("Potato White")) {
                    txtAreaFacts.setText("Potatoes are underground tubers that grow on the roots of the potato plant, Solanum tuberosum. This plant is from the nightshade family and related to tomatoes and tobacco.\n" +
                            "\n" +
                            "They’re generally eaten boiled, baked, or fried and frequently served as a side dish or snack. Cooked potatoes with skin are a good source of many vitamins and minerals, such as potassium and vitamin C.\n" +
                            "\n" +
                            "Aside from being high in water when fresh, potatoes are primarily composed of carbs and contain moderate amounts of protein and fiber — but almost no fat.\n");
                }
                //Quince
                else if(txtFruit114Predicted.getText().equals("Quince")) {
                    txtAreaFacts.setText("Quince resembles a large, lumpy yellow pear with skin that may be smooth or covered with a woolly down depending on variety. A characteristic common to all varieties is their strong aromatic fragrance, a musky-wild, tropical-like perfume. Astringent and sour, the flesh cannot be eaten raw and requires cooking to be edible. The fruit becomes a rich candy-like paste when slowly cooked and turns a deep apricot color with floral honeyed flavors.\n" +
                            "\n" +
                            "Rich in fiber, quince provides a moderate amount of vitamin C and potassium. Four ounces of raw fruit contains about 65 calories.\n");
                }
                //Rambutan
                else if(txtFruit114Predicted.getText().equals("Rambutan")) {
                    txtAreaFacts.setText("Rambutan got its name from the Malay word for hair because the golf-ball-sized fruit has a hairy red and green shell. Its translucent white flesh has a sweet yet creamy taste and contains a seed in its middle.\n" +
                            "\n" +
                            "Rambutan is very nutritious and may offer health benefits ranging from weight loss and better digestion to increased resistance to infections. The rambutan fruit is rich in many vitamins, minerals and beneficial plant compounds. Notable, rambutans are rich in vitamin C, a good amount of copper, and offers smaller amounts of manganese, phosphorus, potassium, magnesium, iron and zinc.\n");
                }
                //Raspberry
                else if(txtFruit114Predicted.getText().equals("Raspberry")) {
                    txtAreaFacts.setText("Raspberries are the edible fruit of a plant species in the rose family. There are many types of raspberries — including black, purple and golden — but the red raspberry, or Rubus idaeus, is the most common.\n" +
                            "\n" +
                            "These sweet, tart berries have a short shelf life and are harvested only during the summer and fall months. \n" +
                            "\n" +
                            "Raspberries boast many nutrients despite being low in calories.Raspberries are a great source of fiber, vitamin C and also contain small amounts of Vitamin A, thiamine, riboflavin, vitamin B6, calcium and zinc.\n");
                }
                //Redcurrant
                else if(txtFruit114Predicted.getText().equals("Redcurrant")) {
                    txtAreaFacts.setText("Red Currants are bright, red berries that grow in bunches in the deciduous red currant shrub, and can be eaten raw as a fruit, or used to prepare sauces and jellies. They contain vitamin c, potassium and other minerals.");
                }
                //Salak
                else if(txtFruit114Predicted.getText().equals("Salak")) {
                    txtAreaFacts.setText("Salak fruit, more technically known as snake or salacca zalacca is a fruit that grows in clusters at the base of the palm and they are called snake fruit because of its reddish-brown scaly skin. The fruit inside consists of 3 lobes, each with large inedible seed. The lobes resemble, and have the consistency of, large peeled garlic cloves and are white colored. The taste is commonly sweet and acidic, but its apple-like texture can vary from very dry and crumbly to moist and crunchy.\n" +
                            "\n" +
                            "Salak is a good source of nutrients, vitamins and minerals. Consuming 100 gram of salak offers 3.9 mg of Iron, 0.2 mg of Vitamin B2, 8.4 mg of Vitamin C, 12.1 g of Carbohydrate, 38 mg of Calcium, 18 mg of Phosphorus, 0.8 g of Protein, 0.4 g of Total Fat and 0.3 g of Total dietary Fiber.\n");
                }
                //Strawberry
                else if(txtFruit114Predicted.getText().equals("Strawberry")) {
                    txtAreaFacts.setText("Strawberries are bright red, juicy, and sweet.\n" +
                            "\n" +
                            "They’re an excellent source of vitamin C and manganese and also contain decent amounts of folate (vitamin B9) and potassium.\n" +
                            "\n" +
                            "Strawberries are very rich in antioxidants and plant compounds, which may have benefits for heart health and blood sugar control.\n");
                }
                //Strawberry Wedge
                else if(txtFruit114Predicted.getText().equals("Strawberry Wedge")) {
                    txtAreaFacts.setText("Strawberries are bright red, juicy, and sweet.\n" +
                            "\n" +
                            "They’re an excellent source of vitamin C and manganese and also contain decent amounts of folate (vitamin B9) and potassium.\n" +
                            "\n" +
                            "Strawberries are very rich in antioxidants and plant compounds, which may have benefits for heart health and blood sugar control. \n");
                }
                //Tamarillo
                else if(txtFruit114Predicted.getText().equals("Tamarillo")) {
                    txtAreaFacts.setText("Juicy, sweet and citric, tamarillo or tree tomato is a small oval-shaped fruit. It is closely related to other Solanaceae members of vegetables and fruits such as tomato, eggplant, tomatillo, groundcherry, and chili peppers.\n" +
                            "\n" +
                            "Tamarillos are one of the very low-calorie fruits. Nevertheless, they have good amounts of health benefiting plant nutrients such as dietary fiber (3.3 mg or 9% of RDA), minerals, antioxidants, and vitamins.\n");
                }
                //Tangelo
                else if(txtFruit114Predicted.getText().equals("Tangelo")) {
                    txtAreaFacts.setText("A tangelo is scientifically known as Citrus x tangelo because it is a citrus hybrid fruit of tangerines and pomelo. This hybrid fruit is very sweet and is roughly the size of a fist, most notably characterized by the protruding nipple on one side of the fruit which is why they are also referred to as “honeybells”.\n" +
                            "\n" +
                            "they are also packed with various important nutrients and active ingredients, such as very high levels of vitamin C and folate, as well as low levels of fat and a small amount of protein.\n");
                }
                //Tomato 1
                else if(txtFruit114Predicted.getText().equals("Tomato 1")) {
                    txtAreaFacts.setText("The tomato (Solanum lycopersicum) is a fruit from the nightshade family native to South America.\n" +
                            "\n" +
                            "Despite botanically being a fruit, it’s generally eaten and prepared like a vegetable.\n" +
                            "\n" +
                            "Tomatoes are the major dietary source of the antioxidant lycopene, which has been linked to many health benefits, including reduced risk of heart disease and cancer.\n" +
                            "\n" +
                            "They are also a great source of vitamin C, potassium, folate, and vitamin K.\n");
                }//Tomato 2
                else if(txtFruit114Predicted.getText().equals("Tomato 2")) {
                    txtAreaFacts.setText("The tomato (Solanum lycopersicum) is a fruit from the nightshade family native to South America.\n" +
                            "\n" +
                            "Despite botanically being a fruit, it’s generally eaten and prepared like a vegetable.\n" +
                            "\n" +
                            "Tomatoes are the major dietary source of the antioxidant lycopene, which has been linked to many health benefits, including reduced risk of heart disease and cancer.\n" +
                            "\n" +
                            "They are also a great source of vitamin C, potassium, folate, and vitamin K.\n");
                }//Tomato 4
                else if(txtFruit114Predicted.getText().equals("Tomato 3")) {
                    txtAreaFacts.setText("The tomato (Solanum lycopersicum) is a fruit from the nightshade family native to South America.\n" +
                            "\n" +
                            "Despite botanically being a fruit, it’s generally eaten and prepared like a vegetable.\n" +
                            "\n" +
                            "Tomatoes are the major dietary source of the antioxidant lycopene, which has been linked to many health benefits, including reduced risk of heart disease and cancer.\n" +
                            "\n" +
                            "They are also a great source of vitamin C, potassium, folate, and vitamin K.\n");
                }//Tomato 4
                else if(txtFruit114Predicted.getText().equals("Tomato 4")) {
                    txtAreaFacts.setText("The tomato (Solanum lycopersicum) is a fruit from the nightshade family native to South America.\n" +
                            "\n" +
                            "Despite botanically being a fruit, it’s generally eaten and prepared like a vegetable.\n" +
                            "\n" +
                            "Tomatoes are the major dietary source of the antioxidant lycopene, which has been linked to many health benefits, including reduced risk of heart disease and cancer.\n" +
                            "\n" +
                            "They are also a great source of vitamin C, potassium, folate, and vitamin K.\n");
                }
                //Tomato Cherry Red
                else if(txtFruit114Predicted.getText().equals("Tomato Cherry Red")) {
                    txtAreaFacts.setText("Cherry tomatoes are generally much sweeter than large tomatoes. The size and color of cherry tomatoes have a wide range, as there are hundreds of varieties. It should be almost firm, thin, and smooth-skinned. The flavor will be a fine balance of sweet and tart.\n" +
                            "\n" +
                            "They are high in fiber and vitamin C, they also have a healthy dose of other vitamins and minerals that are essential for good health, including vitamin B-6, which helps your body metabolize protein and supports cognitive development and brain function. Vitamin A is also present, which aids your body in producing white blood cells and keeps your heart, lungs and kidneys working properly.\n");
                }
                //Tomato Maroon
                else if(txtFruit114Predicted.getText().equals("Tomato Maroon")) {
                    txtAreaFacts.setText("The tomato (Solanum lycopersicum) is a fruit from the nightshade family native to South America.\n" +
                            "\n" +
                            "Despite botanically being a fruit, it’s generally eaten and prepared like a vegetable.\n" +
                            "\n" +
                            "Tomatoes are the major dietary source of the antioxidant lycopene, which has been linked to many health benefits, including reduced risk of heart disease and cancer.\n" +
                            "\n" +
                            "They are also a great source of vitamin C, potassium, folate, and vitamin K.\n");
                }//Tomato Yellow
                else if(txtFruit114Predicted.getText().equals("Tomato Yellow")) {
                    txtAreaFacts.setText("The tomato (Solanum lycopersicum) is a fruit from the nightshade family native to South America.\n" +
                            "\n" +
                            "Despite botanically being a fruit, it’s generally eaten and prepared like a vegetable.\n" +
                            "\n" +
                            "Tomatoes are the major dietary source of the antioxidant lycopene, which has been linked to many health benefits, including reduced risk of heart disease and cancer.\n" +
                            "\n" +
                            "They are also a great source of vitamin C, potassium, folate, and vitamin K.\n");
                }
                //Walnut
                else if(txtFruit114Predicted.getText().equals("Walnut")) {
                    txtAreaFacts.setText("Walnuts (Juglans regia) are a tree nut belonging to the walnut family. These nuts are rich in omega-3 fats and contain higher amounts of antioxidants than most other foods. Eating walnuts may improve brain health and prevent heart disease and cancer.\n" +
                            "\n" +
                            "Walnuts are most often eaten on their own as a snack but can also be added to salads, pastas, breakfast cereals, soups, and baked goods.\n");
                }
                /*//
                else if(txtFruit114Predicted.getText().equals("")) {
                    txtAreaFacts.setText("");
                }*/

            } catch (Exception e){
                PresenterUtils.INSTANCE.displayError(e.getMessage());
            }
            createLog("Classified an image");
        });
        btnClear.setOnAction(event -> {
            imgView.setImage(null);
            txtFile.setText(null);
            txtAlexPredicted.setText(null);
            txtGooglePredicted.setText(null);
            txtResNetPredicted.setText(null);
            txtFruit114Predicted.setText(null);
            txtAlexAccuracy.setText(null);
            txtGoogleAccuracy.setText(null);
            txtResNetAccuracy.setText(null);
            txtFruit114Accuracy.setText(null);
            txtAreaFacts.setText(null);
        });

    }
}
