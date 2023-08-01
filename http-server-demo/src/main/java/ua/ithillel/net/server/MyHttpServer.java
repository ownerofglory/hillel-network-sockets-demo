package ua.ithillel.net.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyHttpServer implements AutoCloseable {
    private static final int DEFAULT_PORT = 8080;

    private final ExecutorService executor;
    private final ServerSocket serverSocket;

    public MyHttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(4);
    }

    public MyHttpServer() throws IOException {
        this(DEFAULT_PORT);
    }

    public void start() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();

                Runnable task = serveRequestAsync(socket);
                executor.submit(task);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Runnable serveRequestAsync(Socket socket) {
        return () -> serveRequest(socket);
    }

    private void serveRequest(Socket socket) {
        try (socket;
             BufferedReader bufferedReader
                     = new BufferedReader(new InputStreamReader(socket.getInputStream()));

             PrintWriter printWriter
                     = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            // here it goes...
            String firstLine = bufferedReader.readLine();
            String[] firstLineParts = firstLine.split(" ");

            String resourcePath = firstLineParts[1];
            String filePath = resourcePath;
            if (resourcePath.equals("/")) {
                filePath = "/index.html";
            }

            filePath = filePath.replace("/" , "");


            String line = bufferedReader.readLine();
//            while (!line.equals("")) {
//                System.out.println(line);
//                line = bufferedReader.readLine();
//            }


            String fileContent = readFileContent(filePath);

            StringBuffer response = new StringBuffer().append("HTTP/1.1 200 OK\r\n")
                    .append("Content-Type: text/html\r\n")
                    .append("\r\n")
                    .append(fileContent);

            printWriter.println(response);
            printWriter.flush();

            System.out.println();
        } catch (IOException e) {
            System.out.println("Serving request failed");
        }
    }

    private String readFileContent(String filePath) {
        try (
                InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {

            Optional<String> reduce = br.lines().reduce((acc, line) -> acc + line);

            return reduce.orElse("");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        serverSocket.close();
        executor.shutdownNow();
    }
}
