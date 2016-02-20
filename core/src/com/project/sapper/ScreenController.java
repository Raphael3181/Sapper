package com.project.sapper;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenController extends Game {
	SpriteBatch batch;
	MenuScreen menuScreen;
	GameScreen gameScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen (batch, this);
		setScreen(menuScreen);
		gameScreen = new GameScreen (batch, this);
	}	
}
