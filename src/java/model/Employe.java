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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author onian
 */
public class Employe {
    private int id;
    private String nom;
    private String prenom;
    private Timestamp naissance;
    private Timestamp embauche;
    private Poste poste;

    public static Employe[] search(String nomPrenom, int ageMin, int ageMax, String dateEmbaucheMin, String dateEmbaucheMax, int idPoste) throws SQLException {
    List<Employe> employes = new ArrayList<>();
    Connection con = null;
    try {
        con = Connect.getPostgresConnection();
    } catch (Exception e) {
        // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
    }
    
    // Requête SQL de base
    String query = "SELECT * FROM Employes WHERE 1 = 1";
    
    // Ajout des critères à la requête en fonction des paramètres non vides
    if (nomPrenom != null && !nomPrenom.isEmpty()) {
        query += " AND (Nom LIKE ? OR Prenom LIKE ?)";
    }
    
    if (ageMin > 0) {
        query += " AND EXTRACT(YEAR FROM age(Date_de_naissance)) >= ?";
    }
    
    if (ageMax > 0) {
        query += " AND EXTRACT(YEAR FROM age(Date_de_naissance)) <= ?";
    }
    
    if (dateEmbaucheMin != null && !dateEmbaucheMin.isEmpty()) {
        query += " AND Date_d_embauche >= ?";
    }
    
    if (dateEmbaucheMax != null && !dateEmbaucheMax.isEmpty()) {
        query += " AND Date_d_embauche <= ?";
    }
    if (idPoste != 0) {
        query += " AND idPoste = ?";
    }
    
    try (PreparedStatement statement = con.prepareStatement(query)) {
        int parameterIndex = 1;
        
        if (nomPrenom != null && !nomPrenom.isEmpty()) {
            statement.setString(parameterIndex++, "%" + nomPrenom + "%");
            statement.setString(parameterIndex++, "%" + nomPrenom + "%");
        }
        
        if (ageMin > 0) {
            statement.setInt(parameterIndex++, ageMin);
        }
        
        if (ageMax > 0) {
            statement.setInt(parameterIndex++, ageMax);
        }
        
        if (dateEmbaucheMin != null && !dateEmbaucheMin.isEmpty()) {
            statement.setTimestamp(parameterIndex++, Timestamp.valueOf(dateEmbaucheMin + " 00:00:00"));
        }
        
        if (dateEmbaucheMax != null && !dateEmbaucheMax.isEmpty()) {
            statement.setTimestamp(parameterIndex++, Timestamp.valueOf(dateEmbaucheMax + " 00:00:00"));
        }
        
        if (idPoste != 0) {
            statement.setInt(parameterIndex++, idPoste);
        }

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Employe employe = new Employe();
                try {
                    employe.getById(resultSet.getInt("ID"));    
                } catch (Exception e) {
                    // Gérer l'exception (imprimer ou enregistrer les logs, etc.)
                }
                employes.add(employe);
            }
        }
    }
    
    return employes.toArray(new Employe[employes.size()]);
}

    
    public Employe() {
    }

    public Employe(int id, String nom, String prenom, Timestamp naissance, Timestamp embauche, Poste poste) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.naissance = naissance;
        this.embauche = embauche;
        this.poste = poste;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Timestamp getNaissance() {
        return naissance;
    }

    public void setNaissance(Timestamp naissance) {
        this.naissance = naissance;
    }

    public Timestamp getEmbauche() {
        return embauche;
    }

    public void setEmbauche(Timestamp embauche) {
        this.embauche = embauche;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }
    
    public void getById(int id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT * FROM Employes WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                this.setId(id);
                String nom = rs.getString("nom");
                this.setNom(nom);
                String prenom = rs.getString("prenom");
                this.setPrenom(prenom);
                Timestamp naissance = rs.getTimestamp("date_de_naissance");
                this.setNaissance(naissance);
                Timestamp embauche = rs.getTimestamp("date_d_embauche");
                this.setEmbauche(embauche);
                Poste p = new Poste();
                p.getById(rs.getInt("idPoste"));
                this.setPoste(p);
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
    
    public static Employe[] getAll() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Employe> list = new Vector<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT * FROM Employes order by idposte";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Employe employe = new Employe();
                employe.setId(rs.getInt("id"));
                employe.setNom(rs.getString("nom"));
                employe.setPrenom(rs.getString("prenom"));
                employe.setNaissance(rs.getTimestamp("date_de_naissance"));
                employe.setEmbauche(rs.getTimestamp("date_d_embauche"));
                Poste p = new Poste();
                p.getById(rs.getInt("idPoste"));
                employe.setPoste(p);
                list.add(employe);
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
            Employe[] rep = list.toArray(new Employe[list.size()]);
            return rep;
        }
    }
    
    public void update() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "UPDATE Employes SET nom = ?, prenom = ?, date_de_naissance = ?, date_d_embauche = ?, idPoste = ? WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, this.nom);
            ps.setString(2, this.prenom);
            ps.setTimestamp(3, this.naissance);
            ps.setTimestamp(4, this.embauche);
            ps.setInt(5, this.poste.getId());
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
            String query = "DELETE FROM Employes WHERE id = ?";
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

            String insertQuery = "INSERT INTO Employes (nom, prenom, date_de_naissance, date_d_embauche, idPoste) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(insertQuery);
            ps.setString(1, this.nom);
            ps.setString(2, this.prenom);
            ps.setTimestamp(3, this.naissance);
            ps.setTimestamp(4, this.embauche);
            ps.setInt(5, this.poste.getId());
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
    
    public static List<Map<String, Object>> getEntryStat() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = Connect.getPostgresConnection();
            con.setAutoCommit(false);
            String query = "SELECT \n" +
"    EXTRACT(YEAR FROM Date_d_embauche) AS Annee,\n" +
"    COUNT(ID) AS Nombre_Employes\n" +
"FROM \n" +
"    Employes\n" +
"GROUP BY \n" +
"    EXTRACT(YEAR FROM Date_d_embauche)\n" +
"ORDER BY \n" +
"    Annee;";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery();
            // Parcourir les résultats
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Annee", rs.getInt("Annee"));
                row.put("Nb_Employes", rs.getInt("nombre_employes"));
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
