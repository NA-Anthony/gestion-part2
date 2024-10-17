/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author onian
 */
public class Produit {
    private int id_produit;
    private String nom;
    private int id_stock;

    public Produit() {
    }

    public Produit(int id_produit, String nom) {
        this.id_produit = id_produit;
        this.nom = nom;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom() {
        return nom;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public static Produit[] getAll() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Produit> list = new Vector<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "select * from Produit";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Produit p = new Produit();
                p.setId_produit(rs.getInt("id_produit"));
                p.setNom(rs.getString("nom"));
                p.id_stock = rs.getInt("id_stock");
                list.add(p);
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
            Produit[] rep = list.toArray(new Produit[list.size()]);
            return rep;
        }
    }
    
    public void update() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "UPDATE Produit SET nom = ?, id_stock = ? WHERE id_produit = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, this.getNom());
            ps.setInt(2, this.getId_stock());
            ps.setInt(3, this.getId_produit());
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
            String query = "DELETE FROM produit WHERE id_produit = ?";
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
    
    public void save(User u) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Connexion à la base de données
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);

            // Étape 1: Insérer un nouveau stock avec quantite et prix_unitaire à 0
            String insertStockQuery = "INSERT INTO stock (prix_unitaire, quantite, id_societe) VALUES (?, ?, ?)";
            ps = con.prepareStatement(insertStockQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, new BigDecimal("0.00")); // prix_unitaire = 0
            ps.setBigDecimal(2, new BigDecimal("0.00")); // quantite = 0
            ps.setInt(3, u.getIdSociete()); // id_societe de la société (tu devras ajouter un getter pour ça)
            ps.executeUpdate();

            // Récupérer l'ID du stock inséré
            rs = ps.getGeneratedKeys();
            int idStock = 0;
            if (rs.next()) {
                idStock = rs.getInt(1);
            }

            // Étape 2: Insérer un nouveau produit avec l'ID du stock
            String insertProduitQuery = "INSERT INTO produit (nom, id_stock) VALUES (?, ?)";
            ps = con.prepareStatement(insertProduitQuery);
            ps.setString(1, this.getNom()); // nom du produit
            ps.setInt(2, idStock); // id_stock nouvellement créé
            ps.executeUpdate();

            // Validation de la transaction
            con.commit();
        } catch (SQLException e) {
            // En cas d'erreur, rollback
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
            throw e;
        } finally {
            // Fermeture des ressources
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
