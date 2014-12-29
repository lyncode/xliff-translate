package com.lyncode.xliff.cache;

import com.google.common.base.Supplier;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InMemoryPersistentMessageCache implements MessageCache {
    private final Locale defaultLocale;
    private final Map<Locale, Map<String, String>> map = new HashMap<Locale, Map<String, String>>();

    public InMemoryPersistentMessageCache(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public String retrieve(Locale locale, String code, Supplier<String> initializer) {
        if (locale == null) locale = defaultLocale;

        if (!map.containsKey(locale))
            map.put(locale, new HashMap<String, String>());

        if (!map.get(locale).containsKey(code))
            map.get(locale).put(code, initializer.get());

        return map.get(locale).get(code);
    }
}
