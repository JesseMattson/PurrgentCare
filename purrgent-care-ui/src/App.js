import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PersonList from "./PersonList";
import PersonEdit from "./PersonEdit";
import PetList from "./PetList";
import PetEdit from "./PetEdit";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route path="/persons" exact={true} element={<PersonList/>}/>
                <Route path="/persons/:id" exact={true} element={<PersonEdit/>}/>
                <Route path="/pets" exact={true} element={<PetList/>}/>
                <Route path="/pets/:id" exact={true} element={<PetEdit/>}/>
            </Routes>
        </Router>
    )
}

export default App;
