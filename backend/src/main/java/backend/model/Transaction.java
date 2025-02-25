package backend.model;

import java.time.LocalDateTime;

public record Transaction(TransactionType type, String cryptocurrency, LocalDateTime date, double quantity,
                          double cryptocurrencyPrice) {

    public static Transaction of(TransactionType type, String cryptocurrency, LocalDateTime date, double quantity,
                                 double cryptocurrencyPrice) {
        return new Transaction(type, cryptocurrency, date, quantity, cryptocurrencyPrice);
    }

}
