package org.nanoj.util.tson.bean;

import java.util.ArrayList;
import java.util.List;

public class EquipementsAndColors {

	private List<Equipement>  equipements = new ArrayList<> () ;

	private List<Color>  colors = new ArrayList<> () ;

	//---------------------------------------------------------------
	public List<Equipement> getEquipements() {
		return equipements;
	}

	public void setEquipements(List<Equipement> equipements) {
		this.equipements = equipements;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	@Override
	public String toString() {
		return "EquipementsAndColors [ \n equipements=" + equipements 
				+ ", \n colors="
				+ colors + "]";
	}

	//---------------------------------------------------------------

	
}
