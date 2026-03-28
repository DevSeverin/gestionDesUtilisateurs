import { Outlet } from "react-router-dom";
import AccessError from "../../components/accessError/AccessError";
import { useUser } from "../../context/UserContext"
import { User } from "../../models/User";

const LoggedInPage = () => {

    const user = useUser();

    return User.isLoggedIn(user) ? <Outlet />  : <AccessError message='Utilisateur non connecté' />;

}

export default LoggedInPage;