package Unittests;


import java.awt.Color;
import renderer.ImageWriter;
import org.junit.Test;

public class ImageWriterTest {
	/**
	 * Test method for {@link renderer.ImageWriter}.
	 */
	@Test
	public void testImageWriter()
	{
		 ImageWriter imageWriter = new ImageWriter("IMAGE", 1600, 1000, 800, 500);
	        int Nx = imageWriter.getNx();
	        int Ny = imageWriter.getNy();
	        for (int i = 0; i < Ny; i++) {
	            for (int j = 0; j < Nx; j++) {
	                if (i % 50 == 0 || j % 50 == 0) {
	                    imageWriter.writePixel(j, i, Color.YELLOW);
	                } else {
	                    imageWriter.writePixel(j, i, Color.BLUE);
	                }
	            }
	        }
	        imageWriter.writeToImage(); 
	}

}
