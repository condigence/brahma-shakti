import React, { Fragment } from "react";
import { connect } from "react-redux";
import Navbar from "../../components/Navbar";
import PageTitle from "../../components/pagetitle";
import { Link } from "react-router-dom";
import Footer from "../../components/footer";
import Scrollbar from "../../components/scrollbar";
import { removeFromWishList, addToCart } from "../../store/actions/action";
import pImage from "../../images/MilkBottle.webp";
import "./style.css";

const OrderHistoryPage = (props) => {
    const { wishs } = props;

  return (
    <Fragment>
      <Navbar hClass={"header-style-2"} />
      <PageTitle pageTitle={"Order History"} pagesub={"Order History"} />
      <div className="cart-area section-padding">
        
        <div className="container">
        <h2>Upcoming Orders</h2>
          <div className="form">
            <div className="cart-wrapper">
              <div className="row">
                <div className="col-12">
                  <form action="cart">
                    <div className="detailBox">
                      <div className="row">
                        <div className="col-lg-10">
                          <div
                            style={{
                              display: "flex",
                              flexDirection: "row",
                              paddingInline: "4%",
                              paddingTop: "1%",
                            }}
                          >
                            <div className="pImages">
                              <img src={pImage} alt="" />
                            </div>
                            <div className="middleDetails m-2">
                              <div className="firstLine">
                                <div style={{ display: "inherit" }}>
                                  OrderID
                                  <p
                                    style={{
                                      color: "#5a4bfa",
                                      fontSize: "13px",
                                    }}
                                  >
                                    #28072022
                                  </p>
                                </div>
                                <div>
                                  <span style={{ fontWeight: "bold" }}>
                                    ₹ 528.75
                                  </span>
                                </div>
                              </div>
                              <div className="secLine">
                                <div>
                                  <p style={{ fontSize: "12px" }}>
                                    Thu, 28 July,2022 06:25PM
                                  </p>
                                </div>
                                <div>
                                  <span
                                    style={{ color: "blue", fontSize: "13px" }}
                                  >
                                    Rate & Review
                                  </span>
                                </div>
                              </div>
                              <div className="thirdLine">
                                <div className="divider" />
                                <div style={{ display: "inherit" }}>
                                  <h6 style={{fontSize: "11px"}}>
                                    &nbsp;&nbsp;2 &nbsp; BUFFALO MILK &nbsp;
                                    &nbsp;
                                  </h6>
                                  <p style={{ fontSize: "13px" }}> ₹ 89.75</p>
                                </div>
                                <div className="divider" />
                                <div style={{ display: "inherit" }}>
                                  <h6 style={{fontSize: "11px"}}>
                                    &nbsp;&nbsp;1 &nbsp; KHOYA &nbsp; &nbsp;
                                  </h6>
                                  <p style={{ fontSize: "13px" }}> ₹ 319.25</p>
                                </div>
                                <div className="divider" />
                                <div style={{ display: "inherit" }}>
                                  <h6 style={{fontSize: "11px"}}>
                                    &nbsp;&nbsp;3 &nbsp; RASMALAI &nbsp; &nbsp;
                                  </h6>
                                  <p style={{ fontSize: "13px" }}> ₹ 119.75</p>
                                </div>
                                <div className="divider" />
                              </div>
                              <div className="lastLine">
                                <div>
                                  <span
                                    style={{ color: "blue", fontSize: "13px" }}
                                  >
                                    View Receipt
                                  </span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div className="col-lg-2">
                          <div className="rightDetails p-2">
                            <div className="reOrder"> Edit Order</div>
                            <div className="addToBag"> Add To Cart</div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <table className="table-responsive cart-wrap">
                      <thead>
                        <tr>
                          <th className="images images-b">Order Id</th>
                          <th className="product-2">Date/Time</th>
                          <th className="ptice">Price</th>
                          <th className="pr">Status</th>
                          <th className="remove remove-b">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {wishs &&
                          wishs.length > 0 &&
                          wishs.map((wish, crt) => (
                            <tr key={crt}>
                              <td className="images">
                                <img src={wish.proImg} alt="" />
                              </td>
                              <td className="product">
                                <ul>
                                  <li className="first-cart">{wish.title} </li>
                                  <li>Brand : {wish.brand}</li>
                                  <li>Size : {wish.size}</li>
                                </ul>
                              </td>
                              <td className="ptice">${wish.price}</td>
                              <td className="stock">{wish.stock}</td>
                              <td className="action">
                                <ul>
                                  <li className="c-btn">
                                    <Link to="/cart" className="c-btn">
                                      <i className="fi flaticon-shopping-cart"></i>
                                    </Link>
                                  </li>
                                  <li className="w-btn">
                                    <button
                                      type="button"
                                      onClick={() =>
                                        props.removeFromWishList(wish.id)
                                      }
                                    >
                                      <i className="fi flaticon-delete"></i>
                                    </button>{" "}
                                  </li>
                                </ul>
                              </td>
                            </tr>
                          ))}
                      </tbody> 
                    </table>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="container">
        <h2>Past Orders</h2>
          <div className="form">
            <div className="cart-wrapper">
              <div className="row">
                <div className="col-12">
                  <form action="cart">
                    <div className="detailBox">
                      <div className="row">
                        <div className="col-lg-10">
                          <div
                            style={{
                              display: "flex",
                              flexDirection: "row",
                              paddingInline: "4%",
                              paddingTop: "1%",
                            }}
                          >
                            <div className="pImages">
                              <img src={pImage} alt="" />
                            </div>
                            <div className="middleDetails m-2">
                              <div className="firstLine">
                                <div style={{ display: "inherit" }}>
                                  OrderID
                                  <p
                                    style={{
                                      color: "#5a4bfa",
                                      fontSize: "13px",
                                    }}
                                  >
                                    #28072022
                                  </p>
                                </div>
                                <div>
                                  <span style={{ fontWeight: "bold" }}>
                                    ₹ 528.75
                                  </span>
                                </div>
                              </div>
                              <div className="secLine">
                                <div>
                                  <p style={{ fontSize: "12px" }}>
                                    Thu, 28 July,2022 06:25PM
                                  </p>
                                </div>
                                <div>
                                  <span
                                    style={{ color: "blue", fontSize: "13px" }}
                                  >
                                    Rate & Review
                                  </span>
                                </div>
                              </div>
                              <div className="thirdLine">
                                <div className="divider" />
                                <div style={{ display: "inherit" }}>
                                  <h6>
                                    &nbsp;&nbsp;2 &nbsp; BUFFALO MILK &nbsp;
                                    &nbsp;
                                  </h6>
                                  <p style={{ fontSize: "13px" }}> ₹ 89.75</p>
                                </div>
                                <div className="divider" />
                                <div style={{ display: "inherit" }}>
                                  <h6>
                                    &nbsp;&nbsp;1 &nbsp; KHOYA &nbsp; &nbsp;
                                  </h6>
                                  <p style={{ fontSize: "13px" }}> ₹ 319.25</p>
                                </div>
                                <div className="divider" />
                                <div style={{ display: "inherit" }}>
                                  <h6>
                                    &nbsp;&nbsp;3 &nbsp; RASMALAI &nbsp; &nbsp;
                                  </h6>
                                  <p style={{ fontSize: "13px" }}> ₹ 119.75</p>
                                </div>
                                <div className="divider" />
                              </div>
                              <div className="lastLine">
                                <div>
                                  <span
                                    style={{ color: "blue", fontSize: "13px" }}
                                  >
                                    View Receipt
                                  </span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div className="col-lg-2">
                          <div className="rightDetails p-2">
                            <div className="reOrder"> Repeat Order</div>
                            <div className="addToBag"> Add To Cart</div>
                          </div>
                        </div>
                      </div>
                    </div>
                    
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
      <Scrollbar />
    </Fragment>
  );
};

const mapStateToProps = (state) => {
  return {
    wishs: state.wishList.w_list,
  };
};
export default connect(mapStateToProps, { removeFromWishList, addToCart })(
    OrderHistoryPage
);