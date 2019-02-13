package ua.solomenko.webserver.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private String webAppPath;

    public Server(int port, String webAppPath) {
        this.port = port;
        this.webAppPath = webAppPath;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(3000, "resources/webapp");
        server.start();
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept() ;
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.setReader(reader);
                    requestHandler.setWriter(writer);
                    requestHandler.setWebAppPath(webAppPath);
                    try {
                        requestHandler.handle();
                    } catch (Exception e) {
                        System.out.println("Something bad happen, but we still alive");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}