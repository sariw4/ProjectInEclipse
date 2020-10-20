/**
 * 
 */
package Unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author user
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
public void testGetNormal() {
		
   Point3D p = new Point3D(3,4,6);
   Vector v= new Vector(0,3,0);
   Point3D p0 = new Point3D(1,2,3);
   double radius = 4.96678;
   Ray ray = new Ray(p0, v);
   Tube tube = new Tube(ray,radius);
   Vector ExpectedNormal = new Vector(2, 0, 3);
    try {
      assertEquals("ERROR: tube doesn't calculate the normal right",ExpectedNormal.normalize(), tube.getNormal(p));
   }
   catch(IllegalArgumentException e) {
   }
   p = new Point3D(6,4,3);
   v= new Vector(2,3,1);
   p0 = new Point3D(9,2,3);
   ray = new Ray(p0, v);
   tube = new Tube( ray,radius );
   ExpectedNormal = new Vector(-3, 2, 0);
   try {
      assertEquals("ERROR: tube doesn't calculate the normal right when t is zero",ExpectedNormal.normalize(), tube.getNormal(p));
   }
   catch(IllegalArgumentException e) {
   }

	}
}
