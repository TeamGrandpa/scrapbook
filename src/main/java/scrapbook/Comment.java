package scrapbook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;

	private String comment;
	
	@ManyToOne
	private Image image;
	
		
	protected Comment() {
		
	}

	public Comment(String comment, Image image) {
		this.comment = comment;
		this.image = image;
		
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
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
