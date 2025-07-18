package com.flexitech.products.three_d.screen;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.flexitech.products.three_d.components.Cube;
import com.flexitech.products.three_d.components.J3DObject;
import com.flexitech.products.three_d.components.J3DPolygon;
import com.flexitech.products.three_d.components.Prism;
import com.flexitech.products.three_d.components.Pyramid;
import com.flexitech.products.three_d.components.SurroundPoly;
import com.flexitech.products.three_d.helpers.GenerateSimpleTerrain;
import com.flexitech.products.three_d.helpers.GenerateTerrain;
import com.flexitech.products.three_d.utils.Calculator;
import com.flexitech.products.three_d.utils.Vector;

public class J3DScreen extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6327480038751920415L;
	public static List<J3DPolygon> DPolygons = new ArrayList<>();
	public static List<Cube> Cubes = new ArrayList<>();
	public static List<Prism> Prisms = new ArrayList<>();
	public static List<Pyramid> Pyramids = new ArrayList<>();
	public static List<SurroundPoly> cylinder = new ArrayList<>();
	public static J3DObject PolygonOver = null;
	Robot r;
	public static double[] ViewFrom = new double[] { 15.0D, 5.0D, 10.0D };
	public static double[] ViewTo = new double[] { 0.0D, 0.0D, 0.0D };
	public static double[] LightDir = new double[] { 1.0D, 1.0D, 1.0D };
	public static double zoom = 1000.0D;
	public static double MinZoom = 500.0D;
	public static double MaxZoom = 2500.0D;
	public static double MouseX = 0.0D;
	public static double MouseY = 0.0D;
	public static double MovementSpeed = 0.5D;
	double drawFPS = 0.0D;
	double MaxFPS = 1000.0D;
	double SleepTime;
	double LastRefresh;
	double StartTime;
	double LastFPSCheck;
	double Checks;
	double VertLook;
	double HorLook;
	double aimSight;
	double HorRotSpeed;
	double VertRotSpeed;
	double SunPos;
	int[] NewOrder;
	static boolean OutLines;
	boolean[] Keys;
	boolean userinterface;
	long repaintTime;

	public J3DScreen() {
		this.SleepTime = 1000.0D / this.MaxFPS;
		this.LastRefresh = 0.0D;
		this.StartTime = (double) System.currentTimeMillis();
		this.LastFPSCheck = 0.0D;
		this.Checks = 0.0D;
		this.VertLook = -0.9D;
		this.HorLook = 0.0D;
		this.aimSight = 4.0D;
		this.HorRotSpeed = 900.0D;
		this.VertRotSpeed = 2200.0D;
		this.SunPos = 0.0D;
		this.Keys = new boolean[4];
		this.userinterface = false;
		this.repaintTime = 0L;
		this.addKeyListener(this);
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.invisibleMouse();
		this.setVisible(true);
	}

	public void GenerateTerrian(double roughtness, int mapSize, Color c) {
		new GenerateTerrain(roughtness, mapSize, c);
	}

	public void GenerateSimpleTerrian(int mapSize, Color c) {
		new GenerateSimpleTerrain(mapSize, c);
	}

	public void addCube(double x, double y, double z, double width, double length, double height, Color c) {
		Cubes.add(new Cube(x, y, z, width, length, height, c));
	}

	public void addPrisms(double x, double y, double z, double width, double length, double height, Color c) {
		Prisms.add(new Prism(x, y, z, width, length, height, c));
	}

	public void addPyramids(double x, double y, double z, double width, double length, double height, Color c) {
		Pyramids.add(new Pyramid(x, y, z, width, length, height, c));
	}

	public void addCylinder(double x, double y, double z, double width, double height, Color c) {
		cylinder.add(new SurroundPoly(x, y, z, width, height, c));
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(100, 130, 180));
		g.fillRect(0, 0, (int) J3DFrame.ScreenSize.getWidth(), (int) J3DFrame.ScreenSize.getHeight());
		this.CameraMovement();
		Calculator.SetPrederterminedInfo();
		this.ControlSunAndLight();

		int i;
		for (i = 0; i < DPolygons.size(); ++i) {
			((J3DPolygon) DPolygons.get(i)).updatePolygon();
		}

		this.setOrder();
		this.setPolygonOver();

		for (i = 0; i < this.NewOrder.length; ++i) {
			((J3DPolygon) DPolygons.get(this.NewOrder[i])).getDrawablePolygon().drawPolygon(g);
		}

		this.drawMouseAim(g);
		g.drawString("J93D : " + (int) this.drawFPS + " (View Space)", 40, 40);
		if (!this.userinterface) {
			g.drawString("Press V to see User interface.", 40, 80);
		} else {
			g.drawString("Press V to hide User interface.", 40, 80);
		}

		if (this.userinterface) {
			g.setColor(Color.white);
			g.fillRect(40, 100, 520, 400);
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", 1, 20));
			g.drawString("User Interfaces", 200, 120);
			g.setFont(new Font("Arial Rounded MT", 1, 15));
			g.drawString("keys", 50, 140);
			ImageIcon wkey = new ImageIcon("res/wkey.png");
			ImageIcon akey = new ImageIcon("res/A key.png");
			ImageIcon skey = new ImageIcon("res/s key.png");
			ImageIcon dkey = new ImageIcon("res/D key.png");
			ImageIcon vkey = new ImageIcon("res/v key.png");
			ImageIcon mouse = new ImageIcon("res/mouse.png");
			g.drawImage(wkey.getImage(), 80, 150, 30, 30, (ImageObserver) null);
			g.setFont(new Font("Consolas", 1, 13));
			g.drawString("W key - Zoom greater per press.", 80, 170);
			g.drawImage(skey.getImage(), 80, 190, 30, 30, (ImageObserver) null);
			g.setFont(new Font("Consolas", 1, 13));
			g.drawString("S key - Zoom smaller per press.", 80, 210);
			g.drawImage(akey.getImage(), 80, 230, 30, 30, (ImageObserver) null);
			g.setFont(new Font("Consolas", 1, 13));
			g.drawString("A key - Look Horizintally (left-size) per press.", 80, 250);
			g.drawImage(dkey.getImage(), 80, 270, 30, 30, (ImageObserver) null);
			g.setFont(new Font("Consolas", 1, 13));
			g.drawString("D key - Look Horizintally (right-size) per press.", 80, 290);
			g.drawImage(vkey.getImage(), 80, 310, 30, 30, (ImageObserver) null);
			g.setFont(new Font("Consolas", 1, 13));
			g.drawString("V key - User Interface.", 80, 330);
			g.setFont(new Font("Arial Rounded MT", 1, 15));
			g.drawString("Mouse", 50, 360);
			g.drawImage(mouse.getImage(), 80, 380, 30, 30, (ImageObserver) null);
			g.setFont(new Font("Consolas", 1, 13));
			g.drawString("Mouse Motion(all directions) - Aims camera's sight to all direction", 80, 400);
			g.drawString("of zoom. Super, press immediately with keys.", 80, 415);
			g.drawString("Right-click + Object on screen : See throught that obj.", 80, 430);
			g.drawString("Left-click + Object on screen : If that obj is see throunght, redraw ", 80, 445);
			g.drawString("to original.", 80, 460);
			g.drawString("Mouse Wheels - Zoom(-/+).", 80, 475);
		}

		this.SleepAndRefresh();
	}

	void setOrder() {
		double[] k = new double[DPolygons.size()];
		this.NewOrder = new int[DPolygons.size()];

		for (int i = 0; i < DPolygons.size(); this.NewOrder[i] = i++) {
			k[i] = ((J3DPolygon) DPolygons.get(i)).getAvgDist();
		}

		for (int a = 0; a < k.length - 1; ++a) {
			for (int b = 0; b < k.length - 1; ++b) {
				if (k[b] < k[b + 1]) {
					double temp = k[b];
					int tempr = this.NewOrder[b];
					this.NewOrder[b] = this.NewOrder[b + 1];
					k[b] = k[b + 1];
					this.NewOrder[b + 1] = tempr;
					k[b + 1] = temp;
				}
			}
		}

	}

	void invisibleMouse() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImage = new BufferedImage(1, 1, 3);
		Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");
		this.setCursor(invisibleCursor);
	}

	void drawMouseAim(Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) (J3DFrame.ScreenSize.getWidth() / 2.0D - this.aimSight),
				(int) (J3DFrame.ScreenSize.getHeight() / 2.0D),
				(int) (J3DFrame.ScreenSize.getWidth() / 2.0D + this.aimSight),
				(int) (J3DFrame.ScreenSize.getHeight() / 2.0D));
		g.drawLine((int) (J3DFrame.ScreenSize.getWidth() / 2.0D),
				(int) (J3DFrame.ScreenSize.getHeight() / 2.0D - this.aimSight),
				(int) (J3DFrame.ScreenSize.getWidth() / 2.0D),
				(int) (J3DFrame.ScreenSize.getHeight() / 2.0D + this.aimSight));
	}

	void SleepAndRefresh() {
		long timeSLU = (long) ((double) System.currentTimeMillis() - this.LastRefresh);
		++this.Checks;
		if (this.Checks >= 15.0D) {
			this.drawFPS = this.Checks / (((double) System.currentTimeMillis() - this.LastFPSCheck) / 1000.0D);
			this.LastFPSCheck = (double) System.currentTimeMillis();
			this.Checks = 0.0D;
		}

		if ((double) timeSLU < 1000.0D / this.MaxFPS) {
			try {
				Thread.sleep((long) (1000.0D / this.MaxFPS - (double) timeSLU));
			} catch (InterruptedException var4) {
				var4.printStackTrace();
			}
		}

		this.LastRefresh = (double) System.currentTimeMillis();
		this.repaint();
	}

	void ControlSunAndLight() {
		this.SunPos += 0.005D;
		double mapSize = (double) GenerateTerrain.mapSize * GenerateTerrain.Size;
		LightDir[0] = mapSize / 2.0D - (mapSize / 2.0D + Math.cos(this.SunPos) * mapSize * 10.0D);
		LightDir[1] = mapSize / 2.0D - (mapSize / 2.0D + Math.sin(this.SunPos) * mapSize * 10.0D);
		LightDir[2] = -200.0D;
	}

	void CameraMovement() {
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0.0D;
		double yMove = 0.0D;
		double zMove = 0.0D;
		Vector VerticalVector = new Vector(0.0D, 0.0D, 1.0D);
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		if (this.Keys[0]) {
			xMove += ViewVector.getX();
			yMove += ViewVector.getY();
			zMove += ViewVector.getZ();
		}

		if (this.Keys[2]) {
			xMove -= ViewVector.getX();
			yMove -= ViewVector.getY();
			zMove -= ViewVector.getZ();
		}

		if (this.Keys[1]) {
			xMove += SideViewVector.getX();
			yMove += SideViewVector.getY();
			zMove += SideViewVector.getZ();
		}

		if (this.Keys[3]) {
			xMove -= SideViewVector.getX();
			yMove -= SideViewVector.getY();
			zMove -= SideViewVector.getZ();
		}

		Vector MoveVector = new Vector(xMove, yMove, zMove);
		this.MoveTo(ViewFrom[0] + MoveVector.getX() * MovementSpeed, ViewFrom[1] + MoveVector.getY() * MovementSpeed,
				ViewFrom[2] + MoveVector.getZ() * MovementSpeed);
	}

	void MoveTo(double x, double y, double z) {
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;
		this.updateView();
	}

	void setPolygonOver() {
		PolygonOver = null;

		for (int i = this.NewOrder.length - 1; i >= 0; --i) {
			if (((J3DPolygon) DPolygons.get(this.NewOrder[i])).getDrawablePolygon().MouseOver()
					&& ((J3DPolygon) DPolygons.get(this.NewOrder[i])).isDraw()
					&& ((J3DPolygon) DPolygons.get(this.NewOrder[i])).getDrawablePolygon().isVisible()) {
				PolygonOver = ((J3DPolygon) DPolygons.get(this.NewOrder[i])).getDrawablePolygon();
				break;
			}
		}

	}

	void MouseMovement(double NewMouseX, double NewMouseY) {
		double difX = NewMouseX - J3DFrame.ScreenSize.getWidth() / 2.0D;
		double difY = NewMouseY - J3DFrame.ScreenSize.getHeight() / 2.0D;
		difY *= 6.0D - Math.abs(this.VertLook) * 5.0D;
		this.VertLook -= difY / this.VertRotSpeed;
		this.HorLook += difX / this.HorRotSpeed;
		if (this.VertLook > 0.999D) {
			this.VertLook = 0.999D;
		}

		if (this.VertLook < -0.999D) {
			this.VertLook = -0.999D;
		}

		this.updateView();
	}

	void updateView() {
		double r = Math.sqrt(1.0D - this.VertLook * this.VertLook);
		ViewTo[0] = ViewFrom[0] + r * Math.cos(this.HorLook);
		ViewTo[1] = ViewFrom[1] + r * Math.sin(this.HorLook);
		ViewTo[2] = ViewFrom[2] + this.VertLook;
	}

	void CenterMouse() {
		try {
			this.r = new Robot();
			this.r.mouseMove((int) J3DFrame.ScreenSize.getWidth() / 2, (int) J3DFrame.ScreenSize.getHeight() / 2);
		} catch (AWTException var2) {
			var2.printStackTrace();
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 87) {
			this.Keys[0] = true;
		}

		if (e.getKeyCode() == 65) {
			this.Keys[1] = true;
		}

		if (e.getKeyCode() == 83) {
			this.Keys[2] = true;
		}

		if (e.getKeyCode() == 68) {
			this.Keys[3] = true;
		}

		if (e.getKeyCode() == 79) {
			OutLines = !OutLines;
		}

		if (e.getKeyCode() == 27
				&& JOptionPane.showConfirmDialog((Component) null, "Exit Project?", "Notification", 0) == 0) {
			System.exit(0);
		}

		if (e.getKeyCode() == 86) {
			if (!this.userinterface) {
				this.userinterface = true;
			} else {
				this.userinterface = false;
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 87) {
			this.Keys[0] = false;
		}

		if (e.getKeyCode() == 65) {
			this.Keys[1] = false;
		}

		if (e.getKeyCode() == 83) {
			this.Keys[2] = false;
		}

		if (e.getKeyCode() == 68) {
			this.Keys[3] = false;
		}

	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseDragged(MouseEvent arg0) {
		this.MouseMovement((double) arg0.getX(), (double) arg0.getY());
		MouseX = (double) arg0.getX();
		MouseY = (double) arg0.getY();
		this.CenterMouse();
	}

	public void mouseMoved(MouseEvent arg0) {
		this.MouseMovement((double) arg0.getX(), (double) arg0.getY());
		MouseX = (double) arg0.getX();
		MouseY = (double) arg0.getY();
		this.CenterMouse();
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() == 1 && PolygonOver != null) {
			PolygonOver.setSeeThrough(false);
		}

		if (arg0.getButton() == 3 && PolygonOver != null) {
			PolygonOver.setSeeThrough(true);
		}

	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if (arg0.getUnitsToScroll() > 0) {
			if (zoom > MinZoom) {
				zoom -= (double) (25 * arg0.getUnitsToScroll());
			}
		} else if (zoom < MaxZoom) {
			zoom -= (double) (25 * arg0.getUnitsToScroll());
		}

	}
}
