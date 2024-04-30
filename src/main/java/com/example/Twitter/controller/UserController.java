package com.example.Twitter.controller;
import com.example.Twitter.DTO.DisplayJson;
import com.example.Twitter.service.PostService;
import com.example.Twitter.service.UserService;
import com.example.Twitter.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private PostService postService;

    // public ResponseEntity<?> addUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("name") String name ){
    // fix the return type success and error

    @PostMapping(value= "/signup")//, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody Users user){
//        Users user = new Users();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(password);
//        Users puser = new Users();
//        puser.setName(user.getName());
//        puser.setEmail(user.getEmail());
//        puser.setPassword(user.getPassword());

        String msg = userService.addUser(user);

        if(msg.equals("Account Creation Successful")){
            return ResponseEntity.ok(msg);
        }
        else{
            DisplayJson displayJson = new DisplayJson();
            //JSONObject obj = new JSONObject();
            try {
                //obj.put("Error", msg);
                //return ResponseEntity.ok(obj.toString());
                displayJson.setError(msg);
                return ResponseEntity.ok(displayJson);
            }
            catch (Exception e) {
                //e.printStackTrace();
                //return ResponseEntity.ok(obj.toString());
                return ResponseEntity.ok(displayJson);
            }
        }
    }

// public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
    @PostMapping(value = "/login")// produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody Users user) {
        String msg = userService.login(user.getEmail(),user.getPassword());
        if(msg.equals("Login Successful")){
            return ResponseEntity.ok(msg);
        }
        //JSONObject obj = new JSONObject();
        DisplayJson json = new DisplayJson();
        try{
            //obj.put("Error", msg);
            json.setError(msg);
            //return ResponseEntity.ok(obj.toString());
            return ResponseEntity.ok(json);
        }
        catch (Exception e) {
           // return ResponseEntity.ok(obj.toString());
            return ResponseEntity.ok(json);

        }
    }


    // always returns json, error and details
    @GetMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByID(@RequestParam("userID") int userID) {
        return userService.getUserByID(userID);

    }


    // function for myself to check. delete it before submission
    @GetMapping("/myusers")
    public List<Users> getMyUsers(){
        return userService.getMyUsers();
    }

    //
    @GetMapping("/users")
    public ResponseEntity<List<?>> getAllUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("/")
    public ResponseEntity<?> getFeed() {
        return postService.getFeed();
    }

}