import React, { useState } from "react";
import Grid from "@material-ui/core/Grid";
import {
  Input,
  Typography,
} from "@material-ui/core";
import SimpleReactValidator from "simple-react-validator";
import { toast } from "react-toastify";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
// import logo from "../../images/Logo_bsmp.jpeg";
import logo from "../../images/tree8.png";
import logo1 from "../../images/fulltree_black_background.png";
//import logo2 from "../../images/tree4.jpeg";
import axios from "axios";
// import FormControlLabel from "@material-ui/core/FormControlLabel";
// import Checkbox from "@material-ui/core/Checkbox";
import { Link, withRouter } from "react-router-dom";

// import { makeStyles } from "@material-ui/styles";
import OtpInput from "react-otp-input";
import Loader from "../../components/loader";
import "./style1.scss";

const LoginPage = (props) => {
  const [isLoading, setIsLoading] = useState(false);
  const [open, setOpen] = React.useState(false);
  const [contact, setContact] = useState("");
  const [registerValue, setRegisterValue] = useState({
    email: "",
    full_name: "",
  });

  // const handleClose = () => {
  //   setOpen(false);
  // };
  // const handleToggle = () => {
  //   setOpen(!open);
  // };

  // const handleRegisterContact = (e) => {
  //   setRegisterValue({ ...registerValue, [e.target.name]: e.target.value });
  //   validator.showMessages();
  // };
  // let otpURL = process.env.REACT_APP_GET_OTP;
  let otpURL =
    "http://ec2-3-108-59-192.ap-south-1.compute.amazonaws.com:8080/brahmashakti/";
  const handleContact = (e) => {
    setContact(e.target.value.replace(/[^0-9]/g, ""));
  };

  const [value, setValue] = useState({
    contact: "",
    otp: "",
  });
  const [otp, setOtp] = useState("");

  const [contactRendering, setContactRendering] = useState(true);
  // const [registerRendering, setRegisterRendering] = useState(false);
  const [isRegistered, setIsRegistered] = useState();

  const changeHandler = (e) => {
    setValue({ ...value, [e.target.contact]: e.target.value });
    validator.showMessages();
  };

  // const rememberHandler = () => {
  //   setValue({ ...value, remember: !value.remember });
  // };

  const otpRendering = () => {
    if (/^(?:(?:\+|0{0,2})91(\s*[\-]\s*)?|[0]?)?[6789]\d{9}$/.test(contact)) {
      axios.post(otpURL + "login", { contact }).then((res) => {
        setIsRegistered(res.data.registered);
        console.log(res);
        // setIsLoading(true);
      });

      setContactRendering(false);
      return true;
    } else {
      validator.showMessages();
      toast.error("Contact Number is not valid!");
      return false;
    }
  };

  const [validator] = React.useState(
    new SimpleReactValidator({
      className: "errorMessage",
    })
  );

  const confirmOtp = async () => {
    axios
      .post(otpURL + "validate-otp", { contact, otp })
      .then((response) => {
        setIsLoading(true);
        // console.log("response confirm otp", isRegistered, response.status);
        if ((response.status == "200" || response.status=="202" )&& isRegistered) {
          // props.history.push("/home");
          console.log(response,"resdata");
          navigateTo("home");
        } else if ((response.status == "200" || response.status=="202" ) && !isRegistered) {
          navigateTo("register");
          // props.history.push("/register");
        }
      })
      .catch((err) => {
        toast.error(err.response.data.errorMessage);
        // console.log(err.response.data.errorMessage);
      });
  };
  const navigateTo = (screen) => {
    let sc = "/" + screen;
    setTimeout(() => {
      props.history.push(sc);
    }, 2750);
  };
  const contactRender = () => {
    setContactRendering(true);
  };

  const resendOTP = () => {
    // console.log("Resend OTP");
  };

  const submitForm = (e) => {
    e.preventDefault();
    if (validator.allValid()) {
      setValue({
        contact: "",
        otp: "",
      });
      validator.hideMessages();

      const userRegex = /^user+.*/gm;
      const contact = value.contact;

      if (contact.match(userRegex)) {
        toast.success("You successfully Login !");
        props.history.push("/home");
      }
    } else {
      validator.showMessages();
      toast.error("Empty field is not allowed!");
    }
  };

  return (
    <Grid className="loginWrapper">
      <Grid className="loginForm">
        <div className="mb-0">
          <h2 style={{ fontSize: "24px", marginLeft: "60px" }}>
            <span>Welcome to </span> Brahmshakti
          </h2>
        </div>
        <h2>Sign In</h2>

        {/* <p>Sign in to your account</p> */}
        <form onSubmit={submitForm}>
          <Grid container spacing={3} style={{ marginTop: 100 }}>
            {contactRendering ? (
              <Grid item xs={12}>
                <Input
                  className="inputOutline"
                  fullWidth
                  placeholder="Contact"
                  value={contact}
                  variant="outlined"
                  name="contact"
                  label="Contact"
                  inputlabelprops={{
                    shrink: true,
                  }}
                  onBlur={(e) => changeHandler(e)}
                  onChange={(e) => handleContact(e)}
                />
                <Grid className="formFooter">
                  <Button
                    disabled={contact.length < 10}
                    onClick={otpRendering}
                    fullWidth
                    className={`${
                      contact.length < 10 ? "cBtnCustom" : "cBtnTheme"
                    }`}
                    type="submit"
                  >
                    Get OTP
                  </Button>
                </Grid>
              </Grid>
            ) : (
              <>
                <Grid item xs={12}>
                  {!isLoading ? (
                    <>
                      <Grid item xs>
                        <Typography
                          variant="h6"
                          gutterBottom
                          style={{ textAlign: "center" }}
                        >
                          Enter the code sent to {contact} &nbsp;
                          <Link className="icon" onClick={contactRender}>
                            <i className="ti-pencil"></i>
                          </Link>
                        </Typography>
                      </Grid>
                      <OtpInput
                        value={otp}
                        type="password"
                        onChange={(otp) => {
                          // console.info(otp);
                          setOtp(otp);
                        }}
                        numInputs={4}
                        inputStyle={{
                          fontSize: "24px",
                          width: "36px",
                          height: "36px",
                          margin: "4px",
                          borderTop: "0px",
                          borderLeft: "0px",
                          borderRight: "0px",
                          outline: "none",
                          borderColor: "#000a46",
                        }}
                        containerStyle={{
                          margin: "20px auto",
                          padding: "10px",
                          justifyContent: "center",
                          alignItems: "center",
                        }}
                        isInputNum
                      />
                      <Link style={{ fontSize: "small" }} onClick={resendOTP}>
                        Resend OTP
                      </Link>

                      <Grid className="formFooter">
                        <Button
                          className={`${
                            otp.length < 4 ? "cBtnCustom" : "cBtnTheme"
                          }`}
                          disabled={otp.length < 4}
                          fullWidth
                          type="number"
                          onClick={confirmOtp}
                        >
                          Submit
                        </Button>
                      </Grid>
                    </>
                  ) : (
                    <Loader isLoading={isLoading} setIsLoading={setIsLoading} />
                  )}
                </Grid>
              </>
            )}
          </Grid>
        </form>
        {/* BackGround(water mark) logo */}
        <div className="shape-img">
          <img src={logo} alt="No Image" />
          {/* <i className="fi flaticon-honeycomb"></i> */}
        </div>
        {/* <div className="shape-img">
          <img src={logo2} alt="No Image" />          
        </div> */}
      </Grid>
    </Grid>
  );
};

export default withRouter(LoginPage);
