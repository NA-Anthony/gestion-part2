/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "Employe_servlet", urlPatterns = {"/Employe_servlet"})
public class Employe_servlet extends HttpServlet {

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
            Employe[] v_employe = Employe.getAll();
            Poste[] v_poste = Poste.getAll();
            List<Map<String, Object>> stat = Employe.getEntryStat();
            
            request.setAttribute("v_employe", v_employe);
            request.setAttribute("v_poste", v_poste);
            request.setAttribute("v_stat", stat);

            
            request.getRequestDispatcher("pages/voir_employe.jsp").forward(request, response);
            
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
            Poste pos = new Poste();
            pos.getById(Integer.parseInt(request.getParameter("idPoste")));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNaissance = dateFormat.parse(request.getParameter("naissance"));
            Timestamp timestampNaissance = new Timestamp(dateNaissance.getTime());
            Date dateEmbauche = dateFormat.parse(request.getParameter("embauche"));
            Timestamp timestampEmbauche = new Timestamp(dateEmbauche.getTime());
            Employe emp = new Employe(0, request.getParameter("nom"), request.getParameter("prenom"), timestampNaissance, timestampEmbauche, pos);
            
            if (request.getParameter("id") != null) {
                emp.setId(Integer.parseInt(request.getParameter("id")));
                emp.update();
            }
            else{
                emp.save();
            }
            
            response.sendRedirect("/mini-projet/Employe_servlet");
            
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
