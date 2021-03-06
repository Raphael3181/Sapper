package com.project.sapper;

import java.util.Iterator;

public class Tests {
	//Вывод в консоль значений массивов
	public static void printArray(int[][] array) {
		for (int j=GameField.getInstance().HEIGHT-1; j > -1; j--){	
				for (int i=0; i < GameField.getInstance().WIDTH; i++){
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
	    	group.print();
	    }
	}
	//Вывод содержимого массива ячеек
		public static void printCells() {
			Iterator<Cell> iter = GameScreen.cells.iterator();
		    while(iter.hasNext()) {
		    	Cell cell = iter.next();
			    System.out.print(cell.width + "::" + cell.height + "---" + cell.chance + "   ");
		    }
		}
}
