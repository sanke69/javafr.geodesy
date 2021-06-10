package fr.gms.map.utils.path;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.java.sdk.file.text.csv.CsvWriter;

public class PathWriter {

	public static void saveCSV(Gis.Path _elt, Path _path) {
		saveCSV(_elt, _path, ' ', false);
	}
	public static void saveCSV(Gis.Path _elt, Path _path, char _delimiter) {
		saveCSV(_elt, _path, _delimiter, false);
	}
	public static void saveCSV(Gis.Path _elt, Path _path, char _delimiter, boolean _writeHeader) {
		CsvWriter writer = new CsvWriter(_path.toString(), _delimiter, Charset.forName("UTF-8"));

		try {
			if(_writeHeader)
				writer.writeRecord(new String[] { "Longitude", "Latitude" });
	
		    for(GeoCoordinate c : _elt.getGeometry())
		    	writer.writeRecord(new String[] { String.valueOf( c.asWGS84().getLongitude() ), String.valueOf( c.asWGS84().getLatitude() ) } );
		} catch(IOException e) {
			e.printStackTrace();
		}

	    writer.close();
	}

	public static void saveRAW(Gis.Path _elt, Path _path) {
		saveRAW(_elt, _path, ' ', false);
	}
	public static void saveRAW(Gis.Path _elt, Path _path, char _delimiter) {
		saveRAW(_elt, _path, _delimiter, false);
	}
	public static void saveRAW(Gis.Path _elt, Path _path, char _delimiter, boolean _writeHeader) {
	    try(BufferedWriter writer = new BufferedWriter(new FileWriter(_path.toFile()))) {

			if(_writeHeader)
				writer.write("Longitude" + _delimiter + "Latitude" + "\n");

		    for(GeoCoordinate c : _elt.getGeometry())
		    	writer.write( c.asWGS84().getLongitude() + _delimiter + c.asWGS84().getLatitude() + "\n");

		    writer.close();

	    } catch(IOException _e) {
	    	_e.printStackTrace();
	    } finally { }
	}

}
