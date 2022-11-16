import {createContext, useState} from "react";

export const UserContext = createContext({
  isLoggedIn: false,
  getAdminUser: () => {
  },
  adminUser: {},
  adminLogout: () => {}
});

export const AdminProvider = ({children}) => {

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [adminUser, setAdminUserUser] = useState({
    'adminToken': '',
  });

  const getAdminUser = async () => {
    const accessToken = localStorage.getItem('adminToken');
    if (accessToken == null) {
      setIsLoggedIn(false);
    }
    if (accessToken != null) {
      setAdminUserUser({
        'adminToken': accessToken,
      });
      setIsLoggedIn(true);
    }
  }

  const adminLogout = () => {
    localStorage.removeItem('adminToken');
    setAdminUserUser({
      'adminToken': '',
    })
    setIsLoggedIn(false);
  }

  return (
      <UserContext.Provider
          value={{isLoggedIn, getAdminUser, adminUser, adminLogout}}>
        {children}
      </UserContext.Provider>
  )
}