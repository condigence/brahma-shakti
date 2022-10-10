import data from './data_copy.json';
import axios from "axios";

export default async () => {
  let data=[];
  data=await axios.get("http://ec2-3-108-59-192.ap-south-1.compute.amazonaws.com:8080/brahmashakti/api/bs-products")
  .then(res=>{
    console.log("API CALLED",res)
    return res.data;
  })
  .catch(e=>{
    console.log(e,"errror")
  })
  return data;
}