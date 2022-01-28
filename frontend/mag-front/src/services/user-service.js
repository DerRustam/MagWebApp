import Axios from 'axios'
import CookiesService from "./cookies-service"

import {USER_URL} from "./api-links";
import {getUserName} from "./auth-service";

export const UserStatus = {
    OK: 0,
    ERR_NF: 1,
    ERR_AUTH: 2,
    ERR_OTHER: 3
}

class UserService{
    getRequestHeaders(){
        return {
            'X-XSRF-TOKEN': CookiesService.getCSRFToken(),
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    loadAccountData(username){
        if (username !== null){
            return Axios.get(USER_URL+"inst/"+username,
                {
                    withCredentials: true
                }).then(
                response => {
                    return {
                        status: UserStatus.OK,
                        accountData: response.data
                    }
                },
                error => {
                    if (error.response){
                        if (error.response.status === 404){
                            return {status: UserStatus.ERR_NF}
                        } else if (error.response.status === 401){
                            return {status: UserStatus.ERR_AUTH}
                        }
                    }
                    return {status: UserStatus.ERR_OTHER}
                }
            )
        } else return {
            status: UserStatus.ERR_OTHER
        }

    }
}

export default new UserService();