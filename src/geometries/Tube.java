package geometries;
import static primitives.Util.*;

import java.util.List;

import primitives.*;

public class Tube extends RadialGeometry
{
	private Ray R;
// ***************** Constructors ********************** // 

	  public Tube(Ray _R,double _radius)
	  {
		  super(_radius);
		  R=new Ray(_R);
	  }
	  public Tube(Color emissionLight, Material _material, double _radius, Ray _ray)
	  {
	        super(Color.BLACK, _radius);
	        this._material = _material;
	        this.R = new Ray(_ray);

	  }
	  public Tube(Color emissionLight, double _radius, Ray _ray)
	  {
	        this(emissionLight, new Material(0, 0, 0), _radius, _ray);
	  }
 
// ***************** Getter********************** // 

	  public Ray getr()
	  {
		  return R;
	  }
	public Vector getNormal(Point3D _p) {
		Point3D point=R.get_Point();
		Vector vec =R.get_direction();
		double t=_p.subtract(point).dotProduct(vec);
		if(!isZero(t))
			point=point.add(vec.scale(t));
		return _p.subtract(point).normalize();
	}
// ***************** Administration  ******************** // 

	@Override
	public List<GeoPoint> findIntsersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}
