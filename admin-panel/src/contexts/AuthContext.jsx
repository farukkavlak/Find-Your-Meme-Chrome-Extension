import { useEffect } from "react";
import { createContext, useContext, useState } from "react";


const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentToken, setCurrentToken] = useState(() => {
        const savedToken = localStorage.getItem("authToken");
        return savedToken ? JSON.parse(savedToken) : null;
      });

        useEffect(() => {
        // Save the currentToken to localStorage whenever it changes
        localStorage.setItem("authToken", JSON.stringify(currentToken));
      }, [currentToken]);
    return (
        <AuthContext.Provider value={ { currentToken, setCurrentToken } }>
            {children}
        </AuthContext.Provider>
    )
}