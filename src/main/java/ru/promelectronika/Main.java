package ru.promelectronika;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.System.in;

public class Main {
    private static String typeConnector = "";
    private static double maxInputW;
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 11000), 0);
        server.createContext("/hello", new MyHandler1());
        server.createContext("/post_data", new MyHandler2());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started at " + server.getAddress());
    }

    static class MyHandler1 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            System.out.println("GOT IN the method");
            String response = "<h1>Hello</h1>";
            exchange.sendResponseHeaders(200, response.length());//response code and length
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes());
            responseBody.close();

            if (!exchange.getResponseHeaders().isEmpty()) {
                System.out.println("NyHandler1 has a response");
            } else {
                System.out.println("NyHandler1 doesn't have any response");
            }

        }
    }

    static class MyHandler2 implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String requestedMessage = "";
            System.out.println("MyHandle2 is : " + t.getRequestMethod() + " " + t.getRequestURI());
            if (t.getRequestMethod().equals("POST")) {
                requestedMessage = getString(t.getRequestBody());
                System.out.println( "Gotten message "+ requestedMessage);
                t.getRequestBody().close();
            } else {
                System.err.println("Sorry! It is not Post Method");
            }
            // In here is forming headers that we send if everything is ok
            Headers responseHeaders = t.getResponseHeaders(); // create response header
            responseHeaders.add("Content-Type", "text/plain"); // specify type and content info
            String response = "Got the message: \n" + requestedMessage; // form response
            t.sendResponseHeaders(200, response.length()); // send response headers
            OutputStream os = t.getResponseBody(); // returns output stream to which the response body must be written.
            os.write(response.getBytes()); // cast it to bytes
            os.close(); // close stream


        }

    }

    public static String getString(InputStream in) {
        // pass in here Stream of request body , process it , get a string
        return new Scanner(in, StandardCharsets.UTF_8)
                .useDelimiter("\\z") // what you want to use as like delimiter
                .next();
    }

}

