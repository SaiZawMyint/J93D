package com.flexitech.products.three_d.components;

import com.flexitech.products.three_d.utils.Vector;

public class Plane {
	private Vector vector1;
	private Vector vector2;
	private Vector nVector;
	double[] p = new double[3];

	public Plane(J3DPolygon DP) {
		this.p[0] = DP.x[0];
		this.p[1] = DP.y[0];
		this.p[2] = DP.z[0];
		this.vector1 = new Vector(DP.x[1] - DP.x[0], DP.y[1] - DP.y[0], DP.z[1] - DP.z[0]);
		this.vector2 = new Vector(DP.x[2] - DP.x[0], DP.y[2] - DP.y[0], DP.z[2] - DP.z[0]);
		this.nVector = this.vector1.CrossProduct(this.vector2);
	}

	public Plane(Vector VE1, Vector VE2, double[] Z) {
		this.p = Z;
		this.vector1 = VE1;
		this.vector2 = VE2;
		this.nVector = this.vector1.CrossProduct(this.vector2);
	}

	public Vector getVector1() {
		return vector1;
	}

	public void setVector1(Vector vector1) {
		this.vector1 = vector1;
	}

	public Vector getVector2() {
		return vector2;
	}

	public void setVector2(Vector vector2) {
		this.vector2 = vector2;
	}

	public Vector getnVector() {
		return nVector;
	}

	public void setnVector(Vector nVector) {
		this.nVector = nVector;
	}

	public double[] getP() {
		return p;
	}

	public void setP(double[] p) {
		this.p = p;
	}

}
