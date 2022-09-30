import React, { Fragment, useState } from "react";
import Navbar from "../../components/Navbar";
import PageTitle from "../../components/pagetitle";
import Footer from "../../components/footer";
import Scrollbar from "../../components/scrollbar";
import EditModal from "../../components/EditModal";
import { Button, Grid } from "@material-ui/core";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import { totalPrice } from "../../utils";
import {
  removeFromCart,
  incrementQuantity,
  decrementQuantity,
  removeFromSubCart,
  incrementSubQuantity,
  decrementSubQuantity,
  editSubProductCartItem,
} from "../../store/actions/action";
import "./style.css";

const CartPage = (props) => {
  const [showEditModal, setShowEditModal] = useState(0);
  const [editModalProduct, setEditModalProduct] = useState({});
  const ClickHandler = () => {
    window.scrollTo(10, 0);
  };

  const { carts, subCarts } = props;

  const subscriptionType = (type) => {
    if (type === 1) return "Everyday";
    else if (type === 2) return "3 Days(Tue,Thu,Sat)";
    else if (type === 3) return "Alternate";
  };
  const editChanges = (sub) => {
    props.editSubProductCartItem(sub, editModalProduct);
  };

  return (
    <Fragment>
      <Navbar hClass={"header-style-2"} />
      <PageTitle pageTitle={"Cart"} pagesub={"Cart"} />
      <div
        // onContextMenu={(e) => {
        //   e.preventDefault();
        //   debugger;
        // }}
        className="cart-area section-padding"
        onClick={() => {
          if (showEditModal === 1) setShowEditModal(0);
        }}
      >
        <div className="container">
          <div className="form">
            <div className="cart-wrapper">
              <div className="row">
                <div className="col-12">
                  <form action="cart">
                    <table className="table-responsive cart-wrap">
                      <thead>
                        <tr>
                          <th className="images images-b">Image</th>
                          <th className="product-2">Product Name</th>
                          <th className="pr">Quantity</th>
                          <th className="ptice">Price</th>
                          <th className="stock">Total Price</th>
                          <th className="remove remove-b">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {carts &&
                          carts.length > 0 &&
                          carts.map((catItem, crt) => (
                            <tr key={crt} className="normalCart">
                              <td className="images">
                                <img src={catItem.image} alt="" />
                              </td>
                              <td className="product">
                                <ul>
                                  <li className="first-cart">
                                    {catItem.title}
                                  </li>
                                  <li>Brand : {catItem.brand}</li>
                                  <li>Size : {catItem.size}</li>
                                </ul>
                              </td>
                              <td className="stock">
                                <div className="pro-single-btn">
                                  <Grid className="quantity cart-plus-minus">
                                    <Button
                                      className="dec qtybutton"
                                      onClick={() =>
                                        props.decrementQuantity(catItem.id)
                                      }
                                    >
                                      -
                                    </Button>
                                    <input
                                      readOnly
                                      value={catItem.qty}
                                      type="text"
                                    />
                                    <Button
                                      className="inc qtybutton"
                                      onClick={() =>
                                        props.incrementQuantity(catItem.id)
                                      }
                                    >
                                      +
                                    </Button>
                                  </Grid>
                                </div>
                              </td>
                              <td className="ptice">
                                ${catItem.qty * catItem.price}
                              </td>
                              <td className="stock">
                                ${catItem.qty * catItem.price}
                              </td>
                              <td className="action">
                                <ul>
                                  <li
                                    className="w-btn"
                                    onClick={() =>
                                      props.removeFromCart(catItem.id)
                                    }
                                  >
                                    <i className="fi flaticon-delete"></i>
                                  </li>
                                </ul>
                              </td>
                            </tr>
                          ))}
                        {subCarts &&
                          subCarts.length > 0 &&
                          subCarts.map((catItem, crt) => (
                            <tr key={crt} className="subCart">
                              <td className="images">
                                <img src={catItem.image} alt="" />
                              </td>
                              <td className="product">
                                <ul>
                                  <li className="first-cart">
                                    {catItem.title}
                                  </li>
                                  <li className="freq-cart">
                                    {subscriptionType(
                                      catItem.subscription.frequency
                                    )}
                                  </li>
                                  <li>
                                    For {catItem.subscription.noOfDays} days
                                  </li>
                                  <li>
                                    <h6
                                      style={{ cursor: "pointer" ,color:"GrayText"}}
                                      onClick={() => {
                                        setShowEditModal(1 - showEditModal);
                                        setEditModalProduct(catItem);
                                      }}
                                      // onBlur={()=>{setShowEditModal(0)}}
                                    >
                                      Edit
                                    </h6>
                                  </li>
                                </ul>
                              </td>
                              <td className="stock">
                                <div className="pro-single-btn">
                                  <Grid className="quantity cart-plus-minus">
                                    <Button
                                      className="dec qtybutton"
                                      onClick={() =>
                                        props.decrementSubQuantity(catItem.id)
                                      }
                                    >
                                      -
                                    </Button>
                                    <input
                                      readOnly
                                      value={catItem.subscription.quantity}
                                      type="text"
                                    />
                                    <Button
                                      className="inc qtybutton"
                                      onClick={() =>
                                        props.incrementSubQuantity(catItem.id)
                                      }
                                    >
                                      +
                                    </Button>
                                  </Grid>
                                </div>
                              </td>
                              <td className="ptice">₹ {catItem.price}</td>
                              <td className="stock">
                                ₹{" "}
                                {catItem.subscription.noOfDays *
                                  catItem.subscription.quantity *
                                  catItem.price}
                              </td>
                              <td className="action">
                                <ul>
                                  <li
                                    className="w-btn"
                                    onClick={() =>
                                      props.removeFromSubCart(catItem.id)
                                    }
                                  >
                                    <i className="fi flaticon-delete"></i>
                                  </li>
                                </ul>
                              </td>
                            </tr>
                          ))}
                      </tbody>
                    </table>
                  </form>

                  <div className="submit-btn-area">
                    <ul>
                      <li>
                        <Link
                          onClick={ClickHandler}
                          className="theme-btn"
                          to="/shop"
                        >
                          Continue Shopping{" "}
                          <i className="fa fa-angle-double-right"></i>
                        </Link>
                      </li>
                      <li>
                        <button type="submit">Update Cart</button>
                      </li>
                    </ul>
                  </div>
                  <div className="cart-product-list">
                    <ul>
                      <li>
                        Total product<span>( {carts.length} )</span>
                      </li>
                      <li>
                        Sub Price<span>${totalPrice(carts)}</span>
                      </li>
                      <li>
                        Vat<span>$0</span>
                      </li>
                      <li>
                        Eco Tax<span>$0</span>
                      </li>
                      <li>
                        Delivery Charge<span>$0</span>
                      </li>
                      <li className="cart-b">
                        Total Price<span>${totalPrice(carts)}</span>
                      </li>
                    </ul>
                  </div>
                  <div className="submit-btn-area">
                    <ul>
                      <li>
                        <Link
                          onClick={ClickHandler}
                          className="theme-btn"
                          to="/checkout"
                        >
                          Proceed to Checkout{" "}
                          <i className="fa fa-angle-double-right"></i>
                        </Link>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
      <Scrollbar />
      {showEditModal === 1 ? (
        <EditModal
          editChanges={editChanges}
          editModalProduct={editModalProduct}
          showEditModal={showEditModal}
          setShowEditModal={setShowEditModal}
        />
      ) : (
        <></>
      )}
    </Fragment>
  );
};

const mapStateToProps = (state) => {
  return {
    carts: state.cartList.cart,
    subCarts: state.subCartList.subCart,
  };
};
export default connect(mapStateToProps, {
  removeFromCart,
  incrementQuantity,
  decrementQuantity,
  removeFromSubCart,
  decrementSubQuantity,
  incrementSubQuantity,
  editSubProductCartItem,
})(CartPage);
