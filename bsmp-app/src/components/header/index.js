import React, { Component } from "react";
import { connect } from "react-redux";
// import Logo from "../../images/final.jpg";
import Logo2 from "../../images/bs-app-icon2.png";
import { Link } from "react-router-dom";
import MobileMenu from "../../components/MobileMenu";
import min3 from "../../images/shop/mini-cart/bee2.png";
import { totalPrice } from "../../utils";
import { removeFromCart, removeFromWishList } from "../../store/actions/action";
import "./style.css";


console.log(window.location.pathname);
const outLetArr = [
  { nickName: "Rohtak Area 1", address: "street building area " },
  { nickName: "Rohtak Area 2", address: "street building area " },
];

class Header extends Component {
  state = {
    isCartShow: false,
    isWishlistShow: false,
    dropDownVisbility: false,
    outLet: outLetArr[0],
    isOutLetShow: false,
    anim: false,
    isLoggedIn:false,
  };
  componentDidMount(){
localStorage.getItem("isLogged").then(res=>{
  this.setState({isLoggedIn:res},()=>{
    console.log("came here")
  })
})
  }

  url = window.location.href;

  cartHandler = () => {
    this.setState({
      isCartShow: !this.state.isCartShow,
    });
  };

  handleLogout =()=>{
    localStorage.removeItem("isLogged")
  }

  wishlistHandler = () => {
    this.setState({
      isWishlistShow: !this.state.isWishlistShow,
    });
  };
  profileHandler = () => {
    this.setState({
      isprofileShow: !this.state.isprofileShow,
    });
  };
  componentDidMount = () => {
    setTimeout(() => {
      this.tradeMarkAnimHandler();
      // console.log("here");
    }, 3000);
  };
  outLetHandler = () => {
    this.setState({
      isOutLetShow: !this.state.isOutLetShow,
    });
  };
  tradeMarkAnimHandler = () => {
    this.setState({
      anim: !this.state.anim,
    });
  };

  render() {
    const {
      isCartShow,
      isWishlistShow,
      isprofileShow,
      isOutLetShow,
      anim,
      outLet,
    } = this.state;

    const ClickHandler = () => {
      window.scrollTo(10, 0);
      this.setState({
        dropDownVisbility: false,
      });
    };

    const openDropDown = () => {
      this.setState({
        dropDownVisbility: true,
      });
    };

    const { carts,subCarts } = this.props;
    const { wishs } = this.props;

    let totalwishlistprice = 0;
    if (wishs && wishs.length > 0) {
      for (let i = 1; i <= wishs.length; i++) {
        totalwishlistprice += Number(wishs[i - 1].price);
      }
    }
    return (
      <header id="header" className={`site-header ${this.props.hClass}`}>
        <nav className="navigation navbar navbar-expand-lg">
          <div className="container">
            <div className="row">
              <div className="col-lg-3">
                <div className="navbar-header d-flex">
                  <Link
                    onClick={ClickHandler}
                    className="navbar-brand"
                    to="/home"
                  >
                    <img style={{height: "5rem", width: "5rem" }} src={Logo2} alt="icon" />
                    <span className={!anim ? "fadeIn" : "fadeOut"}>BSMP</span>
                  </Link>
                  <div className={anim ? "fadeIn" : "fadeOut"}>
                    <div className="outLetLocator">
                      <div className="outLetLocator d-flex">
                        {/* <i className="fi ti-location-pin"></i>
                        <span className="areaExpanded" >Select Outlet</span> */}
                        <span
                          style={{ overflow: "hidden", whiteSpace: "nowrap" }}
                        >
                          {" "}
                          Welcome, User
                        </span>
                      </div>
                    </div>
                    <div>
                      <span
                        onClick={this.outLetHandler}
                        className="areaNickName"
                      >
                        <i className="fi ti-location-pin"></i>
                        {outLet.nickName}{" "}
                        <i
                          className={`${
                            isOutLetShow
                              ? "updown ti-angle-up"
                              : "updown ti-angle-down"
                          }`}
                        ></i>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-lg-7">
                <div
                  id="navbar"
                  className="collapse navbar-collapse navigation-holder"
                >
                  <Link onClick={ClickHandler} className="menu-close" to="/">
                    <i className="fi flaticon-cancel"></i>
                  </Link>
                  <ul className="nav navbar-nav me-auto mb-2 mb-lg-0">
                    <li>
                      <Link
                        className={
                          this.url.includes("home") ||
                          window.location.pathname === "/"
                            ? "active"
                            : ""
                        }
                        onClick={ClickHandler}
                        to="/home"
                      >
                        Home
                      </Link>
                    </li>
                    {/* <li>
                      <Link
                        className={this.url.includes("about") ? "active" : ""}
                        onClick={ClickHandler}
                        to="/about"
                      >
                        About
                      </Link>
                    </li> */}
                    <li className="menu-item-has-children">
                      <Link onClick={ClickHandler} >
                        Category
                      </Link>
                      <ul
                        className="sub-menu"
                        // style={this.state.dropDownVisbility?{visibility: "visible"}: {visibility: "hidden"}}
                      >
                        <li>
                          <Link
                            onClick={ClickHandler}
                            to="/shop?category=dairy_products"
                          >
                            Dairy Products
                          </Link>
                        </li>
                        <li>
                          <Link
                            onClick={ClickHandler}
                            to="/shop?category=vegetables"
                          >
                            Vegetables
                          </Link>
                        </li>
                        <li>
                          <Link
                            onClick={ClickHandler}
                            to="/shop?category=sweets"
                          >
                            Sweets
                          </Link>
                        </li>
                        {/* <li>
                          <Link onClick={ClickHandler} to="/404">
                            404 Error
                          </Link>
                        </li> */}
                        {/* <li className="menu-item-has-children">
                          <Link onClick={ClickHandler} to="/">
                            Product
                          </Link>
                          <ul className="sub-menu">
                            <li>
                              <Link onClick={ClickHandler} to="/shop">
                                Product
                              </Link>
                            </li>
                            <li>
                              <Link
                                onClick={ClickHandler}
                                to="/product-single/1"
                              >
                                Product Single
                              </Link>
                            </li>
                          </ul>
                        </li> */}
                        {/* <li className="menu-item-has-children">
                          <Link onClick={ClickHandler} to="/">
                            Project
                          </Link>
                          <ul className="sub-menu">
                            <li>
                              <Link onClick={ClickHandler} to="/project">
                                Project
                              </Link>
                            </li>
                            <li>
                              <Link onClick={ClickHandler} to="/project-single">
                                Project Single
                              </Link>
                            </li>
                          </ul>
                        </li> */}
                        {/* <li>
                          <Link onClick={ClickHandler} to="/login">
                            Login
                          </Link>
                        </li> */}
                        {/* <li>
                          <Link onClick={ClickHandler} to="/register">
                            Register
                          </Link>
                        </li> */}
                      </ul>
                    </li>
                    <li className="menu-item-has-children">
                      <Link onClick={ClickHandler}>Subscription</Link>
                      <ul className="sub-menu">
                        <li>
                          <Link onClick={ClickHandler} to="/subscription">
                            Products
                          </Link>
                        </li>
                        <li>
                          <Link onClick={ClickHandler}
                           to="/my-subscription"
                           >
                            My Subscriptions
                          </Link>
                        </li>
                        <li>
                          <Link onClick={ClickHandler} to="/setup-Vacation">
                            Pause/Resume Subscription
                          </Link>
                        </li>
                        {/* <li className="menu-item-has-children">
                          <Link onClick={ClickHandler} >
                          Edit Subscription
                          </Link>
                          <ul className="sub-menu">
                            <li>
                              <Link onClick={ClickHandler} to="/blog-single/1">
                              Setup Vacation
                              </Link>
                            </li>
                            <li>
                              <Link
                                onClick={ClickHandler}
                                to="/blog-single-left-sidebar/1"
                              >
                                Cancel Subscription
                              </Link>
                            </li>
                          </ul>
                        </li> */}
                      </ul>
                    </li>
                    <li>
                      <Link onClick={ClickHandler} to="/about">
                        About
                      </Link>
                    </li>
                    {/* <li>
                      <Link onClick={ClickHandler} to="/">
                        Order History
                      </Link>
                    </li> */}
                    <li className="menu-item-has-children">
                      <Link onClick={ClickHandler}>View All</Link>
                      <ul
                        className="sub-menu"
                        // style={this.state.dropDownVisbility?{visibility: "visible"}: {visibility: "hidden"}}
                      >
                        <li>
                          <Link onClick={ClickHandler} to="/order-history">
                            Order History
                          </Link>
                        </li>
                        {/* <li>
                          <Link
                            onClick={ClickHandler}
                            to="/about"
                          >
                            About
                          </Link>
                        </li> */}
                        <li>
                          <Link onClick={ClickHandler} to="/contact">
                            Contact
                          </Link>
                        </li>
                      </ul>
                    </li>
                    {this.state.isLoggedIn!==true?
                    <li>
                      <Link onClick={ClickHandler} to="/login">
                        Login
                      </Link>
                    </li>:<li><Link onClick={this.handleLogout()} to="/login">
                        Logout
                      </Link></li>}
                  </ul>
                </div>
              </div>
              <div className="col-lg-2">
                <div className="header-right d-flex">
                  {/* <div className="header-profile-form-wrapper">
                    <button
                      onClick={this.profileHandler}
                      className="profile-toggle-btn"
                    >
                      <i
                        className={`${
                          isprofileShow ? "fi ti-close" : "fi flaticon-user"
                        }`}
                      ></i>
                    </button>
                    <div
                      className={`header-profile-content ${
                        isprofileShow ? "header-profile-content-toggle" : ""
                      }`}
                    >
                      <ul>
                        <li>
                          <Link onClick={ClickHandler} to="/login">
                            Login
                          </Link>
                        </li>
                        <li>
                          <Link onClick={ClickHandler} to="/register">
                            Register
                          </Link>
                        </li>
                        <li>
                          <Link onClick={ClickHandler} to="/cart">
                            Cart
                          </Link>
                        </li>
                        <li>
                          <Link onClick={ClickHandler} to="/wishlist">
                            Wishlist
                          </Link>
                        </li>
                        <li>
                          <Link onClick={ClickHandler} to="/checkout">
                            Checkout
                          </Link>
                        </li>
                      </ul>
                    </div>
                  </div> */}
                  <div className="mini-cart">
                    <button
                      onClick={this.cartHandler}
                      className="cart-toggle-btn"
                    >
                      {" "}
                      <i className="fi flaticon-bag"></i>{" "}
                      <span className="cart-count">{carts.length+subCarts.length}</span>
                    </button>
                    <div
                      className={`mini-cart-content ${
                        isCartShow ? "mini-cart-content-toggle" : ""
                      }`}
                    >
                      <button
                        onClick={this.cartHandler}
                        className="mini-cart-close"
                      >
                        <i className="ti-close"></i>
                      </button>
                      <div className="mini-cart-items">
                        {carts &&
                          carts.length > 0 &&
                          carts.map((cart, crt) => (
                            <div className="mini-cart-item clearfix" key={crt}>
                              <div className="mini-cart-item-image">
                                <span>
                                  <img src={cart.proImg} alt="icon" />
                                </span>
                              </div>
                              <div className="mini-cart-item-des">
                                <p>{cart.title} </p>
                                <span className="mini-cart-item-price">
                                  ${cart.price} x {cart.qty}
                                </span>
                                <span className="mini-cart-item-quantity">
                                  <button
                                    onClick={() =>
                                      this.props.removeFromCart(cart.id)
                                    }
                                    className="btn btn-sm btn-danger"
                                  >
                                    <i className="ti-close"></i>
                                  </button>{" "}
                                </span>
                              </div>
                            </div>
                          ))}
                      </div>
                      <div className="mini-cart-action clearfix">
                        <span className="mini-checkout-price">
                          Total: ${totalPrice(carts)}
                        </span>
                        <div className="mini-btn">
                          <Link
                            onClick={ClickHandler}
                            to="/checkout"
                            className="view-cart-btn s1"
                          >
                            Checkout
                          </Link>
                          <Link
                            onClick={ClickHandler}
                            to="/cart"
                            className="view-cart-btn"
                          >
                            View Cart
                          </Link>
                        </div>
                      </div>
                      <div className="visible-icon">
                        <img src={min3} alt="icon" />
                      </div>
                    </div>
                  </div>
                  <div className="header-wishlist-form-wrapper">
                    <button
                      onClick={this.wishlistHandler}
                      className="wishlist-toggle-btn"
                    >
                      <i className="fi flaticon-heart"></i>{" "}
                      <span className="cart-count">{wishs.length}</span>{" "}
                    </button>
                    <div
                      className={`mini-wislist-content ${
                        isWishlistShow ? "mini-cart-content-toggle" : ""
                      }`}
                    >
                      <button
                        onClick={this.wishlistHandler}
                        className="mini-cart-close"
                      >
                        <i className="ti-close"></i>
                      </button>
                      <div className="mini-cart-items">
                        {wishs &&
                          wishs.length > 0 &&
                          wishs.map((wish, i) => (
                            <div className="mini-cart-item clearfix" key={i}>
                              <div className="mini-cart-item-image">
                                <span>
                                  <img src={wish.proImg} alt="icon" />
                                </span>
                              </div>
                              <div className="mini-cart-item-des">
                                <p>{wish.title} </p>
                                <span className="mini-cart-item-price">
                                  ${wish.price}
                                </span>
                                <span className="mini-cart-item-quantity">
                                  <button
                                    onClick={() =>
                                      this.props.removeFromWishList(wish.id)
                                    }
                                    className="btn btn-sm btn-danger"
                                  >
                                    <i className="ti-close"></i>
                                  </button>{" "}
                                </span>
                              </div>
                            </div>
                          ))}
                      </div>
                      <div className="mini-cart-action clearfix">
                        <span className="mini-checkout-price">
                          Total: ${totalwishlistprice}
                        </span>
                        <div className="mini-btn">
                          <Link
                            onClick={ClickHandler}
                            to="/checkout"
                            className="view-cart-btn s1"
                          >
                            Checkout
                          </Link>
                          <Link
                            onClick={ClickHandler}
                            to="/wishlist"
                            className="view-cart-btn"
                          >
                            View Wishlist
                          </Link>
                        </div>
                      </div>
                      <div className="visible-icon">
                        <img src={min3} alt="icon" />
                      </div>
                    </div>
                  </div>
                  <MobileMenu />
                </div>
              </div>
            </div>
          </div>
        </nav>
      </header>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    carts: state.cartList.cart,
    subCarts: state.subCartList.subCart,
    wishs: state.wishList.w_list,
  };
};

export default connect(mapStateToProps, { removeFromCart, removeFromWishList })(
  Header
);
