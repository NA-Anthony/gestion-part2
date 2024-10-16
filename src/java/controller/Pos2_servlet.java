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
import model.Departement;
import model.Employe;
import model.Poste;

/**
 *
 * @author onian
 */
@WebServlet(name = "Pos2_servlet", urlPatterns = {"/Pos2_servlet"})
public class Pos2_servlet extends HttpServlet {

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
                    Poste.delete(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("/mini-projet/Poste_servlet");
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
            Departement[] v_dep = Departement.getAll();
            
            request.setAttribute("v_dep", v_dep);
            
            request.getRequestDispatcher("pages/new_poste.jsp").forward(request, response);
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
            String titre = request.getParameter("titre");
            
            int departement = Integer.parseInt(request.getParameter("departement"));
            Poste[] res = Poste.search(titre, request.getParameter("salmin"), request.getParameter("salmax"), departement);
            request.setAttribute("v_poste", res);
            
            Departement[] v_departement = Departement.getAll();
            request.setAttribute("v_departement", v_departement);

            request.getRequestDispatcher("pages/voir_poste.jsp").forward(request, response);
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
