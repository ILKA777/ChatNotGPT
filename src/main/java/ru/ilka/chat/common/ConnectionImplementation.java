package ru.ilka.chat.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ConnectionImplementation implements Connection, Runnable {

    private Socket socket;
    private ConnectionListener connectionListener;

    private static final Logger LOGGER = LogManager.getLogger(ConnectionImplementation.class);
    private boolean needToRun = true;

    private OutputStream out;

    public ConnectionImplementation(Socket socket, ConnectionListener connectionListener) {
        try {
            this.socket = socket;
            this.connectionListener = connectionListener;
            out = socket.getOutputStream();
            Thread t = new Thread(this);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        } catch (Exception ex) {
            LOGGER.error("Error creating ConnectionImplementation", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void send(Message msg) {
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(msg);
            LOGGER.info("Message was sent");
        } catch (Exception ex) {
            LOGGER.error("Error sending message", ex);
        }
    }

    @Override
    public void close() {
        needToRun = false;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (needToRun) {
            try {
                InputStream in = socket.getInputStream();
                int amount = in.available();
                if (amount != 0) {
                    ObjectInputStream objIn = new ObjectInputStream(in);
                    Message msg = (Message) objIn.readObject();
                    connectionListener.recievedContent(msg);

                } else {
                    Thread.sleep(200);
                }

            } catch (Exception ex) {
                LOGGER.error("Error in ConnectionImplementation run method", ex);
                break;
            }
        }
    }
}