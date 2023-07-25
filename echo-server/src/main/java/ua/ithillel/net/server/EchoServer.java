package ua.ithillel.net.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class EchoServer implements AutoCloseable {
    private final ServerSocket serverSocket;

    public EchoServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = this.serverSocket.accept();

               new Thread(() -> {
                   try (OutputStream outputStream = socket.getOutputStream()) {
                       PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));

                       printWriter.println("Echo from server!");
                       printWriter.printf("Date and time: %s\n", LocalDateTime.now());
                       printWriter.flush();
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }).start();


            } catch (IOException e) {
                System.out.println("Error when handling connection");
            }
        }
    }

    @Override
    public void close() throws Exception {
        serverSocket.close();
    }
}
