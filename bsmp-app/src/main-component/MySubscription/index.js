import React, {Fragment} from 'react';
import { connect } from "react-redux";
import Navbar from '../../components/Navbar'
import MySubscriptionPage from '../../components/MySubscriptionPage';


const MySubscription =({cartList}) => {


    return(
        <Fragment>
            <Navbar hClass={"header-style-2"} />
            <MySubscriptionPage cartList={cartList}/>
        </Fragment>
    )
};

const mapStateToProps = state => {
    return {
        cartList: state.subCartList.subCart,
        symbol: state.data.symbol
    }
};

export default connect(mapStateToProps)(MySubscription);