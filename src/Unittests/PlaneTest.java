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
public class PlaneTest {

	

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Plane p=new Plane(new Point3D(3.0,6.0,9.0), new Vector(5.0,6.0,7.0));
		Vector v=new Vector(5.0,6.0,7.0);
		 assertEquals("ERROR:GetNormal() function does not work correctly",p.getNormal(p.getP()), v);	}

	@Test
    public void testFindIntersections () throws Exception
     {
        // TC11:  Ray's line crosses the plane

	   List<Point3D> intersectionPoints= (List<Point3D>) new ArrayList<Point3D>();
	   Plane p=new Plane(new Point3D(1,2,3),new Vector(new Point3D(2,4,6)));
	   intersectionPoints.add(new Point3D(14, 0, 0));
	   List<GeoPoint> MyintersectionPoints=(List<GeoPoint>) p.findIntsersections(new Ray(new Point3D(1,0,0), new Vector(16,0,0)));
       assertEquals(intersectionPoints.get(0),MyintersectionPoints.get(0).point); 
      
       // TC12:  Ray's line isn't crosses the plane

	   Plane p1=new Plane(new Point3D(5,7,3),new Vector(new Point3D(1,0,0)));
	   List<GeoPoint> MyintersectionPoints1=(List<GeoPoint>) p1.findIntsersections(new Ray(new Point3D(16,6,0), new Vector(5,6,7)));
       assertEquals(null,MyintersectionPoints1); 
     }

}
