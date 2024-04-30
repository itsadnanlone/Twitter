package com.example.Twitter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private int postID;
    private String postBody;
    private String date;
//    private Users user;


//    private int userID;
//    private String userName;
    public List<CommentDTO> comments;


}
