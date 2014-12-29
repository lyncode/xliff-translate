package com.lyncode.xliff.message;

import java.util.Locale;
import java.util.Map;

public interface MessageResolver {
    String resolve (Locale locale, int count, String code, Map<String, Object> replacements);
}
