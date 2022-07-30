// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: 'http://localhost:9092/neerseva/api',
  USERS_API_URL: 'http://localhost:9092/neerseva/api/v1/users/',
  BRANDS_API_URL: 'http://localhost:9091/neerseva/api/v1/products/brands/',
  ITEMS_API_URL: 'http://localhost:9091/neerseva/api/v1/products/items/',
  PRODUCT_API_URL: 'http://ec2-3-108-59-192.ap-south-1.compute.amazonaws.com:8090/bs-products/',
  IMAGES_API_URL: 'http://localhost:9090/neerseva/api/v1/images/',
  apiBaseUrl: 'http://localhost:8080/',
  clientUrl: '?redirect_uri=http://localhost:8081/login'


};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
