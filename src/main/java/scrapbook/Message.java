package scrapbook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Message {
	
	@Id
	@GeneratedValue
	private long id;

	private String content;
	
	@ManyToOne
	private Kid kid;
	
	protected Message() {
		
	}

	public Message(String content, Kid kid) {
		this.content = content;
		this.kid = kid;
		
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
		Message other = (Message) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
