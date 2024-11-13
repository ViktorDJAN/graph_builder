package ru.promelectronika.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerHttp {
    private static HttpServer server;

    public static void startHttpServer(String host, int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(host, port), 0);
        server.createContext("/", new RequestHandler1());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at " + host + ":" + port);
    }

    static class RequestHandler1 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers headersResponse = exchange.getResponseHeaders();
            headersResponse.add("Access-Control-Allow-Origin",exchange.getRequestHeaders().get("Origin").get(0));
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    public static HttpServer getServer() {
        return server;
    }

    public static void setServer(HttpServer server) {
        ServerHttp.server = server;
    }
}