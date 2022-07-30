package com.condigence.service;
import com.condigence.model.Category;

import java.util.List;

public interface CategoryQueryService {

    List<Category> getAll();



    List<Category> getCategoryByCategoryNameLike(String categoryName);

    Category getOneCategoryByCategoryName(String categoryName);


}
