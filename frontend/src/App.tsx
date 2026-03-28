import { useEffect, useState } from 'react'
import './App.css'
import UserService from './services/UserService'
import { UserContext } from './context/UserContext';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './pages/public/Login';
import LoggedInPage from './pages/LoggedIn/LoggedInPage';
import PrintBonjour from './pages/LoggedIn/printBonjour/PrintBonjour';

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
                <Route path="/print-bonjour" element={<PrintBonjour />} />
            </Route>
            <Route path='/login' element={<Login />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </UserContext.Provider>
  );
}

export default App;
