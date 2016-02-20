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
	OrthographicCamera camera;
	Stage stage;
	Texture objects;
	TextureRegion objectsTR[];
	//Блок констант
	int WIDTH=10; 
	int HEIGTH=10; 
	int MINES=10; 
	
	public GameScreen(SpriteBatch batch, ScreenController sc) {
		this.batch = batch;
		this.sc = sc;
		camera = new OrthographicCamera();
	    camera.setToOrtho(false,WIDTH*40,HEIGTH*40);
	    FitViewport viewp = new FitViewport(WIDTH*40,HEIGTH*40, camera);
	    stage = new Stage(viewp, batch);
	    initObjects();
	    fillField ();
	    
	}
	public void initObjects(){
		objects = new Texture("objects.jpg");
		objectsTR = new TextureRegion [16];
		for (int i=0; i<4; i++){
			for (int j=0; j<4; j++){
				objectsTR [4*i+j]= new TextureRegion(objects, 40*j, 40*i, 40, 40);
			}
		}
	}
	public void fillField (){
		for (int i=0; i<WIDTH; i++){
			for (int j=0; j<HEIGTH; j++){
			createCell(objectsTR[0],40*i, 40*j);
			}
		}
	}
	
	public void createCell(TextureRegion tr, int x, int y ){
		CustomActor object= new CustomActor(tr);
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
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
