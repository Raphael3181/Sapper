package com.project.sapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {
	ScreenController sc;
	TextureHelper textures;
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera camera;
	GameField field;
	
	class ButtonListener extends ClickListener {
		boolean isGame;
		public ButtonListener(boolean isGame) {
			this.isGame = isGame;
		}
		@Override
	    public void clicked(InputEvent event, float x, float y) {
			field = GameField.getInstance();
			field.isGame = isGame;
			Gdx.graphics.setDisplayMode(field.WIDTH*40, field.HEIGHT*40, false);
			sc.gameScreen = new GameScreen (sc.batch, sc);
			sc.setScreen(sc.gameScreen);
	    }
	 }
	
	public MenuScreen (SpriteBatch batch, ScreenController sc){
		this.batch = batch;
		this.sc = sc;
		textures = TextureHelper.getInstance();
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    FitViewport viewp = new FitViewport(800,480, camera);
	    stage = new Stage(viewp, batch);
	    initButtons();
	}
	
	public void initButtons (){
		createButton(textures.buttonPlay, 400 - textures.buttonPlay.getRegionWidth()/2 , 250);
		createButton(textures.buttonAlg, 400 - textures.buttonPlay.getRegionWidth()/2, 150);
	}
	
	public void createButton(TextureRegion tr, int x, int y ){
		CustomActor button= new CustomActor(tr);
	    button.setSize(300, 75);
	    button.setPosition(x, y);
        button.addListener(new ButtonListener(tr.equals(textures.buttonPlay) ? true : false));
	    stage.addActor(button);
    }

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.draw();
	    stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
