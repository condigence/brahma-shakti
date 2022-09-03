import React, { Fragment, useState,useEffect } from "react";
import { connect } from "react-redux";
import Navbar from "../../components/Navbar";
import PageTitle from "../../components/pagetitle";
import Footer from "../../components/footer";
import Scrollbar from "../../components/scrollbar";
import FilterSidebar from "../../components/FilterSidebar";
import FilterAllProduct from "../../components/FilterAllProduct";
import api from "../../api";
import { addToCart, addToWishList } from "../../store/actions/action";
import axios from "axios";

const ShopPage = ({ addToCart, addToWishList }) => {
  const productsArray = api();
  // axios.get("http://ec2-3-108-59-192.ap-south-1.compute.amazonaws.com:8080/brahmashakti/bs-products").then(res=>{
  //   console.log(res.data);
  //   productsArray=res.data;
  // })
  console.log("productsArray",productsArray)

  const [filter, setFilter] = useState({
    price: "",
    size: "",
    color: "",
    brand: "",
  });
  const [search, setSearch] = useState("");
  const priceChangeHandler = ({ target: { name, value } }) => {
    const val = typeof value === "string" ? JSON.parse(value) : value;
    setFilter({ ...filter, [name]: val });
  };

  const changeHandler = ({ target: { name, value } }) => {
    setFilter({ ...filter, [name]: value });
  };
  const priceFIlter = (price) => {
    if (filter.price === "") {
      return true;
    } else if (filter.price.max && filter.price.min) {
      return price <= filter.price.max && price >= filter.price.min;
    } else if (filter.price.min) {
      return price >= filter.price.min;
    } else {
      return false;
    }
  };

  const addToCartProduct = (product) => {
    addToCart(product, 1, filter.color, filter.size);
  };

  // var products = productsArray
  //   .filter((el) => priceFIlter(el.price))
    // .filter((el) => (filter.size ? el.size === filter.size : true))
    // .filter((el) => (filter.color ? el.color === filter.color : true))
    // .filter((el) => (filter.brand ? el.brand === filter.brand : true));


  var products=productsArray.filter((product)=>{
    if(product.title
      .toUpperCase()
      .includes(search.toUpperCase().trim().replace(/\s/g, ""))
  )return product;
  })  
  const addToWishListProduct = (products) => {
    addToWishList(products);
  };

  return (
    <Fragment>
      <Navbar hClass={"header-style-2"} />
      <PageTitle pageTitle={"Shop"} pagesub={"Shop"} />
      <div className="shop-section">
        <div className="container">
          <div className="row">
            <FilterSidebar
              filter={filter}
              search={search}
              setSearch={setSearch}
              priceChangeHandler={priceChangeHandler}
              changeHandler={changeHandler}
            />
            <FilterAllProduct
              addToCartProduct={addToCartProduct}
              addToWishListProduct={addToWishListProduct}
              products={products}
            />
          </div>
        </div>
      </div>
      <Footer />
      <Scrollbar />
    </Fragment>
  );
};

export default connect(null, { addToCart, addToWishList })(ShopPage);
