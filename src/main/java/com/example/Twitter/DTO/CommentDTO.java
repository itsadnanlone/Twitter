package com.example.Twitter.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
//    private int commentID;
//    private String commentBody;
////    private Users user;
//
//
//    private int userID;
//    private String userName;

    // Constructors, getters, and setters

        private int commentID;
    private String commentBody;
//    private Users user;

    public CommentCreator commentCreator;
//    private int userID;
//    private String userName;


}

