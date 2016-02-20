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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class GameScreen implements Screen {
	
	ScreenController sc;
	SpriteBatch batch;
	TextureHelper textures;
	GameField field;
	OrthographicCamera camera;
	Stage stage;
	
	//Блок констант
	static int WIDTH = 15; 
	static int HEIGHT = 10; 
	static int MINES = 10; 
	
	class CustomListener extends ClickListener {
		@Override
	    public void clicked(InputEvent event, float x, float y) {
			System.out.println(x + " " + y);
	    }
	 }
	public GameScreen(SpriteBatch batch, ScreenController sc) {
		this.batch = batch;
		this.sc = sc;
		textures = TextureHelper.getInstance();
		field = GameField.getInstance();
		field.width = WIDTH;
		field.height = HEIGHT;
		field.fillMines();
		field.fillStates();
		camera = new OrthographicCamera();
	    camera.setToOrtho(false,WIDTH*40,HEIGHT*40);
	    ScreenViewport viewp = new ScreenViewport(camera);
	    stage = new Stage(viewp, batch);
	    fillField ();
	}
	
	public void fillField (){
		for (int i=0; i<WIDTH; i++){
			for (int j=0; j<HEIGHT; j++){
				createCell(textures.objectsTR[0],40*i, 40*j, i, j);
			}
		}
	}
	
	public void createCell(TextureRegion tr, int x, int y, int w, int h){
		Cell cell = new Cell();
		cell.setSize(40,40);
		cell.setPosition(x, y);
	    stage.addActor(cell);
	    cell.w = w;
	    cell.h = h;
	    System.out.println(cell.getX());
    }

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);	
		stage.addListener(new CustomListener());
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
