package com.project.sapper;

public class GameField  {
	public int mines[][];
	public int states[][];
	public boolean isGame;
	
	//Блок констант
	int WIDTH = 30; 
	int HEIGHT = 16; 
	int MINES = 99; 
	int DELAY = 1;
	int ACCURACY = 10;
	int ITERATIONS = 500;
	
	private static GameField gameField;
		
	public static void initInstance() { gameField = new GameField();}
	public static GameField getInstance() { return gameField;}
	
	/** Заполнение массива с расположением мин
	 * @param clickW Номер ячейки первого нажатия по x 
	 * @param clickH Номер ячейки первого нажатия по y 
	 */
	public void fillMines (int clickW, int clickH){
		mines = new int [WIDTH][HEIGHT];
		for(int i=0; i < WIDTH; i++){
			for(int j=0; j < HEIGHT; j++){
				mines[i][j] = 0 ;
			}
		}
		for (int g=0; g < MINES; g++){
			int w=(int)(Math.random()*WIDTH);
			int h=(int)(Math.random()*HEIGHT);
			while (mines[w][h]==9 ||(clickW == w && clickH == h)) {
				 w=(int)(Math.random()*WIDTH);
				 h=(int)(Math.random()*HEIGHT);	
			}
			mines[w][h]=9;
			updateNearCell(w,h);
		}
		
	}
	public void fillStates(){
		states = new int [WIDTH][HEIGHT];
		for(int i=0; i < WIDTH; i++){
			for(int j=0; j < HEIGHT; j++) {
				states[i][j]= 0;	
			}
		}
	}
	
	public void updateNearCell(int w, int h){
		if (w-1!=-1 && h-1!=-1) updateCell(w-1,h-1);
		if (h-1!=-1) updateCell(w,h-1);
		if (w+1!=WIDTH && h-1!=-1) updateCell(w+1,h-1);
		if (w-1!=-1) updateCell(w-1,h);
		if (w+1!=WIDTH) updateCell(w+1,h);
		if (w-1!=-1 && h+1!=HEIGHT) updateCell(w-1,h+1);
		if (h+1!=HEIGHT) updateCell(w,h+1);
		if (w+1!=WIDTH && h+1!=HEIGHT) updateCell(w+1,h+1);
	}
	public void updateCell(int w, int h){
		if (mines[w][h]!=9) mines[w][h]++;
	}
}
