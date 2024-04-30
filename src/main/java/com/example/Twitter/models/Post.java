package com.example.Twitter.models;
import jakarta.persistence.*;
import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postID;
    private String postBody;
    private String date;
    private int userID;

//    @ManyToOne
//    @JoinColumn(name="userID")
//    private User user;
//
//
    @ManyToOne
    private Users user;

    @OneToMany(mappedBy = "post")
    public List<Comment> comments;// = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int getUserID() {
        return userID;
    }


    public int getPostID() {
        return postID;
    }

    public String getPostBody() {
        return postBody;
    }
    public String getDate() {
        return date;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setDate( Date rightNowdate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(rightNowdate);
        this.date = dateString;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}


