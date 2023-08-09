package marketplace.objects;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Message {
    private int messageId;
    private int fromId;
    private int toId;
    private String content;
    private Timestamp sendTime;

    public Message(int messageId, int fromId, int toId, String content, Timestamp sendTime) {
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
        this.sendTime = sendTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSendTime() {
        return  sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}