import { config } from "../../config/Config";

const Login = () => {
    const login = (provider: string) => {
        window.location.href = `${config.BACKEND_BASE_URL}/oauth2/authorization/${provider}`;
    }

    return (
        <div>
            <h1>Se connecter</h1>
            <div>
                <button onClick={() => login('google')}>
                    Se connecter avec google
                </button>
                 <button onClick={() => login('github')}>
                    Se connecter avec github
                </button>
            </div>
        </div>
    )
}

export default Login;