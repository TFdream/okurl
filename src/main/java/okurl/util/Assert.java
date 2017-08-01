package okurl.util;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public abstract class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void notEmpty(String text, String message) {
        if (!StringUtils.isNotEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String text) {
        notEmpty(text,
                "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static void notBlank(String text, String message) {
        if (!StringUtils.isNotBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String text) {
        notBlank(text,
                "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

}
