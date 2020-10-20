/**
 * 
 */
package Unittests;
import primitives.*;
import static primitives.Util.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author user
 *
 */
public class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd()
	{
        // ============ Equivalence Partitions Tests ==============
		Vector p1 = new Vector(1.0, 2.0, 3.0); 
		Vector p2 =new Vector(-1.0, -2.0, -3.0);
		Vector p3 = new Vector(0.0, 1.0, 0.0);
		// Test the add function works correctly
		 assertEquals("ERROR: Vector + Vector does not work correctly",p1.add(p3),new Vector(1.0, 3.0, 3.0));
	        // =============== Boundary Values Tests ==================
	   // Test zero vector from  adding vectors
		 try {
	            p1.add(p2);
	            fail("add () for vectors that create a zero vector does not throw an exception" );
	        } catch (Exception e) {}
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
    // ============ Equivalence Partitions Tests ==============
		Vector p1 = new Vector(1.0, 2.0, 3.0); 
		Vector p3 = new Vector(0.0, 1.0, 0.0);
	// Test the subtract function works correctly
    	 assertEquals("ERROR: Vector - Vector does not work correctly",p1.subtract(p3),new Vector(1.0, 1.0, 3.0));
    // =============== Boundary Values Tests ==================
    // Test zero vector from  subtracting vectors
	     try {
	            p1.subtract(p1);
	            fail("subtract () for vectors that create a zero vector does not throw an exception" );
              } catch (Exception e) {}	
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		 // ============ Equivalence Partitions Tests ==============
		Vector p1 = new Vector(1.0, 2.0, 3.0); 
	// Test the scale function works correctly
    	 assertEquals("ERROR: Vector * Scalar does not work correctly",p1.scale(2),new Vector(2.0, 4.0, 6.0));
    // =============== Boundary Values Tests ==================
    // Test zero vector from scaleing vectors
	     try {
	            p1.scale(0.0);
	            fail("scale () for vectors that create a zero vector does not throw an exception" );
              } catch (Exception e) {}	
	
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct()  {
		  Point3D p=new Point3D(5.0,3.0,4.0);
		  Point3D p2=new Point3D(4.0,2.0,1.0);
		  Vector n=new Vector(p);
		  Vector n2=new Vector(p2);
	 try
	   {
		 double d=n.dotProduct(n2);
		 if(d!=30)
		 fail("dotProduct Not working");
	   }
		 catch (ArithmeticException e)
	   {
		 assertTrue(true);
	   }
	 }

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(-2.0, -4.0, -6.0);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0.0, 3.0, -2.0);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
	}

	

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        // ============ Equivalence Partitions Tests ==============
        // Test the length of vector
        assertTrue("lengthSquared() result is not correct", isZero(v1.lengthSquared()-14));

	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
	     Vector v1 = new Vector(1.0, 2.0, 2.0);
	        // ============ Equivalence Partitions Tests ==============
	        // Test the length of vector
	        assertTrue("lengthSquared() result is not correct", isZero(v1.length()-3));	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
		Vector v = new Vector(1.0, 2.0, 3.0);
        Vector vCopyNormalize = v.normalize();
        assertTrue("normalize() function creates a new vector", v==vCopyNormalize);	
        assertTrue("normalize() result is not a unit vector", isZero(vCopyNormalize.length()-1));	

	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
	    // ============ Equivalence Partitions Tests ==============
			Vector v = new Vector(1.0, 2.0, 3.0);
	        Vector vCopyNormalized = v.normalized();
	        assertTrue("normalized() function does not create a new vector", v!=vCopyNormalized);	
	        assertTrue("normalized() result is not a unit vector", isZero(vCopyNormalized.length()-1));	}

}
