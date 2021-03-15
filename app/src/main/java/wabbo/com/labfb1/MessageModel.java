package wabbo.com.labfb1;

public class MessageModel {
    private String body, title;

    public MessageModel(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public MessageModel() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
