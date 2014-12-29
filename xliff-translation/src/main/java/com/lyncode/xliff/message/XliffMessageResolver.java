package com.lyncode.xliff.message;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.lyncode.choiceprops.PluralProperty;
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
import java.util.Map;

public class XliffMessageResolver implements MessageResolver {
    private final TranslationResourceResolver translationResourceResolver;
    private final MessageCache messageCache;
    private final XliffCache xliffCache;

    public XliffMessageResolver(TranslationResourceResolver translationResourceResolver, MessageCache messageCache, XliffCache xliffCache) {
        this.translationResourceResolver = translationResourceResolver;
        this.messageCache = messageCache;
        this.xliffCache = xliffCache;
    }

    public XliffMessageResolver(Locale defaultLocale, TranslationResourceResolver translationResourceResolver) {
        this.translationResourceResolver = translationResourceResolver;
        this.messageCache = new InMemoryPersistentMessageCache(defaultLocale);
        this.xliffCache = new InMemoryPersistentXliffCache(defaultLocale);
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

    @Override
    public String resolve(final Locale locale, final int count, final String code, final Map<String, Object> replacements) {
        return messageCache.retrieve(locale, code, new Supplier<String>() {
            @Override
            public String get() {
                Collection<XLIFF> xliffs = getXliffs(locale);
                for (XLIFF xliff : xliffs) {
                    String target = xliff.getTarget(code);
                    if (target != null) return PluralProperty.translate(count, target, replacements);
                }
                return PluralProperty.translate(count, code, replacements);
            }
        });
    }
}
