package ru.ilka.chat.common;

public class MessageImplementation implements Message {
    private final String nickName;
    private final String content;
    private final int type;

    public MessageImplementation(String nickName, String content, int type) {
        this.nickName = nickName;
        this.content = content;
        this.type = type;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MessageImplementation{" +
                "nickName='" + nickName + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
