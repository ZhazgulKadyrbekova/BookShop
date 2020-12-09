package com.example.shop.controller;

import com.example.shop.entity.UserEntity;
import com.example.shop.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/block/{id}")
    public ResponseEntity<String> blockUser(@PathVariable("id") Integer id, Principal principal) {
        return new ResponseEntity<>(userService.blockUser(id, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getByEmail(@PathVariable("email") String mail) throws Exception {
        return new ResponseEntity<>(userService.getByEmail(mail), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserEntity>> getAllByParam(@RequestParam(required = false) String name){

        Set <UserEntity> user = new LinkedHashSet<>(userService.getAll());

        if (name != null)
            user.retainAll(userService.getAllByName(name));

        return new ResponseEntity<>(new ArrayList<>(user), HttpStatus.OK);
    }
}
