/**
 * 
 */
package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.Page;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author YK4P
 *
 */
@Entity
public class Detail extends Model {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Required
    public String name;
    
    @Required
    public Date date;
    
    @Required
    public BigDecimal amount;
    
    @Formats.DateTime(pattern="yyyy-MM-dd")
    @CreatedTimestamp
    public Date created;
    
    @JsonBackReference
    @ManyToOne
    public Parent parent;
    
    @Required
    public Category category;
    
    public String description;
    
    public enum Category {
    	Accomodation,
    	Fuel,
    	Meals,
    	Admin
    }
    
	@OneToOne
	public Attachment attachment;
    
    /**
     * Generic query helper for entity Computer with id Long
     */
    public static Finder<Long,Detail> find = new Finder<Long,Detail>(Long.class, Detail.class); 
    
    /**
     * Return a page of details
     *
     * @param page Page to display
     * @param pageSize Number of details per page
     * @param sortBy Detail property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Detail> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("parent")
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
}
