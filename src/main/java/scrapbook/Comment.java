package scrapbook;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;


@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;

	@Lob
	private String commentContent;
	
	private Instant commentTimestamp = Instant.now(); //Default comment instant time stamp from Epoch

	
	@ManyToOne
	private Image image;
	
		
	protected Comment() {
		
	}

	public Comment(String commentContent, Image image) {
		this.commentContent = commentContent;
		this.image = image;
		
	}
	
	//Getters
	public long getId() {
		return id;
	}

	public String getCommentContent() {
		return commentContent;
	}
	
	public String getCommentTimeStamp() {
		return commentTimestamp.toString();
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
