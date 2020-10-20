
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Ray;


public class Geometries implements Intersectable {

	  
		private List<Intersectable> _geometries = new ArrayList<Intersectable>();
		
// ***************** Constructors ********************** // 

	    public Geometries(Intersectable... _geometries)
	    {
	        add( _geometries);
	    }
	    
// ***************** Operations ******************** // 
  /*
   * add geometries to the list	        
   */
	    public void add(Intersectable... geometries) {
	        for (Intersectable geo : geometries ) {
	            _geometries.add(geo);
	        }
	    }
	 public List<GeoPoint> findIntsersections (Ray ray)
	 {
		 List<GeoPoint> newIn=(List<GeoPoint>) new ArrayList<GeoPoint>();
		 for (Intersectable geo : _geometries)
		 {
			 if(geo.findIntsersections(ray)!=null)
			 newIn.addAll(geo.findIntsersections(ray)); 
		 }
        if(!newIn.isEmpty())
		  return newIn;
        else 
        	return null;
	      
	 }

}
