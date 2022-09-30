import React from 'react'
import {Link} from 'react-router-dom'
import catimg from '../../images/category/icon-1.png'
import catimg2 from '../../images/category/icon-2.png'
import catimg3 from '../../images/category/icon-3.png'
import catimg4 from '../../images/category/icon-4.png'
import catimg5 from '../../images/category/category.jpg'
import cimg from '../../images/category/cimg.jpg'

const Category = (props) => {

    

    const ClickHandler = () =>{
        window.scrollTo(10, 0);
     }
    return(
        <section className="category-area section-padding">
            <div className="container">
                <div className="row align-items-center">
                    <div className="col-lg-6">
                        <div className="category-wrap">
                            <div className="category-title">
                                <h2>Our Products Category</h2>
                                <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Nemo, eos fugit officiis
                                    error ipsum, dolor ducimus nam ratione nulla deleniti inventore blanditiis.</p>
                            </div>
                            <div className="category-item">
                                <div className="category-icon">
                                    <img src={catimg} alt="" />
                                </div>
                                <div className="category-content">
                                    <h2><Link onClick={ClickHandler} to="/shop">Dairy Products</Link></h2>
                                    <p>Fresh Diary Products Like Milk, Dahi, Ghee & many more Delivered at Your Door Step.</p>
                                    <Link style={{color: 'green', cursor: 'pointer', textDecoration: 'underline'}} onClick={ClickHandler} to="/shop">Take me to Dairy Products</Link>
                                </div>
                            </div>
                            <div className="category-item">
                                <div className="category-icon">
                                    <img src={catimg2} alt="" />
                                </div>
                                <div className="category-content">
                                    <h2><Link onClick={ClickHandler} to="/shop">Sweets</Link></h2>
                                    <p>We Carry All Your Favouite Milk Beats Sweets Perfect for Gifts and Special Occasions.</p>
                                    <Link style={{color: 'green', cursor: 'pointer', textDecoration: 'underline'}} onClick={ClickHandler} to="/shop">Take me to Sweet Products</Link>

                                </div>
                            </div>
                            <div className="category-item">
                                <div className="category-icon">
                                    <img src={catimg3} alt="" />
                                </div>
                                <div className="category-content">
                                    <h2><Link onClick={ClickHandler} to="/shop">Vegetables</Link></h2>
                                    <p>Our Organic Farm Fresh Vegetables Will Add Veriety To Your Meals.</p>
                                    <Link style={{color: 'green', cursor: 'pointer', textDecoration: 'underline'}} onClick={ClickHandler} to="/shop">Take me to Vegetable Products</Link>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-6">
                        <div className="category-img">
                            <img src={cimg} alt="" />
                            <div className="ct-img"><img src={catimg4} alt="" /></div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default Category;