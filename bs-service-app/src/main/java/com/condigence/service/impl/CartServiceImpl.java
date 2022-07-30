package com.condigence.service.impl;

import com.condigence.bean.CartBean;
import com.condigence.bean.CartDetailBean;
import com.condigence.dto.CartDTO;
import com.condigence.dto.CartDetailDTO;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Cart;
import com.condigence.model.CartDetail;
import com.condigence.model.Product;
import com.condigence.repository.CartRepository;
import com.condigence.repository.ProductRepository;
import com.condigence.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private ProductRepository productRepository;

	private Map<Product, Integer> products = new HashMap<>();

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
					cartDetailDTO.setPrice(product.getPrice());
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

	/**
	 * @param userId
	 */
	@Override
	public void deleteCartByUserId(String userId) {
		repository.delete(repository.findByUserId(userId));
	}


	/**
	 * If product is in the map just increment quantity by 1.
	 * If product is not in the map with, add it with quantity 1
	 *
	 * @param product
	 */
	@Override
	public void addProduct(Product product) {
		if (products.containsKey(product)) {
			products.replace(product, products.get(product) + 1);
		} else {
			products.put(product, 1);
		}
	}

	/**
	 * If product is in the map with quantity > 1, just decrement quantity by 1.
	 * If product is in the map with quantity 1, remove it from map
	 *
	 * @param product
	 */
	@Override
	public void removeProduct(Product product) {
		if (products.containsKey(product)) {
			if (products.get(product) > 1)
				products.replace(product, products.get(product) - 1);
			else if (products.get(product) == 1) {
				products.remove(product);
			}
		}
	}

	/**
	 * @return unmodifiable copy of the map
	 */
	@Override
	public Map<Product, Integer> getProductsInCart() {
		return Collections.unmodifiableMap(products);
	}


	/**
	 * Checkout will rollback if there is not enough of some product in stock
	 *
	 * @throws NotEnoughProductsInStockException
	 */
	@Override
	public void checkout() throws NotEnoughProductsInStockException {
		Product product;
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			// Refresh quantity for every product before checking
			//product = productRepository.findOne(entry.getKey().getId());
			product = productRepository.findOneById(entry.getKey().getId());
			if (product.getQuantity() < entry.getValue())
				throw new NotEnoughProductsInStockException(product);
			entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
		}
		//productRepository.save(products.keySet());
		productRepository.saveAll(products.keySet());
		//productRepository.flush();
		products.clear();
	}

	@Override
	public BigDecimal getTotal() {
		return products.entrySet().stream()
				.map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
}
