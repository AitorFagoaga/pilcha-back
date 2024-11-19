package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.UserFavorite.UserFavoriteDTO;
import pilcha.db.be.models.UserFavorite;
import pilcha.db.be.repository.UserFavoriteRepository;

import java.util.List;

@Service
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

//    public List<UserFavorite> createFavorite(UserFavoriteDTO dto){
//        UserFavorite userFavorite = new UserFavorite();
//        userFavorite.set(dto.getUserId());
//        userFavorite.setBrand(dto.getBrandId());
//         userFavoriteRepository.save(userFavorite);
//    }

}
