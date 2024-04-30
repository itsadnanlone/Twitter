package com.example.Twitter.models;


import jakarta.persistence.*;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentID;
    private String commentBody;
    private int userID;
    private int postID;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Post post;

//    @ManyToOne
//    @JoinColumn(name="postID")
//    private Post post;
//
//    @ManyToOne
//    @JoinColumn(name = "userID")
//    private User user;

    public ResponseEntity<?> getAllDetails() {
        JSONObject obj = new JSONObject();
        try{
        obj.put("commentID", commentID);
        obj.put("commentBody", commentBody);
        JSONObject commentCreator = new JSONObject();
        commentCreator.put("userID", user.getUserID());
        commentCreator.put("name", user.getName());
        obj.put("commentCreator", commentCreator);
        return ResponseEntity.ok(obj);
    }catch (JSONException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(obj);

    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", commentBody='" + commentBody + '\'' +
                '}';
    }

}



