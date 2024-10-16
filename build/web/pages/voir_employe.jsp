<%-- 
    Document   : voir_employe
    Created on : 25 févr. 2024, 09:59:04
    Author     : onian
--%>
<%@page import="model.*"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>

<%
    Employe[] v_employe =  (Employe[])request.getAttribute("v_employe");
    Poste[] v_poste =  (Poste[])request.getAttribute("v_poste");
    List<Map<String, Object>> v_stat = (List<Map<String, Object>>)request.getAttribute("v_stat");
%>
<div class="page-content">

    
    <div class="card-body">
        <h3 class="card-title">Recherche d'employés</h3>
        <form action="/mini-projet/Emp2_servlet" method="post">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">Nom ou prénom</label>
                            <input type="text" class="form-control"name="nom" placeholder="Nom/Prénom de l'employé">
                        </div>
                    </div><!-- Col -->
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label for="exampleFormControlSelect1" class="form-label">Poste</label>
                            <select class="form-select" id="exampleFormControlSelect1" name="poste">
                                <option selected="" value="0">Selectionner son poste</option>
                                <%
                                if(v_poste.length > 0)
                                {
                                    for (int i = 0; i < v_poste.length; i++) {
                                    %>
                                    <option value="<%= v_poste[i].getId()%>"><%= v_poste[i].getTitre()%></option>
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
                            <label class="form-label">Age compris entre</label>
                            <input type="number" class="form-control" placeholder="Age minimal" name="agemin">
                        </div>
                    </div><!-- Col -->
                        <div class="col-sm-6">
                            <div class="mb-3">
                                <label class="form-label">Et</label>
                                <input type="number" class="form-control" placeholder="Age maximal" name="agemax">
                            </div>
                        </div><!-- Col -->
                </div><!-- Row -->

                <hr>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">Date d'embauche entre</label>
                            <input type="date" class="form-control" name="embauchemin">
                        </div>
                    </div><!-- Col -->
                        <div class="col-sm-6">
                            <div class="mb-3">
                                <label class="form-label">Et</label>
                                <input type="date" class="form-control" name="embauchemax">
                            </div>
                            </div><!-- Col -->
                    </div><!-- Row -->
                    
                <button type="submit" class="btn btn-primary submit">Rechercher</button>
            </form>
        <hr>
        <br><br>
        <h3 class="card-title">Liste des employés</h3>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Date de naissance</th>
                        <th>Date d'embauche</th>
                        <th>Poste</th>

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
                        if( v_employe.length>0 ){  
                          for(int i = 0; i< v_employe.length;i++) { 
                            Employe emp = v_employe[i];
                        %>
                    <tr>
                        <td><% out.print(emp.getNom()); %></td>
                        <td><% out.print(emp.getPrenom()); %></td>
                        <td><%= Connect.formatDateOnly(emp.getNaissance()) %></td>
                        <td><%= Connect.formatDateOnly(emp.getEmbauche()) %></td>
                        <td><%= emp.getPoste().getTitre() %></td>


                        <%
                        if (sessions.getAttribute("admin") != null) {
                        // La session existe
                            %>
                            <td><a href="/mini-projet/Emp2_servlet?action=update&id=<%= emp.getId() %>">Modifier</a></td>
                            <td><a href="/mini-projet/Emp2_servlet?action=delete&id=<%=emp.getId() %>">Supprimer</a></td>
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
        <hr>
        <br><br>
        <h3 class="card-title">Statistique de nombre d'employés embauchés par an</h3>
        <hr>
        <div class="col-md-12 grid-margin">
            <div class="card">
                <div class="card-body">
                    <canvas id="statChart" width="400" height="200"></canvas>
                </div>
            </div>
        </div>
</div>
</div>
<script>
    var anneeLabel = [];
    var nbEmpLabel = [];

    <%
        for (Map<String, Object> row : v_stat) {
    %>
        anneeLabel.push('<%= row.get("Annee") %>');
        nbEmpLabel.push(<%= row.get("Nb_Employes") %>);
    <%
        }
    %>
    let statData = {
        labels: anneeLabel,
        datasets: [{
            data: nbEmpLabel,
            label: "Nombre d'employés embauchés",
            borderColor: "#4b55c0",
            fill: true,
            backgroundColor: "rgba(75, 85, 192, 0.2)"
        }]
    }

    let nbEmpData = {
        type: 'line',
        data: statData,
        options: {
            scales: {
                y: { 
                    beginAtZero: true, 
                    min: 0 
                }
            }
        }
    }

    let statCanvas = document.getElementById("statChart").getContext('2d');
    new Chart(statCanvas, nbEmpData);
</script>
<%@ include file = "../includes/footer.jsp" %>
