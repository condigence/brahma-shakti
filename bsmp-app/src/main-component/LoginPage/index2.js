import React, { useState } from "react";
import Grid from "@material-ui/core/Grid";
import { Input, Typography } from "@material-ui/core";
import SimpleReactValidator from "simple-react-validator";
import { toast } from "react-toastify";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
// import logo from "../../images/Logo_bsmp.jpeg";
import logo from "../../images/cow1.png";
import axios from "axios";
// import FormControlLabel from "@material-ui/core/FormControlLabel";
// import Checkbox from "@material-ui/core/Checkbox";
import { Link, withRouter } from "react-router-dom";

// import { makeStyles } from "@material-ui/styles";
import OtpInput from "react-otp-input";

import "./style.scss";

const LoginPage = (props) => {
  const [contact, setContact] = useState("");
  const [registerValue, setRegisterValue] = useState({
    email: '',
    full_name: '',
});
const handleRegisterContact = (e) => {
  setRegisterValue({...registerValue, [e.target.name]: e.target.value});
  validator.showMessages();
};
  let otpURL = process.env.REACT_APP_GET_OTP;
  const handleContact = (e) => {
    setContact(e.target.value.replace(/[^0-9]/g, ""));
  };

  const [value, setValue] = useState({
    contact: "",
    otp: "",
  });
  const [otp, setOtp] = useState("");

  const [contactRendering, setContactRendering] = useState(true);
  const [registerRendering, setRegisterRendering] = useState(false);

  const changeHandler = (e) => {
    setValue({ ...value, [e.target.contact]: e.target.value });
    validator.showMessages();
  };

  // const rememberHandler = () => {
  //   setValue({ ...value, remember: !value.remember });
  // };

  const otpRendering = () => {
    if (/^(?:(?:\+|0{0,2})91(\s*[\-]\s*)?|[0]?)?[6789]\d{9}$/.test(contact)) {
      axios.post(otpURL + "/get-otp", { contact }).then((res) => {
        console.log(res);
        setRegisterRendering(res.isRegistered);
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

  const confirmOtp = () => {
    props.history.push("/home");
    // axios
    // 	.post('http://localhost:8888/verifyOTP', {
    // 		phone: `${value.phone}`,
    // 		hash: `${value.hash}`,
    // 		otp: `${value.otp}`,
    // 		withCredentials: true
    // 	})
    // 	.then(function(res) {
    // 		console.log(res.data);
    // 		window.location.reload();
    // 	})
    // 	.catch(function(error) {
    // 		console.log(error.response.data);
    // 		setError({ ...error, error: error.response.data.msg });
    // 	});
  };

  const contactRender = () => {
    setContactRendering(true);
  };

  const resendOTP = () => {
    console.log("Resend OTP");
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
        <div className="section-title mb-0">
          <h2 style={{ fontSize: "24px" }}>
            <span>Welcome to </span> Brahmshakti
          </h2>
        </div>
        <h2>Sign In</h2>
        <p>Sign in to your account</p>
        <form onSubmit={submitForm}>
          <Grid container spacing={3}>
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
                  InputLabelProps={{
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
              {registerRendering?(<>
              <Grid item xs={12}>
                <TextField
                    className="inputOutline"
                    fullWidth
                    placeholder="Full Name"
                    value={registerValue.full_name}
                    variant="outlined"
                    name="full_name"
                    label="Name"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    onBlur={(e) => handleRegisterContact(e)}
                    onChange={(e) => handleRegisterContact(e)}
                />
                {validator.message('full name', registerValue.full_name, 'required|alpha')}
                </Grid>
                <Grid item xs={12}>
                  <TextField
                      className="inputOutline"
                      fullWidth
                      placeholder="E-mail"
                      value={registerValue.email}
                      variant="outlined"
                      name="email"
                      label="E-mail"
                      InputLabelProps={{
                          shrink: true,
                      }}
                      onBlur={(e) => handleRegisterContact(e)}
                      onChange={(e) => handleRegisterContact(e)}
                  />
                  {validator.message('email', registerValue.email, 'optional|email')}
                </Grid></>):<></>
              }

              <Grid item xs={12}>
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
                  onChange={(otp) => {
                    console.info(otp);
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
                    className={`${otp.length < 4 ? "cBtnCustom" : "cBtnTheme"}`}
                    disabled={otp.length < 4}
                    fullWidth
                    type="number"
                    onClick={confirmOtp}
                  >
                    Submit
                  </Button>
                </Grid>

                {/* <Grid className="loginWithSocial">
                                <Button className="facebook"><i className="fa fa-facebook"></i></Button>
                                <Button className="twitter"><i className="fa fa-twitter"></i></Button>
                                <Button className="linkedin"><i className="fa fa-linkedin"></i></Button>
                            </Grid> */}
                {/* <p className="noteHelp">
                  Don't have an account?{" "}
                  <Link to="/register">Create free account</Link>
                </p> */}
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
      </Grid>
    </Grid>
  );
};

export default withRouter(LoginPage);
