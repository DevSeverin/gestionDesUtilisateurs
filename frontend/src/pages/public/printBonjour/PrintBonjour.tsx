import { useNavigate } from "react-router-dom";
import Button from "../../../components/Button/Button";
import { useUser } from "../../../context/UserContext";
import { NOT_LOGGED_IN_USER } from "../../../models/User";
import UserService from "../../../services/UserService";
import { useState } from "react";
import ConfirmModal from "../../../components/ConfirmModal/ConfirmModal";
import { LOGOUT_MODAL_BUTTON_LABEL, LOGOUT_MODAL_HEADER, LOGOUT_MODAL_MESSAGE } from "../../../constants/Constants";

const PrintBonjour = () => {

    const [openLogoutModal, setOpenLogoutModal] = useState(false);
    const { user, setUser } = useUser();
    const navigate = useNavigate();

    const logout = () => {
        setUser(NOT_LOGGED_IN_USER);
        UserService.saveUser(NOT_LOGGED_IN_USER);
        navigate("/");
    }

    return (
        <>
        <div>
            <h1>Bonjour</h1>
        </div>
        <div>
            <Button>
                Connecter
            </Button>
            <Button onClick={() => setOpenLogoutModal(true)}>
                Deconnecter
            </Button>
             <ConfirmModal
                header={LOGOUT_MODAL_HEADER}
                message={LOGOUT_MODAL_MESSAGE}
                isOpen={openLogoutModal}
                setIsOpen={setOpenLogoutModal}
                action={logout}
                actionLabel={LOGOUT_MODAL_BUTTON_LABEL}
            />
        </div>
        </>
    )
}

export default PrintBonjour;