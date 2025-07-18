package com.flexitech.products.three_d.utils;

public class Vector {
	double x;
	double y;
	double z;

	public Vector(double x, double y, double z) {
		double Length = Math.sqrt(x * x + y * y + z * z);
		if (Length > 0.0D) {
			this.x = x / Length;
			this.y = y / Length;
			this.z = z / Length;
		}

	}

	public Vector CrossProduct(Vector V) {
		Vector CrossVector = new Vector(this.y * V.z - this.z * V.y, this.z * V.x - this.x * V.z,
				this.x * V.y - this.y * V.x);
		return CrossVector;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
