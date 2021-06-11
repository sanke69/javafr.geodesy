/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.utils.geodesic;

public class Angles {

	public static final double quartpi          =  0.7853981633974483096156608458198757;
	public static final double halfpi           =  1.5707963267948966192313216916397514;
	public static final double pi               =  3.1415926535897932384626433832795029;
	public static final double twopi            =  6.28318530717958;
	public static final double fourpi           = 12.5663706144;
	public static final double pisquare         =  9.869604401089339;

	public static final float	PI				= (float) Math.PI;
	public static final float	TWOPI			= (float) (Math.PI * 2);
	public static final float	INV_PI			= 1f / PI;
	public static final float	HALF_PI			= PI / 2;
	public static final float	QUARTER_PI		= PI / 4;
	public static final float	THREE_HALVES_PI	= TWOPI - HALF_PI;

	private static final float 	DEG2RAD  		= PI / 180.0f;
//	private static final float 	RAD2DEG  		= 180.0f / PI;
	private static final float 	RAD2GRAD 		= PI / 200.0f;
	private static final float 	GRAD2RAD 		= 200.0f / PI;
//	private static final float 	DEG2GRAD 		= 180.0f / 200.0f;
//	private static final float 	GRAD2DEG 		= 200.0f / 180.0f;

	static public double   Grade2Radian(double _rad) {
		return _rad * RAD2GRAD;
	}
	static public double   Radian2Grade(double _grad) {
		return _grad * GRAD2RAD;
	}

	static public double   Degree2Radian(double _degree) {
		return _degree * DEG2RAD;
	}
	static public double   Radian2Degree(double _grad) {
		return 180 * _grad / Math.PI;
	}

	static public double   Degree2Grade(double _degree) {
		return 200 * _degree / 180;
	}
	static public double   Grade2Degree(double _grad) {
		return 180 * _grad / 200;
	}

	static public double   DMS2Degree(int _degree, int _minute, double _second) {
		return _degree + _minute / 60 + _second / 3600;
	}
	static public double   DMS2Radian(int _degree, int _minute, double _second) {
		return Math.PI * (_degree + _minute / 60 + _second / 3600) / 180;
	}
	
	static public double[] Degree2DMS(double _grad) { // TODO::
		double D = Math.floor(_grad);
		double M = Math.floor((_grad - D) * 60);
//		double S = Math.floor((_grad - D - M/60) * 3600);
		double S = (_grad - D - M/60) * 3600;

		return new double[] { D, M, S };
	}
	static public double   Radian2DMS(double _grad) { // TODO::
		return -1;
	}

	public static final double reduceRadian(double _theta) {
		_theta %= TWOPI;
		if(Math.abs(_theta) > PI)
			_theta = _theta - TWOPI;
//		if(Math.abs(theta) > HALF_PI)
//			theta = PI - theta;
		return _theta;
	}
	public static final double reduceDegree(double _theta) {
		_theta %= 360;
		if(_theta > 180)
			_theta = _theta - 360;
		if(_theta < - 180)
			_theta = _theta + 360;
		return _theta;
	}
	public static final double positiveDegree(double _theta) {
		return (_theta + 360) % 360;
	}

	public static String toString(int _deg, int _min, double _sec) {
		return (int) _deg + "°" + (int) _min + "'" + _sec + "\"";
	}
	public static String toString(double[] _dms) {
		return (int) _dms[0] + "°" + (int) _dms[1] + "'" + _dms[2] + "\"";
	}

}
