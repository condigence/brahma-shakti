package com.condigence.service.impl;

import com.condigence.bean.FavouriteBean;
import com.condigence.dto.FavouriteDTO;
import com.condigence.dto.ProductDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Favourite;
import com.condigence.repository.FavouriteRepository;
import com.condigence.service.FavouriteService;
import com.condigence.service.ProductService;
import com.condigence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteImpl implements FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public List<FavouriteDTO> getAll(String userId) {
        List<FavouriteDTO> favouriteDTOS = new ArrayList<>();
        List<Favourite> favourites = favouriteRepository.findAllByUserId(userId);
        for (Favourite fav : favourites) {
            FavouriteDTO favouriteDTO = new FavouriteDTO();
            favouriteDTO.setId(fav.getId());

            ProductDTO productDTO = productService.getProductById(fav.getProductId());
            favouriteDTO.setProduct(productDTO);

            UserDTO userDTO = userService.getUserById(fav.getUserId());
            favouriteDTO.setUser(userDTO);

            favouriteDTOS.add(favouriteDTO);
        }
        return favouriteDTOS;
    }

    @Override
    public FavouriteDTO addToFavourite(FavouriteBean bean) {

        Favourite fav=new Favourite();
        fav.setId(bean.getId());
        fav.setUserId(bean.getUserId());
        fav.setProductId(bean.getProductId());
        Favourite savedObj = favouriteRepository.save(fav);

        FavouriteDTO dto = new FavouriteDTO();
        dto.setId(savedObj.getId());

        ProductDTO productDTO = productService.getProductById(savedObj.getProductId());
        dto.setProduct(productDTO);

        UserDTO userDTO = userService.getUserById(fav.getUserId());
        dto.setUser(userDTO);

        return dto;
    }

    @Override
    public void deleteById(String id) {
        favouriteRepository.deleteById(id);
    }

    @Override
    public FavouriteDTO findById(FavouriteBean bean) {
        Favourite favourite = favouriteRepository.findById(bean.getId()).get();
        FavouriteDTO dto = new FavouriteDTO();
        dto.setId(favourite.getId());

        ProductDTO productDTO = productService.getProductById(favourite.getProductId());
        dto.setProduct(productDTO);

        UserDTO userDTO = userService.getUserById(favourite.getUserId());
        dto.setUser(userDTO);

        return dto;
    }
}
