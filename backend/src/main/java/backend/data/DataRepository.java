package backend.data;

public interface DataRepository {

    CacheData getCacheData();

    void startScheduleAtFixedRate();

}
