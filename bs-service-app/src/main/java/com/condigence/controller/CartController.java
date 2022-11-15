package com.condigence.controller;

import com.condigence.dto.CartDTO;
import com.condigence.dto.ProductDTO;
import com.condigence.dto.SubscriptionDetailDTO;
import com.condigence.dto.UserDTO;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Product;
import com.condigence.model.Subscription;
import com.condigence.service.CartService;
import com.condigence.service.ProductService;
import com.condigence.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin()
@RequestMapping("/api/bs-cart")
public class CartController {

    public static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ProductService productService;

    private final UserService userService;

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/healthCheck")
    public String sayHello() {
        return "Hello, I am doing fine!";
    }

//	@GetMapping("/{userId}")
//	public ResponseEntity<?> getCartByUserId(@PathVariable String userId) {
//		logger.info("Fetching Cart with userid {}", userId);
//
//		CartDTO dto = cartService.getProductsInCartByUserId(userId);
//		if (!dto.getUserDTO().getId().equalsIgnoreCase(userId)) {
//			return new ResponseEntity(new CustomErrorType("Cart not found for User Id : "+userId), HttpStatus.NOT_FOUND);
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(dto);
//	}

//	@PostMapping("/")
//	public ResponseEntity<?> addToCart(@RequestBody CartBean cartBean) {
//		logger.info("Entering addBrands with Brand Details >>>>>>>>  : {}", cartBean);
//		HttpHeaders headers = new HttpHeaders();
//		cartService.addToCart(cartBean);
//		return new ResponseEntity<>(headers, HttpStatus.CREATED);
//	}

    @SuppressWarnings({"unchecked", "rawtypes"})
//	@DeleteMapping(value = "/{userId}")
//	public ResponseEntity<?> clearCart(@PathVariable("userId") String userId) {
//		logger.info("Fetching & Deleting Cart with id {}", userId);
//		CartDTO item = cartService.getCartByUserId(userId);
//		if (item != null) {
//			cartService.deleteCartByUserId(userId);
//		} else {
//			logger.error("Unable to delete. Item with id {} not found.", userId);
//			return new ResponseEntity(new CustomErrorType("Unable to delete. Cart with userId " + userId + " not found."),
//					HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<CartDTO>(HttpStatus.OK);
//	}
    ////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/?userId=")
    public ResponseEntity<?> shoppingCartForUser(@RequestParam(name="userId", required=false) String userId) {
        CartDTO dto = cartService.getProductsInCart(userId);
        if (dto.getSubscriptionDetails().size() == 0 && dto.getItemDetails().size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Cart is Empty!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/")
    public ResponseEntity<?> shoppingCart() {
        logger.info("Entering shoppingCart");
        CartDTO dto = cartService.getProductsInCart();
        if (dto.getSubscriptionDetails().size() == 0 && dto.getItemDetails().size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") String productId) {
        logger.info("Inside addProductToCart() with Product Id : "+productId);
        productService.findById(productId).ifPresent(cartService::addProduct);
        logger.info("Existing addProductToCart()");
        return shoppingCart();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductWithQuantityToCart(@RequestBody Product product) {
        logger.info("Inside addProductWithQuantityToCart() with Product Id : "+product.getId() + " and Quantity : " + product.getQuantity());
        if(productService.findById(product.getId()).isPresent()){
            cartService.addProduct(product);
        }else{
            logger.warn("Either Product is not available or product quantity is not provided.");
        }
        logger.info("Existing addProductWithQuantityToCart()");
        return shoppingCart();
    }

    @GetMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("productId") String productId) {
        productService.findById(productId).ifPresent(cartService::removeProduct);
        return shoppingCart();
    }

    @GetMapping("/remove/all/{productId}")
    public ResponseEntity<?> removeAllProductFromCart(@PathVariable("productId") String productId) {
        productService.findById(productId).ifPresent(cartService::removeAllProduct);
        return shoppingCart();
    }


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeProductItemsToCart(@RequestBody Subscription subscription ) {
        // check if product is available
        if(productService.findById(subscription.getProductId()).isPresent()){
            cartService.subscribeProduct(subscription);
            return shoppingCart();
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("Product That your are trying to add does not exist! Please contact admin!");
        }
    }

    @PutMapping("/update/subscription")
    public ResponseEntity<?> updateSubscriptions(@RequestBody Subscription subscription ) {
        // check if product is available
        if(productService.findById(subscription.getProductId()).isPresent()){
            cartService.subscribeProduct(subscription);
            return shoppingCart();
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("Product That your are trying to update does not exist! Please contact admin!");
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unSubscribeProductFromCart(@RequestBody Subscription subscription) {
        cartService.unsubscribeProduct(subscription);
        return shoppingCart();
    }

    @PostMapping("/unsubscribe/all")
    public ResponseEntity<?> unsubscribeAll(@RequestBody Subscription subscription) {
        cartService.unsubscribeAllProduct(subscription);
        return shoppingCart();
    }

    //loggedUserId

    @GetMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam(required = false) String userId) {

        CartDTO dto = cartService.getProductsInCart();
        UserDTO userDTO = userService.getUserById(userId);
        dto.setUserDTO(userDTO);
        try {
            cartService.checkout(dto);
        } catch (NotEnoughProductsInStockException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }
}




