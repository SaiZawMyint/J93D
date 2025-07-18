package com.flexitech.products.three_d;

import java.awt.Color;

import com.flexitech.products.three_d.screen.J3DFrame;
import com.flexitech.products.three_d.screen.J3DScreen;

public class Main {

	public static void main(String[] args) {
		J3DFrame frame = new J3DFrame();
		J3DScreen screen = new J3DScreen();
		
		screen.GenerateSimpleTerrian(50, Color.GREEN);
		
		screen.addCube(0, 0, 0, 10, 10, 10, Color.RED);
		
		frame.add(screen);
	}

}
