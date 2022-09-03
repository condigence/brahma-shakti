import React, { useState } from 'react';
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng,
} from 'react-places-autocomplete';
import TextField from "@material-ui/core/TextField";
 import './style.css'
const LocationSearchInput = () => {
  const [state,setState]=useState({address:''});
 
  const handleChange = address => {
    setState({ address });
  };
 
  const handleSelect = address => {
    geocodeByAddress(address)
      .then(results => getLatLng(results[0]))
      .then(latLng => console.log('Success', latLng))
      .catch(error => console.error('Error', error));
  };
  const searchOptions = {
    location: new window.google.maps.LatLng(28.897297, 76.595535),
    radius: 15,
  }
 
    return (
      <PlacesAutocomplete
        value={state.address}
        onChange={handleChange}
        onSelect={handleSelect}
        searchOptions={searchOptions}
      >
        {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
          <div >
            <TextField
            fullWidth
            variant="outlined"
            label="Area/Locality"
            color='primary'
            disabled="true"
            InputLabelProps={{
                shrink: true,
            }}
              {...getInputProps({
                placeholder: 'Search Places ...',
                className: 'inputOutline',
              })}
              
            />
            <div className="dropdown">
              {loading && <div>Loading...</div>}
              {suggestions.map(suggestion => {
                const className = suggestion.active
                  ? 'suggestion-item--active'
                  : 'suggestion-item';
                // inline style for demonstration purpose
                const style = suggestion.active
                  ? { backgroundColor: '#fafafa', cursor: 'pointer' }
                  : { backgroundColor: '#ffebcd', cursor: 'pointer' };
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
    );
}

export default LocationSearchInput;