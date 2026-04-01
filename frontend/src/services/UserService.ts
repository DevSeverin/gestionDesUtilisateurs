import { USER } from "../constants/Constants";
import type { Tokens } from "../models/Tokens";
import { NOT_LOGGED_IN_USER, User } from "../models/User";
import HttpClient from "./HttpClient";

class UserService {
    static INSTANCE = new UserService();

    saveUser(user: User) {
        localStorage.setItem(USER, JSON.stringify(user));
    }

    getUser() {
        const user = localStorage.getItem(USER);
        if (!user) return NOT_LOGGED_IN_USER;
        return JSON.parse(user);
    }

    refreshUser(setUser: (user: User) => void) {
        const user = this.getUser();

        // ✅ Ne pas appeler le backend si l'utilisateur n'est pas connecté
        if (User.isNotLoggedIn(user)) return;

        // ✅ Ne pas appeler si pas de refresh token
        if (!user.refreshToken) return;

        return HttpClient.get<Tokens>(`/token/refresh?refresh_token=${user.refreshToken}`)
            .then(res => {
                const newUser = User.parse(res.data.accessToken, res.data.refreshToken);
                setUser(newUser);
                this.saveUser(newUser);
            })
            .catch(() => {
                // ✅ Silencieux — ne pas crasher si le refresh token est expiré
                console.warn("Refresh token expiré, session terminée.");
            });
    }

    getAccessToken() {
        const user = this.getUser();
        return User.isLoggedIn(user) && user.accessToken ? `Bearer ${user.accessToken}` : null;
    }
}

export default UserService.INSTANCE;