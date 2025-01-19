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
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(
        !!document.cookie.match(/USER_SESSION/)
    );
    const [activeUsers, setActiveUsers] = useState<number>(0);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleLogin = async (username: string, password: string) => {
        setError(null);
        setLoading(true);
        try {
            await login(username, password);
            setIsLoggedIn(true);
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
            await logout();
        } catch (error) {
            setError("Logout failed on the server, clearing client session.");
        } finally {
            setIsLoggedIn(false);
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
                        isLoggedIn={isLoggedIn}
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
