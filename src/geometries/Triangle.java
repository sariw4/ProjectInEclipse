package geometries;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.isZero;



import primitives.*;

public class Triangle extends Polygon 
{
private Point3D p1;
private Point3D p2;
private Point3D p3;

//***************** Constructors ********************** // 

public Triangle(Point3D p1, Point3D p2, Point3D p3) 
{
	super(p1,p2,p3);
	this.p1 = new Point3D(p1);
	this.p2 = new Point3D(p2);
	this.p3 = new Point3D(p3);
}
public Triangle(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
    super(emissionLight,material,p1,p2,p3);
 }
public Triangle(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
    super(emissionLight,p1, p2, p3);
  }

//***************** Getter********************** // 

public Point3D getP1() 
{
	return p1;
}
public Point3D getP2() 
{
	return p2;
}
public Point3D getP3() 
{
	return p3;
}
//***************** Administration  ******************** // 

@Override
public String toString() 
{
	return "Triangle [p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + "]";
}

@Override
public List<GeoPoint> findIntsersections (Ray ray)//מחזיר רשימת נקודות חיתוך למשטח עם הקרן שמתקבלת
{
	//עבור כל משולש בונים משולש על הצלע שלו מוצאים את ההיטל ולפי זה מוצאים את הנקודה אם הסימנים אותו סימן אז הנקודה על המשולש
	List<GeoPoint> planeIntersections = _plane.findIntsersections(ray);
    if (planeIntersections == null) return null;

    Point3D p0 = ray.get_Point();
    Vector v = ray.get_direction();

    Vector v1 = _vertices.get(0).subtract(p0);
    Vector v2 = _vertices.get(1).subtract(p0);
    Vector v3 = _vertices.get(2).subtract(p0);

    double s1 = v.dotProduct(v1.crossProduct(v2));
    if (isZero(s1)) return null;
    double s2 = v.dotProduct(v2.crossProduct(v3));
    if (isZero(s2)) return null;
    double s3 = v.dotProduct(v3.crossProduct(v1));
    if (isZero(s3)) return null;

    if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
        //for GeoPoint
        List<GeoPoint> result = new LinkedList<>();
        for (GeoPoint geo : planeIntersections) {
            result.add(new GeoPoint(this, geo.getPoint()));
        }
        return result;
    }

    return null;
	
}
}
