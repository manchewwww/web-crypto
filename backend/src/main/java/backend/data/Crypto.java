package backend.data;

public record Crypto(String name, String symbol, double currentPrice) {

    public static Crypto of(String name, String symbol, double currentPrice) {
        return new Crypto(name, symbol, currentPrice);
    }

}
