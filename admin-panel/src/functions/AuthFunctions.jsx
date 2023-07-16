import axios from "axios";

//Login 
export const login = async (user) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = baseUrl + "auth/login";
    const body = {
        username: user.username,
        password: user.password,
    }
    try {
        const response = await axios.post(url, body);
        return response;
    }catch (error) {
        console.log(error)
        return error.response;
    }
}

//Logout
export const logout = async () => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = baseUrl + "auth/logout";
    try {
        const response = await axios.post(url);
        return response;
    }catch (error) {
        return error.response;
    }
}

//Register
export const register = async (user) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = baseUrl + "auth/register";
    const body = {
        username: user.username,
        password: user.password,
    }
    try {
        const response = await axios.post(url, body);
        return response;
    }catch (error) {
        return error.response;
    }
}

