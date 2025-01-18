
import axios from "axios";

export const login = async (username: string, password: string) => {
    const response = await axios.post("http://localhost:8080/login", {
        username,
        password,
    });
    return response.data.token;
};

export const logout = async (token: string) => {
    await axios.post(
        "http://localhost:8080/logout",
        {},
        { headers: { Authorization: `Bearer ${token}` } }
    );
};