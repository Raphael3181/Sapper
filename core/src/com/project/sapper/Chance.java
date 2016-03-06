package com.project.sapper;

public class Chance implements Comparable<Chance> {
	int chance;
	int realChance;
	int mines = 0;
	int noMines = 0;
	
	public Chance(int chance) {
		this.chance = chance;
	}
	
	public void updateRealChance(boolean mine) {
		if(mine)mines++;
		else noMines++;
		realChance = (int)((float)mines/(float)(mines + noMines) * 100);
	}

	@Override
	public int compareTo(Chance other) { return Integer.compare(chance, other.chance); }
}
