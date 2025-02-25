package backend.data;

import backend.api.krakenapi.KrakenApiClient;
import backend.exception.ApiException;

import java.net.http.HttpClient;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InMemoryDataRepository implements DataRepository {

    private static final int PERIOD = 30;
    private static final int START_AFTER = 30;

    private final KrakenApiClient krakenApiClient;
    private CacheData cacheData;
    private final ScheduledExecutorService executor;

    public InMemoryDataRepository() {
        this.krakenApiClient = new KrakenApiClient(HttpClient.newBuilder().build());
        try {
            this.cacheData = krakenApiClient.getResponse();
        } catch (ApiException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        this.executor = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public CacheData getCacheData() {
        return cacheData;
    }

    @Override
    public void startScheduleAtFixedRate() {
        executor.scheduleAtFixedRate(() -> {
            try {
                cacheData = krakenApiClient.getResponse();
            } catch (ApiException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }, START_AFTER, PERIOD, TimeUnit.SECONDS);
    }

}
