import React, { useEffect, useState } from "react";
import moment from "moment";
import { Button, Grid } from "@material-ui/core";
import { connect } from "react-redux";
import {
  removeFromCart,
  incrementQuantity,
  decrementQuantity,
} from "../../store/actions/action";
import "react-calendar/dist/Calendar.css";
import TextField from "@material-ui/core/TextField";
import Calendar from "react-calendar";
import "./style.css";

export function ManageSubscription(props) {
  const {  subProduct, setSubDetails } = props;
  const [quantity, setQuantity] = useState(1);
  const [startDate, setStartDate] = useState(new Date(moment()));
  const [scheduleVariable, setScheduleVariable] = useState(1);
  const [scheduledDates, setScheduledDates] = useState([]);
  const [total, setTotal] = useState(0);
  useEffect(() => {
    let arr;
    let sDate = new Date(startDate);
    sDate.setHours(0, 0, 0, 0);
    let eDate = new Date(sDate.getFullYear(), sDate.getMonth() + 1, 0);
    if (scheduleVariable === 1) arr = getEverydayScheduleDates(sDate, eDate);
    else if (scheduleVariable === 2) arr = get3DaysScheduleDates(sDate, eDate);
    else if (scheduleVariable === 3)
      arr = getAlternateScheduleDates(sDate, eDate);
    setScheduledDates([...arr]);

    setTotal(subProduct.price * arr.length * quantity);
    setSubDetails({
      quantity: quantity,
      startDate:startDate.getDate() === new Date().getDate()? moment(startDate).add(1,"day")._d :startDate,
      endDate: eDate,
      noOfDays: arr.length,
      frequency: scheduleVariable,
    });
  }, [scheduleVariable, quantity, startDate]);

  const getEverydayScheduleDates = (start, end) => {
    if (start.getDate() === new Date().getDate())
    start.setDate(start.getDate() + 1);
    let arrdates = [];
    while (start <= end) {
      arrdates.push(moment(new Date(start)).format("DD-MM-YYYY"));
      start.setDate(start.getDate() + 1);
    }
    return arrdates;
  };
  const getAlternateScheduleDates = (start, end) => {
    if (start.getDate() === new Date().getDate())
      start.setDate(start.getDate() + 1);
    let arrdates = [];
    while (start <= end) {
      arrdates.push(moment(new Date(start)).format("DD-MM-YYYY"));
      start.setDate(start.getDate() + 2);
    }
    return arrdates;
  };
  const get3DaysScheduleDates = (start, end) => {
    if (start.getDate() === new Date().getDate())
      start.setDate(start.getDate() + 1);
    let arrdates = [];
    while (start <= end) {
      if (start.getDay() === 2 || start.getDay() === 4 || start.getDay() === 6)
        arrdates.push(moment(new Date(start)).format("DD-MM-YYYY"));
      start.setDate(start.getDate() + 1);
    }
    return arrdates;
  };
  const handleStartDateChange = (e) => {
    setStartDate(new Date(e.target.value));
    console.log(moment(e.target.value).format("DD-MM-YYYY"));
  };
  const handleQuantity = (arg) => {
    if (arg === "add") {
      quantity < 2.5 ? setQuantity(quantity + 0.5) : setQuantity(quantity);
    } else quantity > 0.5 ? setQuantity(quantity - 0.5) : setQuantity(quantity);
  };
  return (
    <div className="container">
      <div className="stepLabel ">
        <div className="section-title mb-0">
          <h2>
            <span>Step</span> 2
          </h2>
          <h2>
            <span>Subscription </span>Details
          </h2>
        </div>
      </div>

      <div className="productList">
        <div className="productImage">
          <div className="">
            <img src={subProduct.proImg} alt="" />
          </div>
        </div>
        <div className="productName">
          <div className="" style={{ height: "40%" }}>
            {subProduct.title}
          </div>
          <div className="rateQuantity">
            <div className="rate">{subProduct.price}</div>
            <div className="productquantity">
              <Grid className="cart-plus-minus">
                <Button
                  className="qtybutton"
                  style={{ fontSize: "30px", color: "red" }}
                  onClick={() => handleQuantity("sub")}
                >
                  -
                </Button>
                <input
                  className="qValue"
                  value={`${quantity}`}
                  readOnly
                  type="text"
                />
                <Button
                  className="qtybutton"
                  style={{ fontSize: "26px", color: "green" }}
                  onClick={() => handleQuantity("add")}
                >
                  +
                </Button>
              </Grid>
            </div>
          </div>
        </div>
      </div>
      <div className="separator" />
      <div className="row p-4 ">
        <div className="col-lg-6 mb-3">
          <span style={{ fontWeight: "500", fontFamily: "inherit" }}>
            When do yo want to start your Subscription
          </span>
          <div className="m-2">
            <TextField
              id="date"
              // label="Choose your birthdate"e
              style={{ width: "100%", fontFamily: "inherit" }}
              type="date"
              onChange={handleStartDateChange}
              defaultValue={moment().format("YYYY-MM-DD")}
              InputLabelProps={{
                shrink: true,
              }}
            />
          </div>
        </div>
        <div className="col-lg-6 mb-3">
          <p>How you want to receive the product</p>
          <div className="row">
            <div className="col-lg-4 col-4">
              <button
                className={
                  scheduleVariable === 1 ? "schButton-active" : "schButton"
                }
                onClick={() => {
                  setScheduleVariable(1);
                }}
              >
                EveryDay
              </button>
            </div>
            <div className="col-lg-4 col-4">
              <button
                className={
                  scheduleVariable === 2 ? "schButton-active" : "schButton"
                }
                onClick={() => {
                  setScheduleVariable(2);
                }}
              >
                3 Days/Week
              </button>
            </div>
            <div className="col-lg-4 col-4">
              <button
                className={
                  scheduleVariable === 3 ? "schButton-active" : "schButton"
                }
                onClick={() => {
                  setScheduleVariable(3);
                }}
              >
                Alternate Day
              </button>
            </div>
          </div>
        </div>
        <div className="col-lg-6 mb-3">
          <div style={{ pointerEvents: "none" }}>
            <Calendar
              //  onChange={onChange}
              showNavigation={false}
              defaultValue={startDate}
              tileClassName={({ date, view }) => {
                if (
                  scheduledDates.find(
                    (x) => x === moment(date).format("DD-MM-YYYY")
                  )
                ) {
                  return "highlight";
                }
              }}
              minDate={new Date()}
            />
          </div>
        </div>
        <div className="col-lg-6 mb-3">
          Bill displayed here
          <div className="bill">
            <div className="billItems">
              <div className="itemTitle">{subProduct.title}</div>
              <div className="d-flex">
                <div className="col-5 itemqtyprice">
                  <div className="itemqty">{quantity} </div>&nbsp; X&nbsp;
                  <div className="itemprice">{subProduct.price}</div>
                </div>
                <div className=" col-5 itemTotal">
                  {" "}
                  {subProduct.price * quantity}
                </div>
              </div>
            </div>

            <div className="billItems">
              <div className="d-flex">
                <div className="col-5 itemqtyprice">
                  <div className="itemTitle">
                    Total {"   For "}
                    {total / (subProduct.price * quantity)}
                    {"  days"}
                  </div>
                </div>
                <div className=" col-5 itemTotal"> {total}</div>
              </div>
            </div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    carts: state.cartList.cart,
  };
};
export default connect(mapStateToProps, {
  removeFromCart,
  incrementQuantity,
  decrementQuantity,
})(ManageSubscription);
