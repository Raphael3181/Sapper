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
}
