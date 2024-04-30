package com.example.Twitter.service;

import java.util.ArrayList;
import java.util.Optional;

import com.example.Twitter.DisplayUser;
import com.example.Twitter.PostRepository;
import com.example.Twitter.Users;
import com.example.Twitter.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public String addUser(Users user) {
        Users puser = userRepository.findByEmail(user.getEmail());
        if(puser == null) {
            userRepository.save(user);
            return "Account Creation Successful";
        }
        else{
            return "Forbidden, Account already exists";
        }
    }

    public String login(String email, String password) {
        Users user = userRepository.findByEmail(email);
        if(user != null){
            if(user.getPassword().equals(password)) {
                return "Login Successful";
            }
            else{
                return "Username/Password Incorrect";
            }
        }
        else {
            return "User does not exist";
        }
    }

    public List<Users> getMyUsers() {


        return userRepository.findAll();

    }


    public ResponseEntity<?> getUserByID(int userID) {
        Optional<Users> user = userRepository.findById(userID);
        if(user.isPresent()) {
            //return ResponseEntity.ok(user.get());
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", user.get().getName());
                obj.put("userID", user.get().getUserID());
                obj.put("email", user.get().getEmail());
                return ResponseEntity.ok(obj.toString());
            }
            catch (JSONException e) {
                return ResponseEntity.ok(obj.toString());
            }
        }
        else{
            JSONObject obj = new JSONObject();
            try {
                obj.put("Error", "User does not exist");
                return ResponseEntity.ok(obj.toString());

            }
            catch (JSONException e) {
                return ResponseEntity.ok(obj.toString());
            }

        }
    }


    public ResponseEntity<List<?>> getAllUsers() {
        List<Users> users = userRepository.findAll();
        List<DisplayUser> displayUsers = new ArrayList<DisplayUser>();
        for (Users user : users) {
            DisplayUser displayUser = new DisplayUser(user.getName(), user.getUserID(), user.getEmail());
            displayUsers.add(displayUser);
        }
        return ResponseEntity.ok(displayUsers);
    }


//    public ResponseEntity<?> getFeed() {
//
//    }
}
