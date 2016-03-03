package com.project.sapper;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CellTexture extends Actor {
	int w,h;
	
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	TextureHelper textures = TextureHelper.getInstance();
    	GameField field = GameField.getInstance();
    	batch.draw(textures.objectsTR[field.states[w][h]], getX(), getY(), getWidth(), getHeight());
    }
}
