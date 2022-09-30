import React, { Fragment, useState } from "react";
import { connect } from "react-redux";
import Navbar from "../../components/Navbar";
import PageTitle from "../../components/pagetitle";
import { Link } from "react-router-dom";
import Footer from "../../components/footer";
import Scrollbar from "../../components/scrollbar";
import { removeFromWishList, addToCart } from "../../store/actions/action";
import pImage from "../../images/MilkBottle.webp";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import { totalPrice } from "../../utils";
import moment from "moment";
// import "./style.scss";
import "./style.css";

const OrderHistoryPage = (props) => {
  const { cartList, subCartList } = props;
  const [activeHeading, setActiveHeading] = useState(0);
  console.log(subCartList);
  const date = new Date();

  return (
    <Fragment>
      <Navbar hClass={"header-style-2"} />
      <PageTitle pageTitle={"Order History"} pagesub={"Order History"} />
      <div className="cart-area section-padding">
        <div className="container">
          <div className="row">
            <div className="heading-type">
              <span
                onClick={() => {
                  setActiveHeading(0);
                }}
                className={activeHeading === 0 ? "activeheading" : "headings"}
              >
                Usual Orders
              </span>
              <span
                onClick={() => {
                  setActiveHeading(1);
                }}
                className={activeHeading == 1 ? "activeheading" : "headings"}
              >
                Subscription Orders
              </span>
            </div>
          </div>
          <div className="row">
            <Grid className="cartStatus" style={{ background: "#d5bbbb30" }}>
              <Grid container spacing={3}>
                <Grid item xs={12}>
                  <Grid className="cartTotals">
                    <h3>
                      Order Id{" "}
                      <span
                        style={{
                          fontSize: "0.91rem",
                          fontWeight: "bold",
                          color: "GrayText",
                        }}
                      >
                        #28072022
                      </span>
                    </h3>
                    {/* <TableRow><TableCell>Maybe</TableCell></TableRow> */}
                    <Table
                      style={{
                        boxShadow: "0px 5px 15px 0px rgba(62, 65, 159, 0.1)",
                      }}
                    >
                      <TableBody>
                        {activeHeading===0 && cartList.map((item) =>
                          
                          <TableRow
                            key={item.id}
                            style={{
                              background: `${"radial-gradient(circle, rgba(163,160,160,1) 0%, rgba(255,255,255,1) 100%)"}`,
                            }}
                          >
                            <TableCell>
                              {/* <img style="image" src={item.proImg} alt="" />  */}
                              <span
                                style={{
                                  fontSize: "1.2rem",
                                  fontWeight: "600",
                                  margin: "1%",
                                }}
                              >
                                {item.title}
                              </span>
                            </TableCell>
                            <TableCell align="justify">
                              {" "}
                              ₹{item.price} x {item.qty}
                            </TableCell>

                            <TableCell align="right">
                              ₹{item.qty * item.price}
                            </TableCell>
                          </TableRow>
                        )}
                        {activeHeading===1 && subCartList.map((item) => (
                          <TableRow
                            key={item.id}
                            style={{
                              background: `${"radial-gradient(circle, rgba(240,221,204,1) 0%, rgba(255,255,255,1) 100%)"}`,
                            }}
                          >
                            <TableCell>
                              {/* <img style="image" src={item.proImg} alt="" />  */}
                              <span
                                style={{
                                  fontSize: "1.2rem",
                                  fontWeight: "600",
                                  margin: "1%",
                                }}
                              >
                                {item.title}
                              </span>
                            </TableCell>
                            <TableCell align="justify">
                              ₹{item.price} x {item.subscription.quantity} x{" "}
                              {item.subscription.noOfDays} days
                            </TableCell>
                            <TableCell align="right">
                              ₹
                              {item.subscription.quantity *
                                item.price *
                                item.subscription.noOfDays}
                            </TableCell>
                          </TableRow>
                        ))}
                        <TableRow
                          className="totalProduct"
                          style={{
                            background: `${"radial-gradient(circle, rgba(254,209,180,1) 0%, rgba(199,199,199,1) 50%, rgba(255,255,255,1) 100%)"}`,
                          }}
                        >
                          <TableCell>{date.toLocaleString()}</TableCell>
                          <TableCell align="justify">
                            Paid <b> Online</b>(TID2132343454)
                          </TableCell>
                          <TableCell align="right">
                            Total <b>₹ 3232</b>
                          </TableCell>
                        </TableRow>
                        {/* <TableRow>
                          <TableCell>Sub Price</TableCell>
                          <TableCell align="right">
                            ${totalPrice(cartList)}
                          </TableCell>
                        </TableRow> */}
                        {/* <TableRow>
                          <TableCell>
                            <b>Total Price</b>
                          </TableCell>
                          <TableCell align="right">
                            <b>${totalPrice(cartList)}</b>
                          </TableCell>
                        </TableRow> */}
                      </TableBody>
                    </Table>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
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
    cartList: state.cartList.cart,
    subCartList: state.subCartList.subCart,
    symbol: state.data.symbol,
  };
};
export default connect(mapStateToProps)(OrderHistoryPage);
// export default OrderHistoryPage;
