import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PersonList from "./PersonList";
import PersonEdit from "./PersonEdit";
import PetList from "./PetList";
import PetEdit from "./PetEdit";
import {PERSON_BASE_URL, PET_BASE_URL} from "./constants";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route path={`${PERSON_BASE_URL}`} exact={true} element={<PersonList/>}/>
                <Route path={`${PERSON_BASE_URL}/:id`} exact={true} element={<PersonEdit/>}/>
                <Route path={`${PET_BASE_URL}`} exact={true} element={<PetList/>}/>
                <Route path={`${PET_BASE_URL}/:id`} exact={true} element={<PetEdit/>}/>
            </Routes>
        </Router>
    )
}

export default App;
