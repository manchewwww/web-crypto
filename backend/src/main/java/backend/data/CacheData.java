package backend.data;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheData {

    private static final int MAX_ELEMENTS_FOR_LIST = 20;

    private final Map<String, Crypto> data;

    public CacheData(List<Crypto> data) {
        this.data = data.stream()
            .collect(Collectors.toMap(Crypto::symbol, cr -> cr));
    }

    public Map<String, Double> getPrices() {
        return data.values().stream()
            .collect(Collectors.toMap(Crypto::symbol, Crypto::currentPrice));
    }

    public List<Crypto> getDataForDisplaying() {
        return data.values().stream()
            .sorted(Comparator.comparing(Crypto::currentPrice).reversed())
            .limit(MAX_ELEMENTS_FOR_LIST)
            .toList();
    }

    public Map<String, Crypto> getData() {
        return data;
    }

}
