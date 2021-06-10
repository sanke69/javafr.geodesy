package fr.geodesic.api.referential.properties;

class Utils {
/**
 *
 * with:
 *   - a  :  half major axis
 *   - b  :  half minor axis
 *   - f  :  flattening				f  = (a - b) / a
 *   - e  :  1st excentricity		e  = sqrt( a^2 - b^2) / a^2 = sqrt(2 * f - f^2)  
 *   - e' :  2nd excentricity		e' = sqrt( a^2 - b^2) / b^2
 * 
 * 
 * @author sanke
 *
**/
	static double getHalfMajorAxis			(double b, double f) 	{ return b / (f-1); }
	static double getHalfMinorAxis			(double a, double f) 	{ return a * (f-1); }
	static double getFlattening				(double a, double b) 	{ return (a-b) / a; }
	static double getFirstExcentricity		(double a, double b) 	{ return Math.sqrt(a*a - b*b) / (a*a); }
	static double getFirstExcentricity		(double f) 				{ return Math.sqrt(2*f - f*f); }
	static double getSecondExcentricity		(double a, double b) 	{ return Math.sqrt(a*a - b*b) / (b*b); }

}

public enum Ellipsoid {

	IAG_GRS80_0 (6378137.0,			null, 	1. / 298.257222101),
	Clarke_1880	(6378249.2,	   6356515.0, 	null),
	Hayford1909	(6378388.0,			null, 	1. / 297.0),
	Inter1924	(6378388.0,			null,	1. / 297.0),
	GRS80		(6378137.0, 		null, 	1. / 298.257222101),

	IAG_GRS80	(6378137.0, 
				6356752.3142, //computeHalfSmallAxe(6378137, 1/298.257223563), 
			  	1/298.257223563, 
			  	computeFirstExcentricity(6378137, computeHalfSmallAxe(6378137, 1/298.257223563)), 
			  	100),
	WGS84		(6378137.0, 
				6356752.3142, //computeHalfSmallAxe(6378137, 1/298.257223563), 
			  	1/298.257223563, 
			  	computeFirstExcentricity(6378137, computeHalfSmallAxe(6378137, 1/298.257223563)), 
			  	100),
  	;

	private double big_axe;					// 1/2 grand axe de l'ellipsoïde (mètre)
	private double small_axe;				// 1/2 petit axe de l'ellipsoïde (mètre) - Non utilisé directement
	private double flattening;				// Facteur d'aplatissement  - Non utilisé directement
	private double first_excentricity;		// Première excentricité de l'ellipsoïde de référence
	private double second_excentricity;		// Hauteur au dessus de l'ellipsoïde (en mètre)

	private Ellipsoid(Double _a, Double _b, Double _f) {
		big_axe          	= _a != null ? _a : Utils.getHalfMajorAxis(_b, _f);
		small_axe        	= _b != null ? _b : Utils.getHalfMinorAxis(_a, _f);
		flattening      	= _f != null ? _f : Utils.getFlattening(big_axe,  small_axe);
		first_excentricity  = _f != null ? Utils.getFirstExcentricity(flattening) : Utils.getFirstExcentricity(big_axe,  small_axe);
		second_excentricity = Utils.getSecondExcentricity(big_axe,  small_axe);
	}
	private Ellipsoid(double _ba, double _sa, double _ff, double _ex, double _eh) {
		big_axe = _ba;
		small_axe = _sa;
		flattening = _ff;
		first_excentricity = _ex;
		second_excentricity = _eh;
	}

	public double getHalfMajorAxis() 		{ return big_axe; }
	public double getHalfMinorAxis() 		{ return small_axe; }
	public double getFlattening()			{ return flattening; }
	public double getFirstExcentricity()	{ return flattening; }
	public double getSecondExcentricity()	{ return flattening; }

	public double getEquatorialRadius() 	{ return big_axe; }
	public double equatorialRadius() 		{ return big_axe; }

	public double getPolarRadius() 			{ return small_axe; }
	public double polarRadius() 			{ return small_axe; }

	public double getFlatteringFactor()		{ return flattening; }
	public double flattening()				{ return (equatorialRadius()-polarRadius())/equatorialRadius(); /*return flat_factor;*/ }

	public double invFlattening()			{ return 1.0 / flattening(); }

	public double getExcentricity() 		{ return Math.sqrt(1 - Math.pow(polarRadius() / equatorialRadius(), 2)) /*excentricity*/; }
	public double excentricity() 			{ return Math.sqrt(1 - Math.pow(polarRadius() / equatorialRadius(), 2)); }

	public double firstExcentricity() 		{ return first_excentricity; }
	public double secondExcentricity() 		{ return second_excentricity; }

	public double meanRadius() 				{ return Math.pow(equatorialRadius() * polarRadius(), 0.5); }

	public double getEllipsoidHeight() 		{ return second_excentricity; }

	static private double computeHalfSmallAxe(double _hba, double _flatf) {
		// {1/2 Small Axe} = {1/2 Big Axe} * (1 - {flat factor}) 
		return _hba * (1.0 - _flatf);
	}

	static private double computeFirstExcentricity(double _hba, double _hsa) {
		// {1st Excentricity} = sqrt ( ({1/2 Big Axe}^2 - {1/2 Small Axe}^2) / {1/2 Big Axe}^2 ) 
		return Math.sqrt( (Math.pow(_hba, 2) - Math.pow(_hsa, 2)) / Math.pow(_hba, 2) );
	}

	
	
}
