import axios from "axios";
import {CONFIGS} from "../configs.ts";

export const axiosInstance = axios.create({
    baseURL: `${CONFIGS.API_ENDPOINT}`,
    withCredentials: true,
});

export const login = async (username: string, password: string): Promise<void> => {
    await axiosInstance.post("/login", {
        username,
        password,
    });
};

export const logout = async (): Promise<void> => {
    await axiosInstance.post("/logout");
};
