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
        <h2>Insérer un produit:</h2>
        <form class="forms-sample" action="/mini-projet/Produit_servlet" method="post">
            
            <div class="row mb-3">
                <label for="nom" class="col-sm-3 col-form-label">Nom du produit</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="nom" name="nom" placeholder="Entrez le nom du produit">
                </div>
            </div>
                     
            <hr>
            <button type="submit" class="btn btn-primary me-2">Insérer</button>
            <button type="button" class="btn btn-secondary">Annuler</button>
        </form>


    </div>
</div>
<%@ include file = "../includes/footer.jsp" %>
