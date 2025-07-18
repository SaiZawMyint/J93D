package com.flexitech.products.three_d.utils;

import com.flexitech.products.three_d.components.Plane;
import com.flexitech.products.three_d.screen.J3DScreen;

public class Calculator {
   public static double t = 0.0D;
   static Vector W1;
   static Vector W2;
   static Vector ViewVector;
   static Vector RotationVector;
   static Vector DirectionVector;
   static Vector PlaneVector1;
   static Vector PlaneVector2;
   static Plane P;
   public static double[] CalcFocusPos = new double[2];

   public static double[] CalculatePositionP(double[] ViewFrom, double[] ViewTo, double x, double y, double z) {
      double[] projP = getProj(ViewFrom, ViewTo, x, y, z, P);
      double[] drawP = getDrawP(projP[0], projP[1], projP[2]);
      return drawP;
   }

   public static double[] getProj(double[] ViewFrom, double[] ViewTo, double x, double y, double z, Plane P) {
      Vector ViewToPoint = new Vector(x - ViewFrom[0], y - ViewFrom[1], z - ViewFrom[2]);
      t = (P.getnVector().getX() * P.getP()[0] + P.getnVector().getY() * P.getP()[1] + P.getnVector().getZ() * P.getP()[2] - (P.getnVector().getX() * ViewFrom[0] + P.getnVector().getY() * ViewFrom[1] + P.getnVector().getZ() * ViewFrom[2])) / (P.getnVector().getX() * ViewToPoint.getX() + P.getnVector().getY() * ViewToPoint.getY() + P.getnVector().getZ() * ViewToPoint.getZ());
      x = ViewFrom[0] + ViewToPoint.getX() * t;
      y = ViewFrom[1] + ViewToPoint.getY() * t;
      z = ViewFrom[2] + ViewToPoint.getZ() * t;
      return new double[]{x, y, z};
   }

   public static double[] getDrawP(double x, double y, double z) {
      double DrawX = W2.getX() * x + W2.getY() * y + W2.getZ() * z;
      double DrawY = W1.getX() * x + W1.getY() * y + W1.getZ() * z;
      return new double[]{DrawX, DrawY};
   }

   public static Vector getRotationVector(double[] ViewFrom, double[] ViewTo) {
      double dx = Math.abs(ViewFrom[0] - ViewTo[0]);
      double dy = Math.abs(ViewFrom[1] - ViewTo[1]);
      double xRot = dy / (dx + dy);
      double yRot = dx / (dx + dy);
      if (ViewFrom[1] > ViewTo[1]) {
			xRot = -xRot;
      }

      if (ViewFrom[0] < ViewTo[0]) {
         yRot = -yRot;
      }

      Vector V = new Vector(xRot, yRot, 0.0D);
      return V;
   }

   public static void SetPrederterminedInfo() {
      ViewVector = new Vector(J3DScreen.ViewTo[0] - J3DScreen.ViewFrom[0], J3DScreen.ViewTo[1] - J3DScreen.ViewFrom[1], J3DScreen.ViewTo[2] - J3DScreen.ViewFrom[2]);
      DirectionVector = new Vector(1.0D, 1.0D, 1.0D);
      PlaneVector1 = ViewVector.CrossProduct(DirectionVector);
      PlaneVector2 = ViewVector.CrossProduct(PlaneVector1);
      P = new Plane(PlaneVector1, PlaneVector2, J3DScreen.ViewTo);
      RotationVector = getRotationVector(J3DScreen.ViewFrom, J3DScreen.ViewTo);
      W1 = ViewVector.CrossProduct(RotationVector);
      W2 = ViewVector.CrossProduct(W1);
      CalcFocusPos = CalculatePositionP(J3DScreen.ViewFrom, J3DScreen.ViewTo, J3DScreen.ViewTo[0], J3DScreen.ViewTo[1], J3DScreen.ViewTo[2]);
      CalcFocusPos[0] = J3DScreen.zoom * CalcFocusPos[0];
      CalcFocusPos[1] = J3DScreen.zoom * CalcFocusPos[1];
   }
}
