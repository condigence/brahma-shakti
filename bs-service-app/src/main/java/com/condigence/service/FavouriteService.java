package com.condigence.service;

import com.condigence.bean.FavouriteBean;
import com.condigence.dto.FavouriteDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Category;
import com.condigence.model.Favourite;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FavouriteService {

    List<FavouriteDTO> getAll(String userId);

   void addToFavourite(FavouriteBean bean);

    void deleteById(String id);

}
