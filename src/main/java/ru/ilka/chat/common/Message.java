package ru.ilka.chat.common;

import java.io.Serializable;

public interface Message extends Serializable {
    public int CLOSE_TYPE = 0;
    public int CONTENT_TYPE = 1;

    public String getNickName();

    public String getContent();

    public int getType();
}
