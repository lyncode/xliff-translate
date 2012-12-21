package com.lyncode.xliff;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.lyncode.xliff.xml.TransUnit;
import com.lyncode.xliff.xml.Xliff;

public class XLIFF {
	private Map<String, String> translations;
	
	XLIFF (Xliff x) {
		translations = new TreeMap<String, String>();
		for (Xliff.File f : x.getFile())
			for (TransUnit u : f.getBody().getTransUnit())
				translations.put(u.getSource(), u.getTarget());
	}
	
	public String getMessage (String source) {
		return translations.get(source);
	}
	
	public Collection<String> getSource () {
		return translations.keySet();
	}
}
