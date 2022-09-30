import React, { Component } from "react";
import { Link } from "react-router-dom";
import hero5 from "../../images/slider/done3.png";
import button from "../../images/button.svg";

class Hero extends Component {
  render() {
    return (
      <section className="hero hero-style-1">
        <div className="hero-slider">
          <div className="slide">
            <div className="container">
              <div className="row">
                <div className="col col-lg-5 slide-caption">
                  {/* <div className="slide-title">
                                        <h2><span>Fresh</span> and Organic <span></span></h2>
                                    </div> */}
                  <div className="btns">
                    <Link to="/shop">
                      {/* Shop Now 
                                        <i className="fa fa-angle-double-right"></i> */}
                      <img
                        height={"80%"}
                        width={"80%"}
                        src={button}
                        alt="slide-img"
                      />
                    </Link>
                  </div>
                </div>
              </div>
            </div>
            {/* <div className="right-image">
                            <div className="simg-1"><img src={hero5} alt="slide-img"/></div>
                            <div className="simg-2"><img src={hero5} alt="slide-img"/></div>
                        </div> */}
            <div className="hero-shape-img">
              <img src={hero5} alt="slide-img" />
            </div>
          </div>
        </div>
      </section>
    );
  }
}

export default Hero;
