package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.Rating.RatingDTO;
import pilcha.db.be.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) {
        RatingDTO createdRating = ratingService.createRating(ratingDTO);
        return ResponseEntity.ok(createdRating);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> updateRating(
            @PathVariable Long ratingId,
            @RequestBody RatingDTO ratingDTO) {
        try {
            RatingDTO updatedRating = ratingService.updateRating(ratingId, ratingDTO);
            return ResponseEntity.ok(updatedRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        try {
            ratingService.deleteRating(ratingId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
