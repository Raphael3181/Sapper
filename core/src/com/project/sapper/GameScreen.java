package com.project.sapper;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
	
	ScreenController sc;
	SpriteBatch batch;
	TextureHelper textures;
	GameField field;
	OrthographicCamera camera;
	Stage stage;
	long time = 0;
	
	//Блок констант
	static int WIDTH = 15; 
	static int HEIGHT = 10; 
	static int MINES = 30; 
	static int DELAY = 700; 
	
	class CustomListener extends ClickListener {
		@Override
	    public void clicked(InputEvent event, float x, float y) {
			if(field.isGame) {
				int w=(int)(x/40);
				int h=(int)(y/40);
				if(field.mines == null) field.fillMines(w, h);
				int state=field.mines[w][h]+2;
				if (state == 2) {
					openCell(w,h);
				} else if (state == 11){
					for (int i=0; i<WIDTH; i++ ) {
						for (int j=0; j<HEIGHT; j++ ) {
							if (field.mines[i][j] == 9) field.states[i][j] = field.mines[i][j]+2;
						}
					}
					field.states[w][h] = 13;
						
				} else field.states[w][h] = state;
			}
	    }
	}
	
	public GameScreen(SpriteBatch batch, ScreenController sc) {
		this.batch = batch;
		this.sc = sc;
		textures = TextureHelper.getInstance();
		field = GameField.getInstance();
		field.width = WIDTH;
		field.height = HEIGHT;
		field.fillStatesAndChances();
		camera = new OrthographicCamera();
	    camera.setToOrtho(false,WIDTH*40,HEIGHT*40);
	    ScreenViewport viewp = new ScreenViewport(camera);
	    stage = new Stage(viewp, batch);
	    fillField ();
	}
	
	public void openCell(int w, int h) {
		int state=field.mines[w][h]+2;
		int oldState = field.states[w][h];
		field.states[w][h] = state;
		if (state==2 && oldState==0) {
			if (w-1!=-1 && h-1!=-1) openCell(w-1,h-1);
			if (h-1!=-1) openCell(w,h-1);
			if (w+1!=WIDTH && h-1!=-1) openCell(w+1,h-1);
			if (w-1!=-1) openCell(w-1,h);
			if (w+1!=WIDTH) openCell(w+1,h);
			if (w-1!=-1 && h+1!=HEIGHT) openCell(w-1,h+1);
			if (h+1!=HEIGHT) openCell(w,h+1);
			if (w+1!=WIDTH && h+1!=HEIGHT) openCell(w+1,h+1);
		}
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
    }

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);	
		stage.addListener(new CustomListener());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!field.isGame) algorithm();
		stage.draw();
	    stage.act(Gdx.graphics.getDeltaTime());
	}
	
	public void algorithm() {
		Calendar calendar = Calendar.getInstance();
		if(time == 0) time = calendar.getTimeInMillis();
		else if(time + DELAY < calendar.getTimeInMillis()) {
			time = calendar.getTimeInMillis();
			algStep();
		}
	}
	
	public void algStep() {
		if(field.mines == null) field.fillMines((int)(Math.random()*WIDTH), (int)(Math.random()*WIDTH));
		int w =(int)(Math.random()*WIDTH);
		int h =(int)(Math.random()*HEIGHT);	
		openCell(w, h);
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
