package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private int id;
    private String nom;
    private String email;
    private String mdp;
    private String role;
    private int idSociete;
    private int idDepartement;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public int getIdSociete() {
        return idSociete;
    }
    public void setIdSociete(int idSociete) {
        this.idSociete = idSociete;
    }
    public int getIdDepartement() {
        return idDepartement;
    }
    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }
    public boolean isPdg(String email, String mdp) throws Exception{
        Connection connection=(Connection)Connect.getPostgresConnection();
        try {
            String sql = "SELECT * FROM pdg WHERE email = ? AND mdp = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, mdp);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }               
        
    }
    public boolean isChef(String email, String mdp) throws Exception{
        Connection connection=(Connection)Connect.getPostgresConnection();
        try {
            String sql = "SELECT * FROM chef_departement WHERE email = ? AND mdp = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, mdp);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
        
    }
    public User getUserpdg(String email, String mdp) throws Exception{
        Connection connection=(Connection)Connect.getPostgresConnection();
        User user = new User();
        try {
            String sql = "SELECT pdg.id_pdg,pdg.email,pdg.mdp,pdg.nom,societe.id_societe FROM pdg join societe on pdg.id_pdg=societe.id_pdg  WHERE email = ? AND mdp = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, mdp);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user.setId(rs.getInt("id_pdg"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
                user.setRole("pdg");
                user.setIdSociete(rs.getInt("id_societe"));
                user.setIdDepartement(0);
            }
            
            rs.close();
            pstmt.close();
            connection.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUser(String email, String mdp) throws Exception{
        Connection connection=(Connection)Connect.getPostgresConnection();
        User user = new User();
        try {
            String sql = "SELECT * FROM chef_departement as cd JOIN departement on cd.id_departement=departement.id_departement WHERE email =? AND mdp = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, mdp);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user.setId(rs.getInt("id_chef"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
                user.setRole("Chef");
                user.setIdSociete(rs.getInt("id_societe"));
                user.setIdDepartement(rs.getInt("id_departement"));
            }
            
            rs.close();
            pstmt.close();
            connection.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public User checkLogin(String email,String mdp){
        try {
            if(isPdg(email, mdp)){
                return getUserpdg(email, mdp);
            }
            else if(isChef(email, mdp)){
                return getUser(email, mdp);
            }
            else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
