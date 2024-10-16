/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Connect;
import model.Departement;

/**
 *
 * @author onian
 */
@WebServlet(name = "Departement_servlet", urlPatterns = {"/Departement_servlet"})
public class Departement_servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        try {
            Departement[] v_departement = Departement.getAll();
            List<Map<String, Object>> stat = Departement.getStat();
            
            request.setAttribute("v_departement", v_departement);
            request.setAttribute("v_stat", stat);
            
            request.getRequestDispatcher("pages/voir_departement.jsp").forward(request, response);
            
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("error : "+e.getMessage());
        }
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
        try {
            Departement dep = new Departement(0, request.getParameter("nom"));
            
            if (request.getParameter("id") != null) {
                dep.setId(Integer.parseInt(request.getParameter("id")));
                dep.update();
            }
            else{
                dep.save();
            }
            
            response.sendRedirect("/mini-projet/Departement_servlet");
            
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            
            out.print("error : "+e.getMessage());
        }
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
