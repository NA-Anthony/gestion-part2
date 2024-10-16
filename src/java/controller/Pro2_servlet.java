/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employe;
import model.Poste;
import model.Projet;

/**
 *
 * @author onian
 */
@WebServlet(name = "Pro2_servlet", urlPatterns = {"/Pro2_servlet"})
public class Pro2_servlet extends HttpServlet {

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
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete")) {
                try {
                    Projet.delete(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("/mini-projet/Projet_servlet");
                } catch (Exception ex) {
                }
            }
            if (request.getParameter("action").equals("update")) {
                request.setAttribute("id", request.getParameter("id"));
                try {
                } catch (Exception ex) {
                    
                }
            }
        }
        try {
            Employe[] v_emp = Employe.getAll();
            
            request.setAttribute("v_emp", v_emp);
            
            request.getRequestDispatcher("pages/new_projet.jsp").forward(request, response);
        } catch (Exception e) {
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
            String nom = request.getParameter("nom");
            String description = request.getParameter("description");
          
            int poste = Integer.parseInt(request.getParameter("employe"));
            Projet[] res = Projet.search(nom, description, request.getParameter("date"), poste);
            Employe[] v_employe = Employe.getAll();
            request.setAttribute("v_employe", v_employe);
            
            request.setAttribute("v_projet", res);

            request.getRequestDispatcher("pages/voir_projet.jsp").forward(request, response);
        } catch (Exception e) {
            // Affichage de l'erreur dans la console
            e.printStackTrace();

            // Stockage du message d'erreur dans un attribut de la requête
            request.setAttribute("erreurMessage", e.getMessage());

            // Redirection vers la page d'origine avec un paramètre d'erreur
            response.sendRedirect("/mini-projet/index.jsp?erreur="+e.getMessage());
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
