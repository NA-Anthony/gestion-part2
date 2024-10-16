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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author onian
 */
public class Departement {
    private int id;
    private String nom;

    public static Departement[] search(String nom) throws SQLException {
        List<Departement> departements = new ArrayList<>();
        Connection con = null;
        try {
            con = Connect.getPostgresConnection();
        } catch (Exception e) {
            // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
        }

        // Requête SQL de base
        String query = "SELECT * FROM Departements WHERE 1 = 1";

        // Ajout des critères à la requête en fonction des paramètres non vides
        if (nom != null && !nom.isEmpty()) {
            query += " AND (nom LIKE ?)";
        }

        try (PreparedStatement statement = con.prepareStatement(query)) {
            int parameterIndex = 1;

            if (nom != null && !nom.isEmpty()) {
                statement.setString(parameterIndex++, "%" + nom + "%");
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Departement dep = new Departement();
                    try {
                        dep.getById(resultSet.getInt("ID"));
                    } catch (Exception e) {
                        // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
                    }
                    departements.add(dep);
                }
            }
        }

        return departements.toArray(new Departement[departements.size()]);
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

    public Departement(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Departement() {
    }

    public void getById(int id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "select * from Departements where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                this.setId(id);
                String nom = rs.getString("nom");
                this.setNom(nom);
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
        }
    }
    
    public static Departement[] getAll() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Departement> list = new Vector<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "select * from Departements";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Departement dep = new Departement();
                dep.setId(rs.getInt("id"));
                dep.setNom(rs.getString("nom"));
                list.add(dep);
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
            Departement[] rep = list.toArray(new Departement[list.size()]);
            return rep;
        }
    }
    
    public void update() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "UPDATE Departements SET nom = ? WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, this.nom);
            ps.setInt(2, this.id);
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
            String query = "DELETE FROM Departements WHERE id = ?";
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

            String insertQuery = "INSERT INTO Departements (nom) VALUES (?)";
            ps = con.prepareStatement(insertQuery);
            ps.setString(1, this.nom);
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
    
    public static List<Map<String, Object>> getStat() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT \n" +
"    d.Nom AS Departement,\n" +
"    COUNT(e.ID) AS Nombre_Employes\n" +
"FROM \n" +
"    Departements d\n" +
"LEFT JOIN \n" +
"    Postes p ON d.ID = p.idDepartement\n" +
"LEFT JOIN \n" +
"    Employes e ON p.ID = e.idPoste\n" +
"GROUP BY \n" +
"    d.Nom;";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Departement", rs.getString("Departement"));
                row.put("Nb_Employes", rs.getInt("Nombre_Employes"));
                result.add(row);
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
            return result;
        }
    }
}
