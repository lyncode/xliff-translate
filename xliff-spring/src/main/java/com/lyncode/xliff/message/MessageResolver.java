package com.lyncode.xliff.message;

import java.util.Locale;

public interface MessageResolver {
    String resolve (String code, String defaultMessage, Locale locale, Object... args);
}
