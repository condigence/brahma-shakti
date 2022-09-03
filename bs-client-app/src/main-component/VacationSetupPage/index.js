import React,{Fragment} from "react";
import AddVacation from "../../components/AddVacation";
import Footer from "../../components/footer";
import Navbar from "../../components/Navbar";
import Scrollbar from "../../components/scrollbar";

const VacationSetupPage = () => {
    return (
      <Fragment>
        <Navbar hClass={"header-style-2"} />
        <AddVacation/>
        <Footer />
        <Scrollbar />
      </Fragment>
    );
  };
  export default VacationSetupPage;