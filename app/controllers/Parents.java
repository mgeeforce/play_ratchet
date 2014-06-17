/**
 * 
 */
package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.Parent;
import models.User;
import play.Logger;
import play.data.Form;
import static play.data.Form.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;


/**
 * @author Mike Gee
 *
 */
@Security.Authenticated(Secured.class)
public class Parents extends Controller {
	
	public static Result getSummary() {
		return ok(list.render("Projects", Parent.summaryForUser(request().username()), User.find.byId(request().username())));
	}
	
	public static Result listForStatus(String status) {
		Parent.Status s = Parent.Status.valueOf(status);
		return ok(listReports.render(status, Parent.findForUserAndStatus(request().username(), s), User.find.byId(request().username())));
	}
	
	public static Result getParent(Long id) {
		Parent p = Parent.find.byId(id);
		return ok(listDetails.render(p.name, p, User.find.byId(request().username())));
	}
	
	public static Result editParent(Long id) {
		Parent p = Parent.find.byId(id);
		return ok(editDetails.render(p.name, p, User.find.byId(request().username())));
	}
	
   public static Result list() {
        return ok(Json.toJson(Parent.find.all()));
    }
   
   public static Result getProjects() {
	   Parent project = new Parent(User.findByEmail(request().username()));
	   Form<Parent> projectForm = form(Parent.class).fill(project);
	   return ok(projects.render(
			   "Projects",
			   Parent.find.where().eq("created_by_email", request().username()).findList(),
			   User.find.byId(request().username()),
			   projectForm
			   ));
   }
   
   public static Result saveProject() {
	   Form<Parent> projectForm = form(Parent.class).bindFromRequest();
	   if(projectForm.hasErrors()) {
		   Logger.info("projectForm= "+projectForm);
		   return badRequest(projects.render(
				   "Projects",
				   Parent.find.where().eq("created_by_email", request().username()).findList(),
				   User.find.byId(request().username()),
				   projectForm
				   ));
	   }
	   projectForm.get().save();
	   return redirect(routes.Parents.getProjects());
   }
   
   public static Result get(Long id) {
	   return ok(Json.toJson(Parent.find.byId(id)));
   }
   
   public static Result create() {
	   JsonNode node = request().body().asJson();
	   Parent parent = Json.fromJson(node, Parent.class);
	   parent.save();
	   return ok(Json.toJson(parent));
   }

   public static Result delete(Long id) {
	   try {
		   Parent.find.ref(id).delete();
		   return ok("Parent " + id + " deleted.");
	   } catch (Exception e) {
		   return ok("A problem was encountered.");
	   }
   }
   
   public static Result update(Long id) {
	   JsonNode node = request().body().asJson();
	   Parent parent = Json.fromJson(node, Parent.class);
	   parent.update(id);
	   return ok(Json.toJson(parent));
   }
}
