/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author onian
 */
public class Connect {
    public static Connection getPostgresConnection() throws Exception
    {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/gestion2";
        String username = "postgres";
        String password = "system";

        Class.forName("org.postgresql.Driver");

        // Établit la connexion avec les informations de connexion.
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        return connection;
    }
    
    public static int checkAdmin(String email, String mdp) throws Exception
    {
        int val = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "select * from admin where email = ? and mdp = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, mdp);
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                val = rs.getInt("id");
            }
        
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
                throw e;
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
            return val;
        }
    }
    
    public static Admin getAdmin(int id) throws Exception
    {
        Admin val = new Admin();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "select * from admin where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                val.setId(id);
                val.setNom(rs.getString("nom"));
                val.setEmail(rs.getString("email"));
                val.setMdp(rs.getString("mdp"));
            }
        
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
                throw e;
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
            return val;
        }
    }
    
    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return ""; // ou null si vous préférez
        }
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    
    public static String formatDateOnly(Timestamp timestamp) {
        if (timestamp == null) {
            return ""; // ou null si vous préférez
        }
        // Convertir le Timestamp en un objet Date
        Date date = new Date(timestamp.getTime());
        // Formater la date sans l'heure
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
