<%-- 
    Document   : new_employe
    Created on : 25 févr. 2024, 11:11:27
    Author     : onian
--%>

<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>
    <%
        Poste[] all = (Poste[])request.getAttribute("v_pos");
        Employe emp = new Employe();
        if (request.getParameter("id") != null) 
        {
            emp.getById(Integer.parseInt(request.getParameter("id")));
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
                <h3 class="card-title">Nouvel employé</h3>
                <%
            }
        %>

        <form class="forms-sample" action="/mini-projet/Employe_servlet" method="post">
            <div class="row mb-3">
                <label for="nom" class="col-sm-3 col-form-label">Nom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="nom" name="nom" placeholder="Entrez son nom"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= emp.getNom()%>">
                        <input type="hidden" name="id" value="<%= emp.getId() %>"
                     <% }
                     %>
                     >
                </div>
            </div>
                     
            <div class="row mb-3">
                <label for="prenom" class="col-sm-3 col-form-label">Prénom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="prenom" name="prenom" placeholder="Entrez son prénom"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= emp.getPrenom()%>"
                     <% } 
                     %>
                     >
                </div>
            </div>
                     
            <div class="row mb-3">
                <label for="naissance" class="col-sm-3 col-form-label">Date de Naissance</label>
                <div class="col-sm-9">
                    <input type="date" class="form-control" id="naissance" name="naissance" placeholder="Date de naissance"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= Connect.formatTimestamp(emp.getNaissance()) %>"
                    <% } %>
                     >
                </div>
            </div>
                     
            <div class="row mb-3">
                <label for="embauche" class="col-sm-3 col-form-label">Date d'embauche</label>
                <div class="col-sm-9">
                <input type="date" class="form-control" id="embauche" name="embauche" placeholder="Date d'embauche"
                    <% if (request.getParameter("id") != null) { %>
                        value="<%= Connect.formatTimestamp(emp.getEmbauche()) %>"
                    <% } %>
                >


                </div>
            </div>
                     
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Poste</label>
                <div class="col-sm-9">
                    <select class="form-select" name="idPoste">
                        <%
                            for (int i = 0; i < all.length; i++) {
                                if (request.getParameter("id") != null && all[i].getId()==emp.getPoste().getId())
                                {
                                   %>
                                   <option selected="" value="<%= all[i].getId()%>"><%= all[i].getTitre()%></option>
                                   <%
                                }
                                else{
                                    %>
                                    <option value="<%= all[i].getId()%>"><%= all[i].getTitre()%></option>
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


