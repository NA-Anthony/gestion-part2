<%-- 
    Document   : new_demande
    Created on : 16 oct. 2024, 04:02:47
    Author     : onian
--%>
<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>
    <%
        Produit[] produits = Produit.getAll();
    %>
            
<div class="page-content">

    <div class="card-body">
        <h2>Insérer une demande:</h2>
        <form class="forms-sample" action="/mini-projet/Demande_achat_servlet" method="post">
             <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Choisissez le Produit</label>
                <div class="col-sm-9">
                <select class="form-select" name="id_produit">
                    <%
                        for (int i = 0; i < produits.length; i++) {
                            if (request.getParameter("id") != null && produits[i].getId_produit()==Integer.parseInt(request.getParameter("id")))
                            {
                               %>
                               <option selected="" value="<%= produits[i].getId_produit()%>"><%= produits[i].getNom()%></option>
                               <%
                            }
                            else{
                                %>
                                <option value="<%= produits[i].getId_produit()%>"><%= produits[i].getNom()%></option>
                                <%
                            }
                        }
                        %>
                </select>
                <a href="/mini-projet/pages/new_produit.jsp">Produit inexistant?</a>
                </div>
            </div>
            
            <div class="row mb-3">
                <label for="quantite" class="col-sm-3 col-form-label">Quantité</label>
                <div class="col-sm-9">
                    <input type="number" class="form-control" id="quantite" name="quantite" placeholder="Entrez la quantité du produit nécessaire">
                </div>
            </div>
                     
            <hr>
            <button type="submit" class="btn btn-primary me-2">Valider</button>
            <button type="button" class="btn btn-secondary">Annuler</button>
        </form>


    </div>
</div>
<%@ include file = "../includes/footer.jsp" %>
