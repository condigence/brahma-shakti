package com.condigence.service.impl;

import com.condigence.bean.FavouriteBean;
import com.condigence.dto.FavouriteDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Favourite;
import com.condigence.model.User;
import com.condigence.repository.FavouriteRepository;
import com.condigence.service.FavouriteService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouriteImpl implements FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Override
    public Favourite getByUserId(String userId) {

        return favouriteRepository.findByUserId(userId);

    }


    @Override
    public List<FavouriteDTO> getAll() {
        List<FavouriteDTO> favouriteDTOS = new ArrayList<>();
        List<Favourite> favourites = favouriteRepository.findAll();
        for (Favourite fav : favourites) {
            FavouriteDTO favouriteDTO = new FavouriteDTO();
            favouriteDTO.setId(fav.getId());
            favouriteDTO.setProductId(fav.getProductId());
            favouriteDTO.setUserId(fav.getUserId());

            favouriteDTOS.add(favouriteDTO);
        }
        return favouriteDTOS;
    }

    @Override
    public void addToFavourite(FavouriteBean bean) {

        Favourite fav=new Favourite();
        fav.setId(bean.getId());
        fav.setUserId(bean.getUserId());
        fav.setProductId(bean.getProductId());
        favouriteRepository.save(fav);

    }

    public FavouriteDTO getUserById(String userId) {
        Optional<Favourite> favouriteData = favouriteRepository.findById(userId);
        FavouriteDTO favouriteDTO = new FavouriteDTO();
        if (favouriteData.isPresent()) {
            Favourite favourite = favouriteData.get();
            favouriteDTO.setId(favourite.getId());
            favouriteDTO.setProductId(favourite.getProductId());
            favouriteDTO.setUserId(favourite.getUserId());
            return favouriteDTO;
        } else {
            return favouriteDTO.builder().id("0").build();
        }
    }

//    public void removefromFavourite

    @Override
    public void deleteById(String id) {
        favouriteRepository.deleteById(id);
    }
}
