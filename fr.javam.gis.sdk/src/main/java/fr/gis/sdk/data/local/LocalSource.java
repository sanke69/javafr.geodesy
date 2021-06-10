package fr.gis.sdk.data.local;

import java.net.URL;
import java.nio.file.Path;

public class LocalSource {

	public static enum FROM { UNDEF, FILE_SYSTEM, RESOURCE, DATABASE, CUSTOM; }	

	public static LocalSource fromPath(Path _path) {
		return new LocalSource().fromFileSystem(_path);
	}

	protected FROM		 		source;

	protected Class<?>			rcClass;
	protected String			rcFolder;
	protected Path				fsPath;
//	protected DatabaseAccess	dbAccess;

	public LocalSource() {
		super();
	}

	public FROM 	from() {
		return source;
	}
	public Class<?> getResourceClass() {
		return rcClass;
	}
	public String 	getResourceFolder() {
		return rcFolder;
	}
	public Path 	getFileSystemPath() {
		return fsPath;
	}
	
	public LocalSource from(FROM _src, Object... _parameter) {
		source = _src;

		return this;
	}
	public LocalSource fromResource(Class<?> _cls, String _folder) {
		source   = FROM.RESOURCE;
		rcClass  = _cls;
		rcFolder = _folder;

		return this;
	}
	public LocalSource fromFileSystem(Path _path) {
		source = FROM.FILE_SYSTEM;
		fsPath = _path;

		return this;
	}
	public LocalSource fromDatabase(URL _database, String _username, String _password) {
		source = FROM.DATABASE;

		return this;
	}

}
