package okurl.util;

import java.nio.charset.StandardCharsets;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public abstract class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence str) {
        return str!=null && str.length()>0;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int strLen = cs.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsWhitespace(CharSequence str) {
        if (isEmpty(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        else {
            char baseChar = str.charAt(0);
            char updatedChar;
            if (capitalize) {
                updatedChar = Character.toUpperCase(baseChar);
            }
            else {
                updatedChar = Character.toLowerCase(baseChar);
            }
            if (baseChar == updatedChar) {
                return str;
            }
            char[] chars = str.toCharArray();
            chars[0] = updatedChar;
            return new String(chars, 0, chars.length);
        }
    }

    public static String[] split(String str, String delimiter) {
        if (isEmpty(str) || isEmpty(delimiter)) {
            return null;
        }
        int offset = str.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }
        String beforeDelimiter = str.substring(0, offset);
        String afterDelimiter = str.substring(offset + delimiter.length());
        return new String[] {beforeDelimiter, afterDelimiter};
    }

    public static String trimAllWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static byte[] stringToByteArray(String data) {
        return data.getBytes(StandardCharsets.UTF_8);
    }

    public static String byteArrayToString(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }
}
