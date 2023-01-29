package posmy.interview.boot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import posmy.interview.boot.entity.Book;
import posmy.interview.boot.entity.User;
import posmy.interview.boot.request.CreateUserRequest;
import posmy.interview.boot.request.UpdateUserRequest;
import posmy.interview.boot.response.UserResponse;
import posmy.interview.boot.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request){
        return ResponseEntity.ok(userService.create(request));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(userService.update(request));
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.deleteById(userId));
    }

    @GetMapping("/user/showAll")
    @ResponseBody
    public ResponseEntity<List<UserResponse>> showAll(){
        return ResponseEntity.ok(userService.showAll());
    }
}
