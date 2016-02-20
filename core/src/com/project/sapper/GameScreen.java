package com.project.sapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class GameScreen implements Screen {
	
	ScreenController sc;
	SpriteBatch batch;
	TextureHelper textures;
	OrthographicCamera camera;
	Stage stage;
	
	//Блок констант
	int WIDTH=10; 
	int HEIGTH=10; 
	int MINES=10; 
	
	public GameScreen(SpriteBatch batch, ScreenController sc) {
		this.batch = batch;
		this.sc = sc;
		textures = TextureHelper.getInstance();
		camera = new OrthographicCamera();
	    camera.setToOrtho(false,WIDTH*40,HEIGTH*40);
	    FitViewport viewp = new FitViewport(WIDTH*40,HEIGTH*40, camera);
	    stage = new Stage(viewp, batch);
	    fillField ();
	    
	}
	
	public void fillField (){
		for (int i=0; i<WIDTH; i++){
			for (int j=0; j<HEIGTH; j++){
				createCell(textures.objectsTR[0],40*i, 40*j);
			}
		}
	}
	
	public void createCell(TextureRegion tr, int x, int y ){
		CustomActor object = new CustomActor(tr);
		object.setSize(40,40);
		object.setPosition(x, y);
	    stage.addActor(object);
    }

	@Override
	public void show() {
		Gdx.graphics.setDisplayMode(WIDTH*40, HEIGTH*40, false);
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
