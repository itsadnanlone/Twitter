package com.example.Twitter.controller;

import com.example.Twitter.models.Comment;
import com.example.Twitter.DTO.DisplayJson;
import com.example.Twitter.service.CommentService;
import com.example.Twitter.service.PostService;
import com.example.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

/*

To create a new comment. Relevant error needs to be returned if the post does not exist
Method: POST
Request Body: commentBody<string>
	postID<int>
	userID<int>
Response: One of:
Comment created successfully
User does not exist
Post does not exist
*/
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        String msg = commentService.addComment(comment);

        if(msg.equals("Comment created successfully")){
            return ResponseEntity.ok(msg);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError(msg);
            return ResponseEntity.ok(displayJson);
        }
    }
    /*

To edit an existing comment. Relevant error needs to be returned if the comment does not exist
Method: PATCH
Request Body:
commentBody<str>
commentID<int>
Response Body: One of
Comment edited successfully
Comment does not exist
*/
    @PatchMapping
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        String msg = commentService.updateComment(comment);
        if(msg.equals("Comment edited successfully")){
            return ResponseEntity.ok(msg);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError(msg);
            return ResponseEntity.ok(displayJson);
        }
    }
    /*
To delete an existing comment.  Relevant error needs to be returned if the comment does not exist
Method: DELETE
Query Parameter: commentID<int>
Response Body: One of
Comment deleted
Comment does not exist

 */

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam("commentID") int commentID) {
        String msg = commentService.deleteComment(commentID);
        if(msg.equals("Comment deleted")){
            return ResponseEntity.ok(msg);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError(msg);
            return ResponseEntity.ok(displayJson);
        }
    }
/*
To retrieve an existing comment. Relevant error needs to be returned if the comment does not exist
Method: GET
Request Param: CommentID<int>
Response Body: One of:
<object>
                                -commentID<int>
                                -commentBody<string>
		        -commentCreator<Object>
                                                 -userID<int>
                                                 -name<str>
Comment does not exist

 */

    // Sir, has user capital C in CommentID, check it before submission
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCommentByID(@RequestParam("commentID") int commentID) {
        ResponseEntity<?> obj = commentService.getCommentByID(commentID);
        return obj;

       // return ResponseEntity.ok(object.getBody());
    }



}
