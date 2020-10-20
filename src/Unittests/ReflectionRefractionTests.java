package Unittests;

import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
						new Point3D(0, 0, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
				0.0004, 0.0000006));

		ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
		Renderer render = new Renderer(imageWriter, scene) //
				.setMultithreading(3) //
				.setDebugPrint();
		
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

		scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150), 
				   new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
		Renderer render = new Renderer(imageWriter, scene) //
				.setMultithreading(3) //
				.setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 *  producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(60, -50, 50)));

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Renderer render = new Renderer(imageWriter, scene) //
				.setMultithreading(3) //
				.setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void trianglesTransparentSphere2() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 65), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(35, -50, 50)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0.6, 0), // )
						15, new Point3D(25, -50, 50)));

		scene.addLights(new SpotLight(new Color(700, 350, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency2", 200, 200, 600, 600);
		Renderer render = new Renderer(imageWriter, scene) //
				.setMultithreading(3) //
				.setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}
	@Test
	public void softshadow2() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 65), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						15, new Point3D(55, -50, 100)),
				new Sphere(new Color(java.awt.Color.GRAY), new Material(0.2, 0.2, 30, 0.6, 0), // )
						10, new Point3D(55, -50, 100)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0.6, 0), // )
						5, new Point3D(55, -50, 100)),
		        new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
				        15, new Point3D(-85, 80, 100)),
		        new Sphere(new Color(java.awt.Color.GRAY), new Material(0.2, 0.2, 30, 0.6, 0), // )
				        10, new Point3D(-85, 80, 100)),
		        new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0.6, 0), // )
			        	5, new Point3D(-85, 80, 100)));
 
		scene.addLights(new SpotLight(new Color(700, 350, 400).reduce(3), //
				new Point3D(100, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7,10));
		scene.addLights(new SpotLight(new Color(700, 350, 400).reduce(3), //
						new Point3D(-80, 50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7,10));
		scene.addLights(new SpotLight(new Color(700, 350, 400).reduce(3), //
						new Point3D(50, 20, 30), new Vector(0, 0, 1), 1, 4E-5, 2E-7,10));

		ImageWriter imageWriter = new ImageWriter("soft shadow2", 200, 200, 600, 600);
		Renderer render = new Renderer(imageWriter, scene)//
				.setMultithreading(3) //
				.setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void softshadow1() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
	
		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(60, -50, 50)));
	
		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7,10));
	
		ImageWriter imageWriter = new ImageWriter("soft shadow1", 200, 200, 600, 600);
		Renderer render = new Renderer(imageWriter, scene) //
				.setMultithreading(3) //
				.setDebugPrint()
				.setMaxRaysForSuperSampling(81)
				.set_adaptiveSuperSampling(true);
		render.renderImage();
		render.writeToImage();
	}
	
	
	
	@Test
	public void snuker() {
	
	Scene scene = new Scene("Test snuker scene");
	scene.setCamera(new Camera(new Point3D(0,0,0), new Vector(0, 0, -1), new Vector(0,1, 0)));
	scene.setDistance(200);
	scene.setBackground(Color.BLACK);
	scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

	
	scene.addGeometries(
			//רגליים ימין למעלה
		 new Triangle(new Color(255,80,0),new Point3D(340, 145, -280),
					 new Point3D(340,-40, -280),
					 new Point3D(300, -40, -280)),
			
	     new Triangle(new Color(255,80,0),new Point3D(340, 145, -280),
					 new Point3D(300, 145, -280),
					 new Point3D(300,-40, -280)),
 
	//משולשים ירוקים של השולחן
	     new Triangle( new Color(java.awt.Color.GREEN),new Material(0.5,0.5,20,0,1),new Point3D(-145, 145, -270),//green
			 new Point3D(300, 125, -260),
			 new Point3D(125, -125, -260)),
	
	     new Triangle(new Color(java.awt.Color.GREEN),new Material(1,1,20,0,1),new Point3D(-125, 125, -260),//green
			 new Point3D(-300, -125, -260),
			 new Point3D(125, -125, -260)),
	
	//משולשים חומים של המסגרת
	     new Triangle(new Color(255,80,0),new Point3D(-145, 145, -270),//brown
			 new Point3D(340, 145, -270),
			 new Point3D(145, -145, -270)),
	
	     new Triangle( new Color(255,80,0),new Point3D(-145, 145, -270),//brown
			 new Point3D(-340, -145, -270),
			 new Point3D(145, -145, -270)),

	//רגליים שמאל למטה
	      new Triangle(new Color(255,80,0),new Point3D(-340, -145, -280),
			 new Point3D(-300,-145, -280),
			 new Point3D(-300, -300, -280)),
         
	      new Triangle(new Color(255,80,0),new Point3D(-340, -145, -280),
			 new Point3D(-340, -300, -280),
			 new Point3D(-300, -300, -280)),
	
	//רגליים ימין למטה
	       new Triangle(new Color(255,80,0),new Point3D(145, -145, -280),
			 new Point3D(105,-145, -280),
			 new Point3D(105, -300, -280)),
	
	       new Triangle(new Color(255,80,0),new Point3D(145, -145, -280),
			 new Point3D(145, -300, -280),
			 new Point3D(105, -300, -280)),
	       
	       new Triangle(Color.BLACK,new Point3D(40, 40, -149),
  				 new Point3D(40, -40, -149),
  				 new Point3D(-45, 0, -149)),

   		        //משולש ירוק של הכדורים
             new Triangle(new Color(java.awt.Color.green),new Point3D(30, 30, -140),
 				 new Point3D(30, -30, -140),
 				 new Point3D(-35, 0, -140)),
                //כדור לבן מחוץ למשולש
  		     new Sphere(new Color(java.awt.Color.pink),new Material(1,1,20,0,0),6.5, new Point3D(-80, 0, -135)),
  	        	//כדורים שונים 10
             new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),6.5, new Point3D(22, 18, -135)),
			 new Sphere(new Color(java.awt.Color.RED),new Material(1,1,20,0.5,0),6.5, new Point3D(22, 5, -135)),
			 new Sphere(new Color(java.awt.Color.yellow),new Material(1,1,20,0.5,0),6.5, new Point3D(22, -7, -135)),
			 new Sphere(new Color(java.awt.Color.blue),new Material(1,1,20,0.5,0),6.5, new Point3D(22, -19, -135)),
			 new Sphere(new Color(255,100,0),new Material(1,1,20,0.5,0),6.5, new Point3D(9, 12, -135)),
			 new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),6.5, new Point3D(9, -0.5, -135)),
			 new Sphere(new Color(50,150,255),new Material(1,1,20,0.5,0),6.5, new Point3D(9, -13, -135)),
			 new Sphere(new Color(java.awt.Color.pink),new Material(1,1,20,0.5,0),6.5, new Point3D(-5,5, -135)),
			 new Sphere(new Color(255,0,255),new Material(1,1,20,0.5,0),6.5, new Point3D(-5, -5, -135)),
			 new Sphere(new Color(java.awt.Color.gray),new Material(1,1,20,0.5,0),6.5, new Point3D(-19, 0, -135)),
				// 6 כדורים על המסגרת-בורות
			 new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(300, 125, -260)),
			 new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(-125, 125, -260)),
	         //new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(87.5, 125, -260)),
	         new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(125, 125, -260)),
	         new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(-300,-125, -260)),
	         new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(-87.5, -125, -260)),
	         new Sphere(Color.BLACK,new Material(1,1,20,0.5,0),9, new Point3D(125, -125, -260)),
	         
	         new Triangle(new Color(70, 0, 70),new Material(1,1,20,0,0),new Point3D(700, 875, -1000),
	                 new Point3D(900, 1000, -1000),
	                 new Point3D(900, 750, -1000)),
	         new Triangle(new Color(51, 0, 51),new Material(1,1,20,0,0),new Point3D(700, 875, -1000),
	                 new Point3D(700, 625, -1000),
	                 new Point3D(900, 750, -1000)),
	         new Triangle(new Color(0, 76, 153),new Material(1,1,20,0,0),new Point3D(700, 875, -1000),
	                 new Point3D(700, 625, -1000),
	                 new Point3D(500, 750, -1000)),
	         new Triangle(new Color(0, 102, 204),new Material(1,1,20,0,0),new Point3D(700, 875, -1000),
	                 new Point3D(500, 1000, -1000),
	                 new Point3D(500, 750, -1000)),
	         new Triangle(new Color(185, 87, 27),new Material(1,1,20,0,0),new Point3D(-87, 19, -135),
	                 new Point3D(-90, 17, -135),
	                 new Point3D(-158, 195, -170)),
	         new Triangle(new Color(185, 87, 27),new Material(1,1,20,0,0),new Point3D(-87, 19, -135),
	                 new Point3D(-145, 200, -170),
	                 new Point3D(-158, 195, -170)),
	         new Sphere( new Color(185, 87, 27),new Material(1,1,20,0.5,0),9, new Point3D(-150.1, 195, -170)));
        

           

             //אור ספוט
             scene.addLights(new SpotLight(new Color(0, 100, 100), new Point3D(200, 200, -120), 
                     new Vector(-2, -2, -3), 0.5, 0.001, 0.0005,10));//0.5 זה העוצמה
             scene.addLights(new PointLight(Color.WHITE, new Point3D(22, -19, -260), 1, 0.0001, 0.005,10));
             scene.addLights(new SpotLight(new Color(0, 100, 100), new Point3D(500, 500, -700), 
            new Vector(2, 2, 3), 1, 0.001, 0.0005,10));//0.5 זה העוצמה
            ImageWriter imageWriter = new ImageWriter("Snuker Test", 500, 500, 500, 500);
        	Renderer render = new Renderer(imageWriter, scene) //
			.setMultithreading(3) //
			.setDebugPrint()
			.setMaxRaysForSuperSampling(81)
			.set_adaptiveSuperSampling(true);
	         render.renderImage();
	         render.writeToImage();
}

}
	

