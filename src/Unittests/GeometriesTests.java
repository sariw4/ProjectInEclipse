package Unittests;
import java.util.*;

import primitives.*;
import primitives.Vector;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;

public class GeometriesTests {

	@Test
 public void testFindIntersections()
 {     
   // =============== Boundary Values Tests ==================

   // TC01:Don't have any shapes 
	  Ray r=new Ray (new Point3D(0,1,0), new Vector(0,3,0));
      Geometries newIn=new Geometries() ;
      ArrayList<GeoPoint> myintersectionPoints=(ArrayList<GeoPoint>) newIn.findIntsersections(r);
      assertEquals(null,myintersectionPoints);
   // TC02:Ray's line isn't crosses the shapes
      Triangle t =new Triangle(new Point3D(2,0,0),new Point3D(4,0,0),new Point3D(1,3,0));
      Plane p=new Plane (new Point3D(-3,0,0),new Point3D(0,0,3),new Point3D(1,0,0));
      newIn.add(t);
      newIn.add(p);
	ArrayList<GeoPoint> myintersectionPoints1=(ArrayList<GeoPoint>) newIn.findIntsersections(r);
      assertEquals(null,myintersectionPoints1);
   // TC03:Ray's line crosses one of the shapes
	  Ray r1=new Ray (new Point3D(0,0,1), new Vector(0.1,0.1,3));
      Triangle t1=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
	  Plane p1=new Plane (new Point3D(0,-1,0),new Point3D(0,-1,3),new Point3D(-3,-1,0));
	  Geometries newIn1=new Geometries(p1,t1) ;
      List<GeoPoint> myintersectionPoints2= newIn1.findIntsersections(r1);
	  assertEquals(1,myintersectionPoints2.size());
   // TC04:Ray's line crosses all of the shapes 
	  Ray r2=new Ray (new Point3D(0,0,1), new Vector(0.1,0.1,3));
      Triangle t2=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
	  Plane p2=new Plane (new Point3D(0,1,0),new Point3D(-4,1,0),new Point3D(0,0,4));
	  Sphere s2=new Sphere(new Point3D(0,0,5),1.0);
	  Geometries newIn2=new Geometries(t2,p2,s2) ;
      ArrayList<GeoPoint> myintersectionPoints3=(ArrayList<GeoPoint>)  newIn2.findIntsersections(r2);
	  assertEquals(4,myintersectionPoints3.size());//Ray's line crosses the sphere two time  
   // ============ Equivalence Partitions Tests ==============
   // TC05:Ray's line crosses some of the shapes but not all
	  Ray r3=new Ray (new Point3D(0,0,1), new Vector(0.1,0.1,3));
      Triangle t3=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
	  Plane p3=new Plane (new Point3D(0,-1,0),new Point3D(0,-1,3),new Point3D(-3,-1,0));
	  Sphere s3=new Sphere(new Point3D(0,0,5),1.0);
	  Geometries newIn3=new Geometries(t3,p3,s3) ;
      ArrayList<GeoPoint> myintersectionPoints4=(ArrayList<GeoPoint>)  newIn3.findIntsersections(r3);
	  assertEquals(3,myintersectionPoints4.size());//Ray's line crosses the sphere two time 
		  
 }
}
