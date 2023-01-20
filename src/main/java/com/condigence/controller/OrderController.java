package com.condigence.controller;

import com.condigence.bean.OrderBean;
import com.condigence.config.RazorPayClientConfig;
import com.condigence.dto.OrderDTO;
import com.condigence.model.Order;
import com.condigence.service.CartService;
import com.condigence.service.OrderService;
import com.condigence.service.UserService;
import com.condigence.utils.CustomErrorType;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vish
 */
@Slf4j
@CrossOrigin()
@RestController
@RequestMapping("/api/bs-order")
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private RazorpayClient client;

    private RazorPayClientConfig razorPayClientConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;


    @GetMapping("/healthCheck")
    public String sayHello() {
        return "Hello, I am doing fine!";
    }

    @GetMapping("/order-history/{userId}")
    public ResponseEntity<?> orderHistory(@PathVariable String userId) {

        List<OrderDTO> myOrders;
        // check if user Exists
        if (userService.isUserIdExists(userId)) {
            myOrders = orderService.getAllOrderByUserId(userId);
            if (myOrders.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(myOrders);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<OrderDTO>());
            }

        } else {
            return new ResponseEntity(new CustomErrorType("Unable to find. Order with userId " + userId), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/")
    public ResponseEntity<?> placeOrder(@RequestBody OrderBean orderBean) {
        logger.info("Entering placeOrder with Order Details >>>>>>>>  : {}", orderBean);
        HttpHeaders headers = new HttpHeaders();
        orderService.placeOrder(orderBean);
        cartService.clearCart("convId", orderBean.getUserId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") String orderId) {
        logger.info("Fetching & Deleting Order with orderId {}", orderId);
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            orderService.deleteById(orderId);
        } else {
            logger.error("Unable to delete. Item with id {} not found.", orderId);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Order with userId " + orderId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<OrderDTO>(HttpStatus.OK);
    }

    //////////////////////////////

//	@Autowired
//	public OrderController(RazorPayClientConfig razorpayClientConfig) throws RazorpayException {
//		this.razorPayClientConfig = razorpayClientConfig;
//		this.client = new RazorpayClient(razorpayClientConfig.getKey(), razorpayClientConfig.getSecret());
//	}

//	@PostMapping("/order")
//	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, @CurrentUser LocalUser user) {
//		OrderResponse razorPay = null;
//		try {
//			// The transaction amount is expressed in the currency subunit, such
//			// as paise (in case of INR)
//			String amountInPaise = convertRupeeToPaise(orderRequest.getAmount());
//			// Create an order in RazorPay and get the order id
//			Order order = createRazorPayOrder(amountInPaise);
//			razorPay = getOrderResponse((String) order.get("id"), amountInPaise);
//			// Save order in the database
//			orderService.saveOrder(razorPay.getRazorpayOrderId(), user.getUser().getId());
//		} catch (RazorpayException e) {
//			log.error("Exception while create payment order", e);
//			return new ResponseEntity<>(new ApiResponse(false, "Error while create payment order: " + e.getMessage()), HttpStatus.EXPECTATION_FAILED);
//		}
//		return ResponseEntity.ok(razorPay);
//	}

//	@PutMapping("/order")
//	public ResponseEntity<?> updateOrder(@RequestBody PaymentResponse paymentResponse) {
//		String errorMsg = orderService.validateAndUpdateOrder(paymentResponse.getRazorpayOrderId(), paymentResponse.getRazorpayPaymentId(), paymentResponse.getRazorpaySignature(),
//				razorPayClientConfig.getSecret());
//		if (errorMsg != null) {
//			return new ResponseEntity<>(new ApiResponse(false, errorMsg), HttpStatus.BAD_REQUEST);
//		}
//		return ResponseEntity.ok(new ApiResponse(true, paymentResponse.getRazorpayPaymentId()));
//	}

//	private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
//		OrderResponse razorPay = new OrderResponse();
//		razorPay.setApplicationFee(amountInPaise);
//		razorPay.setRazorpayOrderId(orderId);
//		razorPay.setSecretKey(razorPayClientConfig.getKey());
//		return razorPay;
//	}

//    private Order createRazorPayOrder(String amount) throws RazorpayException {
//        JSONObject options = new JSONObject();
//        options.put("amount", amount);
//        options.put("currency", "INR");
//        options.put("receipt", "txn_123456");
//        return (Order)client.Orders.create(options);
//    }

//	private String convertRupeeToPaise(String paise) {
//		BigDecimal b = new BigDecimal(paise);
//		BigDecimal value = b.multiply(new BigDecimal("100"));
//		return value.setScale(0, RoundingMode.UP).toString();
//	}

}




