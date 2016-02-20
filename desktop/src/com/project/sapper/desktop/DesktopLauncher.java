package com.project.sapper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.project.sapper.ScreenController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Sapper";
	    config.width = 800;
	    config.height = 480;
	    config.fullscreen = false;
		new LwjglApplication(new ScreenController() , config);
	}
}
