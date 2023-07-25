package ua.ithillel.net;

import ua.ithillel.net.server.EchoServer;

import java.io.IOException;

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