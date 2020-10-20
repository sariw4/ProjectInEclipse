package elements;

import primitives.*;

public class SpotLight extends PointLight {

	private Vector _direction;
	// ***************** Constructors ********************** // 

	    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
	        super(colorIntensity, position, kC, kL, kQ);
	        this._direction = new Vector(direction).normalized();
	    }
	    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ,double Grid) {
	    	
	    		super(colorIntensity, position, kC, kL, kQ,Grid);
		        this._direction = new Vector(direction).normalized();
		        
	    	}
	    /**
	     * @return spotlight intensity
	     */
	    @Override
	    public Color getIntensity(Point3D p) {
	        double projection = _direction.dotProduct(getL(p));

	        if (Util.isZero(projection)) {
	            return Color.BLACK;
	        }
	        double factor = Math.max(0, projection);
	        Color pointlightIntensity = super.getIntensity(p);

	        return (pointlightIntensity.scale(factor));
	    }
	}


