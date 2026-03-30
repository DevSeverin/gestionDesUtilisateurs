import { config } from "../../config/Config";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import HttpClient from "../../services/HttpClient";
import { Tokens } from "../../models/Tokens";
import { User } from "../../models/User";
import { useUser } from "../../context/UserContext";
import UserService from "../../services/UserService";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const { setUser } = useUser();
    const navigate = useNavigate();

    const login = (provider: string) => {
        window.location.href = `${config.BACKEND_BASE_URL}/oauth2/authorization/${provider}`;
    };

    const loginLocal = async () => {
        try {
            const response = await HttpClient.postPublic<Tokens>(
                "/auth/authenticate/local",
                { email, password }
            );
            console.log("Réponse brute du backend :", response.data);
            console.log("accessToken reçu :", response?.data?.accessToken);
            console.log("refreshToken reçu :", response?.data?.refreshToken);
            console.log("data complet :", JSON.stringify(response.data));
            console.log("clés reçues :", Object.keys(response.data as any));
            const { accessToken, refreshToken } = response.data;
            const loggedInUser = User.parse(accessToken, refreshToken);
            setUser(loggedInUser);
            UserService.saveUser(loggedInUser);
            navigate('/');
        } catch (err: any) {
            // ✅ Affiche le vrai message d'erreur
            console.error("Erreur complète :", err);
            console.error("Status :", err?.response?.status);
            console.error("Message backend :", err?.response?.data);
            alert(`Erreur ${err?.response?.status} : ${JSON.stringify(err?.response?.data)}`);
        }
    };

    return (
        <div>
            <h1>Se connecter</h1>
            <div>
                <button onClick={() => login('google')}>Se connecter avec Google</button>
                <button onClick={() => login('github')}>Se connecter avec GitHub</button>
                <p>----------------------------------</p>
                <input
                    type="text"
                    placeholder="Entrer votre email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Entrer votre mot de passe"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button onClick={loginLocal}>Se connecter</button>
            </div>
        </div>
    );
};

export default Login;