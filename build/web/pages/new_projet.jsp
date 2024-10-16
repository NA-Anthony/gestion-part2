<%-- 
    Document   : new_projet
    Created on : 25 févr. 2024, 12:15:28
    Author     : onian
--%>

<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>
    <%
        Employe[] all = (Employe[])request.getAttribute("v_emp");
        Projet pro = new Projet();
        if (request.getParameter("id") != null) 
        {
            pro.getById(Integer.parseInt(request.getParameter("id")));
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
                <h3 class="card-title">Nouveau projet</h3>
                <%
            }
        %>

        <form class="forms-sample" action="/mini-projet/Projet_servlet" method="post">
            <div class="row mb-3">
                <label for="nom" class="col-sm-3 col-form-label">Nom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="nom" name="nom" placeholder="Entrez le nom du projet"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= pro.getNom()%>">
                        <input type="hidden" name="id" value="<%= pro.getId() %>"
                     <% }
                     %>
                     >
                </div>
            </div>
                     
            <div class="row mb-3">
                <label for="description" class="col-sm-3 col-form-label">Description</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="description" name="description" placeholder="Entrez une brève description"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= pro.getDescription()%>"
                     <% } 
                     %>
                     >
                </div>
            </div>
                     
            <div class="row mb-3">
                <label for="debut" class="col-sm-3 col-form-label">Date de début</label>
                <div class="col-sm-9">
                    <input type="date" class="form-control" id="debut" name="debut" placeholder="Date de début"
                     <% if (request.getParameter("id") != null) { %>
                        value="<%= Connect.formatTimestamp(pro.getDateDebut()) %>"
                    <% } %>
                     >
                </div>
            </div>
                     
            <div class="row mb-3">
                <label for="fin" class="col-sm-3 col-form-label">Date de fin</label>
                <div class="col-sm-9">
                <input type="date" class="form-control" id="fin" name="fin" placeholder="Date de fin"
                    <% if (request.getParameter("id") != null) { %>
                        value="<%= Connect.formatTimestamp(pro.getDateFin()) %>"
                    <% } %>
                >


                </div>
            </div>
                     
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Chef du projet</label>
                <div class="col-sm-9">
                    <select class="form-select" name="idChef">
                        <%
                            for (int i = 0; i < all.length; i++) {
                                if (request.getParameter("id") != null && all[i].getId()==pro.getChef().getId())
                                {
                                   %>
                                   <option selected="" value="<%= all[i].getId()%>"><%= all[i].getNom()%> <%= all[i].getPrenom()%></option>
                                   <%
                                }
                                else{
                                    %>
                                    <option value="<%= all[i].getId()%>"><%= all[i].getNom()%> <%= all[i].getPrenom()%></option>
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


