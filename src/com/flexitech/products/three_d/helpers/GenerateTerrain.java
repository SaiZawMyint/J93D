package com.flexitech.products.three_d.helpers;

import java.awt.Color;
import java.util.Random;

import com.flexitech.products.three_d.components.J3DPolygon;
import com.flexitech.products.three_d.screen.J3DScreen;

public class GenerateTerrain {
	Random r;
	public static double roughness;
	public static int mapSize;
	public static double Size = 2.0D;
	public static Color G = new Color(120, 100, 80);
	Color c;

	public GenerateTerrain(double roughtness, int mapSize, Color c) {
		roughness = roughtness;
		GenerateTerrain.mapSize = mapSize;
		this.c = c;
		this.r = new Random();
		double[] values1 = new double[mapSize];
		double[] values2 = new double[values1.length];

		for (int y = 0; y < values1.length / 2; y += 2) {
			int x;
			for (x = 0; x < values1.length; ++x) {
				values1[x] = values2[x];
				values2[x] = this.r.nextDouble() * roughness;
			}

			if (y != 0) {
				for (x = 0; x < values1.length / 2; ++x) {
					J3DScreen.DPolygons.add(new J3DPolygon(
							new double[] { Size * (double) x, Size * (double) x, Size + Size * (double) x },
							new double[] { Size * (double) y, Size + Size * (double) y, Size + Size * (double) y },
							new double[] { values1[x], values2[x], values2[x + 1] }, c, false, true));
					J3DScreen.DPolygons.add(new J3DPolygon(
							new double[] { Size * (double) x, Size + Size * (double) x, Size + Size * (double) x },
							new double[] { Size * (double) y, Size + Size * (double) y, Size * (double) y },
							new double[] { values1[x], values2[x + 1], values1[x + 1] }, c, false, true));
				}
			}

			for (x = 0; x < values1.length; ++x) {
				values1[x] = values2[x];
				values2[x] = this.r.nextDouble() * roughness;
			}

			if (y != 0) {
				for (x = 0; x < values1.length / 2; ++x) {
					J3DScreen.DPolygons.add(new J3DPolygon(
							new double[] { Size * (double) x, Size * (double) x, Size + Size * (double) x },
							new double[] { Size * (double) (y + 1), Size + Size * (double) (y + 1),
									Size + Size * (double) (y + 1) },
							new double[] { values1[x], values2[x], values2[x + 1] }, c, false, true));
					J3DScreen.DPolygons.add(new J3DPolygon(
							new double[] { Size * (double) x, Size + Size * (double) x, Size + Size * (double) x },
							new double[] { Size * (double) (y + 1), Size + Size * (double) (y + 1),
									Size * (double) (y + 1) },
							new double[] { values1[x], values2[x + 1], values1[x + 1] }, c, false, true));
				}
			}
		}

	}

	public Random getR() {
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}


	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

}
