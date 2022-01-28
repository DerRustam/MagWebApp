import Cookies from "universal-cookie/lib";

const cookies = new Cookies();

class CookiesService {

    getCSRFToken(){
        const token = cookies.get("XSRF-TOKEN");
        if (!token || token === "undefined"){
            cookies.remove("XSRF-TOKEN");
            return null
        }
        return token;
    }

}

export default new CookiesService();