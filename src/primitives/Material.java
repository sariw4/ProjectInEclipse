package primitives;

public class Material {

	    private final double _kD;
        private final double _kS;
        private final int _nShininess;
	    private final double _kr;
	    private final double _kt;
// ***************** Constructors ********************** //
	    public Material(double kD, double kS, int nShininess, double kt, double kr) {
	        this._kD = kD;
	        this._kS = kS;
	        this._nShininess = nShininess;
	        this._kt = kt;
	        this._kr = kr;
	    }

	    public Material(double kD, double kS, int nShininess) {
	        this(kD, kS, nShininess, 0, 0);
	    }

	    public Material(Material material) {
	        this(material._kD, material._kS, material._nShininess, material._kt, material._kr);
	    }
// ***************** Getter********************** // 

	    public double getKd() {
	        return _kD;
	    }

	    public double getKs() {
	        return _kS;
	    }

	    public int getnShininess() {
	        return _nShininess;
	    }
	    public double getKr() {
	        return _kr;
	    }

	    public double getKt() {
	        return _kt;
	    }
}
