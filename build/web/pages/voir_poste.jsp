<%-- 
    Document   : voir_poste
    Created on : 25 févr. 2024, 09:59:04
    Author     : onian
--%>
<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>

<%
    Poste[] v_poste =  (Poste[])request.getAttribute("v_poste");
    Departement[] v_departement =  (Departement[])request.getAttribute("v_departement");
%>
<div class="page-content">

    <div class="card-body">
        <h3 class="card-title">Recherche de postes</h3>
        <form action="/mini-projet/Pos2_servlet" method="post">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">Titre</label>
                            <input type="text" class="form-control"name="titre" placeholder="Titre du poste">
                        </div>
                    </div><!-- Col -->
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label for="exampleFormControlSelect1" class="form-label">Département</label>
                            <select class="form-select" id="exampleFormControlSelect1" name="departement">
                                <option selected="" value="0">Selectionner un département spécifique</option>
                                <%
                                if(v_departement.length > 0)
                                {
                                    for (int i = 0; i < v_departement.length; i++) {
                                    %>
                                    <option value="<%= v_departement[i].getId()%>"><%= v_departement[i].getNom()%></option>
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
                            <label class="form-label">Salaire compris entre</label>
                            <input type="number" class="form-control" placeholder="Salaire minimal" name="salmin">
                        </div>
                    </div><!-- Col -->
                        <div class="col-sm-6">
                            <div class="mb-3">
                                <label class="form-label">Et</label>
                                <input type="number" class="form-control" placeholder="Salaire maximal" name="salmax">
                            </div>
                        </div><!-- Col -->
                </div><!-- Row -->

                <button type="submit" class="btn btn-primary submit">Rechercher</button>
            </form>
        <hr>
        <br><br>
        <h3 class="card-title">Liste des postes</h3>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Salaire</th>
                        <th>Département</th>
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
                        if( v_poste.length>0 ){  
                          for(int i = 0; i< v_poste.length;i++) { 
                            Poste pos = v_poste[i];
                        %>
                    <tr>
                        <td><% out.print(pos.getTitre()); %></td>
                        <td><% out.print(pos.getSalaire()); %></td>
                        <td><% out.print(pos.getDepartement().getNom()); %></td>

                        <%
                        if (sessions.getAttribute("admin") != null) {
                        // La session existe
                            %>
                            <td><a href="/mini-projet/Pos2_servlet?action=update&id=<%= pos.getId() %>">Modifier</a></td>
                            <td><a href="/mini-projet/Pos2_servlet?action=delete&id=<%=pos.getId() %>">Supprimer</a></td>
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
