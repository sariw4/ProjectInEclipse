package primitives;

public class Point3D {

	Coordinate _x;
	Coordinate _y;
	Coordinate _z;
	public static Point3D Zero=new Point3D(0.0,0.0,0.0);
// ***************** Constructors ********************** //
	public Point3D() {};
	public Point3D(Coordinate co1,Coordinate co2,Coordinate co3 )
	{
		_x=new Coordinate(co1);
		_y=new Coordinate(co2);
		_z=new Coordinate(co3);
	}
	public Point3D(double n1,double n2, double n3)
	{
		_x=new Coordinate(n1);
		_y=new Coordinate(n2);
		_z=new Coordinate(n3);
	}
	public Point3D(Point3D point) {
	    this(point.get_x(),point.get_y(),point.get_z())
	    ; 
	}

// ***************** Getter********************** // 
	public Coordinate get_x() {
		return _x;
	}
	public Coordinate get_y() {
		return _y;
	}
	public Coordinate get_z() {
		return _z;
	}
	
// ***************** Operations ******************** // 
	/**
	 * Creates a new Point3D from connecting Point to vector 
	*/
	public Point3D add(Vector vector)
	{
		Point3D Newpoint=new Point3D (this._x.add (vector.get_head().get_x()),this._y.add (vector.get_head().get_y()),this._z.add (vector.get_head().get_z()));	
		return Newpoint;
	}
	/**
	 * Creates a new vector from subtracting two points 
	*/
	public Vector subtract (Point3D p3d) {
		Point3D Newpoint=new Point3D (this._x.Substrct (p3d.get_x()),this._y.Substrct (p3d.get_y()),this._z.Substrct (p3d.get_z()));	
		Vector Newvector=new Vector(Newpoint);
		return Newvector;
	}
	/**
   	 * Calculate the length between to points  
   	*/
	public double distanceSquared (Point3D point)
	{
		return ((this.get_x()._coord-point.get_x()._coord)*(this.get_x()._coord-point.get_x()._coord) +
				(this.get_y()._coord-point.get_y()._coord)*(this.get_y()._coord-point.get_y()._coord) +
				(this.get_z()._coord-point.get_z()._coord)*(this.get_z()._coord-point.get_z()._coord));
	}
	/**
   	 * Calculate the length between to points squared 
   	*/
	public double distance (Point3D point) {
		return Math.sqrt(distanceSquared(point));
	}
	
// ***************** Administration  ******************** // 
	@Override
	public String toString() {
		return"_x= "+ _x+"_y= "+ _y+"_z= "+ _z;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D)) 
			return false;
		Point3D oth = (Point3D)obj;	
    return _x.equals(oth._x) && _y.equals(oth._y)&&  _z.equals(oth._z);}
}
