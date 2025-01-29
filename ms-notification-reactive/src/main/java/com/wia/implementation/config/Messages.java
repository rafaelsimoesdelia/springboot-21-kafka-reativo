package com.wia.implementation.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Classe relacionada a mensageria
 * 
 * @author Salatiel Fiore
 */
@Component
public class Messages {

    private final MessageSource messageSource;

    // Injeção de dependência via construtor
    public Messages(MessageSource messageSource) {
	this.messageSource = messageSource;
    }

    /**
     * @param code
     */
    public String get(String code) {
	return messageSource.getMessage(code, new Object[] {}, LocaleContextHolder.getLocale());
    }

    /**
     * @param code
     * @param arg
     */
    public String get(String code, String arg) {
	return messageSource.getMessage(code, new Object[] { arg }, LocaleContextHolder.getLocale());
    }

    /**
     * Método utilizado para recuperar a mensagem do message properties baseada em
     * uma chave e um args de objetos {0}, {1}, {N}
     * 
     * @param code
     * @param args
     * @return String
     */
    public String getMessageByArgs(String code, Object[] args) {
	return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
