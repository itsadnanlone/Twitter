package com.example.Twitter.service;

import com.example.Twitter.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

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
    public String addComment(Comment comment) {
        Optional<Users> optionalUser = userRepository.findById(comment.getUserID());
        if (optionalUser.isPresent()) {
            Optional<Post> optionalPost = postRepository.findById(comment.getPostID());
            if (optionalPost.isPresent()) {
                Users user = optionalUser.get();
                Post post = optionalPost.get();
                if(user.getComments().size() == 0){
                    user.comments = new ArrayList<>();
                }
                //user.comments.add(comment);
                comment.setUser(user);
                comment.setPost(post);
                commentRepository.save(comment);
                return "Comment created successfully";
                /*
                 post.setUser(user);
            //userRepository.save(user); // does this update the user?
            postRepository.save(post);
            return "Post created successfully";

           // comment.setUSr/Post(User/Post)
                 */
            }
            else{
                return "Post does not exist";
            }
        }
        else{
            return "User does not exist";
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

    public String updateComment(Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(comment.getCommentID());
        if (optionalComment.isPresent()) {
            Comment repoComment = optionalComment.get();
            repoComment.setCommentBody(comment.getCommentBody());
            commentRepository.save(repoComment);
            return "Comment edited successfully";
        }
        return "Comment does not exist";
    }


    public String deleteComment(int commentID) {
        Optional<Comment> optionalComment = commentRepository.findById(commentID);
        if (optionalComment.isPresent()) {
            Comment repoComment = optionalComment.get();
            commentRepository.delete(repoComment);
            return "Comment deleted";

        }
        return "Comment does not exist";
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
//    public ResponseEntity<?> getCommentByID(int commentID) {
//        Optional<Comment> optionalComment = commentRepository.findById(commentID);
//        if (optionalComment.isPresent()) {
////            //return ResponseEntity.ok(optionalComment.get().getCommentID()); working fine
//////            //Comment repoComment = comment;
//////            //int commentCreatorUserID = repoComment.getUserID();
//            Users user = userRepository.findById(optionalComment.get().getUserID()).get();
//
//////
//////            //return ResponseEntity.ok(repoComment); overflow
//            JSONObject obj = new JSONObject();
//////            JSONObject createrObj = new JSONObject();
//            try{
//                obj.put("commentID", optionalComment.get().getCommentID());
//                obj.put("commentBody", optionalComment.get().getCommentBody());
//////                createrObj.put("userID", user.getUserID());
//////                createrObj.put("name", user.getName());
//////                obj.put("commentCreator", createrObj);
//                return ResponseEntity.ok(obj);
//            }
//            catch (JSONException e){
//                //return ResponseEntity.ok(obj);
//
//            return ResponseEntity.ok(optionalComment.get());//.getAllDetails();
//
//            }
//           }
//
//        else {
//            String msg = "Comment does not exist";
//            DisplayJson displayJson = new DisplayJson();
//            displayJson.setError(msg);
//            return ResponseEntity.ok(displayJson);
//        }
//    }


// this works but trying diff
//    public ResponseEntity<?> getCommentByID(int commentID) {
//        Optional<Comment> optionalComment = commentRepository.findById(commentID);
//        if (optionalComment.isPresent()) {
//            Comment comment = optionalComment.get();
//            JSONObject commentDTO = convertToCommentDTO(comment);
//            return ResponseEntity.ok(commentDTO.toString());
//        } else {
//            String msg = "Comment does not exist";
//            DisplayJson displayJson = new DisplayJson();
//            displayJson.setError(msg);
//            return ResponseEntity.ok(displayJson);
//        }
//    }
//
//    private JSONObject convertToCommentDTO(Comment comment) {
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setCommentID(comment.getCommentID());
//        commentDTO.setCommentBody(comment.getCommentBody());
//        commentDTO.setUserID(comment.getUserID());
//        // Fetch user name using userRepository
//        Optional<Users> optionalUser = userRepository.findById(comment.getUserID());
//        if (optionalUser.isPresent()) {
//            commentDTO.setUserName(optionalUser.get().getName());
//        }
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("commentID", commentDTO.getCommentID());
//            obj.put("commentBody", commentDTO.getCommentBody());
//            JSONObject obj2 = new JSONObject();
//            obj2.put("userID", commentDTO.getUserID());
//            obj2.put("name", commentDTO.getUserName());
//            obj.put("commentCreator", obj2);
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return obj;
//    }
    /*
    -commentID<int>
                                -commentBody<string>
		        -commentCreator<Object>
                                                 -userID<int>
                                                 -name<str>

     */

    public ResponseEntity<?> getCommentByID(int commentID) {
        Optional<Comment> optionalComment = commentRepository.findById(commentID);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            CommentDTO commentDTO = convertToCommentDTO(comment);
            return ResponseEntity.ok(commentDTO);
        } else {
            String msg = "Comment does not exist";
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError(msg);
            return ResponseEntity.ok(displayJson);
        }
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentID(comment.getCommentID());
        commentDTO.setCommentBody(comment.getCommentBody());

        if(commentDTO.commentCreator == null){
            commentDTO.commentCreator = new CommentCreator();
        }
        commentDTO.commentCreator.setUserID(comment.getUserID());



        Optional<Users> optionalUser = userRepository.findById(comment.getUserID());
        if (optionalUser.isPresent()) {
            commentDTO.commentCreator.setName(optionalUser.get().getName());
        }
        return commentDTO;


    }



}
