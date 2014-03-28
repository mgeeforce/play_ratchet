package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.avaje.ebean.annotation.Sql;


@Entity
@Sql
public class ReportSummary {
	
//	@OneToMany
//	public List<Parent> parents;
	
	public Parent.Status status;
	
	public Integer count;

}
