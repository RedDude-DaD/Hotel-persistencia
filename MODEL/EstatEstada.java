package MODEL;

/**
 * @author radum
 */
public enum EstatEstada {

    ENCURS,
    ACABADA;

    public static String show(int i) {

        if (i == 1) {
            return "acabada";
        } else {
            return "en curs";
        }
    }
}
