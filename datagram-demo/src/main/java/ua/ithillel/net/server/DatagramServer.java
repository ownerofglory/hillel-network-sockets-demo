package ua.ithillel.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.time.LocalDateTime;

public class DatagramServer implements AutoCloseable {
    private final DatagramSocket datagramSocket;

    public DatagramServer(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    public void start() {
        while (!datagramSocket.isClosed()) {

            try {
                byte[] buffer = new byte[128];
                DatagramPacket clientDatagram = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(clientDatagram);

                new Thread(() -> {
                    try {
                        int clientPort = clientDatagram.getPort();
                        InetAddress clientHost = clientDatagram.getAddress();

                        byte[] data = clientDatagram.getData();
                        String dataStr = new String(data);
                        System.out.printf("Client %s:%d - %s", clientHost, clientPort, dataStr);

                        byte[] response = LocalDateTime.now().toString().getBytes();

                        DatagramPacket serverDatagram = new DatagramPacket(response, response.length, clientHost, clientPort);

                        datagramSocket.send(serverDatagram);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }).start();

            } catch (IOException e) {
                break;
            }
        }
    }


    @Override
    public void close() throws Exception {
        datagramSocket.close();
    }
}
