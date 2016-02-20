package com.project.sapper;

public class GameField  {
	boolean mines[][];
	int states[][];
	int width;
	int height;
	
	private static GameField gameField;
		
	public static void initInstance() { gameField = new GameField();}
	public static GameField getInstance() { return gameField;}
	
	public void fillMines (){
		mines = new boolean [width][height];
		for(int i=0; i < width; i++){
			for(int j=0; j < height; j++){
				mines[i][j] = false ;
			}
		}
		for (int g=0; g<GameScreen.MINES; g++){
			int h=(int)(Math.random()*width);
			int w=(int)(Math.random()*height);
			while (mines[h][w]==true){
				 h=(int)(Math.random()*width);
				 w=(int)(Math.random()*height);	
			}
			mines[h][w]=true;
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
}
