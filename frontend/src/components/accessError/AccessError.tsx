import { useUser } from '../../context/UserContext';
import { User } from '../../models/User';
import Login from '../login/Login';
import { FiUserX } from 'react-icons/fi';

type AccessErrorProps = {
    message: string;
}

const AccessError = ({message}: AccessErrorProps) => {

    const { user } = useUser();

    return (
        <div>
            <FiUserX/>
            <p>{message}</p>
            {User.isNotLoggedIn(user) && <Login style='button' />}
        </div>
    )
}

export default AccessError;