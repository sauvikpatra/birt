/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.roitech.birt.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ROITech-002
 */
public class BirtEngine {

    Logger LOGGER = LoggerFactory.getLogger(BirtEngine.class);
    private EngineConfig config = null;
    private IReportEngine engine = null;
    private static BirtEngine INSTANCE;
    private String server = System.getProperty("catalina.base");

    private BirtEngine() {
        try {
            config = new EngineConfig();
            LOGGER.info("engine configuration......");

            LOGGER.info("tomcat home......" + server);
            //config.setEngineHome(server + GlobalConstant.CATALINA_WEBAPPS
            //        + ErfpConstants.BIRT_HOME);
            config.setEngineHome(server + "//lib//Birt");

            LOGGER.info("startup platform......");

            Platform.startup(config);
            IReportEngineFactory factory = (IReportEngineFactory) Platform
                    .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            engine = factory.createReportEngine(config);
            // engine.changeLogLevel( Level.WARNING );
        } catch (BirtException e) {
            // TODO Auto-generated catch block
            LOGGER.error("Error in Birt Initialization.", e);
        }
    }

    /**
     * Set the parameter into the birt report.
     *
     * @param engine
     * @param design
     * @param task
     * @param paramValueList
     */
    protected void setParameter(IReportEngine engine, IReportRunnable design,
            IRunAndRenderTask task, List paramValueList) {
        IGetParameterDefinitionTask paramDef = engine
                .createGetParameterDefinitionTask(design);

        Collection params = paramDef.getParameterDefns(true);
        Iterator iter = params.iterator();
        int paramCount = 0;
        // Iterate over all parameters
        while (iter.hasNext()) {
            IParameterDefnBase param = (IParameterDefnBase) iter.next();
            IScalarParameterDefn scalar = (IScalarParameterDefn) param;
            LOGGER.info("Param name = " + param.getName());
            // Parameter is a Text Box
            if (scalar.getControlType() == IScalarParameterDefn.TEXT_BOX) {
                LOGGER.info("Setting parameter value = "
                        + paramValueList.get(paramCount));
                // task.setParameterValue(param.getName(), "3");
                task.setParameterValue(param.getName(),
                        paramValueList.get(paramCount++));
            }
        }
    }

    public static synchronized BirtEngine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BirtEngine();
        }
        if (INSTANCE.getEngine() == null) {
            INSTANCE = new BirtEngine();
        }
        if (INSTANCE.getConfig() == null) {
            INSTANCE = new BirtEngine();
        }
        return INSTANCE;
    }

    /**
     * @return the config
     */
    public EngineConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(EngineConfig config) {
        this.config = config;
    }

    /**
     * @return the engine
     */
    public IReportEngine getEngine() {
        return engine;
    }

    /**
     * @param engine the engine to set
     */
    public void setEngine(IReportEngine engine) {
        this.engine = engine;
    }
}
