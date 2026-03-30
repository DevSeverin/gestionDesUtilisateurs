import axios from "axios";
import UserService from "./UserService";


const Axios = axios.create({
    baseURL: '/api'
})

class HttpClient {

    static INSTANCE = new HttpClient();

    get<T>(url: string) {
        return Axios.get<T>(url, this.getAuthorizationHeader());
    }

    post<T>(url: string, data: any) {
        return Axios.post<T>(url, data, this.getAuthorizationHeader());
    }

    postPublic<T>(url: string, data: any) {
        return Axios.post<T>(url, data); // pas de header Authorization
    }

    put(url: string, data: any) {
        return Axios.put(url, data, this.getAuthorizationHeader());
    }

    delete<T>(url: string) {
        return Axios.delete<T>(url, this.getAuthorizationHeader());
    }

    

    private getAuthorizationHeader() {
        return {
            headers: {
                'Authorization': UserService.getAccessToken()
            }
        }
    }

}

export default HttpClient.INSTANCE;