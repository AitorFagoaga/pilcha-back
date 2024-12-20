package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.User.LoginResponseDTO;
import pilcha.db.be.dto.User.UserDTO;
import pilcha.db.be.dto.User.UserRequestBodyDTO;
import pilcha.db.be.models.User;
import pilcha.db.be.repository.UserRepository;
import pilcha.db.be.utils.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public User createUser(UserRequestBodyDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(dto.getCreatedAt());
        user.setIs_premium(dto.getIsPremium());
        user.setIs_brand(dto.getIsBrand());

        return userRepository.save(user);
    }

    public LoginResponseDTO login(String email, String rawPassword) {
        // Buscar el usuario por email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // Validar la contraseña
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        // Generar el token JWT
        String token = jwtUtil.generateToken(user.getEmail());

        // Construir el LoginResponseDTO usando Lombok's Builder
        return LoginResponseDTO.builder()
                .token(token)
                .id(user.getId().toString())
                .email(user.getEmail())
                .age(user.getAge())
                .username(user.getUsername())
                .isPremium(user.getIs_premium())
                .isBrand(user.getIs_brand())
                .build();
    }


    public UserDTO updateUser(Long userId, UserRequestBodyDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setIs_premium(dto.getIsPremium());
        user.setIs_brand(dto.getIsBrand());

        userRepository.save(user);
        return convertToDTO(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
    }

    public UserDTO deleteUser(Long userId) {
        User user = findById(userId);
        UserDTO userDTO = convertToDTO(user);
        userRepository.delete(user);
        return userDTO;
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isPremium(user.getIs_premium())
                .isBrand(user.getIs_brand())
                .build();
    }
}