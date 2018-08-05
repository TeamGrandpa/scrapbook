package scrapbook;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Enduser {

	@Id
	@GeneratedValue
	private long id;
	private String userName;
	private boolean isEditor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "enduser")
	private Collection<Comment> comments;
	

	//default no args constructor
	protected Enduser() {
		
	}
	
	
	//constructor
	public Enduser(String userName, boolean isEditor) {
		this.userName = userName;
		this.isEditor = isEditor;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		
		return userName;
	}
	
	public boolean getIsEditor() {
		return isEditor;
	}

	public Collection<Comment> getComments() {
		
		return comments;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enduser other = (Enduser) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}