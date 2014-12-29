package com.lyncode.xliff.resource;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

public interface TranslationResourceResolver {
    Collection<InputStream> resolve (Locale locale);
}
