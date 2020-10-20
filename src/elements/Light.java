package elements;

import primitives.Color;
public abstract class Light {
 protected Color _intensity;

//***************** Constructors ********************** // 

 public Light(Color c)
 {
	 _intensity=new Color(c); 
 }
//***************** Getter********************** // 

 public Color getIntensity() {
     return new Color(_intensity);
 }

}
