package backend.api.request;

import backend.data.CryptoInfo;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class BuildRequest {

    private static final String URL = "https://api.kraken.com/0/public/Ticker?pair=%s";

    private static final Map<String, CryptoInfo> CRYPTOCURRENCY_MAP = new HashMap<>();

    static {
        CRYPTOCURRENCY_MAP.put("XXBTZUSD", new CryptoInfo("Bitcoin", "BTC"));
        CRYPTOCURRENCY_MAP.put("XETHZUSD", new CryptoInfo("Ethereum", "ETH"));
        CRYPTOCURRENCY_MAP.put("XXRPZUSD", new CryptoInfo("XRP", "XRP"));
        CRYPTOCURRENCY_MAP.put("USDTZUSD", new CryptoInfo("Tether", "USDT"));
        CRYPTOCURRENCY_MAP.put("NOSUSD", new CryptoInfo("Nosana", "NOSUSD"));
        CRYPTOCURRENCY_MAP.put("SOLUSD", new CryptoInfo("Solana", "SOL"));
        CRYPTOCURRENCY_MAP.put("AAVEUSD", new CryptoInfo("Aave", "AAVE"));
        CRYPTOCURRENCY_MAP.put("LUNA2USD", new CryptoInfo("Luna", "UNC"));
        CRYPTOCURRENCY_MAP.put("ADAUSD", new CryptoInfo("Cardano", "ADA"));
        CRYPTOCURRENCY_MAP.put("TRXUSD", new CryptoInfo("TRON", "TRX"));
        CRYPTOCURRENCY_MAP.put("LINKUSD", new CryptoInfo("Chainlink", "LINK"));
        CRYPTOCURRENCY_MAP.put("XXLMZUSD", new CryptoInfo("Stellar", "XLM"));
        CRYPTOCURRENCY_MAP.put("SUIUSD", new CryptoInfo("Sui", "SUI"));
        CRYPTOCURRENCY_MAP.put("AVAXUSD", new CryptoInfo("Avalanche", "AVAX"));
        CRYPTOCURRENCY_MAP.put("XLTCZUSD", new CryptoInfo("Litecoin", "LTC"));
        CRYPTOCURRENCY_MAP.put("CPOOLUSD", new CryptoInfo("Clearpool", "CPOOL"));
        CRYPTOCURRENCY_MAP.put("SHIBUSD", new CryptoInfo("Shiba Inu", "SHIB"));
        CRYPTOCURRENCY_MAP.put("TONUSD", new CryptoInfo("Toncoin", "TON"));
        CRYPTOCURRENCY_MAP.put("MKRUSD", new CryptoInfo("Maker", "MKRUSD"));
        CRYPTOCURRENCY_MAP.put("SIGMAUSD", new CryptoInfo("SIGMA", "SIGMA"));
    }

    public static HttpRequest getRequest() {
        String keys = String.join(",", CRYPTOCURRENCY_MAP.keySet());
        String newUrl = String.format(URL, keys);

        return HttpRequest.newBuilder()
            .uri(URI.create(newUrl))
            .build();
    }

    public static Map<String, CryptoInfo> getCryptocurrencyMap() {
        return CRYPTOCURRENCY_MAP;
    }

}
