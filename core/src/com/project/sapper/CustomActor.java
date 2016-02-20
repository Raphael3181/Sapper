package com.project.sapper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
public class CustomActor extends Actor {
	TextureRegion tr;
	Texture texture;
	public CustomActor(TextureRegion tr) {
		this.tr = tr;
	}
	public CustomActor(Texture tr) {
		texture = tr;
	}
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	if(tr != null) batch.draw(tr, getX(), getY(), getWidth(), getHeight());
    	else batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}