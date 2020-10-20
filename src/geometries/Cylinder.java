package geometries;
import primitives.*;


public class Cylinder extends Tube
{
	private double v;
	
// ***************** Constructors ********************** // 

	  public Cylinder(double _v,double _radius,Ray R)
	  {
		  super(R,_radius);
	  }
	
// ***************** Getter********************** // 
	  public double getV() {
			return v;
		}
	 
// ***************** Administration  ******************** // 

	  @Override
	 public Vector getNormal(Point3D _p) {return null;}

	

}
