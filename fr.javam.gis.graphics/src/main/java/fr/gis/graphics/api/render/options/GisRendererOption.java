package fr.gis.graphics.api.render.options;

import java.util.HashMap;

public class GisRendererOption extends HashMap<String, Object> {
	private static final long serialVersionUID = 7564758159873620685L;

	public static GisRendererOption of(Object... opts) {
		assert(opts.length % 2 == 0);

		GisRendererOption options = new GisRendererOption();
		for(int i = 0; i < opts.length; i += 2)
			options.put((String) opts[i], opts[i + 1]); 

		return options;
	}

}
