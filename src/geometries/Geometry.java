package geometries;
import primitives.*;
public abstract class Geometry implements Intersectable
{
	protected Color _emmission ;
    protected Material _material;

// ***************** Constructors ********************** // 

       public Geometry(Color emission, Material material) {
         this._emmission = new Color(emission);
         this._material = new Material(material);
        }

        public Geometry(Color _emission) {
          this(_emission, new Material(0d, 0d, 0));
        }

	    public Geometry() {
	        this(Color.BLACK);
	    }
// ***************** Getter********************** // 

	    public Color getEmissionLight() {
	        return _emmission;
	    }

	   public Material getMaterial() {
	        return _material;
	    }
	abstract public  Vector getNormal(Point3D P);
}
