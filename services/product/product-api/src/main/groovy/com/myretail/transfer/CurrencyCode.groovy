package com.myretail.transfer

enum CurrencyCode {

    USD('USD'),
    KRONA('Krona'),
    BIRR('Birr')

    String id

    CurrencyCode(String id) {
        this.id = id
    }

    public String toString() {
        id
    }

    public static CurrencyCode fromString(String value) {
        this.values().find { it.id.toLowerCase() == value.toLowerCase() }
    }

}
