package scrapbook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Kid {
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	public long getId() {
		return id;
	}
	//getters
	public String getName() {
		return name;
	}

	public Kid(String name) {
		this.name = name;		
	}
	
	//empty constructor
	protected Kid() {
		
	}
	

}
