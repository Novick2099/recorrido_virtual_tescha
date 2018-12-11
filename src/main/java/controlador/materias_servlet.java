/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.MateriaDAO;
import entidades.Materia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MarzoNegro
 */
public class materias_servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    MateriaDAO obj_materiaDAO = new MateriaDAO();

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            switch(request.getParameter("action")){
                case "cargar_registros":
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin_materias.jsp");
                    request.setAttribute("lista_materia",obj_materiaDAO.get_lista_materia());
                    dispatcher.forward(request,response);
                    break;
                case "cargar_unico_registro":
                    
                    break;
                case "guardar_registro":
                    String clave_materia=request.getParameter("clave_materia").toString();
                    String nombre_materia=request.getParameter("nombre_materia").toString();
                    int creditos=Integer.parseInt(request.getParameter("creditos").toString());
                    int resultado=0;
                    
                    Materia obj_materia = new Materia();
                    obj_materia.setClaveMateria(clave_materia);
                    obj_materia.setNombreMateria(nombre_materia);
                    obj_materia.setCreditos(creditos);
                    
                    out.println("Clave : "+obj_materia.getClaveMateria());
                    out.println("Nombre : "+obj_materia.getNombreMateria());
                    out.println("Creditos : "+obj_materia.getCreditos());
                    resultado=obj_materiaDAO.registrar_materia(obj_materia);
                    out.println(resultado);
                    
                    break;
                case "actualizar_registro":
                    break;
                case "eliminar_registro":
                    break;
                    
                
            }
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
