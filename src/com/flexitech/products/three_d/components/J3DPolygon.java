package com.flexitech.products.three_d.components;

import java.awt.Color;

import com.flexitech.products.three_d.screen.J3DFrame;
import com.flexitech.products.three_d.screen.J3DScreen;
import com.flexitech.products.three_d.utils.Calculator;

public class J3DPolygon {
	Color c;
	double[] x;
	double[] y;
	double[] z;
	boolean draw = true;
	boolean seeThrough = false;
	boolean outline = true;
	double[] calcPos;
	double[] newX;
	double[] newY;
	J3DObject drawablePolygon;
	double avgDist;

	public J3DPolygon(double[] x, double[] y, double[] z, Color c, boolean seeThrough, boolean outline) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		this.seeThrough = seeThrough;
		this.outline = outline;
		this.createPolygon();
	}

	void createPolygon() {
		this.drawablePolygon = new J3DObject(new double[this.x.length], new double[this.x.length], this.c,
				J3DScreen.DPolygons.size(), this.seeThrough, this.outline);
	}

	public void updatePolygon() {
		this.newX = new double[this.x.length];
		this.newY = new double[this.x.length];
		this.draw = true;

		for (int i = 0; i < this.x.length; ++i) {
			this.calcPos = Calculator.CalculatePositionP(J3DScreen.ViewFrom, J3DScreen.ViewTo, this.x[i], this.y[i],
					this.z[i]);
			this.newX[i] = J3DFrame.ScreenSize.getWidth() / 2.0D - Calculator.CalcFocusPos[0]
					+ this.calcPos[0] * J3DScreen.zoom;
			this.newY[i] = J3DFrame.ScreenSize.getHeight() / 2.0D - Calculator.CalcFocusPos[1]
					+ this.calcPos[1] * J3DScreen.zoom;
			if (Calculator.t < 0.0D) {
				this.draw = false;
			}
		}

		this.calcLighting();
		this.drawablePolygon.draw = this.draw;
		this.drawablePolygon.updatePolygon(this.newX, this.newY);
		this.avgDist = this.GetDist();
	}

	void calcLighting() {
		Plane lightingPlane = new Plane(this);
		double angle = Math.acos((lightingPlane.getnVector().getX() * J3DScreen.LightDir[0]
				+ lightingPlane.getnVector().getY() * J3DScreen.LightDir[1]
				+ lightingPlane.getnVector().getZ() * J3DScreen.LightDir[2])
				/ Math.sqrt(
						J3DScreen.LightDir[0] * J3DScreen.LightDir[0] + J3DScreen.LightDir[1] * J3DScreen.LightDir[1]
								+ J3DScreen.LightDir[2] * J3DScreen.LightDir[2]));
		this.drawablePolygon.lighting = 1.2D - Math.sqrt(Math.toDegrees(angle) / 180.0D);
		if (this.drawablePolygon.lighting > 1.0D) {
			this.drawablePolygon.lighting = 1.0D;
		}

		if (this.drawablePolygon.lighting < 0.0D) {
			this.drawablePolygon.lighting = 0.0D;
		}

	}

	public double GetDist() {
		double total = 0.0D;

		for (int i = 0; i < this.x.length; ++i) {
			total += this.GetDistanceToP(i);
		}

		return total / (double) this.x.length;
	}

	public double GetDistanceToP(int i) {
		return Math.sqrt((J3DScreen.ViewFrom[0] - this.x[i]) * (J3DScreen.ViewFrom[0] - this.x[i])
				+ (J3DScreen.ViewFrom[1] - this.y[i]) * (J3DScreen.ViewFrom[1] - this.y[i])
				+ (J3DScreen.ViewFrom[2] - this.z[i]) * (J3DScreen.ViewFrom[2] - this.z[i]));
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}

	public double[] getZ() {
		return z;
	}

	public void setZ(double[] z) {
		this.z = z;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
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

	public double[] getCalcPos() {
		return calcPos;
	}

	public void setCalcPos(double[] calcPos) {
		this.calcPos = calcPos;
	}

	public double[] getNewX() {
		return newX;
	}

	public void setNewX(double[] newX) {
		this.newX = newX;
	}

	public double[] getNewY() {
		return newY;
	}

	public void setNewY(double[] newY) {
		this.newY = newY;
	}

	public J3DObject getDrawablePolygon() {
		return drawablePolygon;
	}

	public void setDrawablePolygon(J3DObject drawablePolygon) {
		this.drawablePolygon = drawablePolygon;
	}

	public double getAvgDist() {
		return avgDist;
	}

	public void setAvgDist(double avgDist) {
		this.avgDist = avgDist;
	}

}
