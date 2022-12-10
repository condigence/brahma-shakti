package com.condigence.controller;

import com.condigence.bean.FavouriteBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.FavouriteDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Category;
import com.condigence.model.Favourite;
import com.condigence.service.CategoryService;
import com.condigence.service.FavouriteService;
import com.condigence.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bs-favourite")
public class FavouriteController {

    public static final Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavouriteDTO>> getAll(@PathVariable String userId) {
        return ResponseEntity.ok().body(favouriteService.getAll(userId));
    }


    @PostMapping(value = "/")
    public ResponseEntity<?> addToMyFavourites(@RequestBody FavouriteBean favouriteBean) {

        logger.info("Entering addFavourite with Favourite Details >>>>>>>>  : {}", favouriteBean);
        HttpHeaders headers = new HttpHeaders();
        favouriteService.addToFavourite(favouriteBean);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<?> removeFromMyFavourites(@RequestBody FavouriteBean favouriteBean) {
        logger.info("Fetching & Deleting Item with id {}", favouriteBean.getId());
        List<FavouriteDTO> favs = favouriteService.getAll(favouriteBean.getUserId());
        if (!favs.isEmpty()) {
            for(FavouriteDTO f : favs){
                if(f.getId().equalsIgnoreCase(favouriteBean.getId())){
                    favouriteService.deleteById(favouriteBean.getId());
                }
            }

        } else {
            logger.error("Unable to delete. Product with id {} not found.", favouriteBean.getId());
            return new ResponseEntity(new CustomErrorType("Unable to delete. Favourite with id " + favouriteBean.getId() + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(HttpStatus.OK);
    }

}
