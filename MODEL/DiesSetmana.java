package MODEL;

/**
 * @author radum
 */
public enum DiesSetmana {

    DILLUNS(true),
    DIMARTS(true),
    DIMECRES(true),
    DIJOUS(true),
    DIVENDRES(true),
    DISSAPTE(false),
    DIUMENGE(false);

    private boolean laborable;

    private DiesSetmana(boolean laborable) {
        this.laborable = laborable;
    }

    @Override
    public String toString() {

        if (laborable) {
            return this.name().toLowerCase() + ": laborable ";
        } else {
            return this.name().toLowerCase() + ": festiu ";
        }
    }

}
