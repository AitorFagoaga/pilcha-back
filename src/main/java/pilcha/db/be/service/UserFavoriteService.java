package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.UserFavorite.UserFavoriteDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.models.User;
import pilcha.db.be.models.UserFavorite;
import pilcha.db.be.repository.BrandRepository;
import pilcha.db.be.repository.UserFavoriteRepository;
import pilcha.db.be.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;

    public List<Long> getFavoriteBrandIdsByUser(Long userId) {
        List<UserFavorite> favorites = userFavoriteRepository.findByUserId(userId);
        if (favorites.isEmpty()) {
            throw new RuntimeException("User not found or has no favorites");
        }
        return favorites.stream()
                .map(favorite -> favorite.getBrand().getId())
                .collect(Collectors.toList());
    }

    public UserFavorite createFavorite(UserFavoriteDTO dto){
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        Optional<Brand> brandOptional = brandRepository.findById(dto.getBrandId());

        if (userOptional.isPresent() && brandOptional.isPresent()) {
            UserFavorite userFavorite = new UserFavorite();
            userFavorite.setUser(userOptional.get());
            userFavorite.setBrand(brandOptional.get());

            return userFavoriteRepository.save(userFavorite);
        } else {
            throw new RuntimeException("User or Brand not found");
        }
    }

    public UserFavorite deleteFavorite(UserFavoriteDTO dto) {
        // Buscar el usuario y la marca por sus IDs
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        Optional<Brand> brandOptional = brandRepository.findById(dto.getBrandId());

        if (userOptional.isPresent() && brandOptional.isPresent()) {
            // Buscar el favorito específico
            Optional<UserFavorite> favoriteOptional = userFavoriteRepository.findByUserAndBrand(
                    userOptional.get(), brandOptional.get());

            if (favoriteOptional.isPresent()) {
                UserFavorite favoriteToDelete = favoriteOptional.get();
                // Eliminar el favorito
                userFavoriteRepository.delete(favoriteToDelete);
                // Retornar el favorito eliminado
                return favoriteToDelete;
            } else {
                throw new RuntimeException("Favorite not found for the specified User and Brand");
            }
        } else {
            throw new RuntimeException("User or Brand not found");
        }
    }



}
