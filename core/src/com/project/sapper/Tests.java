package com.project.sapper;

public class Tests {
	//Вывод в консоль всяких значений
	public static void printStates() {
		for (int j=GameScreen.HEIGHT-1; j > -1; j--){	
			for (int i=0; i < GameScreen.WIDTH; i++){
				System.out.print(GameField.getInstance().states[i][j] + " ");
			}
			System.out.println();
		}
	}
	public static void printMines() {
		for (int j=GameScreen.HEIGHT-1; j > -1; j--){	
				for (int i=0; i < GameScreen.WIDTH; i++){
				System.out.print(GameField.getInstance().mines[i][j] + " ");
			}
			System.out.println();
		}
	}
}
