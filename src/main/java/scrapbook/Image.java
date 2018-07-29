package scrapbook;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Image {

	@Id
	@GeneratedValue
	private long id;

	private String imageUrl;
	private String caption;

	@ManyToOne
	private Kid kid;

	@OneToMany(mappedBy = "image")
	private Collection<Comment> comments;

	protected Image() {

	}

	public Image(String imageUrl, String caption, Kid kid) {
		this.imageUrl = imageUrl;
		this.caption = caption;
		this.kid = kid;

	}

	public long getId() {
		return id;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getCaption() {
		return caption;
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
		Image other = (Image) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
