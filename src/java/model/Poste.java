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
public class Poste {
    private int id;
    private String titre;
    private double salaire;
    private Departement departement;

    public static Poste[] search(String titre, String salaireMinStr, String salaireMaxStr, int idDepartement) throws SQLException {
        List<Poste> postes = new ArrayList<>();
        Connection con = null;
        try {
            con = Connect.getPostgresConnection();
        } catch (Exception e) {
            // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
        }

        // Requête SQL de base
        String query = "SELECT * FROM Postes WHERE 1 = 1";

        // Ajout des critères à la requête en fonction des paramètres non vides
        if (titre != null && !titre.isEmpty()) {
            query += " AND (Titre LIKE ?)";
        }

        double salmin = 0;
        if (salaireMinStr != null && !salaireMinStr.isEmpty()) {
            salmin = Double.parseDouble(salaireMinStr);
            query += " AND salaire >= ?";
        }

        double salmax = 0;
        if (salaireMaxStr != null && !salaireMaxStr.isEmpty()) {
            salmax = Double.parseDouble(salaireMaxStr);
            query += " AND salaire <= ?";
        }
        
        if (idDepartement != 0) {
            query += " AND idDepartement = ?";
        }

        try (PreparedStatement statement = con.prepareStatement(query)) {
            int parameterIndex = 1;

            if (titre != null && !titre.isEmpty()) {
                statement.setString(parameterIndex++, "%" + titre + "%");
            }

            if (salaireMinStr != null && !salaireMinStr.isEmpty()) {
                statement.setDouble(parameterIndex++, salmin);
            }

            if (salaireMaxStr != null && !salaireMaxStr.isEmpty()) {
                statement.setDouble(parameterIndex++, salmax);
            }

            if (idDepartement != 0) {
                statement.setInt(parameterIndex++, idDepartement);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Poste pos = new Poste();
                    try {
                        pos.getById(resultSet.getInt("ID"));
                    } catch (Exception e) {
                        // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
                    }
                    postes.add(pos);
                }
            }
        }

        return postes.toArray(new Poste[postes.size()]);
    }
    public Poste() {
    }

    public Poste(int id, String titre, double salaire, Departement departement) {
        this.id = id;
        this.titre = titre;
        this.salaire = salaire;
        this.departement = departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
    
    public void getById(int id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT * FROM Postes WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                this.setId(id);
                String titre = rs.getString("titre");
                this.setTitre(titre);
                double salaire = rs.getDouble("salaire");
                this.setSalaire(salaire);
                Departement dep = new Departement();
                dep.getById(rs.getInt("idDepartement"));
                this.setDepartement(dep);
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
    
    public static Poste[] getAll() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Poste> list = new Vector<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT * FROM Postes order by idDepartement";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Poste poste = new Poste();
                poste.setId(rs.getInt("id"));
                poste.setTitre(rs.getString("titre"));
                poste.setSalaire(rs.getDouble("salaire"));
                Departement dep = new Departement();
                dep.getById(rs.getInt("idDepartement"));
                poste.setDepartement(dep);
                list.add(poste);
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
            Poste[] rep = list.toArray(new Poste[list.size()]);
            return rep;
        }
    }
    
    public void update() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "UPDATE Postes SET titre = ?, salaire = ?, idDepartement = ? WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, this.titre);
            ps.setDouble(2, this.salaire);
            ps.setInt(3, this.departement.getId());
            ps.setInt(4, this.id);
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
            String query = "DELETE FROM Postes WHERE id = ?";
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

            String insertQuery = "INSERT INTO Postes (titre, salaire, idDepartement) VALUES (?, ?, ?)";
            ps = con.prepareStatement(insertQuery);
            ps.setString(1, this.titre);
            ps.setDouble(2, this.salaire);
            ps.setInt(3, this.departement.getId());
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
