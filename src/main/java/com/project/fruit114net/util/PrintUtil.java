package com.project.fruit114net.util;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PrintUtil {
    public static void printJasperCollection(List<?> collection, File file, Map map) throws Exception {
        if (collection.isEmpty()) {
            throw new Exception("No data to print.");
        }
        showReport(getFilledJasperPrint(collection, file, map));
    }

    public static void showReport(JasperPrint jasperPrint) throws HeadlessException {
        if (null == jasperPrint) {
            return;
        }
        JasperViewer jv = new JasperViewer(jasperPrint);
        JFrame frame = new JFrame("Print Preview");
        frame.add(jv.getContentPane());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new CancelPrinting());
        frame.setLocationRelativeTo(null);
        frame.setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        frame.setVisible(true);
        frame.toFront();
        frame.repaint();
    }

    private static class CancelPrinting extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int con = JOptionPane.showConfirmDialog(e.getWindow(), "Cancel Printing?", "APPROVAL", JOptionPane.YES_NO_OPTION);
            if (con == JOptionPane.YES_OPTION) {
                e.getWindow().setVisible(false);
            }
        }
    }

    private static JasperPrint getFilledJasperPrint(List<?> collection, File file, Map map) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            try {
                return JasperFillManager.fillReport(inputStream, map, new CollectionJasperBean(collection));
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean print(File file, Map map) throws JRException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
            showReport(jasperPrint);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

