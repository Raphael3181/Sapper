package com.project.sapper;

public class Cell {
	int height;
	int width;
	float chance;
	public Cell(int w,int h) {
		width = w;
		height = h;
	}
	public Cell(int w,int h, float ch) {
		width = w;
		height = h;
		chance = ch;
	}
	
	public boolean equals(Cell c) {
		return(this.width == c.width && this.height == c.height);
	}
	
	/**Расчёт количества мин в группе */
	public boolean notOverlap() {
		GameField field = GameField.getInstance();
		if(field.states[width][height] == 0) {
			if (width-1!=-1 && height-1!=-1) if(field.states[width-1][height-1] > 2) return false ;
			if (height-1!=-1) if(field.states[width][height-1] > 2) return false ;
			if (width+1!=field.WIDTH && height-1!=-1) if(field.states[width+1][height-1]>2) return false ;
			if (width-1!=-1) if(field.states[width-1][height] > 2) return false ;
			if (width+1!=field.WIDTH) if(field.states[width+1][height] > 2) return false ;
			if (width-1!=-1 && height+1!=field.HEIGHT) if(field.states[width-1][height+1] > 2) return false ;
			if (height+1!=field.HEIGHT) if(field.states[width][height+1] > 2) return false ;
			if (width+1!=field.WIDTH && height+1!=field.HEIGHT) if(field.states[width+1][height+1] > 2) return false ;
		} else return false;
		return true;
	}
}
