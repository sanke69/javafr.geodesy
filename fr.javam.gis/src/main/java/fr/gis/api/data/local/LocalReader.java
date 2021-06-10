package fr.gis.api.data.local;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.lang.exceptions.NotYetImplementedException;

public abstract class LocalReader {

	private Collection<LocalSource> sources;

	public LocalReader() {
		super();
		sources = new HashSet<LocalSource>();
	}

	public LocalReader 			addSource(LocalSource _source) {
		sources.add(_source);
		return this;
	}
	public LocalReader 			addSources(LocalSource... _sources) {
		sources.addAll(Arrays.asList(_sources));
		return this;
	}
	public LocalReader 			addSources(Collection<LocalSource> _sources) {
		sources.addAll(_sources);
		return this;
	}

	public Set<LocalSource> 	getSources() {
		sources = sources.stream().distinct().collect(Collectors.toSet());
		return (Set<LocalSource>) sources;
	}

	protected static InputStream getSourceStream(LocalSource _source) {
		InputStream is = null;
		
		switch(_source.from()) {
		case FILE_SYSTEM:	try {
								is = new FileInputStream(_source.getFileSystemPath().toFile());
							} catch (FileNotFoundException e) { e.printStackTrace(); }
							break;
		case RESOURCE:		is = _source.getResourceClass().getResourceAsStream(_source.getResourceFolder());
							break;
		case CUSTOM:
		case DATABASE:
		case UNDEF:
		default:			throw new NotYetImplementedException();
		}
		
		return is;
	}
}
