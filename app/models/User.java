/**
 * 
 */
package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;

import play.Logger;
import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author YK4P
 *
 */
@Entity
public class User extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    @NonEmpty
    @Required
    public String email;
    
    @Required
    public String name;

    @Required
    public String password;
    
    @OneToMany
    public List<Parent> parents;
    
    
    public User(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }
    
    public static User authenticate(String email, String password) {
    	Logger.info("User.authenticate called with email = "+email+" and password = "+password);
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }

    public static Finder<String,User> find = new Finder<String,User>(
        String.class, User.class
    );
    
    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public int getUnfiledCount() {
    	return Ebean.find(Item.class)
    			.where()
    			.eq("created_by_email", email)
    			.isNull("parent")
    			.findRowCount();
    }
}