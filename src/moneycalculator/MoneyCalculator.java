package moneycalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoneyCalculator {

    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
    private Map<String, Currency> currencies = new HashMap<>();
    private Money money;
    private Currency currencyTo;
    private double exchangeRate;

    public MoneyCalculator() {
        currencies.put("USD", new Currency("USD", "Dolar americano", "$"));
        currencies.put("EUR", new Currency("EUR", "Euro", "€"));
    }

    private void execute() throws Exception {
        input();
        process();
        output();
    }

    private void input() {
        System.out.println("Introduzca cantidad");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());

        System.out.println("Introduzca código divisa origen");
        Currency currency = currencies.get(scanner.next().toUpperCase());

        money = new Money(amount, currency);

        System.out.println("Introduzca código divisa destino");
        currencyTo = currencies.get(scanner.next().toUpperCase());
    }

    private void process() throws Exception {
        exchangeRate = getExchangeRate(money.getCurrency().getCode(), currencyTo.getCode());
    }

    private void output() {
        System.out.println(money.getAmount() + money.getCurrency().getSymbol() + " equivalen a " + money.getAmount() * exchangeRate + currencyTo.getSymbol());
    }

    private static double getExchangeRate(String from, String to) throws Exception {
        URL url = new URL("http://api.fixer.io/latest?base=" + from + "&symbols=" + to);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        try (BufferedReader reader = new BufferedReader(input)) {
            String line = reader.readLine();
            line = line.substring(line.indexOf(to) + 5, line.indexOf("}"));
            return Double.parseDouble(line);
        }
    }
}
