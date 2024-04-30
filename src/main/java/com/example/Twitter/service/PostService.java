package com.example.Twitter.service;

import com.example.Twitter.DTO.CommentCreator;
import com.example.Twitter.DTO.CommentDTO;
import com.example.Twitter.DTO.DisplayJson;
import com.example.Twitter.DTO.PostDTO;
import com.example.Twitter.models.Comment;
import com.example.Twitter.models.Post;
import com.example.Twitter.models.Users;
import com.example.Twitter.repo.PostRepository;
import com.example.Twitter.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    /*
        /*
    Method: POST
Request Body: postBody<string>
userID<int>
Response: One of:
Post created successfully
User does not exist
     */


    // at this point post has postBody and userid
    public String createPost(Post post) {
        Optional<Users> optionalUser = userRepository.findById(post.getUserID());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            Date date = new Date();
            post.setDate(date); // automatically converts to yyyy-MM-dd format
            // check for list of post in user, empty condn
           // user.addPost(post); // user ki list mein add hoga
            if(user.getPosts().size() == 0){
                user.posts = new ArrayList<>();
            }
            post.setUser(user);
            //userRepository.save(user); // does this update the user?
            postRepository.save(post);
            return "Post created successfully";

           // comment.setUSr/Post(User/Post)
        }
        return "User does not exist";

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

 */

    public String updatePost(Post post) {
        Optional<Post> optionalPost = postRepository.findById(post.getPostID()); // check if correct, else define PostID wala find
        // and then use that , in PostRepo
        if(optionalPost.isPresent()) {
            Post repoPost = optionalPost.get();
            repoPost.setPostBody(post.getPostBody());
            postRepository.save(repoPost);// check if reqd - yes otherwise it doesnt get updated.
            return "Post edited successfully";
        }
        else{
            return "Post does not exist";
        }


    }


    public String deletePost(int postID) {
        Optional<Post> optionalPost = postRepository.findById(postID);
        if(optionalPost.isPresent()) {
            Post repoPost = optionalPost.get();
            postRepository.delete(repoPost);
            return "Post deleted";
        }
        return "Post does not exist";

    }


    public ResponseEntity<?> getPostByID(int postID) {
        Optional<Post> optionalPost = postRepository.findById(postID);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostDTO postDTO = convertToPostDTO(post);
            return ResponseEntity.ok(postDTO);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            displayJson.setError("Post does not exist");
            return ResponseEntity.ok(displayJson);
        }
    }


    private PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostID(post.getPostID());
        postDTO.setPostBody(post.getPostBody());
        postDTO.setDate(post.getDate());

        List<Comment> comments = post.getComments();
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO obj = convertToCommentDTO(comment);
            commentDTOs.add(obj);
        }
        if(postDTO.comments == null){
            postDTO.comments = new ArrayList<>();
        }
        postDTO.setComments(commentDTOs);


//        JSONObject response = new JSONObject();
//        try {
//            response.put("postID", postDTO.getPostID());
//            response.put("postBody", postDTO.getPostBody());
//            response.put("date", postDTO.getDate());
//            response.put("comments", commentDTOs);
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
        return postDTO;
    }
//
//            String jsonStr = commentDTOs.toString();
//            // Parse the JSON string to a JSONObject
//            JSONObject jsonObj = new JSONObject(jsonStr);
//
//            // Get the comments array
//            JSONArray commentsArr = jsonObj.getJSONArray("comments");
//
//            // Create a new JSONArray in the desired format
//            JSONArray newCommentsArr = new JSONArray();
//
//            // Iterate through the comments array
//            for (int i = 0; i < commentsArr.length(); i++) {
//                JSONObject comment = commentsArr.getJSONObject(i);
//
//                // Create a new JSONObject in the desired format
//                JSONObject newComment = new JSONObject();
//                newComment.put("commentID", comment.getInt("commentID"));
//                newComment.put("commentBody", comment.getString("commentBody"));
//
//                JSONObject commentCreator = comment.getJSONObject("commentCreator");
//                JSONObject newCommentCreator = new JSONObject();
//                newCommentCreator.put("userID", commentCreator.getInt("userID"));
//                newCommentCreator.put("name", commentCreator.getString("name"));
//                newComment.put("commentCreator", newCommentCreator);
//
//                newCommentsArr.put(newComment);
//            }
//
//            // Create a new JSONObject with the new comments array
//            JSONObject newJsonObj = new JSONObject();
//            newJsonObj.put("comments", newCommentsArr);
//
//            response.put("comments", newJsonObj);
//
//
//
//
//         ///   response.put("comments", commentDTOs);
////
////            JSONObject obj2 = new JSONObject();
////            obj2.put("userID", response.getUserID());
////            obj2.put("name", response.getUserName());
////            obj.put("commentCreator", obj2);
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
     //   return response;



//private JSONObject convertToPostDTO(Post post) {
//    PostDTO postDTO = new PostDTO();
//    postDTO.setPostID(post.getPostID());
//    postDTO.setPostBody(post.getPostBody());
//    postDTO.setDate(post.getDate());
//    List<Comment> comments = post.getComments();
//    List<CommentDTO> commentDTOs = new ArrayList<>();
//    for(Comment comment : comments){
//        commentDTOs.add(convertToCommentDTO(comment));
//    }
//    postDTO.setComments(comments);
////    List<JSONObject> commentDTOs = new ArrayList<>();
////    for (Comment comment : comments) {
////        JSONObject obj = convertToCommentDTO(comment);
////        commentDTOs.add(obj);
////    }
//
//    JSONObject obj = new JSONObject();
//    try {
//        obj.put("postID", postDTO.getPostID());
//        obj.put("postBody", postDTO.getPostBody());
//        obj.put("date", postDTO.getDate());
//        List<JSONObject> jsonString = commentDTOs.stream().toList();
////        JSONArray jsonArray = new JSONArray(jsonString);
////        List<JSONObject> jsonObjectList = new ArrayList<>();
////        for (int i = 0; i < jsonArray.length(); i++) {
////            JSONObject jsonObject = jsonArray.getJSONObject(i);
////            jsonObjectList.add(jsonObject);
////        }
//        List<JSONObject> jso = new ArrayList<JSONObject>();
//        for(JSONObject j : jsonString){
//            JSONObject temp = new JSONObject();
//            temp = j;
//            jso.add(temp);
//        }
//
//        JSONArray jsonArray = new JSONArray(jso);
//        List<JSONObject> jsonObjects = new ArrayList<>();
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            jsonObjects.add(jsonObject);
//        }
//
//        obj.put("comments",jsonObjects.get(0));
//        return obj;
//        //obj.put("comments", jso);
//        //return obj;
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//    return obj;
//}
//    JSONArray jsonArr = new JSONArray(commentDTOs);
//    //
//    //
//    //
//    JSONObject response = new JSONObject();
//    try {
//        response.put("postID", postDTO.getPostID());
//        response.put("postBody", postDTO.getPostBody());
//        response.put("date", postDTO.getDate());
//
//        // Parse the JSON string to a JSONObject
//        JSONObject jsonObj = new JSONObject(jsonArr.toString());
//
//        // Get the comments array
//        JSONArray commentsArr = jsonObj.getJSONArray("comments");
//
//        // Create a new JSONArray in the desired format
//        JSONArray newCommentsArr = new JSONArray();
//
//        // Iterate through the comments array
//        for (int i = 0; i < commentsArr.length(); i++) {
//            JSONObject comment = commentsArr.getJSONObject(i);
//
//            // Create a new JSONObject in the desired format
//            JSONObject newComment = new JSONObject();
//            newComment.put("commentID", comment.getInt("commentID"));
//            newComment.put("commentBody", comment.getString("commentBody"));
//
//            JSONObject commentCreator = comment.getJSONObject("commentCreator");
//            JSONObject newCommentCreator = new JSONObject();
//            newCommentCreator.put("userID", commentCreator.getInt("userID"));
//            newCommentCreator.put("name", commentCreator.getString("name"));
//            newComment.put("commentCreator", newCommentCreator);
//
//            newCommentsArr.put(newComment);
//        }
//
//        // Create a new JSONObject with the new comments array
//        JSONObject newJsonObj = new JSONObject();
//        newJsonObj.put("comments", newCommentsArr);
//
//        response.put("comments", newJsonObj);
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
  //  return response;



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


    /*
    USER FEED: Endpoint to retrieve all posts by all users in reverse chronological order of creation
URL: /
Method: GET
(All posts by all users sorted in the order of creation, the latest at the top)
Response Body: posts <object>
                                -postID <int>
                                -postBody <string>
                                -date<date>
                                -comments <object>
                                         -commentID<int>
                                         -commentBody<string>
                                         -commentCreator<Object>
                                                 -userID<int>
                                                 -name<str>

     */

    public ResponseEntity<?> getFeed() {
        // Retrieve all posts from the repository in descending order of postID
        List<Post> posts = postRepository.findAllByOrderByPostIDDesc();

        // Convert each post to PostDTO using the PostDTOConverter
        List<PostDTO> postDTOs = posts.stream()
                .map(post -> convertToPostDTO(post))
                .collect(Collectors.toList());

        // Return the list of PostDTOs wrapped in ResponseEntity
        return  ResponseEntity.ok(postDTOs);
    }

}
