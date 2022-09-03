import React, { useState } from "react";
import Grid from "@material-ui/core/Grid";
import SimpleReactValidator from "simple-react-validator";
import { toast } from "react-toastify";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { Link, withRouter } from "react-router-dom";
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng,
} from "react-places-autocomplete";
import "./style.css";
import "./style.scss";

const NewForm = (props) => {
  const [value, setValue] = useState({
    email: "",
    full_name: "",
    contact: "",
    localityAddress: "",
    completeAddress: "",
    coords: {
      latitude: "",
      longitude: "",
    },
  });

  const handleChange = (address) => {
    setValue({...value, localityAddress: address });
    console.log(value)
  };

  const handleCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((pos) => {
        console.log(pos.coords.latitude);
        console.log(pos.coords.longitude);
        const latlng = new window.google.maps.LatLng(
          pos.coords.latitude,
          pos.coords.longitude
        );
        const geocoder = new window.google.maps.Geocoder();
        geocoder.geocode({ latLng: latlng }, (results, status) => {
          if (status !== window.google.maps.GeocoderStatus.OK) {
            alert(status);
          }
          // This is checking to see if the Geoeode Status is OK before proceeding
          if (status === window.google.maps.GeocoderStatus.OK) {
            setValue({...value, localityAddress: results[0].formatted_address });
            console.log(results);
          }
        });
      });
    } else {
      console.log("Current location not enabled");
    }
  };

  const handleSelect = (address) => {
    geocodeByAddress(address)
      .then((results) => getLatLng(results[0]))
      .then((latLng) => {
        setValue({...value, coords: { latitude: latLng.lat, longitude: latLng.lng },localityAddress: address });
        console.log("Success", latLng);
      })
      .catch((error) => console.error("Error", error));
  };
  const searchOptions = {
    location: new window.google.maps.LatLng(28.897297, 76.595535),
    radius: 15,
  };

  const changeHandler = (e) => {
    setValue({ ...value, [e.target.name]: e.target.value });
    console.log(value)
    validator.showMessages();
  };

  const [validator] = React.useState(
    new SimpleReactValidator({
      className: "errorMessage",
    })
  );

  const submitForm = (e) => {
    e.preventDefault();
    if (validator.allValid()) {
      console.log(value);
      validator.hideMessages();
      toast.success("Registration Complete successfully!");
      // props.history.push("/login");
    } else {
      validator.showMessages();
      toast.error("Empty field is not allowed!");
    }
  };
  return (
    <Grid className="loginWrapper">
      <Grid className="loginForm">
        <h2>ADD Details</h2>
        <p>Milk Delivery Address</p>
        <form onSubmit={submitForm}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <PlacesAutocomplete
                value={value.localityAddress}
                onChange={handleChange}
                onSelect={handleSelect}
                searchOptions={searchOptions}
              >
                {({
                  getInputProps,
                  suggestions,
                  getSuggestionItemProps,
                  loading,
                }) => (
                  <div>
                    <div className="d-flex">
                      <TextField
                        style={{ width: "85%" }}
                        variant="outlined"
                        label="Area/Locality"
                        type="text"
                        value={value.localityAddress}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        {...getInputProps({
                          placeholder: "Search Places ...",
                          className: "inputOutline",
                        })}
                      />
                      <div style={{ marginTop: "-6px" }}>
                        <div
                          onClick={handleCurrentLocation}
                          className="locationIcon"
                        >
                          <div className="spinner">
                            <i
                              style={{ fontSize: 35, color: "#e2e2e2" }}
                              className="fi ti-location-pin"
                            />
                          </div>
                        </div>
                        <span>
                          <p className="currentText">Current</p>
                        </span>
                      </div>
                    </div>
                    <div className="dropdown">
                      {loading && <div>Loading...</div>}
                      {suggestions.map((suggestion) => {
                        console.log(suggestions);
                        const className = suggestion.active
                          ? "suggestion-item--active"
                          : "suggestion-item";
                        const style = suggestion.active
                          ? { cursor: "pointer" }
                          : { cursor: "pointer" };
                        return (
                          <div
                            {...getSuggestionItemProps(suggestion, {
                              className,
                              style,
                            })}
                          >
                            <span>{suggestion.description}</span>
                          </div>
                        );
                      })}
                    </div>
                  </div>
                )}
              </PlacesAutocomplete>
              {validator.message("locality",value.localityAddress, "required|min:10|max:100")}
            </Grid>
            <Grid item xs={12}>
              <TextField
                className="inputOutline"
                fullWidth
                placeholder="Full Name"
                value={value.full_name}
                variant="outlined"
                name="full_name"
                label="Name"
                InputLabelProps={{
                  shrink: true,
                }}
                onBlur={(e) => changeHandler(e)}
                onChange={(e) => changeHandler(e)}
              />
              {validator.message(
                "full name",
                value.full_name,
                "required|alpha_space" 
              )}
            </Grid>

            <Grid item xs={12}>
              <TextField
                className="inputOutline"
                fullWidth
                placeholder="E-mail"
                value={value.email}
                variant="outlined"
                name="email"
                label="E-mail"
                InputLabelProps={{
                  shrink: true,
                }}
                onBlur={(e) => changeHandler(e)}
                onChange={(e) => changeHandler(e)}
              />
              {validator.message("email", value.email, "required|email")}
            </Grid>
            <Grid item xs={12}>
              <TextField
                className="inputOutline"
                fullWidth
                placeholder="Mobile No."
                value={value.contact}
                variant="outlined"
                name="contact"
                type="tel"
                label="Contact"
                InputLabelProps={{
                  shrink: true,
                }}
                onBlur={(e) => changeHandler(e)}
                onChange={(e) => changeHandler(e)}
              />
              {validator.message("contact", value.contact, "required|phone")}
            </Grid>
            <Grid item xs={12}>
              <TextField
                className="inputOutline"
                fullWidth
                placeholder="Complete Address"
                value={value.completeAddress}
                variant="outlined"
                name="completeAddress"
                label="Address"
                type="text"
                InputLabelProps={{
                  shrink: true,
                }}
                onBlur={(e) => changeHandler(e)}
                onChange={(e) => changeHandler(e)}
              />
              {validator.message(
                "completeAddress",
                value.completeAddress,
                "required|min:16|max:100"
              )}
            </Grid>
            <Grid item xs={12}>
              <Grid className="formFooter">
                <Button
                  fullWidth
                  className="cBtn cBtnLarge cBtnTheme"
                  type="submit"
                >
                  Next Step
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </form>
        <div className="shape-img">
          <i className="fi flaticon-wine"></i>
        </div>
      </Grid>
    </Grid>
  );
};

export default withRouter(NewForm);
