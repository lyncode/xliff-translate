package com.lyncode.xliff.cache;

import com.google.common.base.Supplier;
import com.lyncode.xliff.XLIFF;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

public class InMemoryPersistentXliffCache implements XliffCache {
    private final Locale defaultLocale;
    private final Map<Locale, Collection<XLIFF>> map = new HashMap<Locale, Collection<XLIFF>>();

    public InMemoryPersistentXliffCache(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Collection<XLIFF> get(Locale locale, Supplier<Collection<XLIFF>> initializer) {
        if (locale == null) locale = defaultLocale;

        if (!map.containsKey(locale))
            map.put(locale, initializer.get());

        return map.get(locale);
    }
}
