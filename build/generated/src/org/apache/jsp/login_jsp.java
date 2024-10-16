package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <title>Login</title>\n");
      out.write("    <link rel=\"stylesheet\" href=\"/mini-projet/assets/css/login1.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div class=\"container\" id=\"container\">\n");
      out.write("\t<div class=\"form-container sign-up-container\">\n");
      out.write("\t\t<form action=\"#\">\n");
      out.write("\t\t\t<h1>Créer un compte ?</h1>\n");
      out.write("\t\t\t<div class=\"social-container\">\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"social\"><i class=\"fab fa-facebook-f\"></i></a>\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"social\"><i class=\"fab fa-google-plus-g\"></i></a>\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"social\"><i class=\"fab fa-linkedin-in\"></i></a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<span>Veuillez remplir les champs</span>\n");
      out.write("\t\t\t<input type=\"text\" placeholder=\"Veuillez entrer votre Nom\" />\n");
      out.write("\t\t\t<input type=\"email\" placeholder=\"Veuillez entrer votre Email\" />\n");
      out.write("\t\t\t<input type=\"password\" placeholder=\"Veuillez entrer votre Mot de passe\" />\n");
      out.write("\t\t\t<button>S'inscrire</button>\n");
      out.write("\t\t</form>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"form-container sign-in-container\">\n");
      out.write("\t\t<form action=\"index.jsp\">\n");
      out.write("\t\t\t<h1>Connexion</h1>\n");
      out.write("\t\t\t<div class=\"social-container\">\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"social\"><i class=\"fab fa-facebook-f\"></i></a>\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"social\"><i class=\"fab fa-google-plus-g\"></i></a>\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"social\"><i class=\"fab fa-linkedin-in\"></i></a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<span>Veuillez remplir les champs</span>\n");
      out.write("\t\t\t<input type=\"email\" placeholder=\"Veuillez entrer votre Email\" />\n");
      out.write("\t\t\t<input type=\"password\" placeholder=\"Veuillez entrer votre Mot de passe\" />\n");
      out.write("\t\t\t<a href=\"#\">Mot de passe oublié ?</a>\n");
      out.write("\t\t\t<button>Se connecter</button>\n");
      out.write("\t\t</form>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"overlay-container\">\n");
      out.write("\t\t<div class=\"overlay\">\n");
      out.write("\t\t\t<div class=\"overlay-panel overlay-left\">\n");
      out.write("\t\t\t\t<h1>Gestion</h1>\n");
      out.write("\t\t\t\t<p></p>\n");
      out.write("\t\t\t\t<button class=\"ghost\" id=\"signIn\">Se connecter</button>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"overlay-panel overlay-right\">\n");
      out.write("\t\t\t\t<h1>Gestion</h1>\n");
      out.write("\t\t\t\t<p></p>\n");
      out.write("\t\t\t\t<button class=\"ghost\" id=\"signUp\">S'inscrire</button>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<footer>\n");
      out.write("\n");
      out.write("</footer>\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("    const signUpButton = document.getElementById('signUp');\n");
      out.write("\tconst signInButton = document.getElementById('signIn');\n");
      out.write("\tconst container = document.getElementById('container');\n");
      out.write("\n");
      out.write("\tsignUpButton.addEventListener('click', () => {\n");
      out.write("\t\tcontainer.classList.add(\"right-panel-active\");\n");
      out.write("\t});\n");
      out.write("\n");
      out.write("\tsignInButton.addEventListener('click', () => {\n");
      out.write("\t\tcontainer.classList.remove(\"right-panel-active\");\n");
      out.write("});\n");
      out.write("\n");
      out.write("</script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
