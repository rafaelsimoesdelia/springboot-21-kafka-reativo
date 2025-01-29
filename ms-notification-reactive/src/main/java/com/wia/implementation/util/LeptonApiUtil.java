package com.wia.implementation.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class providing helper methods for validation, formatting, and other
 * operations.
 */
public class LeptonApiUtil {

    private LeptonApiUtil() {
	throw new IllegalStateException("Utility class");
    }

    /**
     * Formats the elapsed time in a human-readable string with units like days,
     * hours, minutes, seconds, and milliseconds.
     *
     * @param tempoDecorridoMillis The elapsed time in milliseconds.
     * @return A formatted string representing the elapsed time.
     */
    public static String tempoDecorrido(long tempoDecorridoMillis) {
	StringBuilder str = new StringBuilder();
	List<TimeUnit> unidades = Arrays.asList(TimeUnit.values());
	Collections.reverse(unidades);
	Map<TimeUnit, Long> resultado = new LinkedHashMap<>();
	for (TimeUnit un : unidades) {
	    long diff = un.convert(tempoDecorridoMillis, TimeUnit.MILLISECONDS);
	    long diffInMilliesForUnit = un.toMillis(diff);
	    tempoDecorridoMillis -= diffInMilliesForUnit;
	    resultado.put(un, diff);
	}
	Map<TimeUnit, String> unitSymbols = new LinkedHashMap<>();
	unitSymbols.put(TimeUnit.DAYS, "d");
	unitSymbols.put(TimeUnit.HOURS, "h");
	unitSymbols.put(TimeUnit.MINUTES, "m");
	unitSymbols.put(TimeUnit.SECONDS, "s");
	unitSymbols.put(TimeUnit.MILLISECONDS, "ms");
	unitSymbols.keySet().forEach(t -> {
	    if (resultado.containsKey(t) && resultado.get(t) > 0) {
		str.append(resultado.get(t));
		str.append(unitSymbols.get(t));
	    }
	});
	return (StringUtils.isNotBlank(str.toString())) ? str.toString() : "0ms";
    }

    /**
     * Generates a random password with at least one uppercase letter, one lowercase
     * letter, one digit, and one special character.
     *
     * @param length The desired length of the password.
     * @return The generated random password.
     */
    public static String generateRandomPassword(int length) {
	String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	String digits = "0123456789";
	String specialCharacters = "!@#$%^&*()_+[]{}|;:,.<>?";

	String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;

	SecureRandom random = new SecureRandom();
	StringBuilder password = new StringBuilder(length);

	password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
	password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
	password.append(digits.charAt(random.nextInt(digits.length())));
	password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

	for (int i = 4; i < length; i++) {
	    password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
	}

	return shuffleString(password.toString());
    }

    /**
     * Shuffles the characters in the input string for added randomness.
     *
     * @param input The string to shuffle.
     * @return The shuffled string.
     */
    private static String shuffleString(String input) {
	char[] characters = input.toCharArray();
	SecureRandom random = new SecureRandom();
	for (int i = 0; i < characters.length; i++) {
	    int randomIndex = random.nextInt(characters.length);
	    char temp = characters[i];
	    characters[i] = characters[randomIndex];
	    characters[randomIndex] = temp;
	}
	return new String(characters);
    }

    /**
     * Converts an object to a {@link BigDecimal}.
     *
     * @param value The object to convert.
     * @return The {@link BigDecimal} representation of the object.
     */
    public static BigDecimal toBigDecimal(Object value) {
	if (value == null) {
	    return null;
	}
	if (value instanceof BigDecimal bigDecimal) {
	    return bigDecimal;
	}
	if (value instanceof Double doubleValue) {
	    return BigDecimal.valueOf(doubleValue);
	}
	if (value instanceof Integer intValue) {
	    return BigDecimal.valueOf(intValue);
	}
	if (value instanceof Long longValue) {
	    return BigDecimal.valueOf(longValue);
	}
	throw new IllegalArgumentException("Unsupported type for conversion to BigDecimal: " + value.getClass());
    }

    /**
     * Converts an object to a {@link Double}.
     *
     * @param value The object to convert.
     * @return The {@link Double} representation of the object.
     */
    public static Double toDouble(Object value) {
	if (value == null) {
	    return null;
	}
	if (value instanceof Integer integerValue) {
	    return integerValue.doubleValue();
	}
	if (value instanceof Double doubleValue) {
	    return doubleValue;
	}
	throw new IllegalArgumentException("Unsupported data type: " + value.getClass());
    }

    /**
     * Checks if all elements in the array are null starting from index 2.
     *
     * @param row The array to check.
     * @return True if all elements are null, otherwise false.
     */
    public static boolean isAllNull(Object[] row) {
	for (int i = 2; i < row.length; i++) {
	    if (row[i] != null) {
		return false;
	    }
	}
	return true;
    }
}
