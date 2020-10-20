package elements;
import primitives.Color;

public class AmbientLight extends Light
{
// ***************** Constructors ********************** // 

  public AmbientLight(Color _intensity, double ka)
  {
    super( _intensity.scale(ka));
  }	
}
