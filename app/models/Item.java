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
import javax.persistence.Table;

import com.avaje.ebean.Page;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author YK4P
 *
 */
@Entity
@Table(name="detail")
public class Item extends Model {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
	
	public String merchant;
    
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
    
    @Required
    @ManyToOne
    public User createdBy;
    
    public enum Category {
    	Accomodation,
    	Fuel,
    	Meals,
    	Race,
    	Admin
    }
    
    
	@OneToOne
	public Attachment attachment;
    
	//default constructor
	public Item() {} 
	
	public Item(User user) {
		this.createdBy = user;
	}
	
	public Item(Long parentId, User user) {
		this.parent = Parent.find.byId(parentId);
		this.createdBy = user;
	}
	
	public Item(String name, BigDecimal amount, String category, String description) {
		this.name = name;
		this.amount = amount;
		this.category = Category.valueOf(category);
		this.description = description;
	}
	
    /**
     * Generic query helper for entity Computer with id Long
     */
    public static Finder<Long,Item> find = new Finder<Long,Item>(Long.class, Item.class); 
    
    /**
     * Return a page of details
     *
     * @param page Page to display
     * @param pageSize Number of details per page
     * @param sortBy Detail property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Item> page(int page, int pageSize, String sortBy, String order, String filter) {
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
