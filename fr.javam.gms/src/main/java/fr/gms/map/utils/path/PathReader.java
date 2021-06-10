package fr.gms.map.utils.path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.gis.GisPath;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;
import fr.java.sdk.file.text.csv.CsvReader;

public class PathReader {

	public static Gis.Path loadCSV(Path _path) {
		return loadCSV(IDs.random(), _path, ' ', false);
	}
	public static Gis.Path loadCSV(Path _path, char _delimiter) {
		return loadCSV(IDs.random(), _path, _delimiter, false);
	}
	public static Gis.Path loadCSV(Path _path, boolean _skipHeader) {
		return loadCSV(IDs.random(), _path, ' ', _skipHeader);
	}
	public static Gis.Path loadCSV(Path _path, char _delimiter, boolean _skipHeader) {
		return loadCSV(IDs.random(), _path, _delimiter, _skipHeader);
	}

	public static Gis.Path loadCSV(ID _id, Path _path) {
		return loadCSV(_id, _path, ' ', false);
	}
	public static Gis.Path loadCSV(ID _id, Path _path, char _delimiter) {
		return loadCSV(_id, _path, _delimiter, false);
	}
	public static Gis.Path loadCSV(ID _id, Path _path, boolean _skipHeader) {
		return loadCSV(_id, _path, ' ', _skipHeader);
	}
	public static Gis.Path loadCSV(ID _id, Path _path, char _delimiter, boolean _skipHeader) {
		try {
			CsvReader csv = new CsvReader(_path.toString(), _delimiter, Charset.forName("UTF-8"));

			if(_skipHeader)
				csv.skipLine();

			List<GeoCoordinate>	path = new ArrayList<GeoCoordinate>();

			while(csv.readRecord()) {
				GeoCoordinate position = GeoCoordinates.newWGS84(Double.parseDouble(csv.get(0)), Double.parseDouble(csv.get(1)), 0d);

				path.add(position);
			}

			return new GisPath(_id, path);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Gis.Path loadRAW(Path _path) throws NumberFormatException, IOException {
		return loadRAW(IDs.random(), _path, ' ', false);
	}
	public static Gis.Path loadRAW(Path _path, char _delimiter) throws NumberFormatException, IOException {
		return loadRAW(IDs.random(), _path, _delimiter, false);
	}
	public static Gis.Path loadRAW(Path _path, boolean _skipHeader) throws NumberFormatException, IOException {
		return loadRAW(IDs.random(), _path, ' ', _skipHeader);
	}
	public static Gis.Path loadRAW(Path _path, char _delimiter, boolean _skipHeader) throws NumberFormatException, IOException {
		return loadCSV(IDs.random(), _path, _delimiter, _skipHeader);
	}

	public static Gis.Path loadRAW(ID _id, Path _path) throws NumberFormatException, IOException {
		return loadCSV(_id, _path, ' ', false);
	}
	public static Gis.Path loadRAW(ID _id, Path _path, char _delimiter) throws NumberFormatException, IOException {
		return loadRAW(_id, _path, _delimiter, false);
	}
	public static Gis.Path loadRAW(ID _id, Path _path, boolean _skipHeader) throws NumberFormatException, IOException {
		return loadCSV(_id, _path, ' ', _skipHeader);
	}
	public static Gis.Path loadRAW(ID _id, Path _path, char _delimiter, boolean _skipHeader) throws NumberFormatException, IOException {
		List<GeoCoordinate>	path 	= new ArrayList<GeoCoordinate>();

	    try(BufferedReader reader = new BufferedReader(new FileReader(_path.toFile()))) {
			if(_skipHeader)
				reader.readLine();

	    	reader.lines().forEach(line -> {
	    		String[]      tokens = line.split("" + _delimiter);
	    		GeoCoordinate coord  = GeoCoordinates.newUTM(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));

	    		path.add(coord);
	    	});

	    } catch(IOException _e) {
	    	_e.printStackTrace();
	    } finally { }

		return new GisPath(_id, path);
	}

}
