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
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.*;


public class Details extends Controller {
	
	public static Result getDetail(Long id) {
		return ok(detail.render(Detail.find.byId(id), User.findByEmail(request().username())));
	}
	
	public static Result editDetail(Long id) {
		Detail d = Detail.find.byId(id);
		Form<Detail> form = form(Detail.class).fill(d);
		return ok(editDetail.render(d, User.findByEmail(request().username()), form));
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
	
	public static Result createDetail(Long id) {
		Detail detail = new Detail(id);
		Form<Detail> detailForm = form(Detail.class).fill(detail);
		Logger.info("Detail parent = "+ detailForm.get().parent.id);
		return ok(createDetail.render(User.findByEmail(request().username()), detailForm));
	}
	
	public static Result saveDetail() {
		Form<Detail> detailForm = form(Detail.class).bindFromRequest();
        Detail detail = detailForm.get();
		if (detailForm.hasErrors()) {
			return badRequest(createDetail.render(User.findByEmail(request().username()), detailForm));
		}
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart attachment = body.getFile("attachment");
        if (attachment != null) {
            detail.attachment = saveAttachment(attachment);
        }
        if(detail.attachment != null) {Logger.info("Detail.attachment.id = "+detail.attachment.id);};
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
	
	public static Result updateDetail(Long id) {
	   Detail d = Detail.find.byId(id);
	   Form<Detail> detailForm = form(Detail.class).bindFromRequest();
	   if(detailForm.hasErrors()) {
		   return badRequest(editDetail.render(d, User.findByEmail(request().username()), detailForm));
	   }
	   //handle a change in file upload
	   
	   detailForm.get().update(id);
	   flash("success", "Detail has been updated.");
	   return redirect(routes.Details.getDetail(id));
	   //return redirect(routes.Parents.getSummary());
	}
}
