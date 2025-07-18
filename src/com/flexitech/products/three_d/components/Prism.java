package com.flexitech.products.three_d.components;

import java.awt.Color;

import com.flexitech.products.three_d.screen.J3DScreen;

public class Prism {
   double x;
   double y;
   double z;
   double width;
   double length;
   double height;
   double rotation = 2.356194490192345D;
   double[] RotAdd = new double[6];
   Color c;
   double x1;
   double x2;
   double x3;
   double x4;
   double x5;
   double x6;
   double y1;
   double y2;
   double y3;
   double y4;
   double y5;
   double y6;
   J3DPolygon[] Polys = new J3DPolygon[5];
   double[] angle;

   public Prism(double x, double y, double z, double width, double length, double height, Color c) {
      this.Polys[0] = new J3DPolygon(new double[]{x, x + width, x + width, x}, new double[]{y, y, y + length, y + length}, new double[]{z, z, z, z}, c, false, true);
      J3DScreen.DPolygons.add(this.Polys[0]);
      this.Polys[1] = new J3DPolygon(new double[]{x, x, x + width, x + width}, new double[]{y, y, y, y}, new double[]{z, z + height, z + height, z}, c, false, true);
      J3DScreen.DPolygons.add(this.Polys[1]);
      this.Polys[2] = new J3DPolygon(new double[]{x + width, x + width, x + width}, new double[]{y, y, y + length}, new double[]{z, z + height, z + height}, c, false, true);
      J3DScreen.DPolygons.add(this.Polys[2]);
      this.Polys[3] = new J3DPolygon(new double[]{x, x, x + width, x + width}, new double[]{y + length, y + length, y + length, y + length}, new double[]{z, z + height, z + height, z}, c, false, true);
      J3DScreen.DPolygons.add(this.Polys[3]);
      this.Polys[4] = new J3DPolygon(new double[]{x, x, x}, new double[]{y, y, y + length}, new double[]{z, z + height, z + height}, c, false, true);
      J3DScreen.DPolygons.add(this.Polys[4]);
      this.c = c;
      this.x = x;
      this.y = y;
      this.z = z;
      this.width = width;
      this.length = length;
      this.height = height;
      this.setRotAdd();
      this.updatePoly();
   }

   void setRotAdd() {
      this.angle = new double[6];
      double xdif = -this.width + 1.0E-5D;
      double ydif = -this.length + 1.0E-5D;
      this.angle[0] = Math.atan(ydif / xdif);
      double[] var10000;
      if (xdif < 0.0D) {
         var10000 = this.angle;
         var10000[0] += 3.141592653589793D;
      }

      xdif = this.width + 1.0E-5D;
      ydif = -this.length + 1.0E-5D;
      this.angle[1] = Math.atan(ydif / xdif);
      if (xdif < 0.0D) {
         var10000 = this.angle;
         var10000[1] += 3.141592653589793D;
      }

      xdif = this.width + 1.0E-5D;
      ydif = this.length + 1.0E-5D;
      this.angle[2] = Math.atan(ydif / xdif);
      if (xdif < 0.0D) {
         var10000 = this.angle;
         var10000[2] += 3.141592653589793D;
      }

      xdif = -this.width + 1.0E-5D;
      ydif = this.length + 1.0E-5D;
      this.angle[3] = Math.atan(ydif / xdif);
      if (xdif < 0.0D) {
         var10000 = this.angle;
         var10000[3] += 3.141592653589793D;
      }

      xdif = -this.width + 1.0E-5D;
      ydif = 1.0E-5D;
      this.angle[4] = Math.atan(ydif / xdif);
      if (xdif < 0.0D) {
         var10000 = this.angle;
         var10000[4] += 3.141592653589793D;
      }

      xdif = this.width + 1.0E-5D;
      ydif = 1.0E-5D;
      this.angle[5] = Math.atan(ydif / xdif);
      if (xdif < 0.0D) {
         var10000 = this.angle;
         var10000[5] += 3.141592653589793D;
      }

      this.RotAdd[0] = this.angle[0] + 0.7853981633974483D;
      this.RotAdd[1] = this.angle[1] + 0.7853981633974483D;
      this.RotAdd[2] = this.angle[2] + 0.7853981633974483D;
      this.RotAdd[3] = this.angle[3] + 0.7853981633974483D;
      this.RotAdd[4] = this.angle[4] + 0.7853981633974483D;
      this.RotAdd[5] = this.angle[5] + 0.7853981633974483D;
   }

   void UpdateDirection(double toX, double toY) {
      double xdif = toX - (this.x + this.width / 2.0D) + 1.0E-5D;
      double ydif = toY - (this.y + this.length / 2.0D) + 1.0E-5D;
      double anglet = Math.atan(ydif / xdif) + 2.356194490192345D;
      if (xdif < 0.0D) {
         anglet += 3.141592653589793D;
      }

      this.rotation = anglet;
      this.updatePoly();
   }

   void updatePoly() {
      for(int i = 0; i < 5; ++i) {
         J3DScreen.DPolygons.add(this.Polys[i]);
         J3DScreen.DPolygons.remove(this.Polys[i]);
      }

      double radius = Math.sqrt(this.width * this.width + this.length * this.length);
      double innerRadius = Math.sqrt(this.width * this.width);
      this.x1 = this.x + this.width * 0.5D + radius * 0.5D * Math.cos(this.rotation + this.RotAdd[0]);
      this.x2 = this.x + this.width * 0.5D + radius * 0.5D * Math.cos(this.rotation + this.RotAdd[1]);
      this.x3 = this.x + this.width * 0.5D + radius * 0.5D * Math.cos(this.rotation + this.RotAdd[2]);
      this.x4 = this.x + this.width * 0.5D + radius * 0.5D * Math.cos(this.rotation + this.RotAdd[3]);
      this.x5 = this.x + this.width * 0.5D + innerRadius * 0.5D * Math.cos(this.rotation + this.RotAdd[4]);
      this.x6 = this.x + this.width * 0.5D + innerRadius * 0.5D * Math.cos(this.rotation + this.RotAdd[5]);
      this.y1 = this.y + this.length * 0.5D + radius * 0.5D * Math.sin(this.rotation + this.RotAdd[0]);
      this.y2 = this.y + this.length * 0.5D + radius * 0.5D * Math.sin(this.rotation + this.RotAdd[1]);
      this.y3 = this.y + this.length * 0.5D + radius * 0.5D * Math.sin(this.rotation + this.RotAdd[2]);
      this.y4 = this.y + this.length * 0.5D + radius * 0.5D * Math.sin(this.rotation + this.RotAdd[3]);
      this.y5 = this.y + this.length * 0.5D + innerRadius * 0.5D * Math.sin(this.rotation + this.RotAdd[4]);
      this.y6 = this.y + this.length * 0.5D + innerRadius * 0.5D * Math.sin(this.rotation + this.RotAdd[5]);
      this.Polys[0].x = new double[]{this.x1, this.x2, this.x3, this.x4};
      this.Polys[0].y = new double[]{this.y1, this.y2, this.y3, this.y4};
      this.Polys[0].z = new double[]{this.z, this.z, this.z, this.z};
      this.Polys[1].x = new double[]{this.x1, this.x5, this.x6, this.x2};
      this.Polys[1].y = new double[]{this.y1, this.y5, this.y6, this.y2};
      this.Polys[1].z = new double[]{this.z, this.z + this.height, this.z + this.height, this.z};
      this.Polys[2].x = new double[]{this.x3, this.x2, this.x6};
      this.Polys[2].y = new double[]{this.y3, this.y2, this.y6};
      this.Polys[2].z = new double[]{this.z, this.z, this.z + this.height};
      this.Polys[3].x = new double[]{this.x3, this.x6, this.x5, this.x4};
      this.Polys[3].y = new double[]{this.y3, this.y6, this.y5, this.y4};
      this.Polys[3].z = new double[]{this.z, this.z + this.height, this.z + this.height, this.z};
      this.Polys[4].x = new double[]{this.x1, this.x4, this.x5};
      this.Polys[4].y = new double[]{this.y1, this.y4, this.y5};
      this.Polys[4].z = new double[]{this.z, this.z, this.z + this.height};
   }

   void removePrism() {
      for(int i = 0; i < 5; ++i) {
         J3DScreen.DPolygons.remove(this.Polys[i]);
      }

      J3DScreen.Prisms.remove(this);
   }
}
