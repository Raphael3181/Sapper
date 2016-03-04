package com.project.sapper;

public class GameField  {
	public int mines[][];
	public int states[][];
	public int chanсes[][];
	public int width;
	public int height;
	public boolean isGame;
	
	private static GameField gameField;
		
	public static void initInstance() { gameField = new GameField();}
	public static GameField getInstance() { return gameField;}
	
	/** Заполнение массива с расположением мин
	 * @param clickW Номер ячейки первого нажатия по x 
	 * @param clickH Номер ячейки первого нажатия по y 
	 */
	public void fillMines (int clickW, int clickH){
		mines = new int [width][height];
		for(int i=0; i < width; i++){
			for(int j=0; j < height; j++){
				mines[i][j] = 0 ;
			}
		}
		for (int g=0; g<GameScreen.MINES; g++){
			int w=(int)(Math.random()*width);
			int h=(int)(Math.random()*height);
			while (mines[w][h]==9 ||(clickW == w && clickH == h)){
				 w=(int)(Math.random()*width);
				 h=(int)(Math.random()*height);	
			}
			mines[w][h]=9;
			updateNearCell(w,h);
		}
		
	}
	public void fillStatesAndChances (){
		states = new int [width][height];
		chanсes = new int [width][height];
		for(int i=0; i < width; i++){
			for(int j=0; j < height; j++) {
				states[i][j]= 0;	
				chanсes[i][j]= -1;
			}
		}
	}
	
	public void updateNearCell(int w, int h){
		if (w-1!=-1 && h-1!=-1) updateCell(w-1,h-1);
		if (h-1!=-1) updateCell(w,h-1);
		if (w+1!=width && h-1!=-1) updateCell(w+1,h-1);
		if (w-1!=-1) updateCell(w-1,h);
		if (w+1!=width) updateCell(w+1,h);
		if (w-1!=-1 && h+1!=height) updateCell(w-1,h+1);
		if (h+1!=height) updateCell(w,h+1);
		if (w+1!=width && h+1!=height) updateCell(w+1,h+1);
	}
	public void updateCell(int w, int h){
		if (mines[w][h]!=9) mines[w][h]++;
	}
}
