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
import com.condigence.utils.CustomErrorType;
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

//    @GetMapping("/?userId=")
//    public ResponseEntity<?> shoppingCartForUser(@RequestParam(name="userId", required = true) String userId) {
//        CartDTO dto = cartService.getProductsInCart(userId);
//        if (dto.getSubscriptionDetails().size() == 0 && dto.getItemDetails().size() == 0) {
//            return ResponseEntity.status(HttpStatus.OK).body("Cart is Empty!");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(dto);
//    }

    @GetMapping("/")
    public ResponseEntity<?> shoppingCart(@RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        logger.info("Entering shoppingCart");
        CartDTO dto = cartService.getProductsInCart(convId, userId);
        if (dto.getProductsPicked().size() == 0) {
            return new ResponseEntity(new CustomErrorType("Sorry You do not have any Items in your your the cart!:( "),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") String productId, @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        logger.info("Inside addProductToCart() with Product Id : "+productId);

        Product product = null;
        if(productService.findById(productId).isPresent()){
            product =  productService.findById(productId).get();
            if(product.getQuantity() <= 1){
                product.setQuantity(1);
            }
        }else{
            System.out.println("Sorry Product Id is not correct, So product can not be added to the cart!");
            logger.info("Existing addProductToCart()");
            return new ResponseEntity(new CustomErrorType("Sorry Product Id is not correct, So product can not be added to the cart!:( "),
                    HttpStatus.NOT_FOUND);
        }

        //check if user is not Logged In it means userid is not present
        if(userId == null){
            if(convId == null){
                System.out.println("Sorry convId or User Id is not present in the request, So product can not be added to the cart!");
                logger.info("Existing addProductToCart()");
                return new ResponseEntity(new CustomErrorType("Sorry convId or User Id is not present in the request, So product can not be added to the cart!:( "),
                        HttpStatus.BAD_REQUEST);
            }else{
                // Proceed as user is not Logged In
                cartService.addProduct(product, convId, null);
                return  shoppingCart(convId, null);
            }
        }else{
            // Proceed as user is Logged In
            cartService.addProduct(product, convId, userId);
            return  shoppingCart(convId,userId);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductWithQuantityToCart(@RequestBody Product product, @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        logger.info("Inside addProductWithQuantityToCart() with Product Id : "+product.getId() + " and Quantity : " + product.getQuantity());
        if(productService.findById(product.getId()).isPresent()){
            cartService.addProduct(product, convId, userId);
        }else{
            logger.warn("Either Product is not available or product quantity is not provided.");
            System.out.println("Sorry Product Id is not correct, So product can not be added to the cart!");
            return new ResponseEntity(new CustomErrorType("Sorry Product Id is not correct, So product can not be added to the cart!:( "),
                    HttpStatus.NOT_FOUND);
        }
        logger.info("Existing addProductWithQuantityToCart()");
        return shoppingCart(convId,userId);
    }

    @GetMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("productId") String productId, @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        if(productService.findById(productId).isPresent()){
            Product p = productService.findById(productId).get();
            cartService.removeProduct(p,convId,userId);
        }else{
            logger.warn("Either Product is not available or product quantity is not provided.");
            System.out.println("Sorry Product Id is not correct, So product can not be removed from the cart!");
            return new ResponseEntity(new CustomErrorType("Sorry Product Id is not correct, So product can not be removed from  the cart!:( "),
                    HttpStatus.NOT_FOUND);
        }
        return shoppingCart(convId, userId);
    }

    @GetMapping("/remove/all/{productId}")
    public ResponseEntity<?> removeAllProductFromCart(@PathVariable("productId") String productId, @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        if(productService.findById(productId).isPresent()){
            Product p = productService.findById(productId).get();
            cartService.removeAllProduct(p,convId,userId);
            return new ResponseEntity(new CustomErrorType("Product removed from cart Successfully !:) "),
                    HttpStatus.OK);
        }else{
            logger.warn("Either Product is not available or product quantity is not provided.");
            System.out.println("Sorry Product Id is not correct, So product can not be removed from the cart!");
            return new ResponseEntity(new CustomErrorType("Sorry Product Id is not correct, So product can not be removed from  the cart!:( "),
                    HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeProductItemsToCart(@RequestBody Subscription subscription , @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        // check if product is available
        if(productService.findById(subscription.getProductId()).isPresent()){
            cartService.subscribeProduct(subscription);
            return shoppingCart(convId, userId);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("Product That your are trying to add does not exist! Please contact admin!");
        }
    }

    @PutMapping("/update/subscription")
    public ResponseEntity<?> updateSubscriptions(@RequestBody Subscription subscription , @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        // check if product is available
        if(productService.findById(subscription.getProductId()).isPresent()){
            cartService.subscribeProduct(subscription);
            return shoppingCart(convId, userId);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("Product That your are trying to update does not exist! Please contact admin!");
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unSubscribeProductFromCart(@RequestBody Subscription subscription, @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        cartService.unsubscribeProduct(subscription);
        return shoppingCart(convId, userId);
    }

    @PostMapping("/unsubscribe/all")
    public ResponseEntity<?> unsubscribeAll(@RequestBody Subscription subscription, @RequestParam(required = true) String convId, @RequestParam(required = false) String userId) {
        cartService.unsubscribeAllProduct(subscription);
        return shoppingCart(convId, userId);
    }

    //loggedUserId

    @GetMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam(required = false) String userId, @RequestParam(required = true) String convId) {

        CartDTO dto = cartService.getProductsInCart(convId,userId);
        if(userId != null && !userId.equalsIgnoreCase("")){
            UserDTO userDTO = userService.getUserById(userId);
            dto.setUserDTO(userDTO);
        }

        try {
            cartService.checkout(dto);
        } catch (NotEnoughProductsInStockException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }
}




