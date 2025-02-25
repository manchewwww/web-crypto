package backend.api.factory;

import backend.api.request.BuildRequest;
import backend.data.CacheData;
import backend.data.Crypto;
import backend.data.CryptoInfo;
import backend.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatusCodeFactory {

    private static final String RESULT = "result";
    private static final String UNEXPECTED_RESPONSE_CODE_EXCEPTION_MESSAGE =
        "Unexpected response code from Kraken Api";

    private static final int OK = 200;
    private static final Gson GSON = new Gson();

    public static CacheData parseElements(HttpResponse<String> response) throws ApiException {
        if (response.statusCode() == OK) {
            JsonObject jsonObject = GSON.fromJson(response.body(), JsonObject.class);
            JsonObject result = jsonObject.getAsJsonObject(RESULT);
            List<Crypto> cryptos = new ArrayList<>();
            Map<String, CryptoInfo> cryptoInfos = BuildRequest.getCryptocurrencyMap();
            for (Map.Entry<String, CryptoInfo> entry : cryptoInfos.entrySet()) {
                String symbol = entry.getKey();
                CryptoInfo cryptoInfo = entry.getValue();
                if (!result.has(symbol)) {
                    System.err.println("Warning: No data for " + cryptoInfo.name());
                    continue;
                }
                JsonObject tickerData = result.getAsJsonObject(symbol);
                if (!tickerData.has("c") || !tickerData.getAsJsonArray("c").isJsonArray() ||
                    tickerData.getAsJsonArray("c").size() == 0) {
                    System.err.println("Error: Missing or malformed 'c' field for " + cryptoInfo.name());
                    continue;
                }

                double price = tickerData.getAsJsonArray("c").get(0).getAsDouble();

                cryptos.add(Crypto.of(cryptoInfo.name(), cryptoInfo.symbol(), price));
            }

            return new CacheData(cryptos);
        } else {
            throw new ApiException(UNEXPECTED_RESPONSE_CODE_EXCEPTION_MESSAGE);
        }
    }

}
