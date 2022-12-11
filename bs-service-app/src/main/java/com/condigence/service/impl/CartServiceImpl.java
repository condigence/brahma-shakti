package com.condigence.service.impl;

import com.condigence.bean.ProductBean;
import com.condigence.dto.*;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Cart;
import com.condigence.model.CartDetail;
import com.condigence.model.Product;
import com.condigence.model.Subscription;
import com.condigence.repository.CartRepository;
import com.condigence.repository.ProductRepository;
import com.condigence.repository.SubscriptionRepository;
import com.condigence.service.CartService;
import com.condigence.service.ProductService;
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

    @Autowired
    private ProductService productService;



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

    @Override
    public CartDTO getCartByUserId(String userId) {
        return null;
    }

    private CartDTO getMyCart(String convId, String userId) {
        CartDTO dto = cartMap.get(convId);
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
            // delete Product itself
            cart.getProductsPicked().remove(product.getId());
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
            subscriptionDetailDTO.setFromDate(entry.getValue().getFromDate());
            subscriptionDetailDTO.setToDate(entry.getValue().getToDate());
            subscriptionDetailDTO.setFrequency(entry.getValue().getFrequency());
            subscriptionDetailDTO.setNoOfDays(entry.getValue().getNoOfDays());
            subscriptionDetailDTO.setStatus("PENDING");
            subscriptionDetailDTOS.add(subscriptionDetailDTO);
            sum += subscriptionDetailDTO.getTotalAmount();
            cartItems.setGrandTotal(sum);
            count += entry.getValue().getQuantity();
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
     * @return
     * @throws NotEnoughProductsInStockException
     */

    //Definition of checkout process in eCommerce.
    // It is the moment when customers place an order in an online store and proceed to payment.
    // It starts when the user visits the website, continues with the review of the product catalogue
    // and ends when the payment is completed and the confirmation is received.
    @Override
    public CartDTO checkout(CartDTO cartDTO) throws NotEnoughProductsInStockException {
        //TODO : Need to work on
        List<Product> productList = new ArrayList<>();
        Product product;
        for (CartDetailDTO cartDetailDTO : cartDTO.getItemDetails()) {
            // Refresh quantity for every product before checking
            product = productRepository.findOneById(cartDetailDTO.getProductDTO().getId());
            if (product.getQuantityInStock() < cartDetailDTO.getProductDTO().getQuantity())
                throw new NotEnoughProductsInStockException(product);
            product.setQuantityInStock(product.getQuantityInStock() - cartDetailDTO.getProductDTO().getQuantity());
            productList.add(product);
        }
        productRepository.saveAll(productList);
        //// save Cart details to DB
        Cart cart = saveCart(cartDTO);
        cartDTO = populateCartDetails(cart, cartDTO); // TODO: New change as per Virender Request Fixed on 06-12-22
        cartDTO.getProductsPicked().clear();
        cartDTO.getSubscriptions().clear();
        return cartDTO;
    }

    private CartDTO populateCartDetails(Cart cart, CartDTO cartDTO) {
        cartDTO.setConvId(cart.getConvId());
        cartDTO.setTaxAmount(cart.getTaxAmount());
        cartDTO.setDiscountAmount(cart.getDiscountAmount());
        cartDTO.setTotalItemCount(cart.getTotalItemCount());
        cartDTO.setSubtotalAmount(cart.getSubtotalAmount());
        cartDTO.setUserId(cart.getUserId());
        cartDTO.setLastUpdated(cart.getLastUpdated());
        List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
        for(CartDetail cartDetail : cart.getItemDetails()){
            CartDetailDTO cartDetailDTO = new CartDetailDTO();
            cartDetailDTO.setItemQuantity(cartDetail.getItemQuantity());
            cartDetailDTO.setId(cartDetail.getId());
            cartDetailDTO.setTotalAmount(cartDetail.getTotalAmount());
            cartDetailDTO.setProductDTO(productService.getProductById(cartDetail.getProductId()));
            cartDetailDTOS.add(cartDetailDTO);
        }
        cartDTO.setItemDetails(cartDetailDTOS);
        List<SubscriptionDetailDTO> subscriptionDetailDTOS = new ArrayList<>();
        Integer total = 0;
        for(Subscription subscription : cart.getSubscriptionDetails()){
            SubscriptionDetailDTO dto = new SubscriptionDetailDTO();
            dto.setStatus(subscription.getStatus());
            dto.setFrequency(subscription.getFrequency());
            dto.setToDate(subscription.getToDate());
            dto.setFromDate(subscription.getFromDate());
            dto.setItemQuantity(subscription.getQuantity());
            dto.setNoOfDays(subscription.getNoOfDays());
            dto.setId(subscription.getId());
            dto.setProductDTO(productService.getProductById(subscription.getProductId()));
            total = total + (dto.getItemQuantity() + dto.getProductDTO().getPrice());
            dto.setTotalAmount(total);
            subscriptionDetailDTOS.add(dto);
        }
        cartDTO.setSubscriptionDetails(subscriptionDetailDTOS);
        return cartDTO;

    }

    private Cart saveCart(CartDTO cartDTO) {

        Cart cart = new Cart();
        List<Subscription> subscriptions = new ArrayList<>();
        List<CartDetail> cartDetails = new ArrayList<>();
        for (SubscriptionDetailDTO subscriptionDetailDTO : cartDTO.getSubscriptionDetails()) {
            Subscription subscription = new Subscription();
            subscription.setQuantity(subscriptionDetailDTO.getItemQuantity());
            subscription.setFrequency(subscriptionDetailDTO.getFrequency());
            subscription.setToDate(subscriptionDetailDTO.getToDate());
            subscription.setFromDate(subscriptionDetailDTO.getFromDate());
            subscription.setProductId(subscriptionDetailDTO.getProductDTO().getId());
            subscription.setUserId(cartDTO.getUserId());
            subscriptions.add(subscription);
        }

        cart.setSubscriptionDetails(subscriptions);
        // save Subscription
        subscriptionRepository.saveAll(subscriptions);

        for (CartDetailDTO cartDetailDTO : cartDTO.getItemDetails()) {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProductId(cartDetailDTO.getProductDTO().getId());
            cartDetail.setItemQuantity(cartDetailDTO.getItemQuantity());
            cartDetail.setTotalAmount(cartDetailDTO.getTotalAmount());
            cartDetails.add(cartDetail);
        }

        cart.setItemDetails(cartDetails);
        cart.setDiscountAmount(cartDTO.getDiscountAmount());
        cart.setSubtotalAmount(cartDTO.getSubtotalAmount());
        cart.setGrandTotal(cartDTO.getGrandTotal());
        cart.setTaxAmount(cartDTO.getTaxAmount());
        cart.setLastUpdated(cartDTO.getLastUpdated());
        if(cartDTO.getUserDTO() != null){
            cart.setUserId(cartDTO.getUserDTO().getId());
            cart.setConvId(cartDTO.getUserDTO().getContact());
        }
        //save cart
        Cart savedCart = repository.save(cart);
        return savedCart;
    }


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
            // delete Product itself
            cart.getSubscriptions().remove(subscription.getProductId());
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

    @Override
    public void clearCart(String convId, String userId) {
        CartDTO cartDTO= getMyCart(convId,userId);
        cartDTO.getProductsPicked().clear();
        cartDTO.getSubscriptions().clear();
    }

}

