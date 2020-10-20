package primitives;

public class Vector {
	Point3D _head;

// ***************** Constructors ********************** //

	public Vector() {};
	public Vector(Point3D _head) 
	{
		if(_head.equals(Point3D.Zero))
		{
			throw new IllegalArgumentException("you put the zero vector please put other vector!");
		}
		this._head =_head;
		
	}
	public Vector(Vector vectorosh) {
	    this(vectorosh.get_head())
	    ; 
	}
	public Vector(double n1,double n2, double n3)
	{
		Point3D Newpoint=new Point3D(n1,n2,n3);
		if(Newpoint.equals(Point3D.Zero))
		{
			throw new IllegalArgumentException("you put the zero vector please put other vector!");
		}
		this._head=Newpoint;
	
	}
	public Vector(Coordinate co1,Coordinate co2,Coordinate co3 )
	{ 
		Point3D Newpoint=new Point3D(co1,co2,co3);
	    if(Newpoint.equals(Point3D.Zero))
	    {
		  throw new IllegalArgumentException("you put the zero vector please put other vector!");
	    }
		_head=Newpoint;
	}
// ***************** Getter********************** // 
	public Point3D get_head() {
		return _head;
	}
	
// ***************** Operations ******************** // 

	/**
	 * Creates a new vector from connecting two vectors 
	*/
	
	public Vector add(Vector vector)
	{
		Vector Newvector=new Vector(this._head._x.add (vector.get_head().get_x()),this._head._y.add (vector.get_head().get_y()),this._head._z.add (vector.get_head().get_z()));
		return Newvector;	
	}
	/**
	 * Creates a new vector from subtracting two vectors 
	*/
	public Vector subtract (Vector vector) {
		Vector Newvector=new Vector(this._head._x.Substrct (vector.get_head().get_x()),this._head._y.Substrct (vector.get_head().get_y()),this._head._z.Substrct (vector.get_head().get_z()));
		return Newvector;	
	}
	/**
	 * Creates a new vector by multiplying a vector by scalar
	*/
    public Vector scale(double scalingFacor){
		Vector Newvector=new Vector(this._head.get_x()._coord*scalingFacor,this._head.get_y()._coord*scalingFacor,this._head.get_z()._coord*scalingFacor);
		return Newvector;	
	}
    /**
	 * Creates a new vector by dotProduct 
	*/
    public double dotProduct(Vector vector)
	{
		double dot=(this._head.get_x()._coord*vector._head.get_x()._coord)+(this._head.get_y()._coord*vector._head.get_y()._coord+(this._head.get_z()._coord*vector._head.get_z()._coord));
		return dot;
	}
    /**
   	 * Creates a new vector by crossProduct 
   	*/
    public Vector crossProduct (Vector vector)
	{
    	if(this._head.get_x()._coord/vector._head.get_x()._coord==this._head.get_y()._coord/vector._head.get_y()._coord&&this._head.get_x()._coord/vector._head.get_x()._coord==this._head.get_z()._coord/vector._head.get_z()._coord)
    		  throw new IllegalArgumentException("you put parallel vectors!");
     Coordinate x=new Coordinate( (this._head.get_y()._coord*vector._head.get_z()._coord)-(this._head.get_z()._coord*vector._head.get_y()._coord));
	 Coordinate y=new Coordinate( (-1)*((this._head.get_x()._coord*vector._head.get_z()._coord)-(this._head.get_z()._coord*vector._head.get_x()._coord) ));
	 Coordinate z=new Coordinate((this._head.get_x()._coord*vector._head.get_y()._coord)-(this._head.get_y()._coord*vector._head.get_x()._coord));
		Point3D to3=new Point3D(x,y,z);
		Vector vector3 =new Vector(to3);
	 return vector3;
	}
    /**
   	 * Calculate the length of the vector squared 
   	*/
    public double lengthSquared()
	{
		return (this._head.get_x()._coord)*(this._head.get_x()._coord)+ (this._head.get_y()._coord)*(this._head.get_y()._coord)+
				((this._head.get_z()._coord)*(this._head.get_z()._coord));
	}
    /**
   	 * Calculate the length of the vector
   	*/
    public double length()
    {
    	return Math.sqrt(this.lengthSquared());
    }
    /**
   	 * Returns normalized vector
   	*/
	public Vector normalize()
	{
		double len=this.length();
		Vector v = new Vector(this._head._x.get()/len, this._head._y.get()/len, this._head._z.get()/len);
		this._head._x=v._head._x;
		this._head._y=v._head._y;
		this._head._z=v._head._z;

		return this;
	}
	/**
   	 * Creates a normalized new vector 
   	*/
	public Vector normalized()
	{
		Vector Newvector=new Vector(this);
		Newvector.normalize();
		return Newvector;
	}

// ***************** Administration ******************** // 

	@Override
	public String toString() {
		return "Vector [_head=" +_head.toString() + "]";
	}
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
	    if (obj == null) return false;
	    if (!(obj instanceof Vector)) return false;
        Vector oth = (Vector)obj;
        return _head.equals(oth._head) ;
    }
    
    public Vector findOrthogonal() throws Exception 
    {
        double x = this.get_head().get_x().get();
        double y = this.get_head().get_y().get();
        double z = this.get_head().get_z().get();
        double Ax= Math.abs(x), Ay= Math.abs(y), Az= Math.abs(z);
        if (Ax < Ay)
            return Ax < Az ?  new Vector(0, -z, y) : new Vector(-y, x, 0);
        else
            return Ay < Az ?  new Vector(z, 0, -x) : new Vector(-y, x, 0);
    }
    

	
	
}
