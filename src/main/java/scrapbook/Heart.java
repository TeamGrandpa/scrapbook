package scrapbook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Heart {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Enduser enduser;

	@JsonIgnore
	@ManyToOne
	private Image image;

	// empty constructor
	protected Heart() {

	}

	public Heart(Enduser enduser, Image image) {
		this.enduser = enduser;
		this.image = image;
	}

	//getters
	public long getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public Enduser getEnduser() {
		return enduser;
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
		Heart other = (Heart) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
