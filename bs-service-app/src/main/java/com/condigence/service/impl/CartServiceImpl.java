package com.condigence.service.impl;

import com.condigence.bean.ProductBean;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    private Map<String, CartDTO> cartMap = new HashMap<>();  //  convId ---> Cart { id, details }


    /**
     * if quantity is less than 2 i.e 1,0, Not Present
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     * <p>
     * if quantity is greater than 1
     * If product is in the map just increment quantity by provided quantity.
     * If product is not in the map with, add it with provided quantity
     *
     * @param product
     */
    @Override
    public void addProduct(Product product, String convId, String userId) {
        CartDTO cart = getMyCart(convId, userId);
        int newQuantity = 0;
        if (cart.getProductsPicked().get(product.getId()) != null) {
            newQuantity = (product.getQuantity() <= 1 ? 1 : product.getQuantity()) + cart.getProductsPicked().get(product.getId()).getQuantity();
        } else {
            newQuantity = product.getQuantity() <= 1 ? 1 : product.getQuantity();
        }
        product.setQuantity(newQuantity);

        if (cart.getProductsPicked().containsKey(product.getId())) {
            cart.getProductsPicked().replace(product.getId(), product);
        } else {
            cart.getProductsPicked().put(product.getId(), product);
        }
        updateMyCart(convId, userId, cart);
    }

    private CartDTO getMyCart(String convId, String userId) {
        CartDTO dto = null;
        if (userId != null) {  // it means User is Logged In
            dto = cartMap.get(userId);
        } else { // it means User is not Logged In
            dto = cartMap.get(convId);
        }
        if (dto == null) {   // it means User has no cart yet
            dto = new CartDTO();
            dto.setProductsPicked(new HashMap<>());
            dto.setSubscriptions(new HashMap<>());
        }
        return dto;
    }


    private void updateMyCart(String convId, String userId, CartDTO cartItems) {
        if (cartMap.containsKey(convId)) {
            cartMap.replace(convId, cartItems);
        } else {
            cartMap.put(convId, cartItems);
        }
    }


    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    @Override
    public void removeProduct(Product product, String convId, String userId) {
        CartDTO cart = getMyCart(convId, userId);
        int newQuantity = 0;
        int quantityToBeDeleted = product.getQuantity() <= 1 ? 1 : product.getQuantity();

        Product pb = cart.getProductsPicked().get(product.getId());
        if (pb.getQuantity() > quantityToBeDeleted) {
            newQuantity = pb.getQuantity() - quantityToBeDeleted;
        } else {
            // Can not delete
            newQuantity = 0;
        }

        product.setQuantity(newQuantity);
        if (cart.getProductsPicked().containsKey(product.getId())) {
            cart.getProductsPicked().replace(product.getId(), product);
        }
        updateMyCart(convId, userId, cart);
    }

    @Override
    public void removeAllProduct(Product product, String convId, String userId) {
        CartDTO cart = getMyCart(convId, userId);
        ProductBean productBean = new ProductBean();
        Product pb = cart.getProductsPicked().get(product.getId());
        productBean.setId(product.getId());
        if (cart.getProductsPicked().containsKey(product.getId())) {
            cart.getProductsPicked().remove(product.getId());
        }
        updateMyCart(convId, userId, cart);
    }


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
    public CartDTO getProductsInCart(String convId, String userId) {

        CartDTO cartItems = getMyCart(convId, userId);

        List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
        List<SubscriptionDetailDTO> subscriptionDetailDTOS = new ArrayList<>();
        int sum = 0;
        int count = 0;
        for (Map.Entry<String, Product> entry : cartItems.getProductsPicked().entrySet()) {
            Product p = entry.getValue();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getId());
            productDTO.setTitle(p.getName());
            productDTO.setPrice(p.getPrice());
            productDTO.setQuantity(p.getQuantity());
            productDTO.setStockLeft(p.getQuantityInStock());
            productDTO.setSubscribable(p.isSubscribable());
            CartDetailDTO cartDetailDTO = new CartDetailDTO();
            cartDetailDTO.setProductDTO(productDTO);
            cartDetailDTO.setItemQuantity(p.getQuantity());
            cartDetailDTO.setTotalAmount(p.getPrice() * p.getQuantity());
            cartDetailDTOS.add(cartDetailDTO);
            sum += cartDetailDTO.getTotalAmount();
            cartItems.setGrandTotal(sum);
            count += cartDetailDTO.getItemQuantity();
        }

        for (Map.Entry<String, Subscription> entry : cartItems.getSubscriptions().entrySet()) {
            Product p = productRepository.findOneById(entry.getKey());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getId());
            productDTO.setTitle(p.getName());
            productDTO.setPrice(p.getPrice());
            productDTO.setQuantity(entry.getValue().getQuantity());
            productDTO.setSubscribable(p.isSubscribable());
            productDTO.setStockLeft(p.getQuantityInStock());
            SubscriptionDetailDTO subscriptionDetailDTO = new SubscriptionDetailDTO();
            subscriptionDetailDTO.setProductDTO(productDTO);
            subscriptionDetailDTO.setItemQuantity(entry.getValue().getQuantity());
            subscriptionDetailDTO.setTotalAmount(p.getPrice() * entry.getValue().getQuantity());

//            for (Map.Entry<String, Subscription> items : subscriptionDetails.entrySet()) {
//                if (items.getKey().equalsIgnoreCase(entry.getKey())) {
//                    Subscription item = items.getValue();
            subscriptionDetailDTO.setFromDate(entry.getValue().getFromDate());
            subscriptionDetailDTO.setToDate(entry.getValue().getToDate());
            subscriptionDetailDTO.setFrequency(entry.getValue().getFrequency());
            subscriptionDetailDTO.setNoOfDays(entry.getValue().getNoOfDays());
            subscriptionDetailDTO.setStatus("PENDING");
//                }
//            }
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

    //Definition of checkout process in eCommerce.
    // It is the moment when customers place an order in an online store and proceed to payment.
    // It starts when the user visits the website, continues with the review of the product catalogue
    // and ends when the payment is completed and the confirmation is received.
    @Override
    public void checkout(CartDTO cartDTO) throws NotEnoughProductsInStockException {
        //TODO : Need to work on
        List<Product> productList = new ArrayList<>();
        Product product;
        for (Map.Entry<String, Product> entry : cartDTO.getProductsPicked().entrySet()) {
            // Refresh quantity for every product before checking
            //product = productRepository.findOne(entry.getKey().getId());
            product = productRepository.findOneById(entry.getKey());
            if (product.getQuantityInStock() < entry.getValue().getQuantity())
                throw new NotEnoughProductsInStockException(product);
            product.setQuantityInStock(product.getQuantityInStock() - entry.getValue().getQuantity());
            productList.add(product);
        }
        productRepository.saveAll(productList);
        //// save Cart details to DB
        // saveCart(cartDTO);
        cartDTO.getProductsPicked().clear();
        cartDTO.getSubscriptions().clear();
    }
//
//    private void saveCart(CartDTO cartDTO) {
//
//        Cart cart = new Cart();
//        List<Subscription> subscriptions = new ArrayList<>();
//        List<CartDetail> cartDetails = new ArrayList<>();
//        for (SubscriptionDetailDTO subscriptionDetailDTO : cartDTO.getSubscriptionDetails()) {
//            Subscription subscription = new Subscription();
//            subscription.setQuantity(subscriptionDetailDTO.getItemQuantity());
//            subscription.setFrequency(subscriptionDetailDTO.getFrequency());
//            subscription.setToDate(subscriptionDetailDTO.getToDate());
//            subscription.setFromDate(subscriptionDetailDTO.getFromDate());
//            subscription.setProductId(subscriptionDetailDTO.getProductDTO().getId());
//            subscription.setUserId(cartDTO.getUserId());
//            subscriptions.add(subscription);
//        }
//
//        cart.setSubscriptionDetails(subscriptions);
//        // save Subscription
//        subscriptionRepository.saveAll(subscriptions);
//
//        for (CartDetailDTO cartDetailDTO : cartDTO.getItemDetails()) {
//            CartDetail cartDetail = new CartDetail();
//            cartDetail.setProductId(cartDetailDTO.getProductDTO().getId());
//            cartDetail.setItemQuantity(cartDetailDTO.getItemQuantity());
//            cartDetail.setTotalAmount(cartDetailDTO.getTotalAmount());
//            cartDetails.add(cartDetail);
//        }
//
//        cart.setItemDetails(cartDetails);
//        cart.setDiscountAmount(cartDTO.getDiscountAmount());
//        cart.setSubtotalAmount(cartDTO.getSubtotalAmount());
//        cart.setGrandTotal(cartDTO.getGrandTotal());
//        cart.setTaxAmount(cartDTO.getTaxAmount());
//        cart.setLastUpdated(cartDTO.getLastUpdated());
//        cart.setUserId(cartDTO.getUserId());
//        //save cart
//        repository.save(cart);
//    }


    @Override
    public void unsubscribeProduct(Subscription subscription, String convId, String userId) {

        CartDTO cart = getMyCart(convId, userId);
        int newQuantity = 0;
        int quantityToBeDeleted = subscription.getQuantity() <= 1 ? 1 : subscription.getQuantity();

        if (cart.getSubscriptions().get(subscription.getProductId()).getQuantity() > quantityToBeDeleted) {
            newQuantity = cart.getSubscriptions().get(subscription.getProductId()).getQuantity() - quantityToBeDeleted;
        } else {
            // Can not delete
            newQuantity = 0;
        }

        subscription.setQuantity(newQuantity);
        if (cart.getSubscriptions().containsKey(subscription.getProductId())) {
            cart.getSubscriptions().replace(subscription.getProductId(),subscription);
        }
        updateMyCart(convId, userId, cart);
    }

    @Override
    public void unsubscribeAllProduct(Subscription subscription, String convId, String userId) {
        CartDTO cart = getMyCart(convId, userId);
        if (cart.getSubscriptions().containsKey(subscription.getProductId())) {
            cart.getSubscriptions().remove(subscription.getProductId());
        }
        updateMyCart(convId, userId, cart);
    }


    @Override
    public void subscribeProduct(Subscription subscription, String convId, String userId) {

        CartDTO cart = getMyCart(convId, userId);
        int newQuantity = 0;
        if (cart.getSubscriptions().get(subscription.getProductId()) != null) {
            newQuantity = subscription.getQuantity() + cart.getSubscriptions().get(subscription.getProductId()).getQuantity();
        } else {
            newQuantity = subscription.getQuantity();
        }
        subscription.setQuantity(newQuantity);
        if (cart.getSubscriptions().containsKey(subscription.getProductId())) {
            cart.getSubscriptions().replace(subscription.getProductId(), subscription);
        } else {
            cart.getSubscriptions().put(subscription.getProductId(), subscription);
        }
        updateMyCart(convId, userId, cart);
    }

    @Override
    public void updateSubscription(Subscription subscription, String convId, String userId) {

    }

}

