package scrapbook;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Kid {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private String portraitUrl;
	
	private int colorNum;
	
	private boolean notify;
	
	@JsonIgnore
	@OneToMany(mappedBy = "kid")
	private Collection<Image> images;
	
	//empty constructor
	protected Kid() {
		
	}
	
	public Kid(String name, Image...images) {
		this.name = name;
		this.images = new HashSet<>(Arrays.asList(images));
	}
	
	public Kid(String name, String portraitUrl, int colorNum, boolean notify, Image...images) {
		this.name = name;
		this.portraitUrl = portraitUrl;
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
	
	public String getPortraitUrl() {
		return portraitUrl;
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
