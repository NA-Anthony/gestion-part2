/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "Projet_servlet", urlPatterns = {"/Projet_servlet"})
public class Projet_servlet extends HttpServlet {

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
            Projet[] v_projet = Projet.getAll();
            Employe[] v_employe = Employe.getAll();
            request.setAttribute("v_projet", v_projet);
            request.setAttribute("v_employe", v_employe);
            
            request.getRequestDispatcher("pages/voir_projet.jsp").forward(request, response);
            
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
            Employe emp = new Employe();
            emp.getById(Integer.parseInt(request.getParameter("idChef")));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = dateFormat.parse(request.getParameter("debut"));
            Timestamp timestampDebut = new Timestamp(dateDebut.getTime());
            Date dateFin = dateFormat.parse(request.getParameter("fin"));
            Timestamp timestampFin = new Timestamp(dateFin.getTime());
            Projet pro = new Projet(0, request.getParameter("nom"), request.getParameter("description"), timestampDebut, timestampFin, emp);
            
            if (request.getParameter("id") != null) {
                pro.setId(Integer.parseInt(request.getParameter("id")));
                pro.update();
            }
            else{
                pro.save();
            }
            
            response.sendRedirect("/mini-projet/Projet_servlet");
            
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
