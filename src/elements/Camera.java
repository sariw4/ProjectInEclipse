package elements;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

public class Camera {

	private Point3D p0;//מרכז ההקרנה (0,0,0)
	private Vector vUp;
    private Vector vToward;
    private Vector vRight;
	
	// ***************** Constructors ********************** // 
  
	public Camera(Point3D p, Vector vToward, Vector vUp) 
    {
		if(!isZero(vUp.dotProduct(vToward)))
		{
			throw new IllegalArgumentException("the vectors are not Handicapped vectors!");
		}
		this.p0 = new Point3D(p);
		if(vUp.length()!=1)
		{
			vUp.normalize();
		}
		if(vToward.length()!=1)
		{
			vToward.normalize();
		}
		this.vUp = new Vector(vUp);
		this.vToward =new Vector(vToward) ;
		this.vRight=vToward.crossProduct(vUp).normalize();
	}
    
	// ***************** Getters ********************** // 

	public Point3D getP0() 
	{
		return new Point3D(p0);
	}
	public Vector getvUp() 
	{
		return new Vector(vUp);
	}
	public Vector getvToward() 
	{
		return new Vector(vToward);
	}
	public Vector getvRight()
	{
		return new Vector(vRight);
	}
	
	// ***************** Operations ******************** // 

	public Ray constructRayThrowAPixel (int nX, int nY,
            int j, int i, double screenDistance,
            double screenWidth, double screenHeight)
	{
		double rX=screenWidth/nX;
		double rY=screenHeight/nY;
		double xJ=(j-(nX-1)/2d)*rX;
		double yI=(i-(nY-1)/2d)*rY;
		
		Point3D pIJ=p0.add(vToward.scale(screenDistance));
		if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0) pIJ = pIJ.add(vUp.scale(-yI));
		
		return new Ray(p0,pIJ.subtract(p0));
	}
	 public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
		{
			Point3D Pc = p0.add(vToward.scale(screenDistance));
			double Ry = screenHeight/nY;
			double Rx = screenWidth/nX;
			
			double yi;
			double xj;
			
			Vector vIJ;
			Ray myRay;
			List<Ray> allTheRays = new ArrayList<>();
			
			for(double rows = j; rows < j + 1; rows = rows + (1d/9)) {
				
				for (double columns = i; columns < i + 1; columns = columns + (1d/9))
				{
					Point3D pIJ = new Point3D(Pc);
					
					yi = ((columns - nY / 2.0) * Ry + Ry / 2);
					xj = (rows - nX / 2.0) * Rx + Rx / 2;
					
					
					if (xj != 0) 
					{
					pIJ = pIJ.add(vRight.scale(xj));
					}
					
					if (yi != 0)
					{
					pIJ = pIJ.add(vUp.scale(-yi));
					}
					
					vIJ = pIJ.subtract(p0);
					myRay = new Ray(p0, vIJ);
					allTheRays.add(myRay);
					}
				}
			return allTheRays;
		} 
	
}
