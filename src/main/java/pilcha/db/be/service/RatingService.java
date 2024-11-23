package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.Rating.RatingDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.Rating;
import pilcha.db.be.models.User;
import pilcha.db.be.repository.BrandRepository;
import pilcha.db.be.repository.RatingRepository;
import pilcha.db.be.repository.UserRepository;

import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserRepository userRepository;

    public RatingDTO createRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();

        rating.setRating(ratingDTO.getRating());
        rating.setComment(ratingDTO.getComment());
        rating.setPrice(ratingDTO.getPrice());
        rating.setQuality_rating(ratingDTO.getQualityRating());

        if (ratingDTO.getBrandId() != null) {
            Optional<Brand> brand = brandRepository.findById(ratingDTO.getBrandId());
            brand.ifPresent(rating::setBrand);
        }

        if (ratingDTO.getUserId() != null) {
            Optional<User> user = userRepository.findById(ratingDTO.getUserId());
            user.ifPresent(rating::setUser);
        }

        Rating savedRating = ratingRepository.save(rating);

        return new RatingDTO(
                savedRating.getId(),
                savedRating.getRating(),
                savedRating.getComment(),
                savedRating.getPrice(),
                savedRating.getQuality_rating(),
                savedRating.getBrand() != null ? savedRating.getBrand().getId() : null,
                savedRating.getUser() != null ? savedRating.getUser().getId() : null
        );
    }

    public RatingDTO updateRating(Long ratingId, RatingDTO dto) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        if (!optionalRating.isPresent()) {
            throw new RuntimeException("Rating no encontrado con ID: " + ratingId);
        }

        Rating rating = optionalRating.get();

        rating.setComment(dto.getComment());
        rating.setQuality_rating(dto.getQualityRating());
        rating.setPrice(dto.getPrice());
        rating.setRating(dto.getRating());

        if (dto.getBrandId() != null) {
            Optional<Brand> optionalBrand = brandRepository.findById(dto.getBrandId());
            if (!optionalBrand.isPresent()) {
                throw new RuntimeException("Marca no encontrada con ID: " + dto.getBrandId());
            }
            rating.setBrand(optionalBrand.get());
        }

        Rating updatedRating = ratingRepository.save(rating);

        return new RatingDTO(
                updatedRating.getId(),
                updatedRating.getRating(),
                updatedRating.getComment(),
                updatedRating.getPrice(),
                updatedRating.getQuality_rating(),
                updatedRating.getBrand() != null ? updatedRating.getBrand().getId() : null,
                updatedRating.getUser() != null ? updatedRating.getUser().getId() : null
        );
    }

    public void deleteRating(Long ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);

        if (!optionalRating.isPresent()) {
            throw new RuntimeException("Rating no encontrado con ID: " + ratingId);
        }

        ratingRepository.deleteById(ratingId);
    }

}
