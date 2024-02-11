package org.redcare.coding.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redcare.coding.utils.DateUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryParametersResolverTest {
    QueryParametersResolver queryParametersResolver;
    final String baseUrl = "https://api.github.com/search/repositories";

    @BeforeEach
    public void setup() {
        queryParametersResolver = new QueryParametersResolver();
    }

    @Test
    public void whenDateAndLimitIsGiven_thenTheyAreRespected() {
    var uri1 =
        "https://api.github.com/search/repositories?q=created:2023-01-01+language:&SORT=stars&per_page=2&ORDER=desc";
        assertEquals(uri1, queryParametersResolver.getRequestURI(baseUrl,
                "", 2, DateUtils.toDate("2023-01-01")).toString());

    }

    @Test
    public void whenLanguageIsGiven_thenTheyAreRespected() {
        var uri1 = "https://api.github.com/search/repositories?q=created:2023-01-01+language:java&SORT=stars&per_page=2&ORDER=desc";
        assertEquals(uri1, queryParametersResolver.getRequestURI(baseUrl,
                "java", 2, DateUtils.toDate("2023-01-01")).toString());

    }


    @Test
    public void whenEmptyParametersAreGiven_thenUrlIsStillValid() {
        // Note that will not happen when using RESt controller, as w ehave default values for those fields
        var uri1 = "https://api.github.com/search/repositories?q=&SORT=stars&per_page=2&ORDER=desc";
        assertEquals(uri1, queryParametersResolver.getRequestURI(baseUrl,
                null, 2, null).toString());

    }

}