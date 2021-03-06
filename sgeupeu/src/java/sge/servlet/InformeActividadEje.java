/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sge.modelo.Eap;
import sge.service.IndicadorService;
import sge.service.ReporteService;

/**
 *
 * @author Intel
 */
@WebServlet(name = "InformeActividadEje", urlPatterns = {"/InformeActividadEje"})
public class InformeActividadEje extends HttpServlet {

   private static String INDEXCOORDINADOREAPINFORME= "apps/app_informesactividad/indexEje.jsp";
   private static String INDEXCOORDINADOREAPMAIN= "apps/app_informesactividad/main_eje.jsp";
   private static String INDEXCOORDINADOREAPREPOR1= "apps/app_informesactividad/reportGeneral.jsp";

   
   
    public int idTipoAreaPri=0;
    public String idCategoriaUsuarioPri="0";
    public int idPerspectivaPri=0;
    public String idFilialPri="0";
    public int idEjeEstrategicoPri=0;
    public String idUsuarioPri="0";
    
    public void principalValorSession(HttpServletRequest r){
    sge.modelo.Usuario usuSess=(sge.modelo.Usuario)r.getSession().getAttribute("listaPerfilUsuario");
    idTipoAreaPri=usuSess.getIdtipoarea();
    idCategoriaUsuarioPri=usuSess.getIdcategoriausuario();
    idPerspectivaPri=usuSess.getIdperspectiva();
    idFilialPri=usuSess.getIdfilial();
    idEjeEstrategicoPri=usuSess.getIdejeestrategico();
    idUsuarioPri=usuSess.getIdusuario();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int opt = Integer.parseInt(request.getParameter("opt")==null ? "0": request.getParameter("opt"));
        IndicadorService is;
        ReporteService rs;
        try {
            switch (opt) {
                case 1: {
                response.sendRedirect(INDEXCOORDINADOREAPINFORME);
                }break;
                case 2: {    
 
                sge.modelo.Usuario w = null; w = (sge.modelo.Usuario) request.getSession().getAttribute("listaPerfilUsuario");
                int idEjeEstrategicoX=w.getIdejeestrategico(); 
                is=new IndicadorService();
                request.getSession().setAttribute("listar_eje", is.listaEjeIndividual(idEjeEstrategicoX));                    
                is=new IndicadorService();
                request.getSession().setAttribute("listar_periodo_meta", is.listaPeriodoMeta(request));  

                
                response.sendRedirect(INDEXCOORDINADOREAPMAIN);
                }break;
                case 3: {
                String periodo="";
                int mes1=0;
                int mes2=0;
                int eje=0;
                eje=Integer.parseInt(request.getParameter("ideje1")==null?"0":request.getParameter("ideje1") );
                mes1=Integer.parseInt(request.getParameter("mesinicio1")==null?"0":request.getParameter("mesinicio1") );
                mes2=Integer.parseInt(request.getParameter("mesfin1")==null?"0":request.getParameter("mesfin1") );
                Eap eU=(Eap)request.getSession().getAttribute("eapUsuario");
                int ideapfacultad=eU.getIdeapfacultad();               
                rs=new ReporteService();
                ArrayList lista=rs.cabeceraPOA(ideapfacultad);
                request.getSession().setAttribute("cabeceraInformePOA", lista);
                 
                rs=new ReporteService();                
                ArrayList lista2=rs.ejesSeleccionadosPOA(ideapfacultad, eje);
                request.getSession().setAttribute("ejeSeleccionadosPOA", lista2); 
                

                
                response.sendRedirect(INDEXCOORDINADOREAPREPOR1+"?mes1="+mes1+"&mes2="+mes2);
                }break;

                    
                default:{
                    System.out.println("Error...El valor es: "+opt);
                }
            }
        } catch (Exception e) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
