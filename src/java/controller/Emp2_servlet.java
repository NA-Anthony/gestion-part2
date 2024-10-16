/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "Emp2_servlet", urlPatterns = {"/Emp2_servlet"})
public class Emp2_servlet extends HttpServlet {

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
                    Employe.delete(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("/mini-projet/Employe_servlet");
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
            Poste[] v_pos = Poste.getAll();
            
            request.setAttribute("v_pos", v_pos);
            
            request.getRequestDispatcher("pages/new_employe.jsp").forward(request, response);
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
            List<Map<String, Object>> stat = Employe.getEntryStat();
            request.setAttribute("v_stat", stat);
            String nomPrenom = request.getParameter("nom");

            String embauchemin = request.getParameter("embauchemin");
            String embauchemax = request.getParameter("embauchemax");

            int ageMin = 0;
            int ageMax = 120;
            if(request.getParameter("agemin")!=null && !request.getParameter("agemin").isEmpty())
            {
                ageMin = Integer.parseInt(request.getParameter("agemin"));
            }
            if(request.getParameter("agemax")!=null && !request.getParameter("agemax").isEmpty())
            {
                ageMax = Integer.parseInt(request.getParameter("agemax"));
            }
            int poste = Integer.parseInt(request.getParameter("poste"));
            Employe[] res = Employe.search(nomPrenom, ageMin, ageMax, embauchemin, embauchemax, poste);
            request.setAttribute("v_employe", res);
            
            Poste[] v_poste = Poste.getAll();
            request.setAttribute("v_poste", v_poste);

            request.getRequestDispatcher("pages/voir_employe.jsp").forward(request, response);
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
