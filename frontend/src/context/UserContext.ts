import { createContext, useContext } from "react";
import UserService from "../services/UserService";

export const UserContext = createContext(UserService.getUser());

export const useUser = () => useContext(UserContext);