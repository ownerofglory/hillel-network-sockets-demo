package ua.ithillel.net;

import ua.ithillel.net.server.EchoServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {


        try (EchoServer echoServer = new EchoServer(8000)) {
            echoServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}