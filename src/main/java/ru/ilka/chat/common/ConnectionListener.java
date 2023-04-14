package ru.ilka.chat.common;

public interface ConnectionListener {

    public void connectionCreated(Connection c);

    public void connectionClosed(Connection c);

    public void connectionException(Connection c,Exception ex);

    public void  recievedContent(Message msg);
}
