/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.roitech.birt.startup;

import biz.roitech.birt.service.BirtEngine;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.eclipse.birt.core.framework.Platform;

/**
 *
 * @author ROITech-002
 */
public class ApplicationContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       System.out.println("Context initialized....");
       BirtEngine.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (null != BirtEngine.getInstance()) {
            BirtEngine.getInstance().getEngine().destroy();
            Platform.shutdown();
            System.out.println("engine destroyed......");
        }
    }
    
}
