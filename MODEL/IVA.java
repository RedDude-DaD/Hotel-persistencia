package MODEL;

/**
 * @author radum
 */
public enum IVA {

    ESPANYA(1.16),
    FRANCA(1.08),
    ANDORRA(1.00),
    PORTUGAL(1.12);

    private double IVAPais;
    
    private IVA(double IVAPais){
        this.IVAPais = IVAPais;
    }

    public double showIVA() {
        return IVAPais;
    }
    
    
    
}
