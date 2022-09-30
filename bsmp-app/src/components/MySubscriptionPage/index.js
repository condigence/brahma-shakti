import React, { useState } from 'react';
import MySubscriptionPageComponent from "../MySubscriptionPageComponent"
import './style.scss'


const MySubscriptionPage = ({cartList}) => {
    const [clicked,setClicked]=useState(0);
    console.log(cartList);
    const clickedF = (e)=>{
        
        setClicked(e.id);
    }
    return(
        <section className="cart-recived-section section-padding">
            <div className='pageHeading'>My Subscribed Products</div>
            <div className="container">
                <div className="row">
                    {cartList.map(item => (
                        <div key={item.id} className={item.id===clicked?"order-top expanded":"order-top"} >
                            <h2>{item.title} 
                            <span>Price:₹{item.price}</span>
                            <span>Subscription start date: ₹{item.price}</span>
                            </h2>
                            <span>
                                <div>
                                    <span  onClick={()=>clickedF(item)} className='editButton edit'>Edit</span>
                                    <span className='editButton delete'>Delete</span>
                                </div>
                            </span>
                            {/* {item.id===clicked? */}
                            <MySubscriptionPageComponent clicked={clicked} cartList={item}/>
                            {/* :<div style={{opacity:"0"}}></div>} */}
                            {/* <Link to='/home' className="theme-btn">Back Home</Link> */}
                        </div>
                    ))}
                </div>
            </div>
        </section>
    )
}

export default MySubscriptionPage;