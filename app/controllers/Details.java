package controllers;

import models.Detail;
import models.User;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Details extends Controller {
	
	public static Result getDetail(Long id) {
		return ok(detail.render(Detail.find.byId(id), User.findByEmail(request().username())));
	}

	public static Result editDetail(Long id) {
		Detail d = Detail.find.byId(id);
		return ok(editDetail.render(d, User.find.byId(request().username())));
	}
	   public static Result list() {
	        return ok(Json.toJson(Detail.find.all()));
	    }
	   
	   public static Result get(Long id) {
		   return ok(Json.toJson(Detail.find.byId(id)));
	   }
	   
	   public static Result create() {
		   JsonNode node = request().body().asJson();
		   Detail d = Json.fromJson(node, Detail.class);
		   d.save();
		   return ok(Json.toJson(d));
	   }

	   public static Result delete(Long id) {
		   try {
			   Detail.find.ref(id).delete();
			   return ok("Detail " + id + " deleted.");
		   } catch (Exception e) {
			   return ok("A problem was encountered.");
		   }
	   }
	   
	   public static Result update(Long id) {
		   JsonNode node = request().body().asJson();
		   Detail d = Json.fromJson(node, Detail.class);
		   d.update(id);
		   return ok(Json.toJson(d));
	   }

}
