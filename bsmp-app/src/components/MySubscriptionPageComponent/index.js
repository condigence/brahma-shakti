import React, { Fragment, useState, useEffect } from "react";
import { TextField } from "@material-ui/core";
import moment from "moment";
import Calendar from "react-calendar";
import "./style.css"
const SubItemFullDisplay = ({ cartList,clicked }) => {
  const [scheduleVariable, setScheduleVariable] = useState(0);
  const [startDate, setStartDate] = useState(new Date());
  const [scheduledDates, setScheduledDates] = useState([]);

  useEffect(() => {
    console.log("cartItem", cartList);
    let arr;
    setStartDate(cartList.subscription.startDate);
    setScheduleVariable(cartList.subscription.frequency);
    let sDate = new Date(cartList.subscription.startDate);
    sDate.setHours(0, 0, 0, 0);
    let eDate = new Date(sDate.getFullYear(), sDate.getMonth() + 1, 0);
    if (cartList.subscription.frequency === 1)
      arr = getEverydayScheduleDates(sDate, eDate);
    else if (cartList.subscription.frequency === 2)
      arr = get3DaysScheduleDates(sDate, eDate);
    else if (cartList.subscription.frequency === 3)
      arr = getAlternateScheduleDates(sDate, eDate);
    setScheduledDates([...arr]);
    // setScheduleVariable([])
  }, []);
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

  return (
    <Fragment><div className={clicked===cartList.id?"animate1":"out"}>
      <div className="row p-4 ">
        <div className="col-lg-6 mb-3">
          <span style={{ fontWeight: "500", fontFamily: "inherit" }}>
            Subscription Start Date
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
          <p>Product Frequency</p>
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
        {/* <div className="col-lg-6 mb-3">
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
        </div> */}
      </div>
      </div>
    </Fragment>
  );
};

export default SubItemFullDisplay;
