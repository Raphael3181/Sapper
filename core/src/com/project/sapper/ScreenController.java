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
		TextureHelper.initInstance();
		GameField.initInstance();
		menuScreen = new MenuScreen (batch, this);
		gameScreen = new GameScreen (batch, this);
		setScreen(menuScreen);
	}	
}
