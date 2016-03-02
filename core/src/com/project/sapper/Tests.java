package com.project.sapper;

public class Tests {
	//Вывод в консоль всяких значений
	public static void printArray(int[][] array) {
		for (int j=GameScreen.HEIGHT-1; j > -1; j--){	
				for (int i=0; i < GameScreen.WIDTH; i++){
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}
