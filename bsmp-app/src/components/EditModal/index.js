import React, { useState, useEffect } from "react";
import TextField from "@material-ui/core/TextField";
import moment from "moment";
import KeyboardBackspace from "@material-ui/icons/Close";
import Calendar from "react-calendar";
import Done from "@material-ui/icons/Done";
import "./style.css";

export default (props) => {
  const [subDetails, setSubDetails] = useState({
    frequency: props.editModalProduct.subscription.frequency,
    startDate: new Date(),
    noOfDays: props.editModalProduct.subscription.noOfDays,
    endDate: props.editModalProduct.subscription.endDate,
  });
  const [scheduledDates, setScheduledDates] = useState([]);
  useEffect(()=>{
    // setSubDetails({frequency:frequency,startDate:new Date(startDate),noOfDays:noOfDays,endDate:endDate});
    let arr=[];
    let sDate = new Date(subDetails.startDate);
    sDate.setHours(0, 0, 0, 0);
    let eDate = new Date(sDate.getFullYear(), sDate.getMonth() + 1, 0);
    eDate.setHours(0, 0, 0, 0);
    if (subDetails.frequency === 1) {arr = getEverydayScheduleDates(sDate, eDate) }
    else if (subDetails.frequency === 2) {arr = get3DaysScheduleDates(sDate, eDate)}
    else if (subDetails.frequency === 3) {arr = getAlternateScheduleDates(sDate, eDate)}
    setScheduledDates(arr);
    
  },[props.showEditModal,subDetails.startDate,subDetails.frequency])
//   const {frequency,startDate,noOfDays,endDate }= props.editModalProduct.subscription;
  
  const handleFreqChange=(freq)=>{
    setSubDetails({...subDetails,frequency:freq});
  };

  const handleStartDateChange = (e) => {
    setSubDetails({...subDetails,startDate:new Date(e.target.value)});
  };
  const getEverydayScheduleDates = (start, end) => {
    if (start.getDate() === new Date().getDate())
    start.setDate(start.getDate() + 1);
    let arrdates = [];
    while (start <= end) {
      arrdates.push(moment(new Date(start)).format("DD-MM-YYYY"));
      start.setDate(start.getDate() + 1);
    }
    setSubDetails({...subDetails,noOfDays:arrdates.length});
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
    setSubDetails({...subDetails,noOfDays:arrdates.length});
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
    setSubDetails({...subDetails,noOfDays:arrdates.length});
    return arrdates;
  };
  const submitEditDetails=()=>{
    props.editChanges(subDetails);
    props.setShowEditModal(0);

  }

  return (
    <div className="modalEdit">
      <div className="row">
        <div className="col">
          <div className="scheduleButtons">
            <span onClick={()=>handleFreqChange(1)} className={subDetails.frequency === 1 ? "sbuttons-a" : "sbuttons"}>
              Everyday              
            </span>
            <span onClick={()=>handleFreqChange(2)} className={subDetails.frequency === 2 ? "sbuttons-a" : "sbuttons"}>
              3 Days<span style={{ fontSize: "8px" }}>(Tue,Thu,Sat)</span>
            </span>
            <span onClick={()=>handleFreqChange(3)} className={subDetails.frequency === 3 ? "sbuttons-a" : "sbuttons"}>
              Alternate
            </span>
          </div>
        </div>
      </div>
      <div className="row">
        <div className="calenderEdit">
          <h6 style={{ fontWeight: 600 }}>Start Date: </h6>
          <TextField
            id="date"
            // label="Choose your birthdate"e
            style={{ width: "100%", fontFamily: "inherit" }}
            type="date"
            onChange={handleStartDateChange}
            value={moment(new Date(subDetails.startDate)).format("YYYY-MM-DD")}
            // defaultValue={moment(new Date(subDetails.startDate)).format("YYYY-MM-DD")}
            InputLabelProps={{
              shrink: true,
            }}
          />
        </div>
      </div>
      <div className="row">
        <div className="calDates">
          <Calendar
            //  onChange={onChange}
            showNavigation={false}
            // defaultValue={subDetails.startDate}
              tileClassName={({ date, view }) => {
                if(moment(date).format("DD-MM-YYYY")===moment(subDetails.startDate).format("DD-MM-YYYY")){return "highlightStartDate"}
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
        <div className="editChanges">
          <div className="changesButtonDiscard" onClick={()=>{props.setShowEditModal(0)}}>
            <KeyboardBackspace  />{" "}
          </div>
          <div className="changesButtonSave" onClick={submitEditDetails}>
            <Done />
          </div>
        </div>
      </div>
    </div>
  );
};
