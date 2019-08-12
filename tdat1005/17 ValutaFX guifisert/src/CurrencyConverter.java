import java.util.ArrayList;
import java.util.Collections;

public class CurrencyConverter {

    private ArrayList<Currency> currencies;

    private static final Currency[] initialCurrencies = {
            new Currency("Euro", 8.10, 1), new Currency("US Dollar", 6.23, 1),
            new Currency("Britiske pund", 12.27, 1), new Currency("Svenske kroner", 88.96, 100),
            new Currency("Danske kroner", 108.75, 100), new Currency("Yen", 5.14, 100),
            new Currency("Islandske kroner", 9.16, 100), new Currency("Norske kroner", 100, 100)
    };

    public CurrencyConverter() {

        currencies = new ArrayList<>();
        Collections.addAll(currencies, initialCurrencies); // appending initial list

    }

    public ArrayList<Currency> getCurrencies() { // simple aggregation
        return currencies;
    }

    public boolean addCurrency(Currency currency) {

        if (indexOf(currency.getName()) < 0) {

            currencies.add(currency);
            return true;

        }

        return false;
    }

    private int indexOf(String name) {

        if (name == null) return -2;

        for (int i = 0; i < currencies.size(); i++) {

            if (currencies.get(i).getName().equals(name)) {
                return i;
            }

        }

        return -1;

    }

    public static void main(String[] args) {
        CurrencyConverter currencyConverter = new CurrencyConverter();

        double res = currencyConverter.currencies.get(0).convert(100, currencyConverter.currencies.get(0));
        System.out.println(res);
        res = currencyConverter.currencies.get(7).convert(108.75, currencyConverter.currencies.get(4));
        System.out.println(res);
        res = currencyConverter.currencies.get(7).convert(100, currencyConverter.currencies.get(7));
        System.out.println(res);
    }

}
