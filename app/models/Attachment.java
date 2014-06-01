/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;

@Entity
public class Attachment extends Model {
	
    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
	
	
	@CreatedTimestamp
	public Date created;
	
	public static Finder<Long, Attachment> find = new Finder<Long, Attachment>(Long.class, Attachment.class);

}
