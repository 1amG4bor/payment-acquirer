package com.g4bor.payment.acquirer.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Currency {
    AUD("Australian dollar", "AUD", "$", "Cent", 100),
    CAD("Canadian dollar", "CAD", "$", "Cent", 100),
    CHF("Swiss franc", "CHF", "Fr", "Rappen", 100),
    CNY("Chinese yuan", "CNY", "¥", "Jiao", 10),
    CZK("Czech koruna", "CZK", "Kč", "Haléř", 100),
    DKK("Danish krone", "DKK", "kr", "Øre", 100),
    EUR("Euro", "EUR", "€", "cent", 100),
    HUF("Hungarian forint", "HUF", "Ft", "Fillér", 100),
    JPY("Japanese yen", "JPY", "¥", "Sen", 100),
    GBP("British pound", "GBP", "£", "Penny", 100),
    RON("Romanian leu", "RON", "lei", "Ban", 100),
    RUB("Russian ruble", "RUB", "₽", "Kopek", 100),
    SEK("Swedish krona", "SEK", "kr", "Öre", 100),
    USD("United States Dollar", "USD", "$", "cent", 100);

    private final String name;
    private final String isoCode;
    private final String symbol;
    private final String fractionalUnit;
    private final int numToBasic;

    Currency(String name, String isoCode, String symbol, String fractionalUnit, int numToBasic) {
        this.name = name;
        this.isoCode = isoCode;
        this.symbol = symbol;
        this.fractionalUnit = fractionalUnit;
        this.numToBasic = numToBasic;
    }

    private static final Map<String, Currency> currencyMap = Arrays.stream(values())
            .collect(Collectors.toMap(Currency::getIsoCode, currency -> currency));

    public static Currency resolve(String isoCode) {
        return currencyMap.get(isoCode);
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFractionalUnit() {
        return fractionalUnit;
    }

    public int getNumToBasic() {
        return numToBasic;
    }
}
