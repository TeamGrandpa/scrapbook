package scrapbook;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Kid {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private String portraitImageUrl;
	
	private int colorNum;
	
	private boolean notify;
	
	@OneToMany(mappedBy = "kid")
	private Collection<Image> images;
	
	@OneToMany(mappedBy = "kid")
	private Collection<Message> messages;
	
	
	//empty constructor
	protected Kid() {
		
	}
	
	public Kid(String name, Image...images) {
		this.name = name;
		this.images = new HashSet<>(Arrays.asList(images));
	}
	
	public Kid(String name, String portraitImageUrl, int colorNum, boolean notify, Image...images) {
		this.name = name;
		this.portraitImageUrl = portraitImageUrl;
		this.colorNum = colorNum;
		this.notify = notify;
		this.images = new HashSet<>(Arrays.asList(images));
	}
	
	//getters
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPortraitImageUrl() {
		return portraitImageUrl;
	}
	
	public int getColorNum() {
		return colorNum;
	}
	
	public boolean getNotify() {
		return notify;
	}
	
	public Collection<Image> getImages() {		
		return images;
	}
	
	public Collection<Message> getMessages() {		
		return messages;
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
		Kid other = (Kid) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
