package com.condigence.controller;

import com.condigence.dto.CartDTO;
import com.condigence.dto.SubscriptionDetailDTO;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Product;
import com.condigence.model.Subscription;
import com.condigence.service.CartService;
import com.condigence.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/bs-cart")
public class CartController {

    public static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
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
        CartDTO dto = cartService.getProductsInCart();
        if (dto.getSubscriptionDetails().size() == 0 && dto.getItemDetails().size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Cart is Empty!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") String productId) {
        productService.findById(productId).ifPresent(cartService::addProduct);
        return shoppingCart();
    }

    @GetMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("productId") String productId) {
        productService.findById(productId).ifPresent(cartService::removeProduct);
        return shoppingCart();
    }

//    @GetMapping("/subscribe/{productId}")
//    public ResponseEntity<?> subscribeProductToCart(@PathVariable("productId") String productId) {
//        productService.findById(productId).ifPresent(cartService::subscribeProduct);
//        return shoppingCart();
//    }

//    @GetMapping("/subscribe/{productId}")
//    public ResponseEntity<?> subscribeProductItemsToCart(@PathVariable("productId") String productId, @RequestBody SubscriptionDetailDTO dto) {
//      //  productService.findById(productId).ifPresent(cartService::subscribeProduct);
//        Optional<Product> product = productService.findById(productId);
//        if(product.isPresent()){
//            cartService.subscribeProduct(product.get(),dto);
//        }
//        return shoppingCart();
//    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeProductItemsToCart(@RequestBody Subscription subscription ) {
        cartService.subscribeProduct(subscription);
        return shoppingCart();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unSubscribeProductFromCart(@RequestBody Subscription subscription) {
        cartService.unsubscribeProduct(subscription);
        return shoppingCart();
    }

    @GetMapping("/checkout")
    public ResponseEntity<?> checkout() {
        try {
            cartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }
}




