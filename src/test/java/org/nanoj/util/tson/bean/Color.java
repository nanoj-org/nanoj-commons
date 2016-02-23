package org.nanoj.util.tson.bean;

public class Color {

	private String  id = null ;
	
	private boolean available = false ;

	//---------------------------------------------------------------
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean v) {
		this.available = v;
	}
	//---------------------------------------------------------------

	@Override
	public String toString() {
		return "Color [id=" + id + ", available=" + available + "]";
	}

	//---------------------------------------------------------------
	
}
