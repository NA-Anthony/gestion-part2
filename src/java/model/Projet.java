 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author onian
 */
public class Projet {
    private int id;
    private String nom;
    private String description;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private Employe chef;

    public static Projet[] search(String nom, String description, String date, int idChef) throws SQLException {
        List<Projet> projets = new ArrayList<>();
        Connection con = null;
        try {
            con = Connect.getPostgresConnection();
        } catch (Exception e) {
            // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
        }

        // Requête SQL de base
        String query = "SELECT * FROM projets WHERE 1 = 1";

        // Ajout des critères à la requête en fonction des paramètres non vides
        if (nom != null && !nom.isEmpty()) {
            query += " AND (Nom LIKE ?)";
        }
        if (description != null && !description.isEmpty()) {
            query += " AND (description LIKE ?)";
        }
        
        if (date != null && !date.isEmpty()) {
            query += " AND ? between date_de_debut AND date_de_fin";
        }
        if (idChef != 0) {
            query += " AND idChef = ?";
        }

        try (PreparedStatement statement = con.prepareStatement(query)) {
            int parameterIndex = 1;

            if (nom != null && !nom.isEmpty()) {
                statement.setString(parameterIndex++, "%" + nom + "%");
            }

            if (description != null && !description.isEmpty()) {
                statement.setString(parameterIndex++, "%" + description + "%");
            }
            
            if (date != null && !date.isEmpty()) {
                statement.setTimestamp(parameterIndex++, Timestamp.valueOf(date + " 00:00:00"));
            }
            if (idChef != 0) {
                statement.setInt(parameterIndex++, idChef);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Projet projet = new Projet();
                    try {
                        projet.getById(resultSet.getInt("ID"));    
                    } catch (Exception e) {
                        // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
                    }
                    projets.add(projet);
                }
            }
        }

        return projets.toArray(new Projet[projets.size()]);
    }
    
    public Projet() {
    }

    public Projet(int id, String nom, String description, Timestamp dateDebut, Timestamp dateFin, Employe chef) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chef = chef;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }
    
    public void getById(int id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT * FROM Projets WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                this.setId(id);
                String nom = rs.getString("nom");
                this.setNom(nom);
                String description = rs.getString("description");
                this.setDescription(description);
                Timestamp dateDebut = rs.getTimestamp("date_de_debut");
                this.setDateDebut(dateDebut);
                Timestamp dateFin = rs.getTimestamp("date_de_fin");
                this.setDateFin(dateFin);
                Employe e = new Employe();
                e.getById(rs.getInt("idChef"));
                this.setChef(e);
            }
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
                throw e;
            }
        } finally {
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
        }
    }
    
    public static Projet[] getAll() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Projet> list = new Vector<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT * FROM Projets";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Projet projet = new Projet();
                projet.setId(rs.getInt("id"));
                projet.setNom(rs.getString("nom"));
                projet.setDescription(rs.getString("description"));
                projet.setDateDebut(rs.getTimestamp("date_de_debut"));
                projet.setDateFin(rs.getTimestamp("date_de_fin"));
                Employe e = new Employe();
                e.getById(rs.getInt("idChef"));
                projet.setChef(e);
                list.add(projet);
            }
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
                throw e;
            }
        } finally {
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
            Projet[] rep = list.toArray(new Projet[list.size()]);
            return rep;
        }
    }
    
    public void update() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "UPDATE Projets SET nom = ?, description = ?, date_de_debut = ?, date_de_fin = ?, idChef = ? WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, this.nom);
            ps.setString(2, this.description);
            ps.setTimestamp(3, this.dateDebut);
            ps.setTimestamp(4, this.dateFin);
            ps.setInt(5, this.chef.getId());
            ps.setInt(6, this.id);
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
    
    public static void delete(int id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "DELETE FROM Projets WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
    
     public void save() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);

            String insertQuery = "INSERT INTO Projets (nom, description, date_de_debut, date_de_fin, idChef) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(insertQuery);
            ps.setString(1, this.nom);
            ps.setString(2, this.description);
            ps.setTimestamp(3, this.dateDebut);
            ps.setTimestamp(4, this.dateFin);
            ps.setInt(5, this.chef.getId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
            throw e;
        } finally {
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
        }
    }
}
