package com.flexitech.products.three_d.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.flexitech.products.three_d.screen.J3DFrame;
import com.flexitech.products.three_d.screen.J3DScreen;

public class J3DObject {
	Polygon p = new Polygon();
	Color c;
	boolean draw = true;
	boolean visible = true;
	boolean seeThrough;
	boolean outline;
	double lighting = 5.0D;

	public J3DObject(double[] x, double[] y, Color c, int n, boolean seeThrough, boolean outline) {
		for (int i = 0; i < x.length; ++i) {
			this.p.addPoint((int) x[i], (int) y[i]);
		}

		this.c = c;
		this.seeThrough = seeThrough;
		this.outline = outline;
	}

	public void updatePolygon(double[] x, double[] y) {
		this.p.reset();

		for (int i = 0; i < x.length; ++i) {
			this.p.xpoints[i] = (int) x[i];
			this.p.ypoints[i] = (int) y[i];
			this.p.npoints = x.length;
		}

	}

	public void drawPolygon(Graphics g) {
		if (this.draw && this.visible) {
			g.setColor(new Color((int) ((double) this.c.getRed() * this.lighting),
					(int) ((double) this.c.getGreen() * this.lighting),
					(int) ((double) this.c.getBlue() * this.lighting)));
			if (this.seeThrough) {
				g.drawPolygon(this.p);
			} else {
				g.fillPolygon(this.p);
			}

			if (this.outline) {
				g.setColor(new Color(0, 0, 0));
				g.drawPolygon(this.p);
			}

			if (J3DScreen.PolygonOver == this) {
				g.setColor(new Color(255, 255, 255, 100));
				g.fillPolygon(this.p);
			}
		}

	}

	public boolean MouseOver() {
		return this.p.contains(J3DFrame.ScreenSize.getWidth() / 2.0D, J3DFrame.ScreenSize.getHeight() / 2.0D);
	}

	public Polygon getP() {
		return p;
	}

	public void setP(Polygon p) {
		this.p = p;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isSeeThrough() {
		return seeThrough;
	}

	public void setSeeThrough(boolean seeThrough) {
		this.seeThrough = seeThrough;
	}

	public boolean isOutline() {
		return outline;
	}

	public void setOutline(boolean outline) {
		this.outline = outline;
	}

	public double getLighting() {
		return lighting;
	}

	public void setLighting(double lighting) {
		this.lighting = lighting;
	}

}
