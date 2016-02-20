package com.project.sapper;

public class GameField  {
	boolean mines[][];
	int states[][];
	
	private static GameField gameField;
		
	public static void initInstance() { gameField = new GameField(); }
	public static GameField getInstance() { return gameField;}
	
	public void fillMines (){
		mines = new boolean [GameScreen.WIDTH][GameScreen.HEIGTH];
		for(int i=0; i<GameScreen.WIDTH; i++ ){
			for(int j=0; i<GameScreen.HEIGTH; j++){
			mines[i][j] = false ;
			}
		}
		for (int g=0; g<GameScreen.MINES; g++){
			int h=(int)(Math.random()*GameScreen.WIDTH);
			int w=(int)(Math.random()*GameScreen.HEIGTH);
			while (mines[h][w]==true){
				 h=(int)(Math.random()*GameScreen.WIDTH);
				 w=(int)(Math.random()*GameScreen.HEIGTH);	
			}
			mines[h][w]=true;
		}
	}
	public void fillStates (){
		states = new int [GameScreen.WIDTH][GameScreen.HEIGTH];
		for(int i=0; i<GameScreen.WIDTH; i++ ){
			for(int j=0; i<GameScreen.HEIGTH; j++){
				states[i][j]= 0;	
			}
		}
	}
}
