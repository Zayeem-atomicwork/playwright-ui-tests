package utilities;

import java.util.Objects;

/**
 * The class {@code ConvertUtils} holds static methods required for type
 * conversion.
 */
public final class ConvertUtils {

    private ConvertUtils() {
    }

    /**
     * Method to convert string value to an integer (if possible). If the argument
     * is null, then {@link NullPointerException} is thrown.
     *
     * @param numStr: number string to be converted to integer.
     *
     * @return integer value after conversion from String.
     */
    public static int stringToInt(final String numStr) {
        return Integer.parseInt(Objects.requireNonNull(numStr, "numStr must not be null!"));
    }

    /**
     * Method to convert string value to a long integer (if possible). If the
     * argument is null, then {@link NullPointerException} is thrown.
     *
     * @param numStr: number string to be converted to integer.
     *
     * @return long value after conversion from String.
     */
    public static long stringToLong(final String numStr) {
        return Long.parseLong(Objects.requireNonNull(numStr, "numStr must not be null!"));
    }
}