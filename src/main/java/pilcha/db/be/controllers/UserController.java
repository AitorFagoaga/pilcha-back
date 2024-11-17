package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.User.UserDTO;
import pilcha.db.be.dto.User.UserRequestBodyDTO;
import pilcha.db.be.models.User;
import pilcha.db.be.repository.UserRepository;
import pilcha.db.be.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestBodyDTO dto){
        User user = userService.createUser(dto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequestBodyDTO dto){
        UserDTO updateUser = userService.updateUser(userId, dto);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId){
        UserDTO deleteUser = userService.deleteUser(userId);
        return ResponseEntity.ok(deleteUser);
    }
}
