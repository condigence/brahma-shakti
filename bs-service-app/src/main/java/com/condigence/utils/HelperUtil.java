package com.condigence.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.condigence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HelperUtil {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public static Supplier<List<Product>> productSupplier = () ->
            Arrays.asList(
                    Product.builder().id("1").name("BUFFALO MILK").description("Fresh farm Buffalo Milk").quantityInStock(50).price(70).displayPrice(70).offers("FIRSTSELL,SAAVAN").imageLink("https://media-exp1.licdn.com/dms/image/C5622AQHN1-hZ79vEGQ/feedshare-shrink_800/0/1650417387213?e=2147483647&v=beta&t=vzNvN4dUEwEiuhBA0rQTOdGSFEP5FjnQbzKvDGB30Ls").rating(4.5).unit("in Liter").category("Dairy Products").isSubscribable(true).build(),
                    Product.builder().id("2").name("COW MILK").description("Fresh farm Cow Milk").quantityInStock(500).price(55).displayPrice(55).offers("FIRSTSELL").build(),
                    Product.builder().id("3").name("PANEER").description("Fresh farm Paneer").quantityInStock(500).price(320).displayPrice(320).imageLink("https://img.freepik.com/premium-photo/homemade-indian-paneer-cheese-wooden-bowl_114420-600.jpg").build()
            );
    public static Supplier<List<Cart>> cartSupplier = () ->
            Arrays.asList(
                    Cart.builder().id("1").userId("1").totalItemCount(5).subtotalAmount(620).lastUpdated("09: 41 20 - 07 - 2022").grandTotal(500).itemDetails(Arrays.asList(CartDetail.builder().id("1").productId("1").itemCount(2).totalAmount(140).build())).build()
            );
    public static Supplier<List<User>> userSupplier = () ->
            Arrays.asList(
                    User.builder().id("9742503868").username("condigence").firstName("Vishal").lastName("Aryan").address("S M Nagar").contact("9742503868").email("onlyvishalaaryan@gmail.com").password("$2a$12$VZSBLRb/bR9kOJofU.FQIuIK/r9z.61573K.W6XaERFFqkF2C6oMa").otp("1234").build(),
                    User.builder().id("9876543212").username("mukul").firstName("Mukul").lastName("Bhatiya").address("Mathura").contact("9876543212").email("mukul@gmail.com@gmail.com").password("$2a$12$VZSBLRb/bR9kOJofU.FQIuIK/r9z.61573K.W6XaERFFqkF2C6oMa").otp("1234").build()
            );
    public static Supplier<List<Category>> categorySupplier = () ->
            Arrays.asList(
                    Category.builder().categoryName("Vegetables").build(),
                    Category.builder().categoryName("Fruits").build(),
                    Category.builder().categoryName("Dairy Products").build(),
                    Category.builder().categoryName("Sweets").build()
            );

    public static Supplier<List<Order>> orderSupplier = () ->
            Arrays.asList(
                    Order.builder().id("1").dateTime("").discountAmount(0).build()
            );

    public static Supplier<List<Favourite>> favouriteSupplier = () ->
            Arrays.asList(
                    Favourite.builder().productId("1").userId("2").build(),
                    Favourite.builder().productId("3").userId("4").build(),
                    Favourite.builder().productId("5").userId("6").build(),
                    Favourite.builder().productId("7").userId("8").build()
            );

    private HelperUtil() {
    }



}
