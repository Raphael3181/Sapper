package com.project.sapper;

public class Tests {
	//Вывод в консоль всяких значений
	public static void printStates() {
		for (int i=0; i < GameScreen.WIDTH; i++){
			for (int j=0; j < GameScreen.HEIGHT; j++){
				System.out.print(GameField.getInstance().states[i][j] + " ");
			}
			System.out.println();
		}
	}
	public static void printMines() {
		for (int j=0; j < GameScreen.HEIGHT; j++){	
				for (int i=0; i < GameScreen.WIDTH; i++){
				System.out.print(GameField.getInstance().mines[i][j] + " ");
			}
			System.out.println();
		}
	}
}
