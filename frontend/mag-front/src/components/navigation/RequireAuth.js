import React from "react"
import {Route, Navigate, useLocation} from "react-router-dom"
import DataPage from "../../pages/DataPage";
import {isAuth} from "../../services/auth-service";
import SignContainer from "../sign/SignContainer";

export {RequireAuth}

function RequireAuth() {
    let location = useLocation();
    if (!isAuth()){
        return <Navigate to="/" state={{from: location}}/>
    }
    return <DataPage/>
}