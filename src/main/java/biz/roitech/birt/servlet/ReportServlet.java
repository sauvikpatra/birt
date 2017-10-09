/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.roitech.birt.servlet;

import biz.roitech.birt.constants.BirtConstants;
import biz.roitech.birt.service.BirtService;
import biz.roitech.birt.service.BirtServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ROITech-002
 */
//@WebServlet(name = "ReportServlet", urlPatterns = {"/reportServlet"})
public class ReportServlet extends HttpServlet {

    Logger LOGGER = LoggerFactory.getLogger(ReportServlet.class);

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter(BirtConstants.REPORT_TYPE).equals(BirtConstants.REPORT_TYPE_PDF)) {
            String fileName = request.getParameter(BirtConstants.REPORT_FILE);
            //String campId = request.getParameter(BirtConstants.CAMPAIGN_ID);
            //String clientId = request.getParameter(BirtConstants.CLIENT_ID);
            String id = request.getParameter(BirtConstants.ID);
            BirtService birtService = new BirtServiceImpl();
           
            byte[] byteArray =  birtService.generatePDF(id, fileName);
           // byte[] byteArray = FileUtils.readFileToByteArray(new File("c:\\Birt\\temp\\"+id+"_"+clientId+"_"+"challan.pdf"));
            response.getOutputStream().write(byteArray);
        } else if (request.getParameter(BirtConstants.REPORT_TYPE).equals(BirtConstants.REPORT_TYPE_HTML)) {
            String fileName = request.getParameter(BirtConstants.REPORT_FILE);
            String campId = request.getParameter(BirtConstants.CAMPAIGN_ID);
            String clientId = request.getParameter(BirtConstants.CLIENT_ID);
            String id = request.getParameter(BirtConstants.ID);
            BirtService birtService = new BirtServiceImpl();
            byte[] byteArray = birtService.generateHTML(id, fileName, "registration.html", request, Integer.parseInt(clientId));
            response.getOutputStream().write(byteArray);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
