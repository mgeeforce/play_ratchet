/**
 * 
 */
package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author Mike Gee
 *
 */
@Entity
public class Parent extends Model {
	
    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Required
    public String name;
    
    @Required
    public Status status;
    
    @Required
    public Date date;
    
    @Required
    @ManyToOne
    public User createdBy;
    
    public enum Status {
    	
    	//@EnumValue("0")
    	Draft("0"),
    	
    	//@EnumValue("1")
    	Submitted("1"),

    	//@EnumValue("2")
    	Approved("2"),
    	
    	//@EnumValue("3")
    	Rejected("3");
    	
    	int val;
    	public String badgeClass;
    	
    	Status(String v) {
    		val = Integer.parseInt(v);
    		switch(val) {
    			case 0: badgeClass = null;
    				break;
    			case 1: badgeClass = "badge-primary";
    				break;
    			case 2: badgeClass = "badge-positive";
    				break;
    			case 3: badgeClass = "badge-negative";
    				break;
    			default: badgeClass = null;
    				break;
    		}
    	}
    	
    	String getBadgeClass() {
    		return badgeClass;
    	}

    }
    
    public Parent() {};
    
    public Parent(User user) {
    	this.createdBy = user;
    	this.status = Parent.Status.Draft;
    }
    
    @JsonIgnore
    public EnumMap<Status, String> getStatusMap() {
        EnumMap<Status, String> statusMap = new EnumMap<Status, String>(Status.class);
        statusMap.put(Status.Draft, "primary");
        statusMap.put(Status.Submitted, "warning");
        statusMap.put(Status.Approved, "success");
        statusMap.put(Status.Rejected, "danger");
        return statusMap;
    }
    
    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL)
    public List<Item> details;
    
    public BigDecimal getTotal() {
    	BigDecimal total = new BigDecimal(0);
    	for (int i=0; i < details.size(); i++) {
    		total = total.add(details.get(i).amount);
    	}
    	return total;
    }
    
    /**
     * Generic query helper for entity Company with id Long
     */
    public static Model.Finder<Long,Parent> find = new Model.Finder<Long,Parent>(Long.class, Parent.class);

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Parent p: Parent.find.orderBy("name").findList()) {
            options.put(p.id.toString(), p.name);
        }
        return options;
    }

    /**
     * Check if a user is the owner of this object
     */
    public static boolean isOwner(Long parent, String user) {
        return find.where()
            .eq("created_by_email", user)
            .eq("id", parent)
            .findRowCount() > 0;
    }
    
    /**
     * Retrieve project for user
     */
    public static List<Parent> findForUserAndStatus(String user, Status status) {
        return find.where()
            .eq("created_by_email", user)
            .eq("status", status)
            .findList();
    }
    
    // should this be done with SQL
    public static List<ReportSummary> summaryForUser(String user) {
    	String sql = "select status, count(*) as count from parent "
    			   + "group by status";
    	RawSql raw = RawSqlBuilder.parse(sql).create();
    	Query<ReportSummary> query = Ebean.find(ReportSummary.class);
    	query.setRawSql(raw).where().eq("created_by_email", user);
    	List<ReportSummary> list = query.findList();	                       // need to add entity class for the summary rows which allows relationships, etc..
    	return list;
    }

    
}
