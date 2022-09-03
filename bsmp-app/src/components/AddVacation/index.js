import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import TextField from "@material-ui/core/TextField";
import moment from "moment";
import "./style.scss";
import MultiSelector from "../MultiSelector";

const SUBSCRIBEDARR = ["Buffalo Milk A2", "Dahi", "Paneer", "Cow Milk A2"];

const AddVacation = () => {
  const [startDate, setStartDate] = useState(
    moment().hour()>19?moment().add(2, "d").toDate():moment().add(1, "d").toDate()
  );
  const [endDate, setEndDate] = useState(
    moment().hour()>19?moment().add(3, "d").toDate():moment().add(3, "d").toDate()
  );
  const [subProductsArr, setSubProductsArr] = useState([]);
  useEffect(() => {
    // setStartDate(moment(startDate).add(1,"day"));
    // let currTime=new Date();
    // if(currTime.getHours>19){
    //     setStartDate(moment().add(2, "d").toDate());
    //     setEndDate(moment().add(3, "d").toDate())
    // }else{
    //     setStartDate(moment().add(1, "d").toDate());
    //     setEndDate(moment().add(2, "d").toDate())
    // }
    // let ct=moment().hour()
    // console.log(ct);
    setEndDate(moment(startDate).add(1,"d").toDate());
    console.log(endDate)
    console.log(startDate);
  }, [startDate]);
  const handleStartDateChange = (e) => {
    setStartDate(new Date(e.target.value));
    console.log(moment(e.target.value).format("DD-MM-YYYY"));
  };
  const handleEndDateChange = (e) => {
    setEndDate(new Date(e.target.value));
    console.log(moment(e.target.value).format("DD-MM-YYYY"));
  };
  const handleSelectedProduct = (item) => {
    console.log(startDate._d);
    if (item == "All") {
      if (SUBSCRIBEDARR.length === subProductsArr.length) {
        setSubProductsArr([]);
      } else {
        setSubProductsArr([...SUBSCRIBEDARR]);
      }
    } else {
      if (subProductsArr.includes(item)) {
        setSubProductsArr(subProductsArr.filter((p) => p !== item));
      } else {
        setSubProductsArr([item, ...subProductsArr]);
      }
    }
  };
  const handleAddVacation = () => {
    toast.success("Vacation Added Successfully");
  };
  return (
    <section className="cart-recived-section section-padding">
      <div className="container">
        <div className="row">
          <div className="order-top">
            <h2>Pause My Subscription </h2>
            {/* <div className="subItems">
              <div
                className={
                  subProductsArr.length === SUBSCRIBEDARR.length
                    ? "itemBox added"
                    : "itemBox"
                }
                onClick={() => {
                  handleSelectedProduct("All");
                }}
                style={{
                  borderWidth: "2px",
                  borderColor: "#e0903b",
                  fontSize: "17px",
                  borderRadius: "0.6rem",
                }}
              >
                Select All
              </div>
              {SUBSCRIBEDARR.map((item, i) => {
                return (
                  <div
                    key={i}
                    className={
                      subProductsArr.includes(item)
                        ? "itemBox added"
                        : "itemBox"
                    }
                    onClick={() => {
                      handleSelectedProduct(item);
                    }}
                  >
                    {item}
                  </div>
                );
              })}
            </div> */}

            <div className="setDates">
              <div className="setDatesDiv">
                <h5>
                  Start Date
                  {/* <span>your order has been recived</span> */}
                </h5>
                <div className="m-2">
                  <TextField
                    id="date"
                    // label="Choose your birthdate"e
                    style={{ width: "100%", fontFamily: "inherit" }}
                    type="date"
                    onChange={handleStartDateChange}
                    defaultValue={moment(startDate).format("YYYY-MM-DD")}
                    value={moment(startDate).format("YYYY-MM-DD")}
                    InputLabelProps={{
                      shrink: true,
                    }}
                  />
                </div>
              </div>
              <div className="setDatesDiv">
                <h5>
                  End Date
                  <span>(Optional)</span>
                </h5>
                <div className="m-2">
                  <TextField
                    id="date"
                    // label="Choose your birthdate"e
                    style={{ width: "100%", fontFamily: "inherit" }}
                    type="date"
                    onChange={handleEndDateChange}
                    value={moment(endDate).format("YYYY-MM-DD")}
                    defaultValue={moment(endDate).format("YYYY-MM-DD")}
                    InputLabelProps={{
                      shrink: true,
                    }}
                  />
                </div>
              </div>
            </div>
            <div className="sitems">
            <MultiSelector style={{minWidth:"50%"}} />

            </div>
            <div>
              <Link to="/home" className="theme-btn-back">
                Discard
              </Link>
              <Link
                to="/home"
                className="theme-btn-success"
                onClick={() => {
                  handleAddVacation();
                }}
              >
                Finish
              </Link>
            </div>
          </div>
          {/* <Grid className="cartStatus">
                            <Grid container spacing={3}>
                                <Grid item xs={12}>
                                    <Grid className="cartTotals">
                                        <h4>Order details</h4>
                                        <Table>
                                            <TableBody>
                                                {cartList.map(item => (
                                                    <TableRow key={item.id}>
                                                        <TableCell><img src={item.proImg} alt="" /> {item.title} ${item.price} x {item.qty}</TableCell>
                                                        <TableCell
                                                            align="right">${item.qty * item.price}</TableCell>
                                                    </TableRow>
                                                ))}
                                                <TableRow className="totalProduct">
                                                    <TableCell>Total product</TableCell>
                                                    <TableCell align="right">{cartList.length}</TableCell>
                                                </TableRow>
                                                <TableRow>
                                                    <TableCell>Sub Price</TableCell>
                                                    <TableCell align="right">${totalPrice(cartList)}</TableCell>
                                                </TableRow>
                                                <TableRow>
                                                    <TableCell><b>Total Price</b></TableCell>
                                                    <TableCell
                                                        align="right"><b>${totalPrice(cartList)}</b></TableCell>
                                                </TableRow>
                                            </TableBody>
                                        </Table>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid> */}
        </div>
      </div>
    </section>
  );
};
export default AddVacation;
