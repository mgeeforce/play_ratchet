package controllers;

import static play.data.Form.form;
import models.User;
import play.Logger;
import play.Routes;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return redirect(routes.Parents.getSummary());
    }
    
    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }
    
    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            Logger.info("authentication successfull");
            return redirect(
                routes.Parents.getSummary()
            );
        }
    }
    
    public static Result mobile() {
    	return ok(mobile.render("Raceclub Expenses", Parents.list()));
    }

    public static class Login {

        public String email;
        public String password;
        
        public String validate() {
            if(User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }
    
    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.login()
        );
    }
    
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                controllers.routes.javascript.Parents.getParent()
            )
        );
    }
    
}
