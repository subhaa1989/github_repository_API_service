package org.redcare.coding.httprequest;


import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.redcare.coding.exception.ExternalProviderAPICallFailedException;
import org.redcare.coding.exception.ExternalProviderForbiddenAPICallException;
import org.redcare.coding.exception.ExternalProviderServiceUnavailableException;
import org.redcare.coding.exception.TooManyRequestsPerSecondException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@EqualsAndHashCode
@Component
public class HttpRequestHandler implements Serializable {

    @Serial
    private static final long serialVersionUID = 2552667716995704259L;

    public HttpResponse<String> sendRequest(URI uri) throws IOException, InterruptedException {

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_2).GET().uri(uri).build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            switch (statusCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    throw new ExternalProviderAPICallFailedException(
                            "Bad Request : One or more parameters were incorrectly specified in query "
                                    // hiding license key in logs
                                    + uri.getQuery().replaceFirst("key=.*", "key=???")
                                    + "\nResponse details: "
                                    + response.body());
                case HttpURLConnection.HTTP_FORBIDDEN:
                    log.error("Forbidden details: {}", response.body());
                    throw new ExternalProviderForbiddenAPICallException(
                            "Forbidden : Not authorized / Rate or volume limit exceeded / Unknown referer");
                case 429: // no constant available
                    throw new TooManyRequestsPerSecondException("Too Many Requests : The API Key is over QPS");
                case 428: // no constant available
                    throw new ExternalProviderServiceUnavailableException("Invalid token/key", response.body());
                case HttpURLConnection.HTTP_INTERNAL_ERROR,
                        HttpURLConnection.HTTP_NOT_IMPLEMENTED,
                        HttpURLConnection.HTTP_BAD_GATEWAY,
                        HttpURLConnection.HTTP_UNAVAILABLE,
                        HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
                        HttpURLConnection.HTTP_VERSION,
                        HttpURLConnection.HTTP_UNAUTHORIZED:
                    throw new ExternalProviderServiceUnavailableException(
                            "Server Error : The service was unable to process your request. Contact support to resolve the issue.",
                            response.body());
                case HttpURLConnection.HTTP_OK:
                    return response;
                default:
                    throw new RuntimeException(
                            "Response status " + statusCode + " not OK for request " + uri.getQuery());
            }
        }
        catch (Exception e){
            log.error("Exception in sendRequest {}", e.getMessage());
            throw new ExternalProviderAPICallFailedException(e.getMessage());
        }
    }
}
