package com.project.sapper;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class GameField  {
	public int mines[][];
	public int states[][];
	public boolean isGame;
	
	//Блок констант
	int WIDTH = 9; 
	int HEIGHT = 9; 
	int MINES = 10; 
	int DELAY = 0;
	int ACCURACY = 10;
	int ITERATIONS = 10;
	boolean noGUI = false;
	
	private static GameField gameField;
		
	public static void initInstance() { 
		gameField = new GameField();
		gameField.readConstants();
	}
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
	/** Заполнить массив состояний */
	public void fillStates(){
		states = new int [WIDTH][HEIGHT];
		for(int i=0; i < WIDTH; i++){
			for(int j=0; j < HEIGHT; j++) {
				states[i][j]= 0;	
			}
		}
	}
	
	/** Пересчитать количество мин при добавлении мины */
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
	
	public void readConstants() {
		String line = null;
	    FileHandle file = Gdx.files.local("const.cfg");
	    BufferedReader reader = new BufferedReader(file.reader());
	    try {line = reader.readLine();} catch (IOException e) {}
	    while( line != null ) {
		    String str[] = line.split(" ");
		    if(str[0].equals("WIDTH")) WIDTH = Integer.valueOf(str[1]);
		    if(str[0].equals("HEIGHT")) HEIGHT = Integer.valueOf(str[1]);
		    if(str[0].equals("ACCURACY")) ACCURACY = Integer.valueOf(str[1]);
		    if(str[0].equals("ITERATIONS")) ITERATIONS = Integer.valueOf(str[1]);
		    if(str[0].equals("MINES")) MINES = Integer.valueOf(str[1]);
		    if(str[0].equals("DELAY")) DELAY = Integer.valueOf(str[1]);
		    if(line.equals("noGUI")) noGUI = true;
		    try {line = reader.readLine();} catch (IOException e) {}
	    }
	}
}
