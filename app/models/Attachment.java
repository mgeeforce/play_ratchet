/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Attachment extends Model {
	
    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
	
	@Required
	public String filename;
	
	@Required
	public String path;
	
	@Required
	public String contentType;	
	
	@CreatedTimestamp
	public Date created;
	
	public Attachment(String filename, String path, String contentType) {
		this.filename = filename;
		this.path = path;
		this.contentType = contentType;
	}
	
	public static Finder<Long, Attachment> find = new Finder<Long, Attachment>(Long.class, Attachment.class);

}
