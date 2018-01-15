package controlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import model.Currency;
import model.ExchangeRate;
import model.Money;
import view.Ventana;

public class Controlador {
    
    private ExchangeRate exchangeRate;
    private Money dinero;
    private Currency from;
    private Currency to;
    
    private final Ventana vista;

    public Controlador(Ventana vista) {
        this.vista = vista;
        vista.setControlador(this);
    }
    
    public void execute(Money dinero, Currency from, Currency to) throws Exception{
        this.dinero = dinero;
        this.from = from;
        this.to = to;
        
        process();
        output();
    }

    private void process() throws Exception {
        exchangeRate = getExchangeRate(from, to);
        
    }
    
    private void output(){
        DecimalFormat df = new DecimalFormat("#.00");
        double resultado = dinero.getAmount() * exchangeRate.getRate();
        vista.setTextResultado(df.format(resultado) + to.getSymbol());
    }

    private ExchangeRate getExchangeRate(Currency from, Currency to) throws Exception {
        URL url = new URL("http://api.fixer.io/latest?base=" + from.getCode() + "&symbols=" + to.getCode());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        try (BufferedReader reader = new BufferedReader(input)) {
            String line = reader.readLine();
            line = line.substring(line.indexOf(to.getCode()) + 5, line.indexOf("}"));
            return new ExchangeRate(new Date(), from, to, Double.parseDouble(line));
        }
    }
}
