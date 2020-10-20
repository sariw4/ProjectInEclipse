package primitives;
import static primitives.Util.*;
public class Ray {
	Point3D _Point;
	Vector _direction;
    private static final double DELTA = 0.1;

// ***************** Constructors ********************** // 
	
	public Ray(Point3D _POO, Vector _direction1) {

		this._Point = _POO;
		this._direction = _direction1.normalized();
	}
	public Ray(Ray ray) {
	    this(ray.get_Point(),ray.get_direction()); 
	}
    public Ray(Point3D point, Vector direction, Vector normal)
    {
	    //head+ normal.scale(±DELTA)
         _direction = new Vector(direction).normalized();
          double nv = normal.dotProduct(direction);
	      Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
	     _Point = point.add(normalDelta);
 }
// ***************** Getter********************** // 

	public Point3D get_Point() {
		return _Point;
	}
	
	public Vector get_direction() {
		return _direction;
	}
// ***************** Administration  ******************** // 

	@Override
	public String toString() {
		return "Ray [_Point=" + _Point.toString() + ", _direction=" + _direction.toString() + "]";
	}
    public Point3D getTargetPoint(double length) 
	{ 
    	return isZero(length ) ?_Point : _Point.add(_direction.scale(length));
    }

	   @Override
	   public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Ray)) return false;
	      Ray oth = (Ray)obj;
	      return _Point.equals(oth._Point) && _direction.equals(oth._direction);
	   }

	}
