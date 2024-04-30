package com.example.Twitter.controller;

import com.example.Twitter.DTO.DisplayJson;
import com.example.Twitter.models.Post;
import com.example.Twitter.service.PostService;
import com.example.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;
    /*
    Method: POST
Request Body: postBody<string>
userID<int>
Response: One of:
Post created successfully
User does not exist
     */

    @PostMapping//, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        String msg = postService.createPost(post);

        if(msg.equals("Post created successfully")){
            return ResponseEntity.ok(msg);
        }
        else{
            //JSONObject obj = new JSONObject();
            DisplayJson displayJson = new DisplayJson();
            try {
                //obj.put("Error", msg);
                displayJson.setError(msg);
                //return ResponseEntity.ok(obj.toString());
                return ResponseEntity.ok(displayJson);
            }
            catch (Exception e) {
                //e.printStackTrace();
                //return ResponseEntity.ok(obj.toString());
                return ResponseEntity.ok(displayJson);

            }
        }
    }


    /*
   To edit an existing post. Relevant error needs to be returned if the post does not exist
Method: PATCH
Request Body:
postBody<str>
postID<int>
Response Body: One of
Post edited successfully
Post does not exist


To delete a post. Relevant error needs to be returned if the post does not exist
Method: DELETE
Query Parameter: postID<int>
Response: One of:
Post deleted
Post does not exist

     */
    @PatchMapping
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        String msg = postService.updatePost(post);

        if(msg.equals("Post edited successfully")){
            return ResponseEntity.ok(msg);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError(msg);
            return ResponseEntity.ok(displayJson);
        }
    }

    /*
    To delete a post. Relevant error needs to be returned if the post does not exist
Method: DELETE
Query Parameter: postID<int>
Response: One of:
Post deleted
Post does not exist

     */
    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam("postID") int postID) {
        String msg = postService.deletePost(postID);
        if(msg.equals("Post deleted")){
            return ResponseEntity.ok(msg);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError(msg);
            return ResponseEntity.ok(displayJson);
        }
    }

/*
To retrieve an existing post. Relevant error needs to be returned if the post does not exist
Method: GET
Query Parameter: postID
(All the details about a specific post)
Response Body: One of:
<objects>
                                -postID <int>
                                -postBody <string>
                                -date<date>
                                -comments <object>
                                         -commentID<int>
                                         -commentBody<string>
                                         -commentCreator<Object>
                                                 -userID<int>
                                                 -name<str>
Post does not exist

 */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPostByID(@RequestParam("postID") int postID) {
        return postService.getPostByID(postID);
    }



}
