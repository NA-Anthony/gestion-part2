<%-- 
    Document   : index
    Created on : 15 févr. 2024, 19:16:19
    Author     : onian
--%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>

<%
    Departement[] v_departement =  (Departement[])request.getAttribute("v_departement");
    List<Map<String, Object>> v_stat = (List<Map<String, Object>>)request.getAttribute("v_stat");
%>
<div class="page-content">

    <div class="card-body">
        
        <h3 class="card-title">Recherche de Départements</h3>
        <form action="/mini-projet/Dep2_servlet" method="post">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="mb-3">
                            <label class="form-label">Nom</label>
                            <input type="text" class="form-control" name="nom" placeholder="Entrez le nom du département">
                        </div>
                    </div><!-- Col -->
                  
                </div><!-- Row -->
               

                <button type="submit" class="btn btn-primary submit">Rechercher</button>
            </form>
        <hr>
        <br><br>
        <h3 class="card-title">Liste des départements</h3>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nom de département</th>
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
                        if( v_departement.length>0 ){  
                          for(int i = 0; i<v_departement.length;i++) { 
                            Departement dep = v_departement[i];
                        %>
                    <tr>
                        <td><% out.print(dep.getId()); %></td>
                        <td><% out.print(dep.getNom()); %></td>
                        <%
                        if (sessions.getAttribute("admin") != null) {
                        // La session existe
                            %>
                            <td><a href="/mini-projet/Dep2_servlet?action=update&id=<%= dep.getId() %>">Modifier</a></td>
                            <td><a href="/mini-projet/Dep2_servlet?action=delete&id=<%= dep.getId() %>">Supprimer</a></td>
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
        <h3 class="card-title">Statistique de nombre d'employés par département</h3>
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
    var deptLabel = [];
    var nbEmpLabel = [];

    <%
        for (Map<String, Object> row : v_stat) {
    %>
        deptLabel.push('<%= row.get("Departement") %>');
        nbEmpLabel.push(<%= row.get("Nb_Employes") %>);
    <%
        }
    %>
    let deptData = {
        labels: deptLabel,
        datasets: [{
            data: nbEmpLabel,
            label: "Nombre d'employés",
            borderColor: "#4b55c0",
            fill: true,
            backgroundColor: "rgba(75, 85, 192, 0.2)"
        }]
    }

    let nbEmpData = {
        type: 'bar',
        data: deptData,
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
