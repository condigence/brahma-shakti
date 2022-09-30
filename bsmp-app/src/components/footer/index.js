import React from 'react'
import {Link}  from 'react-router-dom'
import Logo3 from "../../images/logo3.png";
import Logo2 from "../../images/bs-app-icon2.png";




const Footer = (props) =>{

    const ClickHandler = () =>{
        window.scrollTo(10, 0);
     }

  return(
    <footer className="tp-site-footer">
        <div className="tp-upper-footer">
            <div className="container">
                <div className="row">
                    <div className="col col-lg-3 col-md-6 col-sm-12 col-12">
                        <div className="widget about-widget">
                            <div className="logo widget-title">
                                <Link><img style={{height: "5rem", width: "5rem", background: "#FFFFFF", borderRadius: "50%" }} src={Logo2} alt="ft-logo"/> Brahmshakti</Link>
                            </div>
                            <p>We Offer The Freshes Farm-To-Table Products Delivered Straight To Your Door.</p>
                            <ul>
                                <p>Connect with Us Online!</p>
                                <li>
                                    <Link onClick={ClickHandler} to="/">
                                        <i className="ti-facebook"></i>
                                    </Link>
                                </li>
                                <li>
                                    <Link onClick={ClickHandler} to="/">
                                        <i className="ti-twitter-alt"></i>
                                    </Link>
                                </li>
                                <li>
                                    <Link onClick={ClickHandler} to="/">
                                        <i className="ti-instagram"></i>
                                    </Link>
                                </li>
                                <li>
                                    <Link onClick={ClickHandler} to="/">
                                        <i className="ti-google"></i>
                                    </Link>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div className="col col-lg-3 col-md-6 col-sm-12 col-12">
                        <div className="widget tp-service-link-widget">
                            <div className="widget-title">
                                <h3>Contact </h3>
                            </div>
                            <div className="contact-ft">
                                <ul>
                                    <li><i className="fi flaticon-pin"></i>Brahamshakti milk products, Gokul colony, Near Sunarian chowk, Rohtak, 124001
                                    </li>
                                    <li><i className="fi flaticon-call"></i>+91 800 123 456 7</li>
                                    <li><i className="fi flaticon-envelope"></i>Brahamshakti@gmail.com</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div className="col col-lg-3 col-md-6 col-sm-12 col-12">
                        <div className="widget link-widget">
                            <div className="widget-title">
                                <h3>My Account</h3>
                            </div>
                            <ul>
                                <li><Link onClick={ClickHandler} to="/project">Our Projects</Link></li>
                                <li><Link onClick={ClickHandler} to="/shop">Our Shop</Link></li>
                                <li><Link onClick={ClickHandler} to="/wishlist">Wishlist</Link></li>
                                <li><Link onClick={ClickHandler} to="/checkout">Checkout</Link></li>
                            </ul>
                        </div>
                    </div>
                    <div className="col col-lg-3 col-md-6 col-sm-12 col-12">
                        <div className="widget newsletter-widget">
                            <div className="widget-title">
                                <h3>Newsletter</h3>
                            </div>
                            <p>You will be notified when somthing new will be appear.</p>
                            <form>
                                <div className="input-1">
                                    <input type="email" className="form-control" placeholder="Email Address *" required/>
                                </div>
                                <div className="submit clearfix">
                                    <button type="submit"><i className="ti-email"></i></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
        <div className="tp-lower-footer">
            <div className="container">
                <div className="row">
                    <div className="col col-xs-12">
                        <p className="copyright"> Copyright &copy; 2021 Annahl by <Link onClick={ClickHandler} to="/">themepresss</Link>.
                            All Rights Reserved.</p>
                    </div>
                </div>
            </div>
        </div>
        <div className="footer-shape1">
            <i className="fi flaticon-honeycomb"></i>
        </div>
        <div className="footer-shape2">
            <i className="fi flaticon-honey-1"></i>
        </div>
    </footer>
  )
} 

export default Footer;