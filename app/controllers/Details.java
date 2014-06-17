package controllers;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.*;

import com.fasterxml.jackson.databind.JsonNode;

import play.Logger;
import play.Play;
import play.data.Form;
import play.libs.Json;
import play.mvc.Http.MultipartFormData;
import play.mvc.Controller;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.*;


@Security.Authenticated(Secured.class)
public class Details extends Controller {
	
	public static Result getDetail(Long id) {
		return ok(detail.render(Item.find.byId(id), User.findByEmail(request().username())));
	}
	
	public static Result editDetail(Long id) {
		Item d = Item.find.byId(id);
		Form<Item> form = form(Item.class).fill(d);
		return ok(editDetail.render(d, User.findByEmail(request().username()), form));
	}
	
	public static Result list() {
	    return ok(Json.toJson(Item.find.all()));
	}
	   
	public static Result get(Long id) {
	   return ok(Json.toJson(Item.find.byId(id)));
	}
	
	public static Result getDetails(Long parentid) {
		return redirect(routes.Parents.getParent(parentid));
	}
	
	public static Result getUnfiledDetails() {
		User user = User.findByEmail(request().username());
		return ok(details.render(
				"details",
				Item.find.where().eq("created_by_email", user.email).isNull("parent_id").findList(),
				user
			));
		}
	
	public static int getUnfiledCount() {
		return Item.find.where()
				.eq("created_by_email", User.findByEmail(request().username()).email)
				.isNull("parent_id")
				.findRowCount();
				
	}
	
	public static Result create() {
		JsonNode node = request().body().asJson();
		Item d = Json.fromJson(node, Item.class);
		d.save();
		return ok(Json.toJson(d));
	}
	
	public static Result createDetail(Long id) {
		Item detail = new Item(id, User.findByEmail(request().username()));
		Form<Item> detailForm = form(Item.class).fill(detail);
		return ok(createDetail.render(User.findByEmail(request().username()), detailForm));
	}
	
	public static Result createDeet() {
		Logger.info("in createDeet");
		Item detail = new Item(User.findByEmail(request().username()));
		Form<Item> detailForm = form(Item.class).fill(detail);
		return ok(createDetail.render(User.findByEmail(request().username()), detailForm));
	}

	
	public static Result saveDetail() {
		Form<Item> detailForm = form(Item.class).bindFromRequest();
		if (detailForm.hasErrors()) {
			return badRequest(createDetail.render(User.findByEmail(request().username()), detailForm));
		}
        Item detail = detailForm.get();
		MultipartFormData body = request().body().asMultipartFormData();
        FilePart attachment = body.getFile("attachment");
        if (attachment != null) {
            detail.attachment = saveAttachment(attachment);
        }
        if(detail.attachment != null) {Logger.info("Detail.attachment.id = "+detail.attachment.id);};
		//detail.createdBy = User.findByEmail(request().username());
        detail.save();
		return redirect(routes.Details.getDetail(detail.id));
	}
	
	private static Attachment saveAttachment(FilePart attachment) {
    	File file = attachment.getFile();
        //file.renameTo(new File(Play.application().configuration().getString("uploadPath"), attachment.getFilename()));
    	String path = null;
        try {
        	Path source = Paths.get(file.getAbsolutePath());
        	Path target = Paths.get(Play.application().configuration().getString("uploadPath"), attachment.getFilename());
        	Files.move(source, target);
        	path = target.toString();
        } catch (FileAlreadyExistsException e) {
        	//TODO: there needs to be a Files.exists(target) check above and method to increment to make unique
        	Logger.error("File already exists and needs renaming!!");
        } catch (Exception e) {
        	Logger.error(e.toString());
        }
        Attachment a = new Attachment(attachment.getFilename(), path, attachment.getContentType());
        a.save();
        return a;
	}
	
	public static Result delete(Long id) {
	   try {
		   Item.find.ref(id).delete();
		   return ok("Detail " + id + " deleted.");
	   } catch (Exception e) {
		   return ok("A problem was encountered.");
	   }
	}
	   
	public static Result update(Long id) {
	   JsonNode node = request().body().asJson();
	   Item d = Json.fromJson(node, Item.class);
	   d.update(id);
	   return ok(Json.toJson(d));
	}
	
	public static Result updateDetail(Long id) {
	   Item d = Item.find.byId(id);
	   Form<Item> detailForm = form(Item.class).bindFromRequest();
	   if(detailForm.hasErrors()) {
		   return badRequest(editDetail.render(d, User.findByEmail(request().username()), detailForm));
	   }
	   Logger.info("in update "+detailForm);	   
	   detailForm.get().update(id);
	   flash("success", "Detail has been updated.");
	   return redirect(routes.Details.getDetail(id));
	   //return redirect(routes.Parents.getSummary());
	}
}
