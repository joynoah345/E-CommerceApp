package org.ecomapp.app.Controller;

import org.ecomapp.app.Dto.UserRequest;
import org.ecomapp.app.Dto.UserResponse;
import org.ecomapp.app.Entity.SavedDeliveryAddresses;
import org.ecomapp.app.Entity.User;
import lombok.RequiredArgsConstructor;
import org.ecomapp.app.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/test")
    public String test() {
        return "Working!";
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserResponse user = userService.fetchUserById(id);
        if  (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.updateUser(id,user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PostMapping("/addresses")
    public ResponseEntity<Void> addDeliveryAddress(@RequestHeader("User-Id") Long userId,
                                   @RequestBody SavedDeliveryAddresses address){
        Boolean created = userService.addDeliveryAddress(userId,address);
        return created ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<SavedDeliveryAddresses>> getSavedAddresses(@RequestHeader("User-Id") Long userId){
        List<SavedDeliveryAddresses> addresses = userService.fetchSavedAddresses(userId);
        if (addresses == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userService.fetchSavedAddresses(userId));
    }

}