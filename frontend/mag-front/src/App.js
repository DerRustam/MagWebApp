import './App.css';
import React, {Component} from "react";
import {Container} from "react-bootstrap"
import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import NavigationMenu from "./components/navigation/NavigationMenu";
import Footer from "./components/default/Footer";
import HomePage from "./pages/HomePage";
import DataPage from "./pages/DataPage";
import {RequireAuth} from "./components/navigation/RequireAuth";

export default class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="App">
                <Router>
                    <NavigationMenu onAccountLoaded/>
                    <Routes>
                        <Route path="/" element={<HomePage/>}/>
                        <Route element={<RequireAuth />}>
                            <Route path="/data_working" element={<DataPage/>}/>
                        </Route>
                        <Route path="*" element={<Navigate to="/"/>}/>
                    </Routes>
                    <Footer/>
                </Router>
            </div>
        );
    }
}
