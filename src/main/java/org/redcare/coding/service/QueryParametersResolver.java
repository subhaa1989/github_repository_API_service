package org.redcare.coding.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.redcare.coding.exception.ExternalProviderAPICallFailedException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;


@Slf4j
@Component
public class QueryParametersResolver {


    public static final String SORT = "SORT=";
    public static final String ORDER = "ORDER=";
    /**
     * And PER_PAGE contains max_limit(if<100)
     * Github API only supports max 100 entries to be return at a time
     * More records can be retreived using pagination, using parameter page=$(page_number) and total_count
     */
    public static final String PER_PAGE = "per_page=";
    private final String LANGUAGE = "language";
    private final String CREATED = "created";


    public URI getRequestURI(final String baseUrl, final String language, int maxLimit, final LocalDate fromDate) {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            var queryBuilder = new StringBuilder();
            queryBuilder.append("q=");
            if (fromDate != null) {
                queryBuilder.append(CREATED + ":").append(fromDate)
                        .append("+");
            }
            if (language != null) {
                queryBuilder.append(LANGUAGE + ":").append(language);
            }
            queryBuilder.append("&")
                    .append(SORT)
                    .append("stars")
                    .append("&");
            if (maxLimit != 0) {
                queryBuilder.append(PER_PAGE)
                        .append(maxLimit);
            }

            queryBuilder.append("&")
                    .append(ORDER)
                    .append("desc");
            var query = queryBuilder.toString();
            log.info("Request {}", query);
            return uriBuilder.setCustomQuery(query).build();
        } catch (URISyntaxException e) {
            log.error("Exception in sendRequest {}", e.getMessage());
            throw new ExternalProviderAPICallFailedException(e.getMessage());
        }
    }
}
