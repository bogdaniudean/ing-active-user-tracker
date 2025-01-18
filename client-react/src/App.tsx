import { useState, useEffect } from "react";
import { Header } from "./components/Header";
import { Footer } from "./components/Footer";
import ActiveUsersBox from "./components/ActiveUsersBox/ActiveUsersBox";
import LoginBox from "./components/LoginBox/LoginBox";
import { login, logout } from "./services/authService";
import { subscribeToActiveUsers } from "./services/activeUsersService";
import styles from "./App.module.css";
import { Container, Box } from "@mui/material";

const App: React.FC = () => {
    const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
    const [activeUsers, setActiveUsers] = useState<number>(0);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleLogin = async (username: string, password: string) => {
        setError(null);
        setLoading(true);
        try {
            const newToken = await login(username, password);
            setToken(newToken);
            localStorage.setItem("token", newToken);
        } catch (err: any) {
            setError(err.message || "Login failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleLogout = async () => {
        setError(null);
        setLoading(true);
        try {
            if (token) {
                await logout(token); // Attempt to log out on the server
            }
        } catch (error) {
            setError("Logout failed on the server, clearing client session.");
            // Optionally, you can inspect the error response for additional handling
        } finally {
            // Clear client-side session regardless of the server response
            setToken(null);
            localStorage.removeItem("token");
            setLoading(false);
        }
    };

    useEffect(() => {
        const unsubscribe = subscribeToActiveUsers(
            (count) => setActiveUsers(count),
            (error) => setError(error)
        );
        return unsubscribe;
    }, []);

    return (
        <div className={styles.appContainer}>
            <Header logoSrc={"/logo.png"} title={"Active Users Tracker"} />
            <Container className={styles.content}>
                <Box className={styles.mainContainer}>
                    <ActiveUsersBox count={activeUsers} />
                    <LoginBox
                        token={token}
                        loading={loading}
                        error={error}
                        onLogin={handleLogin}
                        onLogout={handleLogout}
                    />
                </Box>
            </Container>
            <Footer creatorName={"Bogdan Iudean"} creatorUrl="https://bogdan.iudean.com" />
        </div>
    );
};

export default App;
