package com.condigence.service.impl;

import com.condigence.bean.CartBean;
import com.condigence.bean.CartDetailBean;
import com.condigence.dto.*;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Cart;
import com.condigence.model.CartDetail;
import com.condigence.model.Product;
import com.condigence.repository.CartRepository;
import com.condigence.repository.ProductRepository;
import com.condigence.service.CartService;
import com.condigence.utils.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private ProductRepository productRepository;

	private Map<String, Integer> products = new HashMap<>();

	private Map<String, Integer> subscribedProducts = new HashMap<>();


//	@Override
//	public void addToCart(CartBean cartBean){
//		Cart cart = new Cart();
//		/////
//		cart.setUserId(cartBean.getUserId());
//		cart.setDiscountAmount(cartBean.getDiscountAmount());
//		cart.setGrandTotal(cartBean.getGrandTotal());
//		cart.setLastUpdated(cartBean.getLastUpdated());
//		cart.setSubtotalAmount(cartBean.getSubtotalAmount());
//		cart.setTaxAmount(cartBean.getTaxAmount());
//		List<CartDetail> cartDetails = new ArrayList<>();
//		for(CartDetailBean items :cartBean.getItems()) {
//			CartDetail cartDetail = new CartDetail();
//			Product product = productRepository.findOneById(items.getProductId());
//			cartDetail.setItemCount(items.getItemCount());
//			cartDetail.setTotalAmount(items.getTotalAmount());
//			cartDetails.add(cartDetail);
//		}
//
//		cart.setItemDetails(cartDetails);
//		//////////
//		repository.save(cart);
//
//	}

//	@Override
//	public CartDTO getCartByUserId(String userId) {
//		Cart cart = repository.findByUserId(userId);
//		if(cart != null){
//			CartDTO cartDTO = new CartDTO();
//			cartDTO.setId(cart.getId());
//			cartDTO.setUserId(cart.getUserId());
//			cartDTO.setDiscountAmount(cart.getDiscountAmount());
//			cartDTO.setGrandTotal(cart.getGrandTotal());
//			cartDTO.setLastUpdated(cart.getLastUpdated());
//			cartDTO.setSubtotalAmount(cart.getSubtotalAmount());
//			cartDTO.setTaxAmount(cart.getTaxAmount());
//
//			List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
//			for(CartDetail items :cart.getItemDetails()){
//				CartDetailDTO cartDetailDTO = new CartDetailDTO();
//				Product product = productRepository.findOneById(items.getProductId());
//				cartDetailDTO.setId(items.getId());
//				cartDetailDTO.setTotalAmount(items.getTotalAmount());
//				cartDetailDTOS.add(cartDetailDTO);
//			}
//			cartDTO.setItemDetails(cartDetailDTOS);
//			cartDTO.setLastUpdated(HelperUtil.getCurrentDateTIme());
//
//			return cartDTO;
//		}else{
//			return CartDTO.builder().id("0").userId("Not Found").build();
//		}
//
//	}

	/**
	 * @param userId
	 */
//	@Override
//	public void deleteCartByUserId(String userId) {
//		repository.delete(repository.findByUserId(userId));
//	}


	/**
	 * If product is in the map just increment quantity by 1.
	 * If product is not in the map with, add it with quantity 1
	 *
	 * @param product
	 */
	@Override
	public void addProduct(Product product) {
		if (products.containsKey(product.getId())) {
			products.replace(product.getId(), products.get(product.getId()) + 1);
		} else {
			products.put(product.getId(), 1);
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
		if (products.containsKey(product.getId())) {
			if (products.get(product.getId()) > 1)
				products.replace(product.getId(), products.get(product.getId()) - 1);
			else if (products.get(product.getId()) == 1) {
				products.remove(product.getId());
			}
		}
	}

	/**
	 * @return unmodifiable copy of the map
	 */
//	@Override
//	public Map<Product, Integer> getProductsInCart() {
//		return Collections.unmodifiableMap(products);
//	}

	@Override
	public CartDTO getProductsInCart() {
		CartDTO cartItems = new CartDTO();

		List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
		List<SubscriptionDetailDTO> subscriptionDetailDTOS = new ArrayList<>();
		int sum = 0;
		int count = 0;
		for (Map.Entry<String,Integer> entry : products.entrySet()){
			Product p = productRepository.findOneById(entry.getKey());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(p.getId());
			productDTO.setTitle(p.getName());
			productDTO.setPrice(p.getPrice());
			productDTO.setQuantity(p.getQuantity());
			productDTO.setQuantity(entry.getValue());
			productDTO.setStockLeft(p.getQuantityInStock());
			productDTO.setSubscribable(p.isSubscribable());
			CartDetailDTO cartDetailDTO = new CartDetailDTO();
			cartDetailDTO.setProductDTO(productDTO);
			cartDetailDTO.setItemQuantity(entry.getValue());
			cartDetailDTO.setTotalAmount(p.getPrice() * entry.getValue());
			cartDetailDTOS.add(cartDetailDTO);
			sum += cartDetailDTO.getTotalAmount();
			cartItems.setGrandTotal(sum);
			count += cartDetailDTO.getItemQuantity();
		}

		for (Map.Entry<String,Integer> entry : subscribedProducts.entrySet()){
			Product p = productRepository.findOneById(entry.getKey());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(p.getId());
			productDTO.setTitle(p.getName());
			productDTO.setPrice(p.getPrice());
			productDTO.setQuantity(p.getQuantity());
			productDTO.setQuantity(entry.getValue());
			productDTO.setSubscribable(p.isSubscribable());
			productDTO.setStockLeft(p.getQuantityInStock());
			SubscriptionDetailDTO subscriptionDetailDTO = new SubscriptionDetailDTO();
			subscriptionDetailDTO.setProductDTO(productDTO);
			subscriptionDetailDTO.setItemQuantity(entry.getValue());
			subscriptionDetailDTO.setTotalAmount(p.getPrice() * entry.getValue());
			/////
//			subscriptionDetailDTO.setFromDate(p.);
//			subscriptionDetailDTO.setToDate();
//			subscriptionDetailDTO.setFrequency();
//			subscriptionDetailDTO.getNoOfDays()
			////
			subscriptionDetailDTOS.add(subscriptionDetailDTO);
			sum += subscriptionDetailDTO.getTotalAmount();
			cartItems.setGrandTotal(sum);
			count += subscriptionDetailDTO.getItemQuantity();
		}

		cartItems.setLastUpdated(HelperUtil.getCurrentDateTIme());
		cartItems.setTotalItemCount(count);
		cartItems.setItemDetails(cartDetailDTOS);
		cartItems.setSubscriptionDetails(subscriptionDetailDTOS);
		return cartItems;
	}



	/**
	 * Checkout will rollback if there is not enough of some product in stock
	 *
	 * @throws NotEnoughProductsInStockException
	 */

	@Override
	public void checkout() throws NotEnoughProductsInStockException {
		List<Product> productList = new ArrayList<>();
		Product product;
		for (Map.Entry<String, Integer> entry : products.entrySet()) {
			// Refresh quantity for every product before checking
			//product = productRepository.findOne(entry.getKey().getId());
			product = productRepository.findOneById(entry.getKey());
			if (product.getQuantityInStock() < entry.getValue())
				throw new NotEnoughProductsInStockException(product);
			product.setQuantityInStock(product.getQuantityInStock() - entry.getValue());
			productList.add(product);
		}
		productRepository.saveAll(productList);
		//update order & orderDetails as well
		//productRepository.flush();
		products.clear();
	}

	@Override
	public void subscribeProduct(Product product) {
		if (subscribedProducts.containsKey(product.getId())) {
			subscribedProducts.replace(product.getId(), subscribedProducts.get(product.getId()) + 1);
		} else {
			subscribedProducts.put(product.getId(), 1);
		}
	}

	@Override
	public void unSubscribeProduct(Product product) {
		if (subscribedProducts.containsKey(product.getId())) {
			if (subscribedProducts.get(product.getId()) > 1)
				subscribedProducts.replace(product.getId(), subscribedProducts.get(product.getId()) - 1);
			else if (subscribedProducts.get(product.getId()) == 1) {
				subscribedProducts.remove(product.getId());
			}
		}
	}

//	@Override
//	public Integer getTotal() {
//		Integer sum = 0;
////		return products.entrySet().stream()
////				.map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
////				.reduce(BigDecimal::add)
////				.orElse(BigDecimal.ZERO);
//
//
//		for (Product p:products.keySet()) {
//			sum = p.getPrice() * p.getQuantity();
//		}
//
//		return sum;
//
//	}
}
