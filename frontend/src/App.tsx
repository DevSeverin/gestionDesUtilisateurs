import { useEffect, useState } from 'react'
import './App.css'
import UserService from './services/UserService'
import { UserContext } from './context/UserContext';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './pages/public/Login';
import LoggedInPage from './pages/LoggedIn/LoggedInPage';
import PrintBonjour from './pages/public/printBonjour/PrintBonjour';
import OAuth2Redirect from './components/OAuth2Redirect/OAuth2Redirect';
import PrintBonjourUser from './pages/LoggedIn/PrintBonjourUser/PrintBonjourUser';

function App() {
  const [user, setUser] = useState(UserService.getUser());

  useEffect(() => {
    setInterval(() => UserService.refreshUser(setUser), 60000)
  }, []);

  return (
    <UserContext.Provider value={{user, setUser}}>
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route element={<LoggedInPage />}>
                <Route path="/print-bonjour" element={<PrintBonjourUser />} />
            </Route>
            <Route path='/login' element={<Login />} />
            <Route index element={<PrintBonjour />} />
            <Route path='/oauth2/redirect' element={<OAuth2Redirect />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </UserContext.Provider>
  );
}

export default App;
