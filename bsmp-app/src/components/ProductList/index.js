import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from 'react-bootstrap-v5';
import DefaultModal from "../Modal";
import addToCartService from "../../apiService/addToCart";

const ProductList = ({ products, addToCartProduct, addToWishListProduct }) => {
  const ClickHandler = () => {
    window.scrollTo(10, 0);
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
  const handleAddToCart =async(data)=>{
    let {discount,id,price,}=data;
    let dData=
    {
      discountAmount: discount,
      grandTotal: price,
      items: [
        {
          itemCount: 1,
          productId: id,
          totalAmount: price,
        }
      ],
      lastUpdated: new Date().toDateString(),
      subtotalAmount: price,
      taxAmount: 0,
      totalItemCount: 1,
      totalItems: 1,
      userId: "user1"
    }
    addToCartProduct(data);
   await addToCartService(dData);
  }

  return (
    <div className="product-list">
      <div className="product-wrap">
        <div className="row align-items-center">
          {products.length > 0 &&
            products.slice(0, 6).map((product, pitem) => (
              <div className="col-xl-12 col-12" key={pitem}>
                <div className="product-item">
                  <div className="product-img">
                    <img src={product.image} alt="" />
                    <ul>
                      <li>
                        <button
                          data-bs-toggle="tooltip"
                          data-bs-html="true"
                          title="Add to Cart"
                          onClick={() => handleAddToCart(product)}
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
                      <span>{product.offer}</span>
                    </div>
                  </div>
                  <div className="product-content">
                    <h3>
                      <Link
                        onClick={ClickHandler}
                        to={`/product-single/${product.id}`}
                      >
                        {product.title}
                      </Link>
                    </h3>
                    <div className="product-btm">
                      <div className="product-price">
                        <ul>
                          <li>${product.price}</li>
                          <li>${product.delPrice}</li>
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
                      <Button className="cBtnTheme" onClick={()=>addToCartProduct(product)} style={{border: 'none'}}>Add to Cart</Button>
                    </div>
                    <p>
                      Lorem ipsum dolor sit amet consectetur, adipisicing elit.
                      Aperiam consequuntur laudantium quod ratione nulla modi?
                      Repudiandae quidem dicta quia eveniet dignissimos.
                    </p>
                  </div>
                </div>
              </div>
            ))}
        </div>
        <DefaultModal
          addToCartProduct={addToCartProduct}
          open={open}
          onClose={handleClose}
          product={state}
        />
      </div>
    </div>
  );
};

export default ProductList;
