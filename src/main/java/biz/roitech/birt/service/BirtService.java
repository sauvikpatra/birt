/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.roitech.birt.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROITech-002
 */
public interface BirtService {

    public byte[] generatePDF(String id, String birtFileName);

    public byte[] generateHTML(String id, String rgrptd, String rghtml, HttpServletRequest req, int univId);
}
