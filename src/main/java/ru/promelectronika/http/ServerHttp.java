package ru.promelectronika.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.promelectronika.dto.DataBaseSimple;
import ru.promelectronika.dto.SentParamDto;
import ru.promelectronika.dto.ReceivedParamDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ServerHttp {
    public static String renderMe = "RENDER";
    private static HttpServer server;
    private static String typeConnector = "";
    private static double maxInputW;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void startHttpServer(String host, int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);
        server.createContext("/hello", new MyHandler1());
        server.createContext("/post_data", new MyHandler2());
        server.createContext("/get_data", new MyHandler3());
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
            HashMap<String, Double> hashMapData = new HashMap<>(); // send in Postman this => {"Johnson":2.0,"John":1.0,"Voltage":220.0,"Ampere":32}
            System.out.println("MyHandle2 is : " + t.getRequestMethod() + " " + t.getRequestURI());
            if (t.getRequestMethod().equals("POST")) {
                requestedMessage = getString(t.getRequestBody()); // getRequestBody is input stream
                t.getRequestBody().close();
            } else {
                System.err.println("Sorry! It is not Post Method");
            }
            if (!requestedMessage.isEmpty()) {
                hashMapData = mapper.readValue(requestedMessage, HashMap.class);
                System.out.println("Gotten in request msg: " + hashMapData.toString());
                System.err.println(hashMapData.get("Voltage"));

            } else {
                System.out.println("Sorry there is nothing to read out");
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    static class MyHandler3 implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers headersResponse = exchange.getResponseHeaders();
            headersResponse.add("Access-Control-Allow-Origin", exchange.getRequestHeaders().get("Origin").get(0));

            System.out.println("GOT IN the method");

            Map<String,Object> dataMap = new HashMap<>();

            if(!DataBaseSimple.getSentMsgDataBase().isEmpty()){
                SentParamDto sentParamDto =  DataBaseSimple.getSentMsgDataBase().pollFirst();
                dataMap.put("sentData", sentParamDto);


            }
            if(!DataBaseSimple.getReceivedMsgDataBase().isEmpty()){
                ReceivedParamDto receivedParamDto =  DataBaseSimple.getReceivedMsgDataBase().pollFirst();
                dataMap.put("receivedData", receivedParamDto);
            }

            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(dataMap);
            System.err.println("HERE"+response);
            exchange.sendResponseHeaders(200, response.length());//response code and length
            OutputStream responseBody = exchange.getResponseBody();// stream where response body must be written
            responseBody.write(response.getBytes());
            responseBody.close();
            System.out.println(exchange.getRequestHeaders().get("Origin"));


            if (!exchange.getResponseHeaders().isEmpty()) {
                System.out.println("NyHandler3 has a response" + exchange.getResponseHeaders().toString());
            } else {
                System.out.println("NyHandler3 doesn't have any response");
            }

        }
    }


    public static String getString(InputStream in) {
        // pass in here Stream of request body , process it , get a string
        return new Scanner(in, StandardCharsets.UTF_8)
                .useDelimiter("\\z") // what you want to use as like delimiter
                .next();
    }


    public static HttpServer getServer() {
        return server;
    }

    public static void setServer(HttpServer server) {
        ServerHttp.server = server;
    }
}
