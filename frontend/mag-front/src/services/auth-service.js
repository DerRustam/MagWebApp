import Axios from 'axios'
import CookiesService from "./cookies-service"

import {AUTH_URL, USER_URL} from "./api-links";

export const SignStatus = {
    OK: 0,
    ERR_NF: 1,
    ERR_DUP: 2,
    ERR_OTHER: 3
}

class AuthService {
    constructor() {
        let xsrf = CookiesService.getCSRFToken();
        if (xsrf === null){
            this.requestToken().then(r =>{
                xsrf = CookiesService.getCSRFToken()
                console.log("New xsrf token: ", xsrf);
            });
        }
    }

    async requestToken(){
        await Axios.get(USER_URL+"token", {withCredentials: true});
    }

    getHeaders(){
        let xsrf = CookiesService.getCSRFToken();
        return {
            'X-XSRF-TOKEN': xsrf,
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    register(userData){
        return Axios.post(USER_URL+"register", {
                username: userData.username,
                email: userData.email,
                password: userData.password,
                firstName: userData.firstName,
                gender: userData.gender
            }, {
                withCredentials: true
            }
        ).then(
            response => {
                return { status: SignStatus.OK }
            },
            error => {
                if (error.response.status === 400){
                    return { status: SignStatus.ERR_DUP }
                } else {
                    return { status: SignStatus.ERR_OTHER }
                }
            }
        );
    }

    login(userData){
        let formData = new FormData();
        formData.append("username", userData.userId);
        formData.append("password", userData.password);
        formData.append("isRemember", userData.remembrance);
        return Axios.post(AUTH_URL+ "login", formData, {
            withCredentials: true
            }
        ).then(
            response =>{
                localStorage.setItem("username", response.data.username)
                return {
                    status: SignStatus.OK,
                    accountData: response.data
                };
            },
            error => {
                if (error.response){
                    if (error.response.status === 401){
                        return { status: SignStatus.ERR_NF }
                    }
                }
                return { status: SignStatus.ERR_OTHER }
            }
        )
    }

    logout(){
        removeUserName();
        return Axios.post(AUTH_URL+ "logout", {}, {
                withCredentials: true
            }
        )
    }
}

export default new AuthService();

export function isAuth() {
   return (localStorage.getItem("username") !== null)
}

export function getUserName() {
   return localStorage.getItem("username");
}

export function removeUserName(){
    localStorage.removeItem("username");
}

