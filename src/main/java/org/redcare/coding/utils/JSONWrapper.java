package org.redcare.coding.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redcare.coding.model.Response;

import java.io.IOException;
import java.net.URL;

public class JSONWrapper {

    /** read a JSoN obj **/
    public static <T> T get(URL url, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, type);
    }

    /** write as JSON obj **/
    public static String getJsonResponse(Response type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(type);
    }
}
