/**
 * 
 */
package Unittests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;


/**
 * @author user
 *
 */
public class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		 Point3D p = new Point3D(3.0,4.0,5.0);
	        Sphere sph = new Sphere(p,1.0);
	        Vector v = new Vector();
	        v=sph.getNormal(new Point3D(2.0,4.0,6.0));
	        Vector result = new Vector((-1)/Math.sqrt(2),0.0,1/Math.sqrt(2));
	        assertEquals("ERROR:GetNormal() function does not work correctly",v, result);
//	         TODO review the generated test code and remove the default call to fail	}
	}
	@Test
	public void testFindIntersections() throws Exception 
	{
		Sphere sphere = new Sphere(new Point3D(1, 0, 0),1d);

	// ============ Equivalence Partitions Tests ==============

    // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                        sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

    // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0),
                                                                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        List<Point3D> result1=new ArrayList<Point3D> ();
        result1.add(result.get(0).point);
        result1.add(result.get(1).point);
        assertEquals("Ray crosses sphere", List.of(p1, p2), result1);
  
    // TC03: Ray starts inside the sphere (1 point)
		Sphere sphere2=new Sphere(new Point3D(0,-1,0),2.0);
        Ray ray1 = new Ray (new Point3D(-1,0,0),new Vector(3,-1,0));
		List<GeoPoint> myintersectionPoints= sphere2.findIntsersections(ray1);
		ArrayList<Point3D> intersectionPoints=new ArrayList<Point3D>();
		intersectionPoints.add(new Point3D(2,-1,0));
		assertEquals(intersectionPoints.get(0),myintersectionPoints.get(0).point);
      
    // TC04: Ray starts after the sphere (0 points)
		Ray ray2 = new Ray (new Point3D(-3,0,0),new Vector(3,2,0));
		ArrayList<GeoPoint> myintersectionPoints1=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray2);
		assertEquals(null,myintersectionPoints1);
   // =============== Boundary Values Tests ==================

   // **** Group: Ray's line crosses the sphere (but not the center)
   // TC11: Ray starts at sphere and goes inside (1 points)
		Ray ray3 = new Ray (new Point3D(0,-3,0),new Vector(0,2,2));
		intersectionPoints.clear();
		intersectionPoints.add(new Point3D(0,-1,2));
		List<GeoPoint> myintersectionPoints2= sphere2.findIntsersections(ray3);
		assertEquals(intersectionPoints.get(0),myintersectionPoints2.get(0).point);

  // TC12: Ray starts at sphere and goes outside (0 points)
		Ray ray4 = new Ray (new Point3D(0,-3,0),new Vector(5,-2,6));
		ArrayList<GeoPoint> myintersectionPoints3=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray4);
		assertEquals(null,myintersectionPoints3);
  // **** Group: Ray's line goes through the center
  // TC13: Ray starts before the sphere (2 points)
		Ray ray5 = new Ray (new Point3D(0,-4,0),new Vector(0,7,0));
		intersectionPoints.clear();
		intersectionPoints.add(new Point3D(0,-3,0));
		intersectionPoints.add(new Point3D(0,1,0));
		List<GeoPoint> myintersectionPoints4=(List<GeoPoint>) sphere2.findIntsersections(ray5);
		myintersectionPoints4 = List.of(myintersectionPoints4.get(0), myintersectionPoints4.get(1));
		List<Point3D> result2=new ArrayList<Point3D> ();
	        result2.add(myintersectionPoints4.get(0).point);
	        result2.add(myintersectionPoints4.get(1).point);
	    assertEquals("Ray crosses sphere", intersectionPoints, result2);
  
  // TC14: Ray starts at sphere and goes inside (1 points)
		Ray ray6 = new Ray (new Point3D(0,1,0),new Vector(0,-4,0));
		intersectionPoints.clear();
		intersectionPoints.add(new Point3D(0,-3,0));
		List<GeoPoint> myintersectionPoints5=sphere2.findIntsersections(ray6);
		assertEquals(intersectionPoints.get(0),myintersectionPoints5.get(0).point);

  // TC15: Ray starts inside (1 points)
		Ray ray7 = new Ray (new Point3D(0,-2,0),new Vector(0,4,0));
		intersectionPoints.clear();
		intersectionPoints.add(new Point3D(0,1,0));
		List<GeoPoint> myintersectionPoints6= sphere2.findIntsersections(ray7);
		assertEquals(intersectionPoints.get(0),myintersectionPoints6.get(0).point);

  // TC16: Ray starts at the center (1 points)
		Ray ray8=new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0));
		intersectionPoints.clear();
		intersectionPoints.add(new Point3D(1,1,0));
	    List<GeoPoint> myintersectionPoints7 =sphere.findIntsersections(ray8);
        assertEquals( intersectionPoints.get(0), myintersectionPoints7.get(0).point);

  // TC17: Ray starts at sphere and goes outside (0 points)
		Ray ray9 = new Ray (new Point3D(0,1,0),new Vector(0,1,0));
		ArrayList<GeoPoint> myintersectionPoints8=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray9);
		assertEquals(null,myintersectionPoints8);
  
  // TC18: Ray starts after sphere (0 points)
		Ray ray10 = new Ray (new Point3D(0,2,0),new Vector(0,1,0));
		ArrayList<GeoPoint> myintersectionPoints9=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray10);
		assertEquals(null,myintersectionPoints9);
  // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
  // TC19: Ray starts before the tangent point
		Ray ray11 = new Ray (new Point3D(-1,1,0),new Vector(2.5,1,0));
		ArrayList<GeoPoint> myintersectionPoints10=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray11);
		assertEquals(null,myintersectionPoints10);
  // TC20: Ray starts at the tangent point
		Ray ray12 = new Ray (new Point3D(0,1,0),new Vector(2,0,0));
		ArrayList<GeoPoint> myintersectionPoints11=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray12);
		assertEquals(null,myintersectionPoints11);
  // TC21: Ray starts after the tangent point
		Ray ray13 = new Ray (new Point3D(1,1,0),new Vector(1,0,0));
		ArrayList<GeoPoint> myintersectionPoints12=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray13);
		assertEquals(null,myintersectionPoints12);

		// **** Group: Special cases
  // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
		Ray ray14 = new Ray (new Point3D(0,2,0),new Vector(2,0,0));
		ArrayList<GeoPoint> myintersectionPoints13=(ArrayList<GeoPoint>) sphere2.findIntsersections(ray14);
		assertEquals(null,myintersectionPoints13);
	
	}
}
