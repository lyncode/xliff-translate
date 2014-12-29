package com.lyncode.xliff.cache;

import com.google.common.base.Supplier;
import com.lyncode.xliff.XLIFF;

import java.util.Collection;
import java.util.Locale;

public interface XliffCache {
    Collection<XLIFF> get (Locale locale, Supplier<Collection<XLIFF>> initializer);
}
