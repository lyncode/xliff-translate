package com.lyncode.xliff;

import com.lyncode.xliff.message.MessageResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class XliffResourceBundleMessageSource implements MessageSource {
    private final MessageResolver messageResolver;

    public XliffResourceBundleMessageSource(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageResolver.resolve(code, defaultMessage, locale, args);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return messageResolver.resolve(code, code, locale, args);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        for (String code : resolvable.getCodes()) {
            String message = getMessage(code, resolvable.getArguments(), resolvable.getDefaultMessage(), locale);
            if (message != null) return message;
        }

        if (StringUtils.isBlank(resolvable.getDefaultMessage())) throw new NoSuchMessageException(String.format("No message found for the given codes [%s]", StringUtils.join(resolvable.getCodes(), ", ")));
        else return resolvable.getDefaultMessage();
    }
}
