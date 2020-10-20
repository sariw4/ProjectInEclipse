package scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import elements.*;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;



public class Scene {
    private final String _name;
    private final Geometries _geometries ;
    private Color _background;
    private Camera _camera;
    private double _distance;
    private AmbientLight _ambientLight;
    List<LightSource> _lights =new LinkedList<LightSource>();
// ***************** Constructors ********************** // 

    public Scene(String _sceneName) {
        _name = _sceneName;
        _geometries = new Geometries();
    }
    
// ***************** Getters/Setters ********************** // 

    public String getName(String sceneName) 
	{
		return _name;
	}
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Camera getCamera() {
        return _camera;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public double getDistance() {
        return _distance;
    }


    public Color getBackground() {
        return this._background;
    }
    

	public void setBackground(Color backGround) 
	{
		if (backGround == null) 
        {
            this._background = new Color(255,255,255);
        }
		else 
		{
            this._background =new Color(backGround.getColor());
        }
		}


	public void setCamera(Camera camera) 
	{
		this._camera = camera;
	}
	public void setDistance(double screenDistance)
	{
		this._distance = screenDistance;
	}

	public void setAmbientLight(AmbientLight ambientLight) 
	{
		_ambientLight =ambientLight;
	}


    public List<LightSource> getLightSources() {
        return _lights;
    }	
// ***************** Operations ******************** // 
/**
 * add intersectables to Geometries
 */
    public void addGeometries(Intersectable... intersectables) {
        for (Intersectable i : intersectables) {
            _geometries.add(i);
        }
    }
 /**
  * add LightSource to LinkedList<LightSource>
 */
   public void addLights(LightSource light) {
        if (_lights == null) {
            _lights = new ArrayList<>();
        }
       _lights.add(light);
    }

}