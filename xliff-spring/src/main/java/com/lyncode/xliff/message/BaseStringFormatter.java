package com.lyncode.xliff.message;

public class BaseStringFormatter implements StringFormatter {
    @Override
    public String format(String input, Object... args) {
        return String.format(input, args);
    }
}
