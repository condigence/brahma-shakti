package com.spring.mongo.demo.service.impl;

import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.CartDetailDTO;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Cart;
import com.spring.mongo.demo.model.CartDetail;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.repository.CartRepository;
import com.spring.mongo.demo.repository.ProductRepository;
import com.spring.mongo.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public CartDTO getCartByUserId(String userId) {
		Cart cart = repository.findByUserId(userId);
		if(cart != null){
			CartDTO cartDTO = new CartDTO();
			cartDTO.setId(cart.getId());
			cartDTO.setUserId(cart.getUserId());
			cartDTO.setDiscountAmount(cart.getDiscountAmount());
			cartDTO.setGrandTotal(cart.getGrandTotal());
			cartDTO.setLastUpdated(cart.getLastUpdated());
			cartDTO.setSubtotalAmount(cart.getSubtotalAmount());
			cartDTO.setTaxAmount(cart.getTaxAmount());

			List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
			for(CartDetail items :cart.getItemDetails()){
				CartDetailDTO cartDetailDTO = new CartDetailDTO();
				Product product = productRepository.findOneById(items.getProductId());
				cartDetailDTO.setDiscount(product.getDiscount());
				cartDetailDTO.setId(items.getId());
				cartDetailDTO.setPrice(product.getActualPrice());
				cartDetailDTO.setUnit(product.getUnit());
				cartDetailDTO.setItemCount(items.getItemCount());
				cartDetailDTO.setTitle(product.getName());
				cartDetailDTO.setTotalAmount(items.getTotalAmount());
				cartDetailDTOS.add(cartDetailDTO);
				cartDTO.setItemDetails(cartDetailDTOS);
			}

			return cartDTO;
		}else{
			return CartDTO.builder().id("0").userId("Not Found").build();
		}

	}


}
