package com.lyncode.xliff.message;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.lyncode.xliff.XLIFF;
import com.lyncode.xliff.XLiffUtils;
import com.lyncode.xliff.XliffException;
import com.lyncode.xliff.cache.InMemoryPersistentMessageCache;
import com.lyncode.xliff.cache.InMemoryPersistentXliffCache;
import com.lyncode.xliff.cache.MessageCache;
import com.lyncode.xliff.cache.XliffCache;
import com.lyncode.xliff.resource.TranslationResourceResolver;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

public class XliffMessageResolver implements MessageResolver {
    private final StringFormatter stringFormatter;
    private final TranslationResourceResolver translationResourceResolver;
    private final MessageCache messageCache;
    private final XliffCache xliffCache;

    public XliffMessageResolver(StringFormatter stringFormatter, TranslationResourceResolver translationResourceResolver, MessageCache messageCache, XliffCache xliffCache) {
        this.stringFormatter = stringFormatter;
        this.translationResourceResolver = translationResourceResolver;
        this.messageCache = messageCache;
        this.xliffCache = xliffCache;
    }

    public XliffMessageResolver(Locale defaultLocale, TranslationResourceResolver translationResourceResolver) {
        this.translationResourceResolver = translationResourceResolver;
        this.stringFormatter = new BaseStringFormatter();
        this.messageCache = new InMemoryPersistentMessageCache(defaultLocale);
        this.xliffCache = new InMemoryPersistentXliffCache(defaultLocale);
    }

    public XliffMessageResolver(Locale defaultLocale, TranslationResourceResolver translationResourceResolver, StringFormatter stringFormatter) {
        this.translationResourceResolver = translationResourceResolver;
        this.stringFormatter = stringFormatter;
        this.messageCache = new InMemoryPersistentMessageCache(defaultLocale);
        this.xliffCache = new InMemoryPersistentXliffCache(defaultLocale);
    }

    @Override
    public String resolve(final String code, String defaultMessage, final Locale locale, Object... args) {
        return messageCache.retrieve(locale, code, new Supplier<String>() {
            @Override
            public String get() {
                Collection<XLIFF> xliffs = getXliffs(locale);
                for (XLIFF xliff : xliffs) {
                    String target = xliff.getTarget(code);
                    if (target != null) return target;
                }
                return null;
            }
        });
    }

    private Collection<XLIFF> getXliffs(final Locale locale) {
        return xliffCache.get(locale, new Supplier<Collection<XLIFF>>() {
            @Override
            public Collection<XLIFF> get() {
                return Collections2.transform(translationResourceResolver.resolve(locale), new Function<InputStream, XLIFF>() {
                    @Override
                    public XLIFF apply(InputStream input) {
                        try {
                            return XLiffUtils.read(input);
                        } catch (XliffException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
}
