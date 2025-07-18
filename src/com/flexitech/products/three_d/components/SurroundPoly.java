package com.flexitech.products.three_d.components;

import java.awt.Color;

import com.flexitech.products.three_d.screen.J3DScreen;

public class SurroundPoly {
   double[] xx = new double[360];
   double[] yy = new double[360];
   double[] zz = new double[360];
   double[] hh = new double[360];
   double x;
   double y;
   double z;
   double width;
   double height;
   Color c;

   public SurroundPoly(double x, double y, double z, double width, double height, Color c) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.width = width;
      this.height = height;
      this.c = c;
      double r = width / 2.0D;

      int i;
      for(i = 0; i < 360; ++i) {
         this.yy[i] = r * Math.sin((double)i) + y;
         this.xx[i] = r * Math.cos((double)i) + x;
         this.zz[i] = z;
      }

      for(i = 0; i < this.zz.length; ++i) {
         this.hh[i] = height;
      }

      J3DScreen.DPolygons.add(new J3DPolygon(this.xx, this.yy, this.zz, c, false, true));

      for(double i1 = 0.0D; i1 < 360.0D; ++i1) {
         double j = i1 + 1.0D;
         if (j > 359.0D) {
            j = 0.0D;
         }

         J3DScreen.DPolygons.add(new J3DPolygon(new double[]{this.xx[(int)i1], this.xx[(int)j], this.xx[(int)j], this.xx[(int)i1]}, new double[]{this.yy[(int)i1], this.yy[(int)j], this.yy[(int)j], this.yy[(int)i1]}, new double[]{height, height, this.zz[(int)i1], this.zz[(int)i1]}, c, false, false));
      }

      J3DScreen.DPolygons.add(new J3DPolygon(this.xx, this.yy, this.hh, c, false, true));
   }
}
