package com.spring.mongo.demo.service.impl;

import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.bean.CartDetailBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.CartDetailDTO;
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
	public void addToCart(CartBean cartBean){
		Cart cart = new Cart();
		/////
		cart.setUserId(cartBean.getUserId());
		cart.setDiscountAmount(cartBean.getDiscountAmount());
		cart.setGrandTotal(cartBean.getGrandTotal());
		cart.setLastUpdated(cartBean.getLastUpdated());
		cart.setSubtotalAmount(cartBean.getSubtotalAmount());
		cart.setTaxAmount(cartBean.getTaxAmount());
		List<CartDetail> cartDetails = new ArrayList<>();
		for(CartDetailBean items :cartBean.getItems()) {
			CartDetail cartDetail = new CartDetail();
			Product product = productRepository.findOneById(items.getProductId());
			cartDetail.setItemCount(items.getItemCount());
			cartDetail.setTotalAmount(items.getTotalAmount());
			cartDetails.add(cartDetail);
		}

		cart.setItemDetails(cartDetails);
		//////////
		repository.save(cart);

	}

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

				if(product != null){
					cartDetailDTO.setDiscount(product.getDiscount());
					cartDetailDTO.setPrice(product.getActualPrice());
					cartDetailDTO.setUnit(product.getUnit());
					cartDetailDTO.setTitle(product.getName());
				}

				cartDetailDTO.setId(items.getId());
				cartDetailDTO.setItemCount(items.getItemCount());
				cartDetailDTO.setTotalAmount(items.getTotalAmount());

				cartDetailDTOS.add(cartDetailDTO);

			}
			cartDTO.setItemDetails(cartDetailDTOS);

			return cartDTO;
		}else{
			return CartDTO.builder().id("0").userId("Not Found").build();
		}

	}


}
