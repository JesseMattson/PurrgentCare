import AppNavbar from "./AppNavBar";
import { Link } from "react-router-dom";
import { Button, Container } from "reactstrap";
import care from './care.png'

const Home = () => {
    return (
        <div>
            <AppNavbar/>
            <img src={care} alt={"Care"} />
            <Container fluid>
                <Button color="link"><Link to="/accounts">Manage Accounts</Link></Button>
                <Button color="link"><Link to="/persons">Manage Customers</Link></Button>
                <Button color="link"><Link to="/pets">Manage Pets</Link></Button>
            </Container>
        </div>
    );
}

export default Home;