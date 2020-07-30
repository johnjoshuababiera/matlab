package com.project.fruit114net.util;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

public class EngineUtil {

    private static MatlabEngine INSTANCE;

    public static MatlabEngine getMatlabEngineInstance(){
        if(INSTANCE==null){
            try {
                INSTANCE =  MatlabEngine.startMatlab();
            } catch (EngineException e) {
                PresenterUtils.INSTANCE.displayError(e.getMessage());
            } catch (InterruptedException e) {
                PresenterUtils.INSTANCE.displayError(e.getMessage());
            }
        }
        return INSTANCE;
    }
}

