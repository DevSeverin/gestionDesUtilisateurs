import axios from "axios";


const Axios = axios.create({
    baseURL: '/api'
})

class HttpClient {

    static INSTANCE = new HttpClient();

    get<T>(url: string) {
        Axios.get<T>(url)
    }

    private qetAuthorizationHeader() {
        return {
            headers: {
                'Authorization': UserService.getAccessToken();
            }
        }
    }

    

}