package ua.ithillel.net.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

public class EchoClient {

    public String requestServer(String host, int port) {
        try(Socket socket = new Socket(host, port);
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ) {

            Optional<String> reduce = br.lines().reduce((line, acc) -> line + acc);

            return reduce.orElse("");
        } catch (UnknownHostException e) {
            System.out.println("Incorrect host: " + host);
        } catch (IOException e) {
            System.out.println("Erro when connecting to server");
        }
        return null;
    }

}
