package controlador;

import java.util.Date;
import java.util.Map;

public class RatioConversion {
    private final String base;
    private final Date date;
    private final Map<String, Double> rates;

    public RatioConversion(String base, Date date, Map<String,  Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }
    
    public double getRatio(String clave){
        return rates.get(clave);
    }
}
