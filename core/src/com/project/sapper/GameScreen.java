package com.project.sapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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
	long startTime = 0;
	int minesLeft;
	int curIteration = 1;
	int wins = 0;
	
	static ArrayList<Group> groups;
	static ArrayList<Cell> cells; //Список ячеек для вспомогательного алгоритма
	static ArrayList<Chance> chances;
	
	class CustomListener extends ClickListener {
		@Override
	    public void clicked(InputEvent event, float x, float y) {
			Tests.printCells();
			if(field.isGame) {
				int w=(int)(x/40);
				int h=(int)(y/40);
				if(field.mines == null) field.fillMines(w, h);
				int state=field.mines[w][h]+2;
				if (state == 2) {
					openCell(w,h);
				} else if (state == 11){
					for (int i=0; i < field.WIDTH; i++ ) {
						for (int j=0; j < field.HEIGHT; j++ ) {
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
		field.fillStates();
		camera = new OrthographicCamera();
	    camera.setToOrtho(false,field.WIDTH*40, field.HEIGHT*40);
	    ScreenViewport viewp = new ScreenViewport(camera);
	    stage = new Stage(viewp, batch);
	    fillField ();
	    groups = new ArrayList<Group>();
	    chances = new ArrayList<Chance>();
	    if(field.noGUI && !field.isGame) {
	    	startTime = Calendar.getInstance().getTimeInMillis();
		    new Runnable() {
				public void run() {
					while(curIteration <= field.ITERATIONS) algorithm();
				}
		    }.run();
	    }
	}
	
	/** Открыть ячейку на игровом поле */
	public void openCell(int w, int h) {
		int state=field.mines[w][h]+2;
		int oldState = field.states[w][h];
		field.states[w][h] = state;
		if (state==2 && oldState==0) {
			if (w-1!=-1 && h-1!=-1) openCell(w-1,h-1);
			if (h-1!=-1) openCell(w,h-1);
			if (w+1!=field.WIDTH && h-1!=-1) openCell(w+1,h-1);
			if (w-1!=-1) openCell(w-1,h);
			if (w+1!=field.WIDTH) openCell(w+1,h);
			if (w-1!=-1 && h+1!=field.HEIGHT) openCell(w-1,h+1);
			if (h+1!=field.HEIGHT) openCell(w,h+1);
			if (w+1!=field.WIDTH && h+1!=field.HEIGHT) openCell(w+1,h+1);
		}
		if(!field.isGame && minesLeft == 0) { //все мины отмечены, всё ОК
			wins++;
			restartGame();
		} 
	}
	
	/** Отрисовать поле */
	public void fillField (){
		for (int i=0; i<field.WIDTH; i++){
			for (int j=0; j<field.HEIGHT; j++){
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
		if(!field.noGUI) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
			if(!(curIteration > field.ITERATIONS) && !field.isGame) algorithm();
			stage.draw();
		    stage.act(Gdx.graphics.getDeltaTime());
		}
	}
	
	public void algorithm() {
		Calendar calendar = Calendar.getInstance();
		if(time == 0) {
			minesLeft = field.MINES;
			algStep();
			time = calendar.getTimeInMillis();
		}
		else if(time + field.DELAY < calendar.getTimeInMillis() || field.noGUI) {
			time = calendar.getTimeInMillis();
			algStep();
		}
	}
	/** Шаг алгоритма */
	public void algStep() {
		if(field.mines == null) {
			int w =(int)(Math.random()*field.WIDTH);
			int h =(int)(Math.random()*field.HEIGHT);	
			field.fillMines(w, h);
			openCell(w, h);
			refreshGroups();
		} else {
			if(!openOrMark()){
				refreshGroups();
				if(!openOrMark()) {
					cells = new ArrayList<Cell>();
					Cell cell = getBestCell();
					if(cell == null)Tests.printArray(field.states);
					openCell(cell.width, cell.height);
					addToChances(cell);
					if(field.states[cell.width][cell.height] > 10) restartGame(); //Подорвался на мине...всё ОК :D	
				}
			}
		}	
	}
	
	/** Статистика попаданий на мины */
	public void addToChances(Cell c) {
		for(Chance chance: chances) {
			if(chance.chance == (int)(c.chance*100)) {
				chance.updateRealChance(field.states[c.width][c.height] > 10 ? true : false);
				return;
			}
		} 
		Chance chance = new Chance((int)(c.chance*100));
		chance.updateRealChance(field.states[c.width][c.height] > 10 ? true : false);
		chances.add(chance);
	}
	
	/**Обновление списка групп */
	public void refreshGroups() {
		groups = new ArrayList<Group>();
		for(int i=0; i < field.WIDTH; i++) {
			for(int j=0; j < field.HEIGHT; j++) {
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
		if (w+1!=field.WIDTH && h-1!=-1) if(field.states[w+1][h-1]==0)group.cells.add(new Cell(w+1, h-1)) ;
		if (w-1!=-1) if(field.states[w-1][h] == 0) group.cells.add(new Cell(w-1, h)) ;
		if (w+1!=field.WIDTH) if(field.states[w+1][h] == 0) group.cells.add(new Cell(w+1, h)) ;
		if (w-1!=-1 && h+1!=field.HEIGHT) if(field.states[w-1][h+1] == 0) group.cells.add(new Cell(w-1, h+1)) ;
		if (h+1!=field.HEIGHT) if(field.states[w][h+1] == 0) group.cells.add(new Cell(w, h+1)) ;
		if (w+1!=field.WIDTH && h+1!=field.HEIGHT) if(field.states[w+1][h+1] == 0) group.cells.add(new Cell(w+1, h+1)) ;
		if (group.cells.size() != 0) {
			group.number = calcNumber(w, h);
			groups.add(group);
		}
	}
	
	/**Расчёт количества мин в группе */
	private int calcNumber(int w, int h) {
		int number = field.states[w][h] - 2;
		if (w-1!=-1 && h-1!=-1) if(field.states[w-1][h-1] == 1) number-- ;
		if (h-1!=-1) if(field.states[w][h-1] == 1) number--;
		if (w+1!=field.WIDTH && h-1!=-1) if(field.states[w+1][h-1]==1)number--;
		if (w-1!=-1) if(field.states[w-1][h] == 1) number--;
		if (w+1!=field.WIDTH) if(field.states[w+1][h] == 1) number--;
		if (w-1!=-1 && h+1!=field.HEIGHT) if(field.states[w-1][h+1] == 1) number--;
		if (h+1!=field.HEIGHT) if(field.states[w][h+1] == 1) number--;
		if (w+1!=field.WIDTH && h+1!=field.HEIGHT) if(field.states[w+1][h+1] == 1) number--;
		return number;
	}
	
	/**Обработка групп*/
	public void handleGroups() {
		boolean repeat;
		do {
			repeat = false;
			//Удаление одинаковых групп
			for(int i=0; i < groups.size(); i++) {
				Group group = groups.get(i);
				for(int j=i+1; j < groups.size(); j++) {
					if(group.equals(groups.get(j))) {groups.remove(j); repeat = true; break;}
				}
			}
			//Разделение групп
			for(int i=0; i < groups.size(); i++) {
				Group g1 = groups.get(i);
				for(int j=i+1; j < groups.size(); j++) {
					Group g2 = groups.get(j);		
					if(g1.cells.size() > g2.cells.size()) repeat = devideGroups(g1, g2, i, j);
					else if(g2.cells.size() > g1.cells.size())repeat = devideGroups(g2, g1, j, i);
					if(repeat) break;	
				}
			}
		} while (repeat);
	}
	
	/**Разделение групп*/
	public boolean devideGroups(Group g1, Group g2, int i, int j) {	
		if(g1.contains(g2)) {
			Group ng = g1.difference(g2);
			ng.number = g1.number - g2.number;
			groups.remove(i);
			groups.add(ng);
			return true;
		}
		return false;
	}
	
	/** Попытка отметить или открыть ячейку*/
	public boolean openOrMark() {
		for(Group group: groups) {
			Cell cell = group.cells.get(0);
			if(group.number == 0) {
				openCell(cell.width, cell.height);
				group.cells.remove(0);
				if(group.cells.size() == 0) groups.remove(group);
				return true;
			} else if(group.number == group.cells.size()) {
				if(field.states[cell.width][cell.height] == 0) {
					field.states[cell.width][cell.height] = 1;
					group.cells.remove(0);
					minesLeft--;	
					if(group.cells.size() == 0) groups.remove(group);
					if(minesLeft == 0) { //все мины отмечены, всё ОК
						wins++;
						restartGame();
					} 
					return true;
				}	
			}
		}
		return false;
	}
	
	/** Получить ячейку с минимальным шансом подрыва */
	public Cell getBestCell() {
		setFirstChances();
		for(int i=0; i<field.ACCURACY; i++) {
			for(Group group: groups) {
				float summ = 0;
				for(Cell cell: group.cells) {
					for(Cell c: cells) {
						if(c.equals(cell)) {
							summ+=c.chance;
						}	
					}
				}
				float mnozh = (float)group.number/summ;
				for(Cell cell: group.cells) {
					for(Cell c: cells) {
						if(c.equals(cell)) {
							c.chance = c.chance*mnozh;
						}	
					}
				}
			}	
		}
		
		Cell rez = null;
		for(Cell cell: cells) {
			if(rez == null) rez = cell;
			else if(cell.chance < rez.chance) rez = cell;
		}
		if(cells.isEmpty()) { //Редкий случай, когда остались некотрытые ячейки в углу и группы не смогли сформироваться
			for(int i=0; i < field.WIDTH; i++){
				for(int j=0; j < field.HEIGHT; j++){
					if(field.states[i][j] == 0) rez = new Cell(i,j);
				}
			}
			
		}
		refreshGroups();
		return rez;
	}
	
	/** Формирование первоначальных шансов */
	public void setFirstChances() {
		Group ng = otherCells(); 
		if(ng.cells.size()!=0)groups.add(ng);
		for(Group group: groups) {
			for(Cell cell: group.cells) {
				boolean add = true;
				for(Cell c: cells) {
					if(c.equals(cell)) {
						add = false;
						c.chance = 1-(1-c.chance)*(1-(float)group.number/(float)group.cells.size());
					}
				}
				float chance = (float)group.number/(float)group.cells.size();
				if(chance < 0){
					chance = 0;
				}
				if(add)cells.add(new Cell(cell.width, cell.height, chance));
			}
		}
	}
	
	/**Формирование группы из ячеек, для которых неизвестно количество мин */
	public Group otherCells() {
		Group ng = new Group();
		ng.number = minesLeft;
		for(int i=0; i < field.WIDTH; i++){
			for(int j=0; j < field.HEIGHT; j++){
				Cell cell = new Cell(i,j);
				if(cell.notOverlap())ng.cells.add(cell);
			}
		}
		if(ng.number > ng.cells.size()) ng.number = ng.cells.size();
		return ng;
	}
	
	public void printResults() {
		Collections.sort(chances);
		File file = new File("results.txt");
		try {file.createNewFile();} 
		catch (IOException e) {}
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(file.getAbsoluteFile());
			out.write("ПОЛЕ: "+ field.WIDTH + "x" + field.HEIGHT + ", МИНЫ:" + field.MINES + 
					", ПОБЕДЫ:" + new BigDecimal((float)wins/(float)field.ITERATIONS*100).setScale(2, BigDecimal.ROUND_HALF_UP) 
					+ "% (" + wins + "/" + field.ITERATIONS + ")" + "\r\n");
			if(field.noGUI)out.write("ВРЕМЯ РАБОТЫ:" + new BigDecimal((float)((float)(Calendar.getInstance().getTimeInMillis()-startTime)/1000f))
					.setScale(2, BigDecimal.ROUND_HALF_UP) +" с." + "\r\n");
			for(Chance chance: chances) {
				if(chance.chance == 0) out.write("ШАНС НЕИЗВЕСТЕН" + "::" + chance.realChance + " "+ chance.mines+ "/" + (chance.mines + chance.noMines) +"\r\n");
				else out.write(chance.chance + "::" + chance.realChance + " "+ chance.mines+ "/" + (chance.mines + chance.noMines) +"\r\n");
			}	
		} catch (FileNotFoundException e) {}
		finally {
			out.close();
		}	    	
	}
	
	/** Перезапуск игры */
	public void restartGame() {
		curIteration++;
		if(curIteration > field.ITERATIONS) printResults();
		field.fillStates();
		fillField();
		field.mines = null;
		time = 0;
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
