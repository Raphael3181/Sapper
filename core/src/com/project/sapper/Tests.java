package com.project.sapper;

import java.util.Iterator;

public class Tests {
	//Вывод в консоль значений массивов
	public static void printArray(int[][] array) {
		for (int j=GameScreen.HEIGHT-1; j > -1; j--){	
				for (int i=0; i < GameScreen.WIDTH; i++){
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	//Вывод содержимого групп
	public static void printGroups() {
		Iterator<Group> iterGroup = GameScreen.groups.iterator();
	    while(iterGroup.hasNext()) {
	    	Group group = iterGroup.next();
	    	Iterator<Cell> iterCell = group.cells.iterator();
	    	while(iterCell.hasNext()) {
		    	Cell cell = iterCell.next();
		    	System.out.print(cell.width + "::" + cell.height + "  ");
	    	}
	    	System.out.println(group.number);
	    }
	}
}
