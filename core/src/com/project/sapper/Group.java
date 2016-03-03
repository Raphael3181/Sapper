package com.project.sapper;

import java.util.ArrayList;

public class Group {
	public ArrayList<Cell> cells; //Лист содержащий ячейки группы
	int number; //Количество мин в группе
	public Group() {
		cells = new ArrayList<Cell>();
	}
}
