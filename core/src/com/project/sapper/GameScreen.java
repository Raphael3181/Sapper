package com.project.sapper;

import java.util.ArrayList;
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
	static ArrayList<Group> groups;
	
	//Блок констант
	static int WIDTH = 15; 
	static int HEIGHT = 10; 
	static int MINES = 25; 
	static int DELAY = 3000; 
	
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
			groups = new ArrayList<Group>();
		refreshGroups();
		Tests.printGroups();
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
	    groups = new ArrayList<Group>();
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
		CellTexture cell = new CellTexture();
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
			Tests.printGroups();
		}
	}
	/** Шаг алгоритма */
	public void algStep() {
		if(field.mines == null) {
			int w =(int)(Math.random()*WIDTH);
			int h =(int)(Math.random()*HEIGHT);	
			field.fillMines(w, h);
			openCell(w, h); 
		} else {
			refreshGroups();
		}	
	}
	
	/**Обновление списка групп */
	public void refreshGroups() {
		for(int i=0; i < WIDTH; i++) {
			for(int j=0; j < HEIGHT; j++) {
				if(field.states[i][j] > 2 && field.states[i][j] < 11) addGroup(i, j);
			}
		}
		handleGroups();
	}
	
	/**Нужно ли добавлять группу для данной ячейки */
	public void addGroup(int w, int h) {
		Group group = new Group();
		if (w-1!=-1 && h-1!=-1) if(field.states[w-1][h-1] == 0) group.cells.add(new Cell(w-1, h-1)) ;
		if (h-1!=-1) if(field.states[w][h-1] == 0) group.cells.add(new Cell(w, h-1)) ;
		if (w+1!=WIDTH && h-1!=-1) if(field.states[w+1][h-1]==0)group.cells.add(new Cell(w+1, h-1)) ;
		if (w-1!=-1) if(field.states[w-1][h] == 0) group.cells.add(new Cell(w-1, h)) ;
		if (w+1!=WIDTH) if(field.states[w+1][h] == 0) group.cells.add(new Cell(w+1, h)) ;
		if (w-1!=-1 && h+1!=HEIGHT) if(field.states[w-1][h+1] == 0) group.cells.add(new Cell(w-1, h+1)) ;
		if (h+1!=HEIGHT) if(field.states[w][h+1] == 0) group.cells.add(new Cell(w, h+1)) ;
		if (w+1!=WIDTH && h+1!=HEIGHT) if(field.states[w+1][h+1] == 0) group.cells.add(new Cell(w+1, h+1)) ;
		if (group.cells.size() != 0) {
			group.number = field.states[w][h] - 2;
			groups.add(group);
		}
	}
	
	public void handleGroups() {
		boolean repeat;
		do {
			repeat = false;
			//Удаление одинаковых групп
			for(int i=0; i < groups.size(); i++) {
				Group group = groups.get(i);
				for(int j=0; j < groups.size(); j++) {
					if(i!=j && group.equals(groups.get(j))) {groups.remove(j); repeat = true; break;}
				}
				if(repeat) break;
			}
			//Разделение групп
			for(int i=0; i < groups.size(); i++) {
				Group g1 = groups.get(i);
				for(int j=i+1; j < groups.size(); j++) {
					Group g2 = groups.get(j);
					repeat = devideGroups(g1, g2, i, j);
					if(repeat) break;
					
				}
				if(repeat) break;
			}
		} while (repeat);
	}
	
	public boolean devideGroups(Group g1, Group g2, int i, int j) {	
		Group ng = new Group();
		if(g1.cells.size() > g2.cells.size()) {
			if(g1.contains(g2)) {
				ng = difference(g1, g2);
				ng.number = g1.number - g2.number;
				groups.remove(i);
				groups.add(ng);
				return true;
			}
		} else if(g2.cells.size() > g1.cells.size()) {
			if(g2.contains(g1)) {
				ng = difference(g2, g1);
				ng.number = g2.number - g1.number;
				groups.remove(j);
				groups.add(ng);
				return true;
			}
		}
		return false;
	}
	
	public Group intersection (Group g1, Group g2) {
		Group newGroup = new Group();
		for(int i=0; i< g1.cells.size(); i++) {
			Cell c1 = g1.cells.get(i);
			for(int j=0; j< g2.cells.size(); j++) {
				Cell c2 = g2.cells.get(j);
				if(c1.equals(c2)) {
					newGroup.cells.add(c1);
					break;
				}
			}
		}
		return newGroup;
	}
	
	public Group difference(Group g1, Group g2) {
		Group newGroup = new Group();
			for(int i=0; i< g1.cells.size(); i++) {
				boolean add = true;
				Cell c1 = g1.cells.get(i);
				for(int j=0; j< g2.cells.size(); j++) {
					Cell c2 = g2.cells.get(j);
					if(c1.equals(c2)) add = false;
				}
				if(add)newGroup.cells.add(c1);
			}
		return newGroup;
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
