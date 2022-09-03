import React, {Fragment} from 'react';
import Navbar from '../../components/Navbar'
import PageTitle from '../../components/pagetitle'
import Footer from '../../components/footer'
import Scrollbar from '../../components/scrollbar'
import Project from '../../components/Project'
import Stepper from '../../components/Stepper'



const ProjectPage =(props) => {
    
    const cartEditItem=props.history.location.state;
    return(
        <Fragment>
            <Navbar hClass={"header-style-2"} />
            <PageTitle pageTitle={'Project'} pagesub={'Project'}/> 
            <Stepper cartEditItem={cartEditItem} />
            {/* <Project/> */}
            <Footer/>
            <Scrollbar/>
        </Fragment>
    )
};
export default ProjectPage;
