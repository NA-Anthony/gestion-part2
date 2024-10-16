<%-- 
    Document   : body
    Created on : 15 fï¿½vr. 2024, 19:23:53
    Author     : onian
--%>


<%@page import="model.Connect"%>
<%@page import="model.Admin"%>
<body>
    <!-- core:js -->
    <script src="/mini-projet/assets/vendors/core/core.js"></script>
    <!-- endinject -->

    <!-- Plugin js for this page -->
    <!-- End plugin js for this page -->

    <!-- inject:js -->
    <script src="/mini-projet/assets/vendors/feather-icons/feather.min.js"></script>
    <script src="/mini-projet/assets/js/template.js"></script>
    <script src="/mini-projet/assets/vendors/chartjs/Chart.min.js"></script>
    <script src="/mini-projet/assets/vendors/jquery.flot/jquery.flot.js"></script>
    <script src="/mini-projet/assets/vendors/jquery.flot/jquery.flot.resize.js"></script>
    <script src="/mini-projet/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script src="/mini-projet/assets/vendors/apexcharts/apexcharts.min.js"></script>
    <script src="/mini-projet/assets/vendors/feather-icons/feather.min.js"></script>
    <script src="/mini-projet/assets/js/template.js"></script>
    <script src="/mini-projet/assets/js/dashboard-dark.js"></script>
    <script src="/mini-projet/assets/js/datepicker.js"></script>
    <!-- endinject -->
    <div class="main-wrapper">
    <nav class="sidebar">
      <div class="sidebar-header">
        <a href="#" class="sidebar-brand">
          Gestion
        </a>
        
      </div>
      <div class="sidebar-body ps ps--active-y">
        <ul class="nav">
            <%
                // Récupérer la session
                HttpSession sessions = request.getSession();

                if (sessions.getAttribute("admin") != null) {
                // La session existe
                    %>
                        <li class="nav-item">
                        <a class="nav-link" data-bs-toggle="collapse" href="#insert" role="button" aria-expanded="false" aria-controls="insert">
                          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
                          <span class="link-title">Insertion</span>
                          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down link-arrow"><polyline points="6 9 12 15 18 9"></polyline></svg>
                        </a>
                        <div class="collapse" id="insert">
                          <ul class="nav sub-menu">
                            <li class="nav-item">
                              <a href="/mini-projet/pages/new_departement.jsp" class="nav-link">Insertion demande</a>
                            </li>
                            <li class="nav-item">
                              <a href="/mini-projet/Pos2_servlet" class="nav-link">Poste</a>
                            </li>
                            <li class="nav-item">
                              <a href="/mini-projet/pages/new_departement.jsp" class="nav-link">Département</a>
                            </li>

                          </ul>
                        </div>
                      </li>
                    <%
                }
            %>
            
          <li class="nav-item">
            <a class="nav-link" data-bs-toggle="collapse" href="#list" role="button" aria-expanded="false" aria-controls="list">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
              <span class="link-title">Listes</span>
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down link-arrow"><polyline points="6 9 12 15 18 9"></polyline></svg>
            </a>
            <div class="collapse" id="list">
              <ul class="nav sub-menu">
                <li class="nav-item">
                  <a href="/mini-projet/pages/new_demande_achat.jsp" class="nav-link">Insertion demande</a>
                </li>
                <li class="nav-item">
                  <a href="/mini-projet/Poste_servlet" class="nav-link">Poste</a>
                </li>
                <li class="nav-item">
                  <a href="/mini-projet/Departement_servlet" class="nav-link">Département</a>
                </li>
              </ul>
            </div>
          </li>
        
    </div>
</nav>

<div class="page-wrapper">
    <nav class="navbar">
    <a href="#" class="sidebar-toggler">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-menu"><line x1="3" y1="12" x2="21" y2="12"></line><line x1="3" y1="6" x2="21" y2="6"></line><line x1="3" y1="18" x2="21" y2="18"></line></svg>
    </a>
    <div class="navbar-content">
            <form class="search-form">
                    <div class="input-group">
              <div class="input-group-text">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
              </div>
    </div>
</form>
<ul class="navbar-nav">
   
<li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <img class="wd-30 ht-30 rounded-circle" src="/mini-projet/assets/images/profil.png" alt="profile">
        </a>
        <div class="dropdown-menu p-0" aria-labelledby="profileDropdown">
                <div class="d-flex flex-column align-items-center border-bottom px-5 py-3">
                        <div class="mb-3">
                                <img class="wd-80 ht-80 rounded-circle" src="/mini-projet/assets/images/profil.png" alt="">
                        </div>
                        
                    <%

                        if (sessions.getAttribute("admin") != null) {
                            Admin ad = Connect.getAdmin((int)sessions.getAttribute("admin"));
                        // La session existe
                            %>
                                <p class="tx-16 fw-bolder"><%= ad.getNom()%></p>
                                <p class="tx-12 text-muted"><%= ad.getEmail()%></p>
                            <%
                        }
                    %>
                        
                </div>
                <ul class="list-unstyled p-1">
                  
                  
                    
                        <%
                        // Récupérer la session

                        if (sessions.getAttribute("admin") != null) {
                        // La session existe
                            %>
                            
                                <a href="/mini-projet/Login_servlet" class="text-body ms-0">
                                    <li class="dropdown-item py-2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-log-out me-2 icon-md"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
                                        <span>Log Out</span>
                                    </li>
                                </a>
                            <%
                        }
                        else{
                            %>
                                <a href="/mini-projet/pages/login.jsp" class="text-body ms-0">
                                    <li class="dropdown-item py-2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-log-in me-2 icon-md"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"></path><polyline points="10 17 15 12 10 7"></polyline><line x1="15" y1="12" x2="3" y2="12"></line></svg>
                                        <span>Log In</span>
                                    </li>
                                </a>
                            <%
                        }  
                        %>
                      
                   
                  
                </ul>
                </div>
        </li>
</ul>
</div>
</nav>
    