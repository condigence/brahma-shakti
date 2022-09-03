import data from './data_copy.json';
import axios from "axios";

export default () => {
  // var data;
  axios.get("http://ec2-3-108-59-192.ap-south-1.compute.amazonaws.com:8080/brahmashakti/bs-products").then(res=>{
    console.log(res.data);
    // return res.data;
  })
  return data;
}