package dev.fringe.model;

import lombok.Data;

@Data
public class Href {

	private String value;
	private String url;
	
	public String toString() {
		return "Href [name=" + value + ", url=" + url + "]";
	}
	public Href(String value, String url) {
		super();
		this.value = value;
		this.url = url;
	}
	public Href() {
		super();
	}
	
}
