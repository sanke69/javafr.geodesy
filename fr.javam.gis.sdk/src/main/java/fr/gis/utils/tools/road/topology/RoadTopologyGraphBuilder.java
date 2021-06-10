package fr.gis.utils.tools.road.topology;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.sdk.objects.road.RoadElement;
import fr.java.lang.properties.ID;
import fr.java.sdk.log.LogInstance;

public class RoadTopologyGraphBuilder {
	public static final LogInstance log = LogInstance.getLogger(RoadTopologyGraphBuilder.class, Level.ALL);
	static {
//		log.setLevel(Level.FINEST);
		log.setLevel(Level.OFF);
//		log.setLevel(Level.ALL);
//		log.setLevel(500);
	}
	
	static class Connexion {
		ID      s0, s1;
		int     index;
		GeoCoordinate intersection;
		
		Connexion(ID _id0, ID _id1, int _i, GeoCoordinate _p) {
			s0 = _id0;
			s1 = _id1;
			index = _i;
			intersection = _p;
		}
		
		public String toString() {
			return "ID_0: " + s0 + ", ID_1: " + s1 + "   ==> " + intersection;
		}
	}

	public static final Collection<Road.Element.Linkable> buildGraph(Map<ID, Road.Element.Linkable> _roadElements) {
		log.debug(300, "Generating connexions...");

		buildConnections(_roadElements);

		return _roadElements.values();
	}
	private static final void buildConnections(Map<ID, Road.Element.Linkable> _segments) {
		RoadElement[] elts = _segments.values().stream().toArray(RoadElement[]::new);

		for(int j = 0; j < elts.length; ++j)
			for(int i = j; i < elts.length; ++i)
				makeConnection(elts[i], elts[j]);
	}
 	private static void makeConnection(Road.Element.Linkable A, Road.Element.Linkable B) {
		if( A.getId().equals(B.getId()) )
			return ;

 		GeoCoordinate  	begA = A.getStart(),		endA = A.getEnd(),
						begB = B.getStart(),		endB = B.getEnd();
 		Gis.Direction  dirA = A.getDrivingWay(),	dirB = B.getDrivingWay();

		if(makeConnection(A, begA, endA, dirA, B, begB, endB, dirB))
			return ;

		// HARD CASE => MUST BE VALIDATED !!!!
		if(A.getTops().contains(begB))
			makeConnection(A, dirA, B, dirB, true);
		else
		if(A.getTops().contains(endB))
			makeConnection(A, dirA, B, dirB, false);
		else
		if(B.getTops().contains(begA))
			makeConnection(B, dirB, A, dirA, true);
		else
		if(B.getTops().contains(endA))
			makeConnection(B, dirB, A, dirA, false);
	}

 	private static final boolean makeConnection(Road.Element.Linkable A, GeoCoordinate _beg0, GeoCoordinate _end0, Gis.Direction _dir0, Road.Element.Linkable B, GeoCoordinate _beg1, GeoCoordinate _end1, Gis.Direction _dir1) {
		if(_beg0.equals(_beg1)) {
			if(_dir0 == Gis.Direction.BOTH) {
				if(_dir1 == Gis.Direction.BOTH) {
					A.addNextElement(B);
					A.addPreviousElement(B);
					B.addNextElement(A);
					B.addPreviousElement(A);
					return true;
				} else if(_dir1 == Gis.Direction.DIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				} else if(_dir1 == Gis.Direction.INDIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				}
			} else if(_dir0 == Gis.Direction.DIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.INDIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				}
			} else if(_dir0 == Gis.Direction.INDIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.DIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				}
			}
		}
		else
		if(_end0.equals(_beg1)) {
			if(_dir0 == Gis.Direction.BOTH) {
				if(_dir1 == Gis.Direction.BOTH) {
					A.addNextElement(B);
					A.addPreviousElement(B);
					B.addNextElement(A);
					B.addPreviousElement(A);
					return true;
				} else if(_dir1 == Gis.Direction.DIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				} else if(_dir1 == Gis.Direction.INDIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				}
			} else if(_dir0 == Gis.Direction.DIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.DIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				}
			} else if(_dir0 == Gis.Direction.INDIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.INDIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				}
			}
		}
		else
		if(_end0.equals(_end1)) {
			if(_dir0 == Gis.Direction.BOTH) {
				if(_dir1 == Gis.Direction.BOTH) {
					A.addNextElement(B);
					A.addPreviousElement(B);
					B.addNextElement(A);
					B.addPreviousElement(A);
					return true;
				} else if(_dir1 == Gis.Direction.DIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				} else if(_dir1 == Gis.Direction.INDIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				}
			} else if(_dir0 == Gis.Direction.DIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.INDIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				}
			} else if(_dir0 == Gis.Direction.INDIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.DIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				}
			}
		}
		else
		if(_end1.equals(_beg0)) {
			if(_dir0 == Gis.Direction.BOTH) {
				if(_dir1 == Gis.Direction.BOTH) {
					A.addNextElement(B);
					A.addPreviousElement(B);
					B.addNextElement(A);
					B.addPreviousElement(A);
					return true;
				} else if(_dir1 == Gis.Direction.DIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				} else if(_dir1 == Gis.Direction.INDIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				}
			} else if(_dir0 == Gis.Direction.DIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.DIRECT) {
					B.addNextElement(A);
					A.addPreviousElement(B);
					return true;
				}
			} else if(_dir0 == Gis.Direction.INDIRECT) {
				if(_dir1 == Gis.Direction.BOTH || _dir1 == Gis.Direction.INDIRECT) {
					A.addNextElement(B);
					B.addPreviousElement(A);
					return true;
				}
			}
		}
		
		return false;
 	}

 	private static final boolean makeConnection(Road.Element.Linkable A, Gis.Direction _dirA, Road.Element.Linkable B, Gis.Direction _dirB, boolean _begB) {
		if(_dirA == Gis.Direction.BOTH) {
			if(_dirB == Gis.Direction.BOTH) {
				A.addNextElement(B);
				A.addPreviousElement(B);
				B.addNextElement(A);
				B.addPreviousElement(A);
				return true;
			} else if(_dirB == Gis.Direction.DIRECT) {
				if(_begB) {
 					A.addNextElement(B);
 					B.addPreviousElement(A);
 				} else {
 					B.addNextElement(A);
 					A.addPreviousElement(B);
 				}
				return true;
			} else if(_dirB == Gis.Direction.INDIRECT) {
				if(_begB) {
 					B.addNextElement(A);
 					A.addPreviousElement(B);
 				} else {
 					A.addNextElement(B);
 					B.addPreviousElement(A);
 				}
				return true;
			}
		} else if(_dirA == Gis.Direction.DIRECT) {
			if(_dirB == Gis.Direction.BOTH) {
				A.addPreviousElement(B);
				A.addNextElement(B);
				B.addPreviousElement(A);
				B.addNextElement(A);
				return true;
			} else if(_dirB == Gis.Direction.DIRECT) {
				if(_begB) {
 					A.addNextElement(B);
 					B.addPreviousElement(A);
 				} else {
 					A.addPreviousElement(B);
 					B.addNextElement(A);
 				}
				return true;
			} else if(_dirB == Gis.Direction.INDIRECT) {
				if(_begB) {
 					A.addPreviousElement(B);
 					B.addNextElement(A);
 				} else {
 					B.addPreviousElement(A);
 					A.addNextElement(B);
 				}
				return true;
			}
		} else if(_dirA == Gis.Direction.INDIRECT) {
			if(_dirB == Gis.Direction.BOTH) {
				A.addPreviousElement(B);
				A.addNextElement(B);
				B.addPreviousElement(A);
				B.addNextElement(A);
				return true;
			} else if(_dirB == Gis.Direction.DIRECT) {
				if(_begB) {
 					A.addPreviousElement(B);
 					B.addNextElement(A);
 				} else {
 					A.addNextElement(B);
 					B.addPreviousElement(A);
 				}
				return true;
			} else if(_dirB == Gis.Direction.INDIRECT) {
				if(_begB) {
 					B.addPreviousElement(A);
 					A.addNextElement(B);
 				} else {
 					A.addPreviousElement(B);
 					B.addNextElement(A);
 				}
				return true;
			}
		}
 
		return false;
 	}

}
