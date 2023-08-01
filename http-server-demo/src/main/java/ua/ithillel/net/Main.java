package ua.ithillel.net;

import ua.ithillel.net.server.MyHttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (MyHttpServer myHttpServer = new MyHttpServer(8000)) {
            myHttpServer.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}