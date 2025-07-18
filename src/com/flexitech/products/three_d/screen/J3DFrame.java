package com.flexitech.products.three_d.screen;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class J3DFrame extends JFrame {
	private static final long serialVersionUID = 4602613386554384073L;
	public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static JTextField TF;
	static J3DScreen ScreenObject = new J3DScreen();

	public J3DFrame() {
		this.add(ScreenObject);
		this.setUndecorated(true);
		this.setSize(ScreenSize);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
	}

	public static void main(String[] args) {
		J3DFrame home = new J3DFrame();
		home.setVisible(true);
	}
}
