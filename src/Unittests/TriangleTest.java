package Unittests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;


import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

public class TriangleTest {

	@Test
	public void testFindIntersections() throws Exception 
	{
		// ============ Equivalence Partitions Tests ==============
		//TC01: Ray's line crosses the triangle 
				Triangle triangle1=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
				List<GeoPoint> myintersectionPoints1= triangle1.findIntsersections(new Ray(new Point3D(0,0,1), new Vector(0.1,0.1,3)));
				List<Point3D> intersectionPoints1=new ArrayList<Point3D>();
				intersectionPoints1.add(new Point3D(0.06666666666666667,0.06666666666666667,3));
				assertEquals(intersectionPoints1.get(0),myintersectionPoints1.get(0).point);
		//TC02: Ray's line isn't crosses the triangle she is outside against edge 
				Triangle triangle2=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
				ArrayList<GeoPoint> myintersectionPoints2=(ArrayList<GeoPoint>) triangle2.findIntsersections(new Ray(new Point3D(0,0,1), new Vector(1,1,3)));
				assertEquals(null,myintersectionPoints2);
		//TC03: Ray's line isn't crosses the triangle she is outside against vertex 
		        Triangle triangle3=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
		        ArrayList<GeoPoint> myintersectionPoints3=(ArrayList<GeoPoint>) triangle3.findIntsersections(new Ray(new Point3D(0,0,1), new Vector(-0.5,2,2)));
		        assertEquals(null,myintersectionPoints3);
		        
        // =============== Boundary Values Tests ==================
		//TC04: Ray's line crosses the triangle in vertex
		        Triangle triangle4=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
    	        ArrayList<GeoPoint> myintersectionPoints4=(ArrayList<GeoPoint>) triangle4.findIntsersections(new Ray(new Point3D(0,0,1), new Vector(1,0,2)));
		        assertEquals(null,myintersectionPoints4);
		 
		//TC05: Ray's line crosses the triangle on edge
	           Triangle triangle5=new Triangle(new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
		       ArrayList<GeoPoint> myintersectionPoints5=(ArrayList<GeoPoint>) triangle5.findIntsersections(new Ray(new Point3D(0,-1,0), new Vector(0.5,0.5,0)));
		       assertEquals(null,myintersectionPoints5);
		
	  //TC06: Ray's line crosses the triangle on edge's continuation
	           Triangle triangle6=new Triangle(new Point3D(0,0,3),new Point3D(1,0,3),new Point3D(0,1,3));
		       ArrayList<GeoPoint> myintersectionPoints6=(ArrayList<GeoPoint>) triangle6.findIntsersections(new Ray(new Point3D(0,0,1), new Vector(0,2,2)));
		       assertEquals(null,myintersectionPoints6);
	    	
	}


}
