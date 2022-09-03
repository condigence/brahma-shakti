import React, { useEffect } from "react";
import { CircularProgress, Backdrop } from "@material-ui/core";

const Loader = ({ isLoading, setIsLoading }) => {
  useEffect(() => {
    setTimeout(() => {
      setIsLoading(false);
    }, 3000);
  }, []);
  return (
    <>
      <Backdrop
        style={{
          margin: "auto",
          width: "500px",
          height: "75%",
          maxWidth: "90%",
          paddingTop: "3%",
          paddingBottom: "3%",
          transition: "all",
        }}
        sx={{ color: "#fff", zIndex: 100 }}
        open={isLoading}
      >
        <CircularProgress />
      </Backdrop>
    </>
  );
};
export default Loader;
