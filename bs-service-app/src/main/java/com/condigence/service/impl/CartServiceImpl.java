package com.condigence.service.impl;

import com.condigence.dto.*;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Product;
import com.condigence.model.Subscription;
import com.condigence.repository.CartRepository;
import com.condigence.repository.ProductRepository;
import com.condigence.repository.SubscriptionRepository;
import com.condigence.service.CartService;
import com.condigence.service.UserService;
import com.condigence.utils.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    private Map<String, Integer> products = new HashMap<>();

    private Map<Subscription, Integer> subscribedProducts = new HashMap<>();


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


//	The authentication instance now provides the following methods:
//
//	Get the username of the logged in user: getPrincipal()
//	Get the password of the authenticated user: getCredentials()
//	Get the assigned roles of the authenticated user: getAuthorities()
//	Get further details of the authenticated user: getDetails()
    public UserDTO getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        UserDTO userDTO = userService.getUserById(auth.getPrincipal().toString());
        return userDTO;
    }

    @Override
    public CartDTO getProductsInCart(String userId) {
        UserDTO userDTO = new UserDTO();
        if (userId != null || !userId.equalsIgnoreCase("")) {
            userDTO = getLoggedInUser();
        }
        CartDTO cartItems = getProductsInCart();
        cartItems.setUserDTO(userDTO);
        return cartItems;
    }

    @Override
    public CartDTO getProductsInCart() {

        CartDTO cartItems = new CartDTO();

        List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
        List<SubscriptionDetailDTO> subscriptionDetailDTOS = new ArrayList<>();
        int sum = 0;
        int count = 0;
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
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

        for (Map.Entry<Subscription, Integer> entry : subscribedProducts.entrySet()) {
            Product p = productRepository.findOneById(entry.getKey().getProductId());
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
            subscriptionDetailDTO.setFromDate(entry.getKey().getFromDate());
            subscriptionDetailDTO.setToDate(entry.getKey().getToDate());
            subscriptionDetailDTO.setFrequency(entry.getKey().getFrequency());
            subscriptionDetailDTO.setNoOfDays(entry.getKey().getNoOfDays());
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
        products.clear();
    }


    @Override
    public void unsubscribeProduct(Subscription subscription) {
        if (subscribedProducts.containsKey(subscription)) {
            if (subscribedProducts.get(subscription) > 1)
                subscribedProducts.replace(subscription, subscribedProducts.get(subscription) - 1);
            else if (subscribedProducts.get(subscription) == 1) {
                subscribedProducts.remove(subscription);
            }
        }
    }

    @Override
    public CartDTO getProductsInCartByUserId(String userId) {
        return null;
    }

    @Override
    public void subscribeProduct(Subscription subscription) {

        if (subscribedProducts.containsKey(subscription)) {
            subscribedProducts.replace(subscription, subscribedProducts.get(subscription) + 1);
        } else {
            subscribedProducts.put(subscription, 1);
        }

    }
}
