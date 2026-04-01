import { useEffect } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { URL_BEFORE_LOGIN } from "../../constants/Constants";
import { User } from "../../models/User";
import { useUser } from "../../context/UserContext";
import UserService from "../../services/UserService";

const OAuth2Redirect = () => {
    const location = useLocation();
    const { setUser } = useUser(); 
    const redirectUrl = '/print-bonjour-user';

    useEffect(() => {
        const urlParams = new URLSearchParams(location.search);
        const accessToken = urlParams.get('access_token');
        const refresh_token = urlParams.get('refresh_token');
        if (!accessToken || !refresh_token) return;
        const loggedInUser = User.parse(accessToken, refresh_token);
        setUser(loggedInUser);
        UserService.saveUser(loggedInUser);
    }, [location]);

    return <Navigate to={redirectUrl} />;
}

export default OAuth2Redirect;