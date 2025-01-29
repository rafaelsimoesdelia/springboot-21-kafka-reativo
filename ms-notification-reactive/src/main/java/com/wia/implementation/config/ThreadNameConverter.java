package com.wia.implementation.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Classe que retorna o nome da Thread para exportar no logback.xml
 */
public class ThreadNameConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
	return event.getThreadName();
    }
}
