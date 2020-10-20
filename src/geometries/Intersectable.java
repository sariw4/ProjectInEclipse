/**
 * 
 */
package geometries;
import java.util.List;
import primitives.*;
/**
 * @author user
 *
 */

    public interface Intersectable {
    	List<GeoPoint> findIntsersections(Ray ray);
	
	public static class GeoPoint {
	    public Geometry geometry;
	    public Point3D point;
	    public GeoPoint(Geometry _geometry, Point3D pt) {
            this.geometry = _geometry;
            point = pt;
        }

        public Point3D getPoint() {
            return point;
        }

        public Geometry getGeometry() {
            return geometry;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint gpoint = (GeoPoint) o;
            return geometry.equals(gpoint.geometry) &&
            		point.equals(gpoint.point);
        }
 
	}

}
