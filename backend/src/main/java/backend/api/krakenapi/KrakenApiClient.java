package backend.api.krakenapi;

import backend.api.factory.StatusCodeFactory;
import backend.api.request.BuildRequest;
import backend.data.CacheData;
import backend.exception.ApiException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KrakenApiClient {

    private static final String REQUEST_INTERRUPTED_EXCEPTION_MESSAGE = "Request interrupted";
    private static final String REQUEST_IO_EXCEPTION_MESSAGE = "I/O exception";

    private final HttpClient httpClient;

    public KrakenApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CacheData getResponse() throws ApiException {
        HttpResponse<String> response;
        HttpRequest request = BuildRequest.getRequest();

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ApiException(REQUEST_IO_EXCEPTION_MESSAGE, e);
        } catch (InterruptedException e) {
            throw new ApiException(REQUEST_INTERRUPTED_EXCEPTION_MESSAGE, e);
        }

        return StatusCodeFactory.parseElements(response);
    }

}
