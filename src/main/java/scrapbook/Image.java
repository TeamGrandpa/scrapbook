package scrapbook;

import java.util.Collection;

import javax.persistence.Cacheable;
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
	
	private String date;

	@ManyToOne
	private Kid kid;

	@OneToMany(mappedBy = "image")
	private Collection<Comment> comments;
	
	@OneToMany(mappedBy = "image")
	private Collection<Heart> hearts;

	protected Image() {

	}

	public Image(String imageUrl, String caption, Kid kid) {
		this.imageUrl = imageUrl;
		this.caption = caption;
		this.kid = kid;
	}
	
	public Image(String imageUrl, String caption, String date, Kid kid) {
		this.imageUrl = imageUrl;
		this.caption = caption;
		this.date = date;
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
	
	public String getDate() {
		return date;
	}

	public Collection<Comment> getComments() {
		return comments;
	}
	
	public Collection<Heart> getHearts() {
		return hearts;
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
