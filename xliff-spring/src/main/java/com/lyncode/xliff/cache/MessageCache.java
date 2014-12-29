package com.lyncode.xliff.cache;

import com.google.common.base.Supplier;

import java.util.Locale;

public interface MessageCache {
    String retrieve (Locale locale, String code, Supplier<String> initializer);
}
