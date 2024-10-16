<%-- 
    Document   : voir_projet
    Created on : 25 févr. 2024, 12:23:26
    Author     : onian
--%>

<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>

<%
    Projet[] v_projet =  (Projet[])request.getAttribute("v_projet");
    Employe[] v_employe = (Employe[])request.getAttribute("v_employe");
%>
<div class="page-content">

    <div class="card-body">
        <h3 class="card-title">Recherche de projets</h3>
        <form action="/mini-projet/Pro2_servlet" method="post">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">Nom</label>
                            <input type="text" class="form-control"name="nom" placeholder="Nom du projet">
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label for="exampleFormControlSelect1" class="form-label">Chef du projet</label>
                            <select class="form-select" id="exampleFormControlSelect1" name="employe">
                                <option selected="" value="0">Selectionner un employe</option>
                                <%
                                if(v_employe.length > 0)
                                {
                                    for (int i = 0; i < v_employe.length; i++) {
                                    %>
                                    <option value="<%= v_employe[i].getId()%>"><%= v_employe[i].getNom()%> <%= v_employe[i].getPrenom()%></option>
                                    <%
                                    }
                                }%>
                            </select>
                    </div>
                    </div>
                </div><!-- Row -->
                <hr>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <input type="text" class="form-control" placeholder="Description" name="description">
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">En cours le:</label>
                            <input type="date" class="form-control" name="date">
                        </div>
                    </div>
                        
                </div><!-- Row -->

                <hr>

                <button type="submit" class="btn btn-primary submit">Rechercher</button>
            </form>
        <hr>
        <br><br>
        <h3 class="card-title">Liste des projets</h3>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Description</th>
                        <th>Date de début</th>
                        <th>Date de fin</th>
                        <th>Chef de projet</th>

                        <%
                        if (sessions.getAttribute("admin") != null) {
                        // La session existe
                            %>
                                <th>Modification</th>
                                <th>Suppression</th>

                            <%
                        }
                    %>
                    </tr>
                </thead>
                <tbody>
                    <%  
                        if( v_projet.length>0 ){  
                          for(int i = 0; i< v_projet.length;i++) { 
                            Projet pro = v_projet[i];
                        %>
                    <tr>
                        <td><% out.print(pro.getNom()); %></td>
                        <td><% out.print(pro.getDescription()); %></td>
                        <td><%= Connect.formatDateOnly(pro.getDateDebut()) %></td>
                        <td><%= Connect.formatDateOnly(pro.getDateFin()) %></td>
                        <td><%= pro.getChef().getNom() %> <%= pro.getChef().getPrenom()%></td>


                        <%
                        if (sessions.getAttribute("admin") != null) {
                        // La session existe
                            %>
                            <td><a href="/mini-projet/Pro2_servlet?action=update&id=<%= pro.getId() %>">Modifier</a></td>
                            <td><a href="/mini-projet/Pro2_servlet?action=delete&id=<%= pro.getId() %>">Supprimer</a></td>
                            <%
                        }
                    %>
                    </tr>
                    <%      }
                    }
                %>
                </tbody>
            </table>
        </div>
</div>
</div>
<%@ include file = "../includes/footer.jsp" %>
