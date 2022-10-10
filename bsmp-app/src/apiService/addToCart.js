import axios from "axios"
const URL="http://ec2-3-108-59-192.ap-south-1.compute.amazonaws.com:8080/brahmashakti/api/bs-cart/add/"


const addToCartService=async (data)=>{
    // console.log(data)
    await fetch( URL+data.items[0].productId,{method:"GET",headers: {
        // 'Content-Type': 'application/json',
        // 'Access-Control-Allow-Origin': '*'
        // 'Content-Type': 'application/x-www-form-urlencoded',
      }
    //   body: data
      
    }).then(res=>res.json())
    .then(res=>{console.log(Object.keys(res.productsInCart))})
    .catch(er=>console.log(er))

    // console.log(responseData)
}

export default addToCartService;