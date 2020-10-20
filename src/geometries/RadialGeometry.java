package geometries;

import static primitives.Util.isZero;
import primitives.Color;
import primitives.Material;


public abstract class RadialGeometry extends Geometry {
public double _radius;
//***************** Constructors ********************** // 

public  RadialGeometry(double r)
{
	_radius=r;
}
public RadialGeometry(Color emissionLight, double radius, Material material) {
    super(emissionLight, material);
    setRadius(radius);
}

public RadialGeometry(Color emissionLight, double radius) {
    this(emissionLight,radius,new Material(0, 0, 0));
}

public RadialGeometry(RadialGeometry r) {
	this(r._radius);
}
// ***************** Getters/Setters ********************** // 

public double getradius()
{
	return _radius;
}
public void setRadius(double radius) {
    if (isZero(radius) || (radius < 0.0))
        throw new IllegalArgumentException("radius " + radius + " is not valid");
    this._radius = radius;
}
}
