package com.project.sapper;

import java.util.ArrayList;

public class Group {
	public ArrayList<Cell> cells; //Лист содержащий ячейки группы
	int number; //Количество мин в группе
	public Group() {
		cells = new ArrayList<Cell>();
	}
	
	public boolean equals (Group g) {
		if(this.cells.size() == g.cells.size()) {
			for(int i = 0; i< this.cells.size(); i++) if(!this.cells.get(i).equals(g.cells.get(i))) return false;
			return true;
		}
		return false;
	}
	
	public boolean contains(Group g) {
		boolean next;
		for(int i =0; i < g.cells.size(); i++) {
			next = false;
			Cell c1 = g.cells.get(i);
			for(int j = 0; j < cells.size(); j++) {
				Cell c2 = cells.get(j);
				if(c1.equals(c2)) next = true;
			}
			if(!next) return false;
		}
		return true;
	}
}
