package geometries;
import static primitives.Util.*;
import primitives.*;
import java.util.List;

public class Plane extends Geometry {
	private Point3D p;
	private Vector vNormal;

// ***************** Constructors ********************** // 

	public Plane(Point3D p, Vector v)
	{
		this.p =new Point3D(p);
		this.vNormal = new Vector(v);
	}
	public Plane(Point3D p1,Point3D p2,Point3D p3)
	{
	  this.p =new Point3D(p1);
      Vector v1=p1.subtract(p2);
      Vector v2=p1.subtract(p3);
      this.vNormal=v1.crossProduct(v2).normalize();
	}
	public Plane(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight, material);

        p = new Point3D(p1);

        Vector U = new Vector(p1.subtract(p2));
        Vector V = new Vector(p1.subtract(p3));
        Vector N = U.crossProduct(V);
        N.normalize();

        vNormal = N;
//        _normal = N.scale(-1);

    }

    public Plane(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
        this(emissionLight, new Material(0, 0, 0), p1, p2, p3);
    }
// ***************** Getter********************** // 
 
	public Point3D getP() 
	{
		return p;
	}
	
	public Vector getNormal() 
	{
		return vNormal;
	}

// ***************** Administration  ******************** // 

	@Override
	public String toString() 
	{
		return "Plane [point=" + p + ", Normal=" +vNormal + "]";
	}
	public Vector getNormal (Point3D p)
	{
		return this.getNormal();
	}
	@Override
	public List<GeoPoint> findIntsersections (Ray ray)//מחזיר רשימת נקודות חיתוך למשטח עם הקרן שמתקבלת
	{
		Vector p0Q;
        try {
            p0Q = p.subtract(ray.get_Point());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = vNormal.dotProduct(ray.get_direction());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(vNormal.dotProduct(p0Q) / nv);

        if (t <= 0) {
            return null;
        }

        GeoPoint geo = new GeoPoint(this, ray.getTargetPoint(t));
        return List.of(geo);
	}
	
}
