import { createContext, useState } from "react";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(
    () => !!localStorage.getItem("token"),
  );

  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem("user");
    return saved ? JSON.parse(saved) : null;
  });

  const login = (token, userData) => {
    const userWithExpiry = {
      ...userData,
      expiresAt: Date.now() + 1000 * 60 * 60,
    };

    localStorage.setItem("token", token);
    localStorage.setItem("user", JSON.stringify(userWithExpiry)); // ← expiresAt 포함해서 저장
    setIsLoggedIn(true);
    setUser(userWithExpiry);
  };
  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setIsLoggedIn(false);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
