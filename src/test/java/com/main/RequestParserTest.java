package com.main;

import com.main.exceptions.BadRequestException;
import com.main.exceptions.MethodNotAllowedException;
import com.main.server.request.HttpMethod;
import com.main.server.request.Request;
import com.main.server.request.RequestParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestParserTest {

    @Test
    void testInjectUriAndMethodSetsGetMethodToRequest() {
        Request request = new Request();
        String testLine = "GET /test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void testInjectUriAndMethodThrowsMethodNotAllowedExceptionWhenReceivingPostRequest() {
        Request request = new Request();
        String testLine = "POST /test HTTP/1.1";

        Exception exception = assertThrows(MethodNotAllowedException.class, () ->
                RequestParser.injectUriAndMethod(testLine, request));

        assertEquals("Method POST is not allowed", exception.getMessage());
    }

    @Test
    void testInjectUriAndMethodSetsUriToRequest() {
        Request request = new Request();
        String testLine = "GET /test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals("/test", request.getUri());
    }

    @Test
    void testInjectUriAndMethodSetsUriWithFoldersToRequest() {
        Request request = new Request();
        String testLine = "GET /folder/anotherfolder/test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals("/folder/anotherfolder/test", request.getUri());
    }

    @Test
    void testInjectUriAndMethodSetsEmptyUriToRequest() {
        Request request = new Request();
        String testLine = "GET / HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals("/", request.getUri());
    }

    @Test
    void testInjectUriAndMethodThrowsBadRequestExceptionWhePassingNull() {
        Request request = new Request();

        assertThrows(BadRequestException.class, () ->
                RequestParser.injectUriAndMethod(null, request));
    }

}
