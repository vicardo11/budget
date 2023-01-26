package it.sosinski.accountbalance.configuration;

import org.springframework.beans.factory.annotation.Value;

public class UriConstants {

    @Value(value = "${server.port}")
    private static int port;

    private UriConstants() {}

    public static final String URI_EXPENSES = "/expenses";
    public static final String URI_LIST = "/list";
    public static final String URI_CREATE = "/create";
    public static final String URI_EXPENSES_LIST = URI_EXPENSES + URI_LIST;
    public static final String URI_EXPENSES_CREATE = URI_EXPENSES + URI_CREATE;

    /**
     * URIs for testing purposes
     * LH at the beginning stands for LocalHost
     * */
    public static String LH_URI_EXPENSES_LIST = makeLocalhost(URI_EXPENSES_LIST);
    public static String LH_URI_EXPENSES_CREATE = makeLocalhost(URI_EXPENSES_CREATE);

    private static String makeLocalhost(String uri) {
        return String.format("%s:%d/%s", "http://localhost", port, uri);
    }
}
