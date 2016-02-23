package org.nanoj.util.tson.bean;

public class Equipement {

	private int  id = 0 ;
	
	private String text  = null ;

	//---------------------------------------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	//---------------------------------------------------------------

	@Override
	public String toString() {
		return "Equipement [id=" + id + ", text=" + text + "]";
	}

	//---------------------------------------------------------------
	
}
