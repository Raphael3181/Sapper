package com.project.sapper;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	/** Вычесть меньшую группу из большей группы */
	public Group difference(Group group) {
		Group newGroup = new Group();
			for(int i=0; i< cells.size(); i++) {
				boolean add = true;
				Cell c1 = cells.get(i);
				for(int j=0; j< group.cells.size(); j++) {
					Cell c2 = group.cells.get(j);
					if(c1.equals(c2)) { add = false; break;}
				}
				if(add)newGroup.cells.add(c1);
			}
		return newGroup;
	}
	
	public void print() {
		Iterator<Cell> iterCell = cells.iterator();
    	while(iterCell.hasNext()) {
	    	Cell cell = iterCell.next();
	    	System.out.print(cell.width + "::" + cell.height + "  ");
    	}
    	System.out.println(number);
	}
}
