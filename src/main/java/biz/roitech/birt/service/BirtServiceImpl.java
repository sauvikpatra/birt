/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.roitech.birt.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

/**
 *
 * @author ROITech-002
 */
public class BirtServiceImpl implements BirtService {

    Logger LOGGER = LoggerFactory.getLogger(BirtServiceImpl.class);
    private String server = System.getProperty("catalina.base");

    @Override
    public byte[] generatePDF(String id,String birtFileName)
    {
        byte[] fileContent = null;
        ByteArrayOutputStream bytArrOutStr = new ByteArrayOutputStream();
        try {

            // Run reports, etc.
            LOGGER.info("Open the report......");
            IReportRunnable design = null;
            // Open the report design
            //design = BirtEngine.getInstance().getEngine().openReportDesign(server + "\\ERFP\\" + univId + "\\rpt\\" + rgrptd);
            //design = BirtEngine.getInstance().getEngine().openReportDesign(server + "\\webapps\\birt-viewer\\report\\" + "ERFP\\" + univId + "\\rpt\\" + rgrptd);
            design = BirtEngine.getInstance().getEngine().openReportDesign(server + "\\webapps\\birt-viewer\\report\\" + "logiksms\\" + birtFileName);
            IRunAndRenderTask task = BirtEngine.getInstance().getEngine().createRunAndRenderTask(design);

            LOGGER.info("generate PDF report......");
            PDFRenderOption options = new PDFRenderOption();

            /* //options.setOutputFileName(server + "\\lib\\Birt\\temp\\" + fileName);
             File f = new File("c:\\Birt\\temp\\");
             if(!f.isDirectory()){
             f.mkdir();
             }
             options.setOutputFileName("c:\\Birt\\temp\\"+id+"_"+univId+"_"+fileName); */
            
            options.setOutputFormat("pdf");
            options.setOutputStream(bytArrOutStr);
            // Create the parameter list
            List paramValueList = new ArrayList();
            paramValueList.add(Integer.parseInt(id));
            //paramValueList.add(id);
           
            BirtEngine.getInstance().setParameter(BirtEngine.getInstance().getEngine(), design, task, paramValueList);

            LOGGER.info("close the task......");
            task.setRenderOption(options);
            task.run();
            task.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Error in generatePDF.", ex);
        }
         fileContent = bytArrOutStr.toByteArray();
         
        return fileContent;
    }

    @Override
    public byte[] generateHTML(String id, String rgrptd, String rghtml, HttpServletRequest req, int univId) {
        ByteArrayOutputStream oStream = null;
        try {

            // Run reports, etc.
            LOGGER.info("Open the report......");
            IReportRunnable design = null;
            // Open the report design

            design = BirtEngine.getInstance().getEngine().openReportDesign(server + "\\webapps\\birt-viewer\\report\\" + "ERFP\\" + univId + "\\rpt\\" + rgrptd);
            IRunAndRenderTask task = BirtEngine.getInstance().getEngine().createRunAndRenderTask(design);

            LOGGER.info("generate HTML report......");

            //set output options
            HTMLRenderOption options = new HTMLRenderOption();
            options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
            oStream = new ByteArrayOutputStream();
            options.setOutputStream(oStream);
            options.setImageHandler(new HTMLServerImageHandler());
            //options.setBaseImageURL(server + "\\ERFP\\" + univId + "\\rpt\\" + "\\images");
            //options.setImageDirectory(server + "\\ERFP\\" + univId + "\\rpt\\" + "\\images");
            options.setBaseImageURL(req.getContextPath() + "/images");
            options.setImageDirectory(req.getSession().getServletContext().getRealPath("/images"));

            // Create the parameter list
            List paramValueList = new ArrayList();
            paramValueList.add(Integer.parseInt(String.valueOf(id)));
            BirtEngine.getInstance().setParameter(BirtEngine.getInstance().getEngine(), design, task, paramValueList);

            LOGGER.info("close the task......");
            task.setRenderOption(options);
            task.run();
            task.close();

        } catch (Exception ex) {
            LOGGER.error("Error in generateHTML.", ex);
        }
        return oStream.toByteArray();
    }
}
