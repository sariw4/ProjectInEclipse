package renderer;

import elements.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;



public class Renderer {
	private  int numOfRaysSoftShadow =81;//num of the rays we send from plane to light source
	
		private int maxRaysForSuperSampling = 1;
		private static final int MAX_CALC_COLOR_LEVEL = 5;
	    private static final double MIN_CALC_COLOR_K = 0.0001;
	    private int _threads = 3;
		private final int SPARE_THREADS = 2;
		private boolean _print = true;
		private boolean _adaptiveSuperSampling = false;

	    private  ImageWriter _imageWriter;
	    private  Scene _scene;
	    // ***************** Constructors ********************** //
	    /**
	     *  constructor accepting imagewriter  and scene parameters
	     *  @param imageWriter is object of type imageWriter class
	     *  @param scene is object of type scene class
	     */
	    public Renderer(ImageWriter imageWriter, Scene scene) {
	        this._imageWriter = imageWriter;
	        this._scene = scene;
	    }
	    /**
	     * function printGrid produces a grid 
	     * 
	     */
	    public void printGrid(int interval, java.awt.Color color) {
	        int Nx = _imageWriter.getNx();
	        int Ny = _imageWriter.getNy();
	        for (int i = 0; i < Ny; i++) {
	            for (int j = 0; j < Nx; j++) {
	                if (i % interval == 0 || j % interval == 0) {
	                    _imageWriter.writePixel(j, i, color);
	                }
	            }
	        }
	    }

	    public void writeToImage() {
	        _imageWriter.writeToImage();
	    }
	    /**
	     * function renderImage passes on the pixels and send it to writepixel function
	     * 
	     */

	    public void renderImage() {
	        Camera camera = _scene.getCamera();
	        double distance = _scene.getDistance();

	        //width and height are the number of pixels in the rows
	        //and columns of the view plane
	        int width = (int) _imageWriter.getWidth();
	        int height = (int) _imageWriter.getHeight();

	        //Nx and Ny are the width and height of the image.
	        int Nx = _imageWriter.getNx(); //columns
	        int Ny = _imageWriter.getNy(); //rows
	        //pixels grid
	        final Pixel thePixel = new Pixel(Ny, Nx); // Main pixel management object
	        Thread[] threads = new Thread[_threads];
	        for (int i = _threads - 1; i >= 0; --i) { // create all threads
	            threads[i] = new Thread(() -> {
	                Pixel pixel = new Pixel(); // Auxiliary thread’s pixel object
	                while (thePixel.nextPixel(pixel)) {
//	                    Ray rays = camera.constructRayThroughPixel(Nx, Ny, pixel.col, pixel.row, distance, width, height);
//	                    GeoPoint closestPoint = findCLosestIntersection(rays);
//	                    _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background : calcColor(closestPoint, rays).getColor());
	                		if(_adaptiveSuperSampling)
	                		{
	 	                	   primitives.Color color = AdaptiveSuperSampling(Nx, Ny, pixel.col, pixel.row, distance, width, height , maxRaysForSuperSampling);
	                           _imageWriter.writePixel(pixel.col, pixel.row, color.getColor());
	                		}
	                		else 
	                		{
	    						List<Ray> rays =camera.constructRaysThroughPixel(Nx, Ny, pixel.col, pixel.row, distance, width, height);
	    						_imageWriter.writePixel(pixel.col, pixel.row, calcColor(rays).getColor());
	                		}



	                }});
	        }
	        for (Thread thread : threads) thread.start(); // Start all the threads
	        // Wait for all threads to finish
	        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
	        if (_print) System.out.printf("\r100%%\n"); // Print 100%
	    

	            }
	    



		/**
	     * Find intersections of a ray with the scene geometries and get the
	     * intersection point that is closest to the ray head. If there are no
	     * intersections, null will be returned.
	     *
	     * @param ray intersecting the scene
	     * @return the closest point
	     */
	    private GeoPoint findClosestIntersection(Ray ray) {
	        if (ray == null) {
	            return null;
	        }
	        GeoPoint closestPoint = null;
	        double closestDistance = Double.MAX_VALUE;
	        Point3D ray_p0 = ray.get_Point();

	        List<GeoPoint> intersections = _scene.getGeometries().findIntsersections(ray);
	        if (intersections == null)
	            return null;

	        for (GeoPoint geoPoint : intersections) {
	            double distance = ray_p0.distance(geoPoint.getPoint());
	            if (distance < closestDistance) {
	                closestPoint = geoPoint;
	                closestDistance = distance;
	            }
	        }
	        return closestPoint;
	    }
	    /**
	     * fanction calcColor call to recursive function calcColor 
	     * 
	     */
	    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
	        Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
	        color = color.add(_scene.getAmbientLight().getIntensity());
	        return color;
	    }
	    /**
	     * recursive function calcColor calculates the color of geometry by all its parameter of material and light 
	     * 
	     */
	    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
	    	try {
	        if (level == 1 || k < MIN_CALC_COLOR_K) {
	            return Color.BLACK;
	        }

	        Color result = geoPoint.getGeometry().getEmissionLight();
	        Point3D pointGeo = geoPoint.getPoint();

	        Vector v = pointGeo.subtract(_scene.getCamera().getP0()).normalize();
	        Vector n = geoPoint.getGeometry().getNormal(pointGeo);

	        Material material = geoPoint.getGeometry().getMaterial();
	        int nShininess = material.getnShininess();
	        double kd = material.getKd();
	        double ks = material.getKs();
	        double kr = geoPoint.getGeometry().getMaterial().getKr();
	        double kt = geoPoint.getGeometry().getMaterial().getKt();
	        double kkr = k * kr;
	        double kkt = k * kt;

	        result = getLightSourcesColors(geoPoint, k, result, v, n, nShininess, kd, ks);

	        if (kkr > MIN_CALC_COLOR_K) {
	            Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);
	            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
	            if (reflectedPoint != null) {
	                result = result.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
	            }
	        }
	        if (kkt > MIN_CALC_COLOR_K) {
	            Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);
	            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
	            if (refractedPoint != null) {
	                result = result.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
	            }
	        }
	        return result;
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.print(e.getMessage());
	    		return Color.BLACK;
	    	}
	    }
	    
	    
	   private Color calcColor(List<Ray> rays)
	    {

	        Color x=Color.BLACK;
	        GeoPoint p;
	        for(Ray r:rays){
	             p= findClosestIntersection(r);
	            if(p==null)
	                x=x.add(_scene.getBackground());
	            else
	                x=x.add(calcColor(p,r));
	        }
	        x= x.reduce(rays.size());
	        return x;
	    }


	    /**
	     * function getLightSourcesColors add the Specular and Diffusive to the color that we calculates
	     * 
	     */
	    private Color getLightSourcesColors(GeoPoint geoPoint, double k, Color result, Vector v, Vector n, int nShininess, double kd, double ks)throws Exception  {
	        Point3D pointGeo = geoPoint.getPoint();
	        List<LightSource> lightSources = _scene.getLightSources();
	        if (lightSources != null) {
	            for (LightSource lightSource : lightSources) {
	                Vector l = lightSource.getL(pointGeo);
	                double nl = alignZero(n.dotProduct(l));
	                double nv = alignZero(n.dotProduct(v));
	                if (nl * nv > 0) {
	                    double ktr = transparency(lightSource, l, n, geoPoint);
	                    if (ktr * k > MIN_CALC_COLOR_K) {
	                        Color ip = lightSource.getIntensity(pointGeo).scale(ktr);
	                        result = result.add(
	                                calcDiffusive(kd, nl, ip),
	                                calcSpecular(ks, l, n, nl, v, nShininess, ip));
	                    }
	                }
	            }
	        }
	        return result;
	    }
	    /**
	     * function constructRefractedRay produces a ray from Point3D,Ray,Vector 
	     * 
	     */
	    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
	        return new Ray(pointGeo, inRay.get_direction(), n);
	    }
	    /**
	     * function constructReflectedRay produces a ray from Point3D,Ray,Vector 
	     * 
	     */
	    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
	        Vector v = inRay.get_direction();
	        double vn = v.dotProduct(n);

	        if (vn == 0) {
	            return null;
	        }

	        Vector r = v.subtract(n.scale(2 * vn));
	        return new Ray(pointGeo, r, n);
	    }

	    /**
	     * Calculate Specular component of light reflection.
	     *
	     * @param ks         specular component coef
	     * @param l          direction from light to point
	     * @param n          normal to surface at the point
	     * @param nl         dot-product n*l
	     * @param V          direction from point of view to point
	     * @param nShininess shininess level
	     * @param ip         light intensity at the point
	     * @return specular component light effect at the point
	     * @author Dan Zilberstein (slightly modified by me)
	     * <p>
	     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
	     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
	     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
	     * the surface.
	     */
	    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector V, int nShininess, Color ip) {
	        double p = nShininess;
	        if (isZero(nl)) {
	            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
	        }
	        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
	        double VR = alignZero(V.dotProduct(R));
	        if (VR >= 0) {
	            return Color.BLACK; // view from direction opposite to r vector
	        }
	        // [rs,gs,bs]ks(-V.R)^p
	        return ip.scale(ks * Math.pow(-1d * VR, p));
	    }

	    /**
	     * Calculate Diffusive component of light reflection.
	     *
	     * @param kd diffusive component coef
	     * @param nl dot-product n*l
	     * @param ip light intensity at the point
	     * @return diffusive component of light reflection
	     * @author Dan Zilberstein
	     * <p>
	     * The diffuse component is that dot product n•L. It approximates light, originally from light source L,
	     * reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossysurface is paper.
	     * In general, you'll also want this to have a non-gray color value,
	     * so this term would in general be a color defined as: [rd,gd,bd](n•L)
	     */
	    private Color calcDiffusive(double kd, double nl, Color ip) {
	        return ip.scale(Math.abs(nl) * kd);
	    }

	   
	    /**
	     * function transparency returns the value of transparency partial shading by checking if there ara object 
	     * thet block the light source from the specific geopoint
	     */
	 
	    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint)throws Exception  {
	        Vector lightDirection = l.scale(-1); // from point to light source
	        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
	        Point3D pointGeo = geopoint.getPoint();

	        List<GeoPoint> intersections = _scene.getGeometries().findIntsersections(lightRay);
	        if (intersections == null) {
	            return 1d;//no shadow
	        }
	        
	        double ktr=1;
	        double lightDistance = light.getDistance(pointGeo);
	        for (GeoPoint gp : intersections) {
	            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0) {//real intersection
	                ktr *= gp.getGeometry().getMaterial().getKt();
	                if (ktr < MIN_CALC_COLOR_K) {
	                	ktr = 0.0;
	                	break;
   
	                }
	            }
	        }
	        ktr += softShadow(geopoint,light,l);
	        if(ktr>1)
	        	ktr=1;
	        return ktr;
	    }
	    /**
	     * function softShadow improve the shadow to be softer 
	     * 
	     */
	    private double softShadow(GeoPoint geo, LightSource light,Vector lightDirection ) throws Exception
	    {
	    	//save all the test that don't have grid
	    	if(isZero(light.getGridSize()))
	    		return 0d;
	    	
	    	//sqrt of numOfRaysSoftShadow and rounds the number up
	    	int numOfRays = (int)Math.ceil(Math.sqrt(numOfRaysSoftShadow));
	    	//count the num of the rays that hit the light source
	    	int counter = 0;
	    	Ray tempRay;
	    	GeoPoint tempGeoPoint;
	    	Point3D lightP = light.getPosition();
	    	//size of cube in the grid 
	    	double cubeSize = light.getGridSize()/numOfRays;
	    	Vector Vright=lightDirection.findOrthogonal().normalize();
	    	Vector Vup=Vright.crossProduct(lightDirection).normalize();
	    	Point3D tempPosition = lightP;
	    	//pass on the grid 
	    	for(int i = 0 ; i < numOfRays ; i++) 
	    	{
	    		//take the center point of the light source to the center of the cube (coordinate x) 
	    		double PX= (i - (numOfRays/2d))*cubeSize + cubeSize/2d;
	    		for(int j = 0 ; j < numOfRays ; j++)
	    		{
	    			//take the center point of the light source to the center of the cube (coordinate y) 
	    			double PY= (j - (numOfRays/2d))*cubeSize + cubeSize/2d;
	                if(PX != 0) tempPosition = tempPosition.add(Vright.scale(-PX)) ;
	                if(PY != 0) tempPosition = tempPosition.add(Vup.scale(-PY)) ;
	                
	
	    			tempRay = new Ray(tempPosition, geo.getPoint().subtract(tempPosition));
	    			tempGeoPoint = findClosestIntersection(tempRay);
	    			//check if the ray that we sent from the geopoint don't Intersection with another object
	    			if(tempGeoPoint.equals(geo))
	    				counter++;
	    			tempPosition = lightP;
	    		}
	    		 
	    	}
	    	//return the number of rays that hit the light source /  the number of rays we sent
	    	return (double)counter/(numOfRays*numOfRays);
	    }
	    	
	    
	    private Color AdaptiveSuperSampling(int Nx,int Ny,int col, int row,double distance,double width,double height, int rays)
	    {
	        Camera camera = _scene.getCamera();
	        Vector Vright = camera.getvRight();
	        Vector Vup = camera.getvUp();
	        Point3D cameraLoc = camera.getP0();
	        rays = (int)Math.floor(Math.sqrt(rays));
	        Point3D Pc;
	        if (distance != 0)
	            Pc = cameraLoc.add(camera.getvToward().scale(distance));
	        else
	            Pc = cameraLoc;
	        Point3D Pij = Pc;
	        double Ry = height/Ny;
	        double Rx = width/Nx;
	        double Yi= (row - (Ny/2d))*Ry + Ry/2d;
	        double Xj= (col - (Nx/2d))*Rx + Rx/2d;
	        if(Xj != 0) Pij = Pij.add(Vright.scale(Xj)) ;
	        if(Yi != 0) Pij = Pij.add(Vup.scale(-Yi)) ;
	        double PRy = Ry/rays;
	        double PRx = Rx/rays;
	        return RecSuperSampling(Pij,Vright,Vup,Rx,Ry,cameraLoc,(PRy+PRx)/2);
	    	
	    	
	    }
		
	    
	    private Color RecSuperSampling(Point3D tempPosition, Vector Vright, Vector Vup, double Width, double Height, Point3D camera , double cubeSize)
	    {
	    	
	    	
    		Width=Width/2;
    		Height=Height/2;
    		Point3D center = tempPosition;
	    	Point3D p1=(tempPosition.add(Vright.scale(Width))).add(Vup.scale(Height));
	    	Ray tempRay1 = new Ray(camera,p1.subtract(camera));
	    	GeoPoint tempGeoPoint = findClosestIntersection(tempRay1);
		    Color c1= primitives.Color.BLACK;;
		    if(tempGeoPoint==null)
                c1 = c1.add(this._scene.getBackground());
            else
            	c1 = c1.add(calcColor(tempGeoPoint, tempRay1));
			Point3D tempPosition1=p1.add(Vright.scale(Width*-0.5)).add(Vup.scale(Height*-0.5));
			tempPosition = center;
			
			Point3D p2=(tempPosition.add(Vright.scale(-Width))).add(Vup.scale(Height));
		    Ray tempRay2 = new Ray(camera,p2.subtract(camera));
		    GeoPoint tempGeoPoint2 = findClosestIntersection(tempRay2);
		    Color c2= primitives.Color.BLACK;;
		    if(tempGeoPoint2==null)
                c2 = c2.add(this._scene.getBackground());
            else
            	c2 = c2.add(calcColor(tempGeoPoint2, tempRay2));
			Point3D tempPosition2=p2.add(Vright.scale(Width*0.5)).add(Vup.scale(Height*-0.5));
			tempPosition = center;
			
			Point3D p3=(tempPosition.add(Vright.scale(-Width))).add(Vup.scale(-Height));
			Ray tempRay3 = new Ray(camera,p3.subtract(camera));
			GeoPoint tempGeoPoint3 = findClosestIntersection(tempRay3);
		    Color c3 = primitives.Color.BLACK;
		    if(tempGeoPoint3==null)
                c3 = c3.add(this._scene.getBackground());
            else
            	c3 = c3.add(calcColor(tempGeoPoint3, tempRay3));
			Point3D tempPosition3=p3.add(Vright.scale(Width*0.5)).add(Vup.scale(Height*0.5));
			tempPosition = center;
			
			Point3D p4=(tempPosition.add(Vright.scale(Width))).add(Vup.scale(-Height));
		    Ray tempRay4 = new Ray(camera,p4.subtract(camera));
		    GeoPoint tempGeoPoint4 = findClosestIntersection(tempRay4);
		    Color c4= primitives.Color.BLACK;
		    if(tempGeoPoint4==null)
                c4 = c4.add(this._scene.getBackground());
            else
            	c4 = c4.add(calcColor(tempGeoPoint4, tempRay4));
			Point3D tempPosition4=p4.add(Vright.scale(Width*-0.5)).add(Vup.scale(Height*0.5));

			if(Width*2<=2*cubeSize||Height*2<=2*cubeSize)
				return c1.add(c2,c3,c4).reduce(4);
				
			if(equalcolor( c1, c2, c3, c4)) 
			{
				 return c1;
			}
			else
			{
				return  RecSuperSampling(tempPosition1,  Vright,  Vup,  Width,  Height,  camera ,  cubeSize).add(//);
						RecSuperSampling(tempPosition2,  Vright,  Vup,  Width,  Height,  camera ,  cubeSize),//);
						RecSuperSampling(tempPosition3, Vright,  Vup,  Width,  Height,  camera ,  cubeSize),//);
						RecSuperSampling(tempPosition4, Vright,  Vup,  Width,  Height,  camera ,  cubeSize)).reduce(4);
			}
				
		   /* //check if the ray that we sent from the geopoint don't Intersection with another object
			if(tempGeoPoint4.equals(geo))
				counter += numOfRays*0.25;
			else 
			{	
				tempPosition=p4.add(Vright.scale(GridSize*-0.5)).add(Vup.scale(GridSize*0.5));
				counter += 0.25*RecSoftShados(tempPosition,Vright,Vup,GridSize,geo,tempPosition,numOfRays*0.25,cubeSize);
			}*/
			
			

	    }
	    public boolean  equalcolor(Color c1,Color c2,Color c3,Color c4) 
		{
	    	if(			Math.abs(c1.getColor().getBlue()-c2.getColor().getBlue())<=2 
					&&  Math.abs(c1.getColor().getBlue()-c3.getColor().getBlue())<=2
					&&  Math.abs(c1.getColor().getBlue()-c4.getColor().getBlue())<=2
					&&  Math.abs(c1.getColor().getGreen()-c2.getColor().getGreen())<=2
					&&  Math.abs(c1.getColor().getGreen()-c3.getColor().getGreen())<=2
					&&  Math.abs(c1.getColor().getGreen()-c4.getColor().getGreen())<=2
					&&  Math.abs(c1.getColor().getRed()-c2.getColor().getRed())<=2
					&&  Math.abs(c1.getColor().getRed()-c3.getColor().getRed())<=2
					&&  Math.abs(c1.getColor().getRed()-c4.getColor().getRed())<=2)
			{
				 return true;
			}
	    	return false;
		}


	

	    
	    private class Pixel {
			private long _maxRows = 0;
			private long _maxCols = 0;
			private long _pixels = 0;
			public volatile int row = 0;
			public volatile int col = -1;
			private long _counter = 0;
			private int _percents = 0;
			private long _nextCounter = 0;

			/**
			 * The constructor for initializing the main follow up Pixel object
			 * @param maxRows the amount of pixel rows
			 * @param maxCols the amount of pixel columns
			 */
			public Pixel(int maxRows, int maxCols) {
				_maxRows = maxRows;
				_maxCols = maxCols;
				_pixels = maxRows * maxCols;
				_nextCounter = _pixels / 100;
				if (Renderer.this._print) System.out.printf("\r %02d%%", _percents);
			}

			/**
			 *  Default constructor for secondary Pixel objects
			 */
			public Pixel() {}

			/**
			 * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
			 * critical section for all the threads, and main Pixel object data is the shared data of this critical
			 * section.<br/>
			 * The function provides next pixel number each call.
			 * @param target target secondary Pixel object to copy the row/column of the next pixel 
			 * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
			 * finished, any other value - the progress percentage (only when it changes)
			 */


			 private synchronized int nextP(Pixel target) {
			      ++col;
			      ++_counter;
			      if (col < _maxCols) {
			        target.row = this.row;
			        target.col = this.col;
			        if (_print && _counter == _nextCounter) {
			          ++_percents;
			          _nextCounter = _pixels * (_percents + 1) / 100;
			          return _percents;
			        }
			        return 0;
			      }
			      ++row;
			      if (row < _maxRows) {
			        col = 0;
			        target.row = this.row;
			        target.col = this.col;
			        //if (_print && _counter == _nextCounter) {
			        if (_counter == _nextCounter) {
			          ++_percents;
			          _nextCounter = _pixels * (_percents + 1) / 100;
			          return _percents;
			        }
			        return 0;
			      }
			      return -1;
			    }

			/**
			 * Public function for getting next pixel number into secondary Pixel object.
			 * The function prints also progress percentage in the console window.
			 * @param target target secondary Pixel object to copy the row/column of the next pixel 
			 * @return true if the work still in progress, -1 if it's done
			 */
			public boolean nextPixel(Pixel target) {
				int percents = nextP(target);
				if (percents > 0)
					if (Renderer.this._print) System.out.printf("\r %02d%%", percents);
				if (percents >= 0)
					return true;
				if (Renderer.this._print) System.out.printf("\r %02d%%", 100);
				return false;
			}
		}
	    /**
			 * Set multithreading <br>
			 * - if the parameter is 0 - number of coress less 2 is taken
			 * 
			 * @param threads number of threads
			 * @return the Render object itself
			 */
			public Renderer setMultithreading(int threads) {
				if (threads < 0)
					throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
				if (threads != 0)
					_threads = threads;
				else {
					int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;

		            _threads = cores <= 2 ? 1 : cores;
		        }
		        return this;
		   
				
			}

			/**
			 * Set debug printing on
			 * 
			 * @return the Render object itself
			 */
			public Renderer setDebugPrint() {
				_print = true;
				return this;
			}
			
			public Renderer setMaxRaysForSuperSampling(int maxRaysForSuperSampling) {
				this.maxRaysForSuperSampling = maxRaysForSuperSampling;
				return this;
			}
			
		    public Renderer set_adaptiveSuperSampling(boolean _adaptiveSuperSampling) {
				this._adaptiveSuperSampling = _adaptiveSuperSampling;
				return this;
			}
		
	}

