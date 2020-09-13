package com.chat.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity // This tells Hibernate to make a table out of this class
@Table
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @NotBlank(message = "Please, input your message!")
    @Length(max = 2048, message = "Message too long")
    private String textMessage;
    private String filename;

    public String getFilename() {

        return filename;
    }

    public void setFilename(String filename) {
        //this.filename = filename333;
        this.filename = filename;
    }

    public Message() {
    }

    public User getAuthor() {
        return author;
    }
    public String getAuthorName() {
        return author.getUsername();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Message(User author, String textMessage) {
        this.author = author;
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {

        this.textMessage = textMessage;
    }
}