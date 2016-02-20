package com.project.sapper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cell extends Actor {
	TextureRegion tr;
	int w,h;
	public Cell(TextureRegion tr) {
		this.tr = tr;
	}
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	batch.draw(TextureHelper.getInstance()., getX(), getY(), getWidth(), getHeight());
    }

}
