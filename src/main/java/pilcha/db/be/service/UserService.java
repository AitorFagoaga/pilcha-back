package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.User.UserDTO;
import pilcha.db.be.dto.User.UserRequestBodyDTO;
import pilcha.db.be.models.User;
import pilcha.db.be.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    private BCryptPasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            UserDTO dto = UserDTO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .isPremium(user.getIs_premium())
                    .isBrand(user.getIs_brand())
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }

    public User createUser(UserRequestBodyDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
//        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPassword(dto.getPassword());
        user.setCreatedAt(dto.getCreatedAt());
        user.setIs_premium(dto.getIsPremium());
        user.setIs_brand(dto.getIsBrand());

        userRepository.save(user);
        return user;
    }

    public UserDTO updateUser(Long userId, UserRequestBodyDTO dto){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()){
            throw new RuntimeException("Usuario no encontrada con ID: " + userId);
        }
        User user = optionalUser.get();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setIs_premium(dto.getIsPremium());
        user.setIs_brand(dto.getIsBrand());
        userRepository.save(user);
        return convertToDTO(user);
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado con id:" + userId));
    }

    public UserDTO deleteUser(Long userId){
        User user = findById(userId);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isPremium(user.getIs_premium())
                .isBrand(user.getIs_brand())
                .build();
        userRepository.delete(user);
        return userDTO;
    }

    private UserDTO convertToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isPremium(user.getIs_premium())
                .isBrand(user.getIs_brand())
                .build();
    }
}
