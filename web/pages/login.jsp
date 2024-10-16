<%@ include file = "../includes/head.jsp" %>
<%@ include file = "../includes/body.jsp" %>
<div class="page-content">

    <div class="card-body">

        <h6 class="card-title">LOGIN</h6>

        <form class="forms-sample" action="/mini-projet/Login_servlet" method="post">
            <div class="row mb-3">
                <label for="email" class="col-sm-3 col-form-label">Email</label>
                <div class="col-sm-9">
                        <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Email"name="email">
                </div>
            </div>
            
            <div class="row mb-3">
                <label for="mdp" class="col-sm-3 col-form-label">Mot de passe</label>
                <div class="col-sm-9">
                        <input type="password" class="form-control" id="exampleInputEmail2" placeholder="Mot de passe"name="mdp">
                </div>
            </div>
             
            <button type="submit" class="btn btn-primary me-2">Valider</button>
            <button type="button" class="btn btn-secondary">Annuler</button>
        </form>

    </div>
</div>
<%@ include file = "../includes/footer.jsp" %>
