package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.UserFavorite.UserFavoriteDTO;
import pilcha.db.be.models.UserFavorite;
import pilcha.db.be.models.UserFavoriteId;
import pilcha.db.be.service.UserFavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class UserFavoriteController {
    @Autowired
    private UserFavoriteService userFavoriteService;

    @PostMapping
    public UserFavorite createFavorite(@RequestBody UserFavoriteDTO dto){
        UserFavorite userFavorites = userFavoriteService.createFavorite(dto);
        return userFavorites;
    }

    @DeleteMapping
    public ResponseEntity<UserFavorite> deleteFavorite(@RequestBody UserFavoriteDTO dto) {
        UserFavorite deletedFavorite = userFavoriteService.deleteFavorite(dto);
        return ResponseEntity.ok(deletedFavorite);
    }
}
