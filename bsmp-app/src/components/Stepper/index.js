import * as React from "react";
import Box from "@material-ui/core/Box";
import {
  Stepper,
  Step,
  StepLabel,
} from "@material-ui/core";
import { connect } from "react-redux";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import ManageSubscription from "../StepperStep2";
import Card from "../../main-component/Card/card";
import { addSubToCart, addToWishList } from "../../store/actions/action";
import { TransitionGroup, CSSTransition } from "react-transition-group";

import "./style.scss";
import api from "../../api";

const styles = (theme) => ({
  root: {
    width: "90%",
  },
  button: {
    marginRight: theme.spacing(),
  },
  instructions: {
    marginTop: theme.spacing(),
    marginBottom: theme.spacing(),
  },
  icon: {
    color: "secondary",
    "&$activeIcon": {
      color: "#b83807",
    },
    "&$completedIcon": {
      color: "green",
    },
  },
  activeIcon: {},
  completedIcon: {},
});
const steps = ["Step 1", "Step 2"];

export function HorizontalNonLinearStepper(props) {
  const [activeStep, setActiveStep] = React.useState(0);
  const[productsArray,setProductsArray]=React.useState([]);
  const [completed, setCompleted] = React.useState({});
  const [subProduct, setSubProduct] = React.useState();
  const [skipped, setSkipped] = React.useState(new Set());
  const [yOffset,setYOffset]=React.useState(null);
  const [subDetail,setSubDetails]=React.useState({quantity:"",startDate:"",endDate:"",noOfDays:"",frequency:""});

  React.useEffect(()=>{
    console.log(props.cartEditItem);
    if(props.cartEditItem){
      setSubProduct(props.cartEditItem.catItem);
      handleNext();
    };
    fetchData();
  },[])

  const isStepOptional = (step) => {
    return step === 1;
  };
  const { classes , addSubToCart, addToWishList  } = props;
  
  const isStepSkipped = (step) => {
    return skipped.has(step);
  };
  const handleNext = () => {
    console.log(window.pageYOffset-500)
    setYOffset(300);
    setActiveStep((prevActiveStep) => prevActiveStep + 1);

  };
  const handleAddToCart = () => {
    addSubToCart(subProduct,subDetail);
    handleScroll(yOffset);
    if(props.cartEditItem){}
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
    console.log(subDetail);
  };
  const handleScroll=(y)=>{
    window.scrollTo(0,y);
  }
  const fetchData=async()=>{
    const productsArray =await api();
    const pSubArr=productsArray.filter((item)=>{if(item.subscribable)return item});
    setProductsArray(pSubArr)
  }


  const addSubProduct = (product) => {
    setSubProduct(product);
  };
  const addToWishListProduct = (product, qty = 1) => {
    addToWishList(product, qty);
  };

  const products = productsArray;
  console.log(products,"productss")

  const totalSteps = () => {
    return steps.length;
  };

  const completedSteps = () => {
    return Object.keys(completed).length;
  };

  const allStepsCompleted = () => {
    return completedSteps() === totalSteps();
  };

  const handleBack = () => {
    handleScroll(300);
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
    
  };
  const handleReset = () => {
    setActiveStep(0);
    setCompleted({});
  };

  return (
    <Box>
      <Stepper
        style={{ minHeight: "140px", margin: "auto" }}
        activeStep={activeStep}
      >
        {steps.map((label, index) => {
          const stepProps = {};
          const labelProps = {};
          if (isStepSkipped(index)) {
            stepProps.completed = false;
          }
          return (
            <Step
              style={{ flexDirection: "column" }}
              key={label}
              {...stepProps}
            >
              <StepLabel
                StepIconProps={{
                  classes: {
                    root: classes.icon,
                    active: classes.activeIcon,
                    completed: classes.completedIcon,
                  },
                }}
                {...labelProps}
              >
                <div className=""> {label}</div>
              </StepLabel>
            </Step>
          );
        })}
      </Stepper>
      <div>
        {allStepsCompleted() ? (
          <React.Fragment>
            <Typography sx={{ mt: 2, mb: 1 }}>
              All steps completed - you&apos;re finished
            </Typography>
            <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
              <Box sx={{ flex: "1 1 auto" }} />
              <Button onClick={handleReset}>Reset</Button>
            </Box>
          </React.Fragment>
        ) : (
          <React.Fragment>
            <TransitionGroup component="div">
              <CSSTransition
                key={activeStep}
                timeout={{ enter: 600, exit: 300 }}
                classNames="pageSlider"
                mountOnEnter={false}
                unmountOnExit={true}
              >
                <div className={activeStep === 1 ? "left" : "right"}>
                  {/*  Step 1 */}
                  {activeStep === 0 && (
                    <Card
                      handleNext={handleNext}
                      setSubProduct={setSubProduct}
                      addToWishListProduct={addToWishListProduct}
                      products={products}
                    />
                  )}

                  {activeStep === 1 && (
                    //    Step 2 here
                    <ManageSubscription setSubDetails={setSubDetails} subProduct={subProduct} />
                  )}
                </div>
              </CSSTransition>
            </TransitionGroup>
            <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
              <Button
                color="inherit"
                disabled={activeStep === 0}
                onClick={()=>{window.scrollTo({left:10, top:500,behavior: 'smooth'});handleBack();}}
                sx={{ mr: 1 }}
              >
                Back
              </Button>
              <Box sx={{ flex: "1 1 auto" }} />
              <Button onClick={handleAddToCart} sx={{ mr: 1 }} disabled={activeStep === 0}>
                Add to Cart
              </Button>
            </Box>
          </React.Fragment>
        )}
      </div>
    </Box>
  );
}

export default withStyles(styles)(connect(null, { addSubToCart, addToWishList })(HorizontalNonLinearStepper));
// export default connect(null, { addToCart, addToWishList })(ShopPage);
