package ru.ilka.chat.server;

import java.io.IOException;

public class ServerStarter {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }
}
