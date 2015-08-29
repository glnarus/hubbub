package edu.acc.j2ee.hubbub5;

import java.util.Date;

public class Post implements java.io.Serializable {
    private String content;
    private Date postDate;
    private User author;
    private int id;
    
    public Post(String content, Date postDate, User author) {
        this.content = content;
        this.postDate = postDate;
        this.author = author;
    }
    
    public Post(String content, Date postDate, User author, int id) {
        this(content, postDate, author);
        this.id = id;
    }
    
    public Post() {}

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }
    
    public int getId() {
        return id;
    }
    
    public User getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setAuthor(User author) {
        this.author = author;
    }
    
    @Override
    public String toString() {
        return String.format("%d characters posted by User %s on %s",
                content.length(), author, postDate);
    }
}