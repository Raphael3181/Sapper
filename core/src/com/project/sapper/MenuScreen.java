package com.project.sapper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {
	ScreenController sc;
	SpriteBatch batch;
	Texture buttons;
	TextureRegion buttonPlay, buttonAlg;
	Stage stage;
	OrthographicCamera camera;
	
	class GoToGameListener extends ClickListener {
		@Override
	    public void clicked(InputEvent event, float x, float y) {
			sc.setScreen(sc.gameScreen);
	    }
	 }
	
	public MenuScreen (SpriteBatch batch, ScreenController sc){
		this.batch = batch;
		this.sc = sc;
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    FitViewport viewp = new FitViewport(800,480, camera);
	    stage = new Stage(viewp, batch);
	    initButtons();
	}
	public void initButtons (){
		buttons = new Texture("buttons.png");
		buttonPlay = new  TextureRegion(buttons, 0, 0, 300, 75);
		buttonAlg = new  TextureRegion(buttons, 0, 75, 300, 75);
		createButton(buttonPlay, 10, 100);
		createButton(buttonAlg, 10, 10);
	}
	
	public void createButton(TextureRegion tr, int x, int y ){
		CustomActor button= new CustomActor(tr);
	    button.setSize(300, 75);
	    button.setPosition(x, y);
        button.addListener(new GoToGameListener());
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
