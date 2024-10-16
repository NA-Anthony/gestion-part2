<%-- 
    Document   : new_poste
    Created on : 25 févr. 2024, 10:12:53
    Author     : onian
--%>
<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>
    <%
        Departement[] all = (Departement[])request.getAttribute("v_dep");
        Poste pos = new Poste();
        if (request.getParameter("id") != null) 
        {
            pos.getById(Integer.parseInt(request.getParameter("id")));
        }
    %>
            
<div class="page-content">

    <div class="card-body">

        <%

        if (request.getParameter("id") != null) 
        {
            %>   
                <h3 class="card-title">Mise à jour</h3>
                <%
            }
            else{
            %>   
                <h3 class="card-title">Nouveau poste</h3>
                <%
            }
        %>

        <form class="forms-sample" action="/mini-projet/Poste_servlet" method="post">
            <div class="row mb-3">
                <label for="titre" class="col-sm-3 col-form-label">Titre</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="titre" name="titre" placeholder="Entrez le titre du poste"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= pos.getTitre()%>">
                        <input type="hidden" name="id" value="<%= pos.getId() %>">
                     <% } 
                        else{
                            %>><%
                        }
                     %>
                     
                </div>
            </div>
            <div class="row mb-3">
                <label for="salaire" class="col-sm-3 col-form-label">Salaire</label>
                <div class="col-sm-9">
                    <input type="number" class="form-control" id="salaire" name="salaire" placeholder="Entrez montant du salaire"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= pos.getSalaire() %>">
                    
                     <% } 
                    else {%>
                    >
                    <%}%>
                 
                </div>
            </div>
                     
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Département</label>
                <div class="col-sm-9">
                <select class="form-select" name="idDepartement">
                    <%
                        for (int i = 0; i < all.length; i++) {
                            if (request.getParameter("id") != null && all[i].getId()==pos.getDepartement().getId())
                            {
                               %>
                               <option selected="" value="<%= all[i].getId()%>"><%= all[i].getNom()%></option>
                               <%
                            }
                            else{
                                %>
                                <option value="<%= all[i].getId()%>"><%= all[i].getNom()%></option>
                                <%
                            }
                        }
                        %>
                </select>
                </div>
            </div>
            <hr>
            <button type="submit" class="btn btn-primary me-2">Valider</button>
            <button type="button" class="btn btn-secondary">Annuler</button>
        </form>

    </div>
</div>
<%@ include file = "../includes/footer.jsp" %>

