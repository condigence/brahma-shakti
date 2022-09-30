import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from 'react-bootstrap-v5';

import DefaultModal from "../../../src/components/Modal";

const Card = (props) => {
   // products, addToCartProduct,addToWishListProduct
  const  {handleNext, products, addToWishListProduct,setSubProduct}=props

  // console.log(products)
  const ClickHandler = () => {
    window.scrollTo(0,300);
  };
  const [open, setOpen] = React.useState(false);

  function handleClose() {
    setOpen(false);
  }

  const [state, setState] = useState({});

  const handleClickOpen = (item) => {
    setOpen(true);
    setState(item);
  };
  const handleSubscriptionClick = (product)=>{
    setSubProduct(product);
    handleNext();
    // addSubProduct(product);
    ClickHandler()

  }
  // console.log(subProduct);
  return (
    <div className="p-4">
      <div className="section-title mb-0">
      <h2>Step <span>1</span> </h2>
          <h2>Select Product For <span>Subscription</span></h2>
      </div>
      <div className="product-wrap">
        <div className="row align-items-center">
          {products.length > 0 &&
            products.map((product, pitem) => (
            <div
              className="col-xl-4 col-lg-6 col-md-6 col-sm-12 col-12"
              key={pitem}
            >
              <div className="product-item">
                <div className="product-img">
                  <img src={product.image} alt="" />
                  <ul>
                    <li>
                      <button
                        data-bs-toggle="tooltip"
                        data-bs-html="true"
                        title="Add to Cart"
                        onClick={() => handleSubscriptionClick(product)}
                      >
                        <i className="fi flaticon-shopping-cart"></i>
                      </button>
                    </li>
                    <li>
                        <button
                            data-bs-toggle="tooltip"
                            data-bs-html="true"
                            title="Add to Cart"
                            onClick={() => handleClickOpen(product)}
                          >
                            <i className="fi ti-eye"></i>
                        </button>
                    </li>
                    <li>
                      <button
                          data-bs-toggle="tooltip"
                          data-bs-html="true"
                          title="Add to Cart"
                          onClick={() => addToWishListProduct(product)}
                      >
                          <i className="fi flaticon-like"></i>
                      </button>
                    </li>
                  </ul>
                  <div className="offer-thumb">
                    <span>{`${product.discount} %`}</span>
                  </div>
                </div>
                <div className="product-content">
                  <h3>
                    {/* <Link onClick={ClickHandler} to={`/product-single/${product.id}`}> */}
                      {product.title}
                    {/* </Link> */}
                  </h3>
                  <div className="product-btm">
                    <div className="product-price">
                      <ul>
                        <li>₹{product.price}</li>
                        <li>₹{parseFloat(parseInt(product.price) * (parseInt(product.discount)/100 +1)).toFixed(2)}</li>
                      </ul>
                    </div>
                    <div className="product-ratting">
                      <ul>
                        <li>
                          <i className="fa fa-star" aria-hidden="true"></i>
                        </li>
                        <li>
                          <i className="fa fa-star" aria-hidden="true"></i>
                        </li>
                        <li>
                          <i className="fa fa-star" aria-hidden="true"></i>
                        </li>
                        <li>
                          <i className="fa fa-star" aria-hidden="true"></i>
                        </li>
                        <li>
                          <i className="fa fa-star" aria-hidden="true"></i>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div className="product-content mt-2" style={{textAlign: 'center'}}>
                  <div>
                    <Button className="cBtnTheme" onClick={()=>handleSubscriptionClick(product)} style={{border: 'none'}}>Subscribe</Button>
                    </div>
                    </div>
                </div>
              </div>
            </div>
          ))}
      </div>
      <DefaultModal
        handleSubscriptionClick={handleSubscriptionClick}
        open={open}
        onClose={handleClose}
        product={state}
      />
    </div>
    </div>
  );
};

export default Card;















