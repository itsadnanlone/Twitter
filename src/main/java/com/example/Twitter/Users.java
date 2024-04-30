package com.example.Twitter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

// userID, name, email, password, posts
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;
    //private Integer userID;
    private String email;
    private String password;
    private String name;


     @OneToMany(mappedBy = "user")
     public List<Post> posts;


     @OneToMany(mappedBy = "user")
     public List<Comment> comments;


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getUserID() {
        return userID;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public List<Post> getPosts(){
        return posts;
    }
    public List<Comment> getComments(){
        return comments;
    }

    public void addPost(Post post){
        posts.add(post);
    }
    public void addComment(Comment comment){
        comments.add(comment);
    }


    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                '}';
    }


}
