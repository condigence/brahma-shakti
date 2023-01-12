package com.condigence.service;

import com.condigence.bean.FavouriteBean;
import com.condigence.dto.FavouriteDTO;

import java.util.List;


public interface FavouriteService {

    List<FavouriteDTO> getAll(String userId);

    FavouriteDTO addToFavourite(FavouriteBean bean);

    void deleteById(String id);

    FavouriteDTO findById(FavouriteBean bean);

}
