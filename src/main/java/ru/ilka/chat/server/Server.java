package ru.ilka.chat.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ilka.chat.common.Connection;
import ru.ilka.chat.common.ConnectionImplementation;
import ru.ilka.chat.common.ConnectionListener;
import ru.ilka.chat.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;
import java.util.Set;

public class Server implements ConnectionListener {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private final Set<Connection> connections;
    private final ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(Connection.PORT);
            connections = new LinkedHashSet<>();
        } catch (IOException ex) {
            LOGGER.error("Error creating ServerSocket", ex);
            throw new RuntimeException(ex);
        }
    }

    public void startServer() {
        LOGGER.info("Server Started");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                connectionCreated(new ConnectionImplementation(socket, this));
            } catch (IOException ex) {
                LOGGER.error("Error accepting new connection", ex);
            }
        }
    }

    @Override
    public synchronized void connectionCreated(Connection c) {
        connections.add(c);
        LOGGER.info("Connection was added");
    }

    @Override
    public synchronized void connectionClosed(Connection c) {
        connections.remove(c);
        c.close();
        LOGGER.info("Connection was closed");
    }

    @Override
    public synchronized void connectionException(Connection c, Exception ex) {
        connections.remove(c);
        c.close();
        LOGGER.error("Connection was closed", ex);
    }

    @Override
    public synchronized void recievedContent(Message msg) {
        for (Connection c : connections) {
            c.send(msg);
        }
    }
}
