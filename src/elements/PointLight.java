package elements;

import primitives.*;

public class PointLight extends Light implements LightSource{

	protected Point3D _position;
	protected double _kC; // Constant attenuation
	protected double _kL; // Linear attenuation
	protected  double _kQ; // Quadratic attenuation
	protected  double _GridSize; 


// ***************** Constructors ********************** // 

	    public PointLight(Color colorIntensity, Point3D position, double kC, double kL, double kQ) {
	        super(colorIntensity);
	        this._position = new Point3D(position);
	        this._kC = kC;
	        this._kL = kL;
	        this._kQ = kQ;
	    }
	    public PointLight(Color colorIntensity, Point3D position, double kC, double kL, double kQ,double GridSize) {
	    this( colorIntensity,  position,  kC,  kL,  kQ);
	    this._GridSize=GridSize;
	    }
	    
	    //overriding LightSource getIntensity(Point3D)
	    @Override
	    public Color getIntensity(Point3D p) {
	        double dsquared = p.distanceSquared(_position);
	        double d = p.distance(_position);

	        return (_intensity.reduce(_kC + _kL * d + _kQ * dsquared));
	    }
	    public Point3D getPosition()
	    {
	    	return _position;
	    }
	    public double getGridSize()
	    {
	    	return _GridSize;
	    }
	    // Light vector
	    @Override
	    public Vector getL(Point3D p) {
	        if (p.equals(_position)) {
	            return null;
	        }
	        return p.subtract(_position).normalize();
	    }
	    @Override
	    public double getDistance(Point3D point) {
	        return _position.distance(point);
	    }

	    
}
