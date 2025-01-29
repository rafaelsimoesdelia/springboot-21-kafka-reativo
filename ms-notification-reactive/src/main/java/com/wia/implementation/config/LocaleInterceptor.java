package com.wia.implementation.config;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
	String lang = request.getParameter("lang");
	if (lang != null && !lang.isEmpty()) {
	    Locale locale = Locale.forLanguageTag(lang.replace("_", "-"));
	    LocaleContextHolder.setLocale(locale);
	}
	return true;
    }
}
