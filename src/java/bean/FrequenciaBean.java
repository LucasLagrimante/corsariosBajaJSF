/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.BD;
import dao.FrequenciaDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import model.Frequencia;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBean
@SessionScoped
public class FrequenciaBean extends CrudBean<Frequencia, FrequenciaDAO> {

    private FrequenciaDAO frequenciaDAO;

    @Override
    public FrequenciaDAO getDao() {
        if (frequenciaDAO == null) {
            frequenciaDAO = new FrequenciaDAO();
        }
        return frequenciaDAO;
    }

    /**
     *
     * @return
     */
    @Override
    public Frequencia criarNovaEntidade() {
        return new Frequencia();
    }

    public void imprimeRelatorio() throws IOException, SQLException, ClassNotFoundException {
        Connection conexao = null;
        conexao = BD.getConexao();

        URL arquivo = getClass().getResource("/reports/reportFrequencia.jasper");

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        HashMap map = new HashMap();
        map.put("COD", 216);
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conexao);
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setHeader("Content-Disposition", "attachment; filename=PRPG.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();
        } catch (JRException erro) {
            JOptionPane.showMessageDialog(null, "erro " + erro);
        }
    }
}
