package com.main;

import com.main.exceptions.BadRequestException;
import com.main.request.HttpMethod;
import com.main.request.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestParserTest {

    @Test
    void testInjectUriAndMethodSetsGetMethodToRequest() throws BadRequestException {
        Request request = new Request();
        String testLine = "GET /test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void testInjectUriAndMethodSetsPostMethodToRequest() throws BadRequestException {
        Request request = new Request();
        String testLine = "POST /test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals(HttpMethod.POST, request.getMethod());
    }

    @Test
    void testInjectUriAndMethodSetsUriToRequest() throws BadRequestException {
        Request request = new Request();
        String testLine = "GET /test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals("/test", request.getUri());
    }

    @Test
    void testInjectUriAndMethodSetsUriWithFoldersToRequest() throws BadRequestException {
        Request request = new Request();
        String testLine = "GET /folder/anotherfolder/test HTTP/1.1";

        RequestParser.injectUriAndMethod(testLine, request);

        assertEquals("/folder/anotherfolder/test", request.getUri());
    }

    @Test
    void testInjectUriAndMethodSetsEmptyUriToRequest() throws BadRequestException {
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
