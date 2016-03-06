package com.project.sapper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHelper {
	
	private static TextureHelper textureHelper;
	//Текстуры
	public Texture buttons;
	public Texture objects;
	//Регионы текстур
	TextureRegion objectsTR[], buttonPlay, buttonAlg;;
	
	public static void initInstance() { textureHelper = new TextureHelper(); textureHelper.initTextures(); }
	public static TextureHelper getInstance() { return textureHelper;}
	
	public void initTextures() {
		buttons = new Texture("buttons.png");
		buttonPlay = new  TextureRegion(buttons, 0, 0, 300, 75);
		buttonAlg = new  TextureRegion(buttons, 0, 75, 300, 75);
		objects = new Texture("graphic.png");
		initObjects();
	}
	
	public void initObjects(){
		objectsTR = new TextureRegion [16];
		for (int i=0; i<4; i++){
			for (int j=0; j<4; j++){
				objectsTR [4*i+j]= new TextureRegion(objects, 40*j, 40*i, 40, 40);
			}
		}
	}
}
