package geometries;

import java.util.List;
import primitives.Point3D;
import primitives.*;
import static primitives.Util.alignZero;
public class Sphere extends RadialGeometry

{
	private Point3D P;

// ***************** Constructors ********************** // 

	  public Sphere(Point3D _P,double _r)
	  {
		  super(_r);
		  P=new Point3D(_P);
	  }
	  public Sphere(Color emissionLight, Material material, double radius, Point3D center) {
	        super(emissionLight, radius, material);
	        this.P = new Point3D(center);
	    }

	    public Sphere(Color emissionLight, double radius, Point3D center) {
	       this(emissionLight,new Material(0,0,0),radius,center);
	    }
	
// ***************** Getter********************** // 

	  public Point3D getp()
	  {
		  return P;
	  }
	  public Vector getNormal (Point3D p)
		{
			 Point3D point = new Point3D(p);
		     return  point.subtract(this.P).normalize();
		}
	  @Override	
		public List<GeoPoint> findIntsersections (Ray ray)//מחזיר רשימת נקודות חיתוך למשטח עם הקרן שמתקבלת
		{	
		  Point3D p0 = ray.get_Point();
	        Vector v = ray.get_direction();
	        Vector u;
	        try {
	            u = P.subtract(p0);   // p0 == _center
	        } catch (IllegalArgumentException e) {
	            return List.of(new GeoPoint(this, (ray.getTargetPoint(this._radius))));
	        }
	        double tm = alignZero(v.dotProduct(u));
	        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
	        double thSquared = alignZero(this._radius * this._radius - dSquared);

	        if (thSquared <= 0) return null;

	        double th = alignZero(Math.sqrt(thSquared));
	        if (th == 0) return null;

	        double t1 = alignZero(tm - th);
	        double t2 = alignZero(tm + th);
	        if (t1 <= 0 && t2 <= 0) return null;
	        if (t1 > 0 && t2 > 0) {
	            return List.of(
	                    new GeoPoint(this,(ray.getTargetPoint(t1)))
	                    ,new GeoPoint(this,(ray.getTargetPoint(t2)))); //P1 , P2
	        }
	        if (t1 > 0)
	            return List.of(new GeoPoint(this,(ray.getTargetPoint(t1))));
	        else
	            return List.of(new GeoPoint(this,(ray.getTargetPoint(t2))));
	    }
}
