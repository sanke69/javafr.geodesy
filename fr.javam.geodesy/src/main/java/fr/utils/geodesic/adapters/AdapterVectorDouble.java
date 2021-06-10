package fr.utils.geodesic.adapters;

import java.nio.DoubleBuffer;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.linear.Vector1D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.geometry.space.Vector4D;

public abstract class AdapterVectorDouble implements DoubleVector {
	private static final long serialVersionUID = 8796583955084628530L;

	final int nbDimension;

	AdapterVectorDouble(int _nbDimension) {
		super();
		nbDimension = _nbDimension;
	}

	@Override
	public int 				getCapacity() {
		return nbDimension;
	}
	@Override
	public boolean 			isDirect() {
		return false;
	}
	@Override
	public boolean 			isValid() {
		switch(nbDimension) {
		case 4  : Vector4D v4 = (Vector4D) this;
				  return !Double.isNaN(v4.getX()) && !Double.isInfinite(v4.getX()) && !Double.isNaN(v4.getY()) && !Double.isInfinite(v4.getY()) && !Double.isNaN(v4.getZ()) && !Double.isInfinite(v4.getZ()) && !Double.isNaN(v4.getW()) && !Double.isInfinite(v4.getW());
		case 3  : Vector3D v3 = (Vector3D) this;
				  return !Double.isNaN(v3.getX()) && !Double.isInfinite(v3.getX()) && !Double.isNaN(v3.getY()) && !Double.isInfinite(v3.getY()) && !Double.isNaN(v3.getZ()) && !Double.isInfinite(v3.getZ());
		case 2  : Vector2D v2 = (Vector2D) this;
		  		  return !Double.isNaN(v2.getX()) && !Double.isInfinite(v2.getX()) && !Double.isNaN(v2.getY()) && !Double.isInfinite(v2.getY());
		case 1  : Vector1D v1 = (Vector1D) this;
		  		  return !Double.isNaN(v1.getX()) && !Double.isInfinite(v1.getX());
		default : return false;
		}
	}

	@Override
	public double 			get(int _i) {
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					switch(_i) {
					case 3  : return v4.getW();
					case 2  : return v4.getZ();
					case 1  : return v4.getY();
					case 0  : return v4.getX();
					default : return Double.NaN;
					}
		case 3  :   Vector3D v3 = (Vector3D) this;
					switch(_i) {
					case 2  : return v3.getZ();
					case 1  : return v3.getY();
					case 0  : return v3.getX();
					default : return Double.NaN;
					}
		case 2  :   Vector2D v2 = (Vector2D) this;
					switch(_i) {
					case 1  : return v2.getY();
					case 0  : return v2.getX();
					default : return Double.NaN;
					}
		case 1  :   Vector1D v1 = (Vector1D) this;
					switch(_i) {
					case 0  : return v1.getX();
					default : return Double.NaN;
					}
		default :   return Double.NaN;
		}
	}

	@Override
	public double 			getValue(int _index) {
		return get((int) _index);
	}
	@Override
	public double 			getValue(int... _coords) {
		if(_coords.length != 1)
			return Double.NaN;
		return getValue(_coords[0]);
	}

	@Override
	public Number 			getNumber(int _index) {
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					switch(((int) _index)) {
					case 3  : return v4.getW();
					case 2  : return v4.getZ();
					case 1  : return v4.getY();
					case 0  : return v4.getX();
					default : return null;
					}
		case 3  :   Vector3D v3 = (Vector3D) this;
					switch(((int) _index)) {
					case 2  : return v3.getZ();
					case 1  : return v3.getY();
					case 0  : return v3.getX();
					default : return null;
					}
		case 2  :   Vector2D v2 = (Vector2D) this;
					switch(((int) _index)) {
					case 1  : return v2.getY();
					case 0  : return v2.getX();
					default : return null;
					}
		case 1  :   Vector1D v1 = (Vector1D) this;
					switch(((int) _index)) {
					case 0  : return v1.getX();
					default : return null;
					}
		default :   return null;
		}
	}
	@Override
	public Number 			getNumber(int... _coords) {
		if(_coords.length != 1)
			return null;
		return getNumber(_coords[0]);
	}

	@Override
	public double[] 		getArray() {
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					return new double[] { v4.getX(), v4.getY(), v4.getZ(), v4.getW() } ;
		case 3  :   Vector3D v3 = (Vector3D) this;
					return new double[] { v3.getX(), v3.getY(), v3.getZ() } ;
		case 2  :   Vector2D v2 = (Vector2D) this;
					return new double[] { v2.getX(), v2.getY() } ;
		case 1  :   Vector1D v1 = (Vector1D) this;
					return new double[] { v1.getX() } ;
		default :   return null;
		}
	}
	@Override
	public DoubleBuffer 	getBuffer() {
		throw new IllegalAccessError();
	}
	@Override
	public DoubleStream 	getStream() {
		throw new IllegalAccessError();
	}
	@Override
	public DoubleTensor 	getSliceView(int... _slice) {
		throw new IllegalAccessError();
	}
	@Override
	public DoubleTensor 	getSliceCopy(int... _slice) {
		throw new IllegalAccessError();
	}

	@Override
	public void 			reshape(int... _shape) {
		throw new IllegalAccessError();
	}

	@Override
	public DoubleVector 	plus(DoubleVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()+_vector.get(0), v4.getY()+_vector.get(1), v4.getZ()+_vector.get(2), v4.getW()+_vector.get(3));
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()+_vector.get(0), v3.getY()+_vector.get(1), v3.getZ()+_vector.get(2));
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()+_vector.get(0), v2.getY()+_vector.get(1));
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()+_vector.get(0));
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}
	@Override
	public NumberVector 	plus(NumberVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()+_vector.getNumber(0).doubleValue(), v4.getY()+_vector.getNumber(1).doubleValue(), v4.getZ()+_vector.getNumber(2).doubleValue(), v4.getW()+_vector.getNumber(3).doubleValue());
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()+_vector.getNumber(0).doubleValue(), v3.getY()+_vector.getNumber(1).doubleValue(), v3.getZ()+_vector.getNumber(2).doubleValue());
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()+_vector.getNumber(0).doubleValue(), v2.getY()+_vector.getNumber(1).doubleValue());
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()+_vector.getNumber(0).doubleValue());
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}

	@Override
	public DoubleVector 	minus(DoubleVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()-_vector.get(0), v4.getY()-_vector.get(1), v4.getZ()-_vector.get(2), v4.getW()-_vector.get(3));
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()-_vector.get(0), v3.getY()-_vector.get(1), v3.getZ()-_vector.get(2));
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()-_vector.get(0), v2.getY()-_vector.get(1));
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()-_vector.get(0));
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}
	@Override
	public NumberVector 	minus(NumberVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()-_vector.getNumber(0).doubleValue(), v4.getY()-_vector.getNumber(1).doubleValue(), v4.getZ()-_vector.getNumber(2).doubleValue(), v4.getW()-_vector.getNumber(3).doubleValue());
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()-_vector.getNumber(0).doubleValue(), v3.getY()-_vector.getNumber(1).doubleValue(), v3.getZ()-_vector.getNumber(2).doubleValue());
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()-_vector.getNumber(0).doubleValue(), v2.getY()-_vector.getNumber(1).doubleValue());
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()-_vector.getNumber(0).doubleValue());
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}

	@Override
	public DoubleVector 	times(DoubleVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()*_vector.get(0), v4.getY()*_vector.get(1), v4.getZ()*_vector.get(2), v4.getW()*_vector.get(3));
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()*_vector.get(0), v3.getY()*_vector.get(1), v3.getZ()*_vector.get(2));
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()*_vector.get(0), v2.getY()*_vector.get(1));
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()*_vector.get(0));
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}
	@Override
	public NumberVector 	times(NumberVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()*_vector.getNumber(0).doubleValue(), v4.getY()*_vector.getNumber(1).doubleValue(), v4.getZ()*_vector.getNumber(2).doubleValue(), v4.getW()*_vector.getNumber(3).doubleValue());
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()*_vector.getNumber(0).doubleValue(), v3.getY()*_vector.getNumber(1).doubleValue(), v3.getZ()*_vector.getNumber(2).doubleValue());
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()*_vector.getNumber(0).doubleValue(), v2.getY()*_vector.getNumber(1).doubleValue());
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()*_vector.getNumber(0).doubleValue());
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}

	@Override
	public DoubleVector 	divides(DoubleVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()/_vector.get(0), v4.getY()/_vector.get(1), v4.getZ()/_vector.get(2), v4.getW()/_vector.get(3));
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()/_vector.get(0), v3.getY()/_vector.get(1), v3.getZ()/_vector.get(2));
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()/_vector.get(0), v2.getY()/_vector.get(1));
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()/_vector.get(0));
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}
	@Override
	public NumberVector 	divides(NumberVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() < 4)
						throw new IllegalArgumentException();
					Vector4D v4 = (Vector4D) this;
					return new AdapterVector4D(v4.getX()/_vector.getNumber(0).doubleValue(), v4.getY()/_vector.getNumber(1).doubleValue(), v4.getZ()/_vector.getNumber(2).doubleValue(), v4.getW()/_vector.getNumber(3).doubleValue());
		case 3  :   if(_vector.size() < 3)
						throw new IllegalArgumentException();
					Vector3D v3 = (Vector3D) this;
					return new AdapterVector3D(v3.getX()/_vector.getNumber(0).doubleValue(), v3.getY()/_vector.getNumber(1).doubleValue(), v3.getZ()/_vector.getNumber(2).doubleValue());
		case 2  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector2D v2 = (Vector2D) this;
					return new AdapterVector2D(v2.getX()/_vector.getNumber(0).doubleValue(), v2.getY()/_vector.getNumber(1).doubleValue());
		case 1  :   if(_vector.size() < 2)
						throw new IllegalArgumentException();
					Vector1D v1 = (Vector1D) this;
					return new AdapterVector1D(v1.getX()/_vector.getNumber(0).doubleValue());
		default :   throw new IllegalArgumentException("What The Fuck");
		}
	}

	@Override
	public boolean 			isEqual(Number _value) {
		double value = _value.doubleValue();
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					return (v4.getX() == value && v4.getY() == value && v4.getZ() == value && v4.getW() == value) ? true : false;
		case 3  :   Vector3D v3 = (Vector3D) this;
					return (v3.getX() == value && v3.getY() == value && v3.getZ() == value) ? true : false;
		case 2  :   Vector2D v2 = (Vector2D) this;
					return (v2.getX() == value && v2.getY() == value) ? true : false;
		case 1  :   Vector1D v1 = (Vector1D) this;
					return (v1.getX() == value) ? true : false;
		default :   return false;
		}
	}
	@Override
	public boolean 			isEqual(Number[] _values) {
		switch(nbDimension) {
		case 4  :   if(_values.length != 4)
						return false;
					Vector4D v4 = (Vector4D) this;
					return (v4.getX() == _values[0].doubleValue() && v4.getY() == _values[1].doubleValue() && v4.getZ() == _values[2].doubleValue() && v4.getW() == _values[3].doubleValue()) ? true : false;
		case 3  :   if(_values.length != 3)
						return false;
					Vector3D v3 = (Vector3D) this;
					return (v3.getX() == _values[0].doubleValue() && v3.getY() == _values[1].doubleValue() && v3.getZ() == _values[2].doubleValue()) ? true : false;
		case 2  :   if(_values.length != 2)
						return false;
					Vector2D v2 = (Vector2D) this;
					return (v2.getX() == _values[0].doubleValue() && v2.getY() == _values[1].doubleValue()) ? true : false;
		case 1  :   if(_values.length != 1)
						return false;
					Vector1D v1 = (Vector1D) this;
					return (v1.getX() == _values[0].doubleValue()) ? true : false;
		default :   return false;
		}
	}
	@Override
	public boolean 			isEqual(NumberVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() != 4)
						return false;
					Vector4D v4 = (Vector4D) this;
					return (v4.getX() == _vector.getNumber(0).doubleValue() && v4.getY() == _vector.getNumber(1).doubleValue() && v4.getZ() == _vector.getNumber(2).doubleValue() && v4.getW() == _vector.getNumber(3).doubleValue()) ? true : false;
		case 3  :   if(_vector.size() != 3)
						return false;
					Vector3D v3 = (Vector3D) this;
					return (v3.getX() == _vector.getNumber(0).doubleValue() && v3.getY() == _vector.getNumber(1).doubleValue() && v3.getZ() == _vector.getNumber(2).doubleValue()) ? true : false;
		case 2  :   if(_vector.size() != 2)
						return false;
					Vector2D v2 = (Vector2D) this;
					return (v2.getX() == _vector.getNumber(0).doubleValue() && v2.getY() == _vector.getNumber(1).doubleValue()) ? true : false;
		case 1  :   if(_vector.size() != 1)
						return false;
					Vector1D v1 = (Vector1D) this;
					return (v1.getX() == _vector.getNumber(0).doubleValue()) ? true : false;
		default :   return false;
		}
	}
	@Override
	public boolean 			isEqual(DoubleVector _d) {
		switch(nbDimension) {
		case 4  :   if(_d.size() != 4)
						return false;
					Vector4D v4 = (Vector4D) this;
					return (v4.getX() == _d.get(0) && v4.getY() == _d.get(1) && v4.getZ() == _d.get(2) && v4.getW() == _d.get(3)) ? true : false;
		case 3  :   if(_d.size() != 3)
						return false;
					Vector3D v3 = (Vector3D) this;
					return (v3.getX() == _d.get(0) && v3.getY() == _d.get(1) && v3.getZ() == _d.get(2)) ? true : false;
		case 2  :   if(_d.size() != 2)
						return false;
					Vector2D v2 = (Vector2D) this;
					return (v2.getX() == _d.get(0) && v2.getY() == _d.get(1)) ? true : false;
		case 1  :   if(_d.size() != 1)
						return false;
					Vector1D v1 = (Vector1D) this;
					return (v1.getX() == _d.get(0)) ? true : false;
		default :   return false;
		}
	}

	@Override
	public boolean 			isDifferent(Number _value) {
		double value = _value.doubleValue();
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					return (v4.getX() != value || v4.getY() != value || v4.getZ() != value || v4.getW() != value) ? true : false;
		case 3  :   Vector3D v3 = (Vector3D) this;
					return (v3.getX() != value || v3.getY() != value || v3.getZ() != value) ? true : false;
		case 2  :   Vector2D v2 = (Vector2D) this;
					return (v2.getX() != value || v2.getY() != value) ? true : false;
		case 1  :   Vector1D v1 = (Vector1D) this;
					return (v1.getX() != value) ? true : false;
		default :   return false;
		}
	}
	@Override
	public boolean 			isDifferent(Number[] _values) {
		switch(nbDimension) {
		case 4  :   if(_values.length != 4)
						return false;
					Vector4D v4 = (Vector4D) this;
					return (v4.getX() != _values[0].doubleValue() || v4.getY() != _values[1].doubleValue() || v4.getZ() != _values[2].doubleValue() || v4.getW() != _values[3].doubleValue()) ? true : false;
		case 3  :   if(_values.length != 3)
						return false;
					Vector3D v3 = (Vector3D) this;
					return (v3.getX() != _values[0].doubleValue() || v3.getY() != _values[1].doubleValue() || v3.getZ() != _values[2].doubleValue()) ? true : false;
		case 2  :   if(_values.length != 2)
						return false;
					Vector2D v2 = (Vector2D) this;
					return (v2.getX() != _values[0].doubleValue() || v2.getY() != _values[1].doubleValue()) ? true : false;
		case 1  :   if(_values.length != 1)
						return false;
					Vector1D v1 = (Vector1D) this;
					return (v1.getX() != _values[0].doubleValue()) ? true : false;
		default :   return false;
		}
	}
	@Override
	public boolean 			isDifferent(NumberVector _vector) {
		switch(nbDimension) {
		case 4  :   if(_vector.size() != 4)
						return false;
					Vector4D v4 = (Vector4D) this;
					return (v4.getX() != _vector.getNumber(0).doubleValue() || v4.getY() != _vector.getNumber(1).doubleValue() || v4.getZ() != _vector.getNumber(2).doubleValue() || v4.getW() != _vector.getNumber(3).doubleValue()) ? true : false;
		case 3  :   if(_vector.size() != 3)
						return false;
					Vector3D v3 = (Vector3D) this;
					return (v3.getX() != _vector.getNumber(0).doubleValue() || v3.getY() != _vector.getNumber(1).doubleValue() || v3.getZ() != _vector.getNumber(2).doubleValue()) ? true : false;
		case 2  :   if(_vector.size() != 2)
						return false;
					Vector2D v2 = (Vector2D) this;
					return (v2.getX() != _vector.getNumber(0).doubleValue() || v2.getY() != _vector.getNumber(1).doubleValue()) ? true : false;
		case 1  :   if(_vector.size() != 1)
						return false;
					Vector1D v1 = (Vector1D) this;
					return (v1.getX() != _vector.getNumber(0).doubleValue()) ? true : false;
		default :   return false;
		}
	}
	@Override
	public boolean 			isDifferent(DoubleVector _d) {
		switch(nbDimension) {
		case 4  :   if(_d.size() < 4)
						return false;
					Vector4D v4 = (Vector4D) this;
					return (v4.getX() != _d.get(0) || v4.getY() != _d.get(1) || v4.getZ() != _d.get(2) || v4.getW() != _d.get(3)) ? true : false;
		case 3  :   if(_d.size() < 3)
						return false;
					Vector3D v3 = (Vector3D) this;
					return (v3.getX() != _d.get(0) || v3.getY() != _d.get(1) || v3.getZ() != _d.get(2)) ? true : false;
		case 2  :   if(_d.size() < 2)
						return false;
					Vector2D v2 = (Vector2D) this;
					return (v2.getX() != _d.get(0) || v2.getY() != _d.get(1)) ? true : false;
		case 1  :   if(_d.size() < 1)
						return false;
					Vector1D v1 = (Vector1D) this;
					return (v1.getX() != _d.get(0)) ? true : false;
		default :   return false;
		}
	}
	
	@Override
	public int 				compareTo(Object o) {
		if(o instanceof Number) {
			double d = ((Number) o).doubleValue();
			switch(nbDimension) {
			case 4  :   Vector4D v4 = (Vector4D) this;
						double mag_v4 = v4.getMagnitude();
						double mag_o4 = 2 * d;
						return (int) (mag_v4 - mag_o4);
			case 3  :   Vector3D v3 = (Vector3D) this;
						double mag_v3 = v3.getMagnitude();
						double mag_o3 = Math.sqrt(3) * d;
						return (int) (mag_v3 - mag_o3);
			case 2  :   Vector2D v2 = (Vector2D) this;
						double mag_v2 = v2.getMagnitude();
						double mag_o2 = Math.sqrt(2) * d;
						return (int) (mag_v2 - mag_o2);
			case 1  :   Vector1D v1 = (Vector1D) this;
						double mag_v = v1.getX();
						double mag_o = d;
						return (int) (mag_v - mag_o);
			default :   return -1;
			}
		}

		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isColinear(NumberVector _vector) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 			isColinear(DoubleVector _vector) {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isOrthogonal(DoubleVector _vector) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 			isOrthogonal(NumberVector _vector) {
		throw new NotYetImplementedException();
	}

	@Override
	public double 			norm(Norm _norm) {
		return 0;
	}
	@Override
	public double 			magnitude() {
		return norm();
	}
	@Override
	public double 			norm() {
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					return Math.sqrt(v4.getX()*v4.getX() + v4.getY()*v4.getY() + v4.getZ()*v4.getZ() + v4.getW()*v4.getW());
		case 3  :   Vector3D v3 = (Vector3D) this;
					return Math.sqrt(v3.getX()*v3.getX() + v3.getY()*v3.getY() + v3.getZ()*v3.getZ());
		case 2  :   Vector2D v2 = (Vector2D) this;
					return Math.sqrt(v2.getX()*v2.getX() + v2.getY()*v2.getY());
		case 1  :   Vector1D v1 = (Vector1D) this;
					return Math.sqrt(v1.getX()*v1.getX());
		default :   return Double.NaN;
		}
	}
	@Override
	public double 			norm2() {
		switch(nbDimension) {
		case 4  :   Vector4D v4 = (Vector4D) this;
					return v4.getX()*v4.getX() + v4.getY()*v4.getY() + v4.getZ()*v4.getZ() + v4.getW()*v4.getW();
		case 3  :   Vector3D v3 = (Vector3D) this;
					return v3.getX()*v3.getX() + v3.getY()*v3.getY() + v3.getZ()*v3.getZ();
		case 2  :   Vector2D v2 = (Vector2D) this;
					return v2.getX()*v2.getX() + v2.getY()*v2.getY();
		case 1  :   Vector1D v1 = (Vector1D) this;
					return v1.getX()*v1.getX();
		default :   return Double.NaN;
		}
	}

	@Override
	public Number 			dotProduct(NumberVector _vector) {
		throw new NotYetImplementedException();
	}
	@Override
	public NumberVector 	crossProduct(NumberVector _vector) {
		throw new NotYetImplementedException();
	}

	public abstract DoubleVector clone();

	public static abstract class Editable extends AdapterVectorDouble implements DoubleVector.Editable {
		private static final long serialVersionUID = 503590710449563348L;

		Editable(int _nbDimension) {
			super(_nbDimension);
		}

		@Override
		public void 		set(double _value, int _i) {
			switch(nbDimension) {
			case 4  :   Vector4D.Editable v4 = (Vector4D.Editable) this;
						switch(_i) {
						case 3  : v4.setW(_value);
						case 2  : v4.setZ(_value);
						case 1  : v4.setY(_value);
						case 0  : v4.setX(_value);
						default : return ;
						}
			case 3  :   Vector3D.Editable v3 = (Vector3D.Editable) this;
						switch(_i) {
						case 2  : v3.setZ(_value);
						case 1  : v3.setY(_value);
						case 0  : v3.setX(_value);
						default : return ;
						}
			case 2  :   Vector2D.Editable v2 = (Vector2D.Editable) this;
						switch(_i) {
						case 1  : v2.setY(_value);
						case 0  : v2.setX(_value);
						default : return ;
						}
			case 1  :   Vector1D.Editable v1 = (Vector1D.Editable) this;
						switch(_i) {
						case 0  : v1.setX(_value);
						default : return ;
						}
			default :   return ;
			}
		}
		@Override
		public void 		set(NumberVector _vector) {
			throw new NotYetImplementedException();
		}
		@Override
		public void 		set(DoubleVector _vector) {
			throw new NotYetImplementedException();
		}

		@Override
		public void 		setNumber(Number _value, int _i) {
			set(_value.doubleValue(), _i);
		}
		@Override
		public void 		setValue(double _value, int _i) {
			set(_value, _i);
		}
		@Override
		public void 		setValue(double _value, int... _coords) {
			assert(_coords.length == 1);
			set(_value, _coords[0]);
		}

		@Override
		public void 		setSlice(DoubleTensor _tensor, int... _slice) {
			throw new NotYetImplementedException();
		}

		@Override
		public NumberVector plusEquals(NumberVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()+_vector.getNumber(0).doubleValue());
						v4.setY(v4.getY()+_vector.getNumber(1).doubleValue());
						v4.setZ(v4.getZ()+_vector.getNumber(2).doubleValue());
						v4.setW(v4.getW()+_vector.getNumber(3).doubleValue());
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()+_vector.getNumber(0).doubleValue());
						v3.setY(v3.getY()+_vector.getNumber(1).doubleValue());
						v3.setZ(v3.getZ()+_vector.getNumber(2).doubleValue());
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()+_vector.getNumber(0).doubleValue());
						v2.setY(v2.getY()+_vector.getNumber(1).doubleValue());
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()+_vector.getNumber(0).doubleValue());
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}
		@Override
		public DoubleVector plusEquals(DoubleVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()+_vector.get(0));
						v4.setY(v4.getY()+_vector.get(1));
						v4.setZ(v4.getZ()+_vector.get(2));
						v4.setW(v4.getW()+_vector.get(3));
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()+_vector.get(0));
						v3.setY(v3.getY()+_vector.get(1));
						v3.setZ(v3.getZ()+_vector.get(2));
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()+_vector.get(0));
						v2.setY(v2.getY()+_vector.get(1));
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()+_vector.get(0));
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}

		@Override
		public NumberVector minusEquals(NumberVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()-_vector.getNumber(0).doubleValue());
						v4.setY(v4.getY()-_vector.getNumber(1).doubleValue());
						v4.setZ(v4.getZ()-_vector.getNumber(2).doubleValue());
						v4.setW(v4.getW()-_vector.getNumber(3).doubleValue());
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()-_vector.getNumber(0).doubleValue());
						v3.setY(v3.getY()-_vector.getNumber(1).doubleValue());
						v3.setZ(v3.getZ()-_vector.getNumber(2).doubleValue());
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()-_vector.getNumber(0).doubleValue());
						v2.setY(v2.getY()-_vector.getNumber(1).doubleValue());
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()-_vector.getNumber(0).doubleValue());
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}
		@Override
		public DoubleVector minusEquals(DoubleVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()-_vector.get(0));
						v4.setY(v4.getY()-_vector.get(1));
						v4.setZ(v4.getZ()-_vector.get(2));
						v4.setW(v4.getW()-_vector.get(3));
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()-_vector.get(0));
						v3.setY(v3.getY()-_vector.get(1));
						v3.setZ(v3.getZ()-_vector.get(2));
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()-_vector.get(0));
						v2.setY(v2.getY()-_vector.get(1));
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()-_vector.get(0));
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}

		@Override
		public NumberVector timesEquals(NumberVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()*_vector.getNumber(0).doubleValue());
						v4.setY(v4.getY()*_vector.getNumber(1).doubleValue());
						v4.setZ(v4.getZ()*_vector.getNumber(2).doubleValue());
						v4.setW(v4.getW()*_vector.getNumber(3).doubleValue());
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()*_vector.getNumber(0).doubleValue());
						v3.setY(v3.getY()*_vector.getNumber(1).doubleValue());
						v3.setZ(v3.getZ()*_vector.getNumber(2).doubleValue());
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()*_vector.getNumber(0).doubleValue());
						v2.setY(v2.getY()*_vector.getNumber(1).doubleValue());
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()*_vector.getNumber(0).doubleValue());
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}
		@Override
		public DoubleVector timesEquals(DoubleVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()*_vector.get(0));
						v4.setY(v4.getY()*_vector.get(1));
						v4.setZ(v4.getZ()*_vector.get(2));
						v4.setW(v4.getW()*_vector.get(3));
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()*_vector.get(0));
						v3.setY(v3.getY()*_vector.get(1));
						v3.setZ(v3.getZ()*_vector.get(2));
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()*_vector.get(0));
						v2.setY(v2.getY()*_vector.get(1));
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()*_vector.get(0));
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}

		@Override
		public NumberVector dividesEquals(NumberVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()/_vector.getNumber(0).doubleValue());
						v4.setY(v4.getY()/_vector.getNumber(1).doubleValue());
						v4.setZ(v4.getZ()/_vector.getNumber(2).doubleValue());
						v4.setW(v4.getW()/_vector.getNumber(3).doubleValue());
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()/_vector.getNumber(0).doubleValue());
						v3.setY(v3.getY()/_vector.getNumber(1).doubleValue());
						v3.setZ(v3.getZ()/_vector.getNumber(2).doubleValue());
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()/_vector.getNumber(0).doubleValue());
						v2.setY(v2.getY()/_vector.getNumber(1).doubleValue());
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()/_vector.getNumber(0).doubleValue());
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}
		@Override
		public DoubleVector dividesEquals(DoubleVector _vector) {
			switch(nbDimension) {
			case 4  :   if(_vector.size() < 4)
							throw new IllegalArgumentException();
						Vector4D.Editable v4 = (Vector4D.Editable) this;
						v4.setX(v4.getX()/_vector.get(0));
						v4.setY(v4.getY()/_vector.get(1));
						v4.setZ(v4.getZ()/_vector.get(2));
						v4.setW(v4.getW()/_vector.get(3));
						return this;
			case 3  :   if(_vector.size() < 3)
							throw new IllegalArgumentException();
						Vector3D.Editable v3 = (Vector3D.Editable) this;
						v3.setX(v3.getX()/_vector.get(0));
						v3.setY(v3.getY()/_vector.get(1));
						v3.setZ(v3.getZ()/_vector.get(2));
						return this;
			case 2  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector2D.Editable v2 = (Vector2D.Editable) this;
						v2.setX(v2.getX()/_vector.get(0));
						v2.setY(v2.getY()/_vector.get(1));
						return this;
			case 1  :   if(_vector.size() < 2)
							throw new IllegalArgumentException();
						Vector1D.Editable v1 = (Vector1D.Editable) this;
						v1.setX(v1.getX()/_vector.get(0));
						return this;
			default :   throw new IllegalArgumentException("What The Fuck");
			}
		}

		public abstract DoubleVector.Editable clone();

	}

}
