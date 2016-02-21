package com.project.sapper;

public class GameField  {
	int mines[][];
	int states[][];
	int width;
	int height;
	
	private static GameField gameField;
		
	public static void initInstance() { gameField = new GameField();}
	public static GameField getInstance() { return gameField;}
	
	public void fillMines (){
		mines = new int [width][height];
		for(int i=0; i < width; i++){
			for(int j=0; j < height; j++){
				mines[i][j] = 0 ;
			}
		}
		for (int g=0; g<GameScreen.MINES; g++){
			int w=(int)(Math.random()*width);
			int h=(int)(Math.random()*height);
			while (mines[w][h]==9){
				 w=(int)(Math.random()*width);
				 h=(int)(Math.random()*height);	
			}
			mines[w][h]=9;
			updateNearCell(w,h);
		}
		
	}
	public void fillStates (){
		states = new int [width][height];
		for(int i=0; i < width; i++){
			for(int j=0; j < height; j++){
				states[i][j]= 0;	
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
		if (h+1!=-1) updateCell(w,h+1);
		if (w+1!=-1 && h+1!=-1) updateCell(w+1,h+1);
	}
	public void updateCell(int w, int h){
		if (mines[w][h]!=9) mines[w][h]++;
	}
}
