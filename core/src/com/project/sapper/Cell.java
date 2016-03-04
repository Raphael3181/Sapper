package com.project.sapper;

public class Cell {
	int height;
	int width;
	public Cell(int w,int h) {
		width = w;
		height = h;
	}
	
	public boolean equals(Cell c) {
		return(this.width == c.width && this.height == c.height);
	}
}
