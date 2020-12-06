package common;

public class Post {

    private String title;
    private String text;

    public Post() {}

    public Post(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
