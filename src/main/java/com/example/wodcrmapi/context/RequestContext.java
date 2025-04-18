package com.example.wodcrmapi.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestContext {
    private static final ThreadLocal<String> requestLang = new ThreadLocal<>();

    public String getRequestLang() {
        return requestLang.get() != null ? requestLang.get() : "ru"; // default to Russian
    }

    public void setRequestLang(String lang) {
        requestLang.set(lang);
    }

    public void clear() {
        requestLang.remove();
    }
}
