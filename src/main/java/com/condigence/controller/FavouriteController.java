package com.condigence.controller;

import com.condigence.bean.FavouriteBean;
import com.condigence.dto.FavouriteDTO;
import com.condigence.dto.UserDTO;
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

@CrossOrigin()
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
        FavouriteDTO favouriteDTO = favouriteService.addToFavourite(favouriteBean);
        return ResponseEntity.ok().body(favouriteDTO);
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<?> removeFromMyFavourites(@RequestBody FavouriteBean favouriteBean) {
        logger.info("Fetching & Deleting Item with id {}", favouriteBean.getId());
        FavouriteDTO fav = favouriteService.findById(favouriteBean);
        if (fav != null) {
            if (fav.getUser().getId().equalsIgnoreCase(favouriteBean.getUserId())) {
                favouriteService.deleteById(favouriteBean.getId());
            }
        } else {
            logger.error("Unable to delete. Product with id {} not found.", favouriteBean.getId());
            return new ResponseEntity(new CustomErrorType("Unable to delete. Favourite with id " + favouriteBean.getId() + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(HttpStatus.OK);
    }

}
