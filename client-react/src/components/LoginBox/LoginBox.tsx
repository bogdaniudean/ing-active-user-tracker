
import styles from "./LoginBox.module.css";
import { Button, TextField, Typography, Tooltip, IconButton, CircularProgress } from "@mui/material";
import InfoOutlinedIcon from "@mui/icons-material/InfoOutlined";
import {useState} from "react";

export interface LoginBoxProps {
    token: string | null;
    loading: boolean;
    error: string | null;
    onLogin: (username: string, password: string) => void;
    onLogout: () => void;
}

const LoginBox: React.FC<LoginBoxProps> = ({ token, loading, error, onLogin, onLogout }) => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    return (
        <div className={styles.box}>
            {!token ? (
                <>
                    <div className={styles.titleContainer}>
                        <Typography variant="h5">Login</Typography>
                        <Tooltip title="For this POC, no registration is required. A user is considered authenticated if the username matches the password.">
                            <IconButton>
                                <InfoOutlinedIcon />
                            </IconButton>
                        </Tooltip>
                    </div>
                    <TextField
                        label="Username"
                        variant="outlined"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        fullWidth
                        margin="normal"
                    />
                    <Button
                        sx={{
                            marginTop: "var(--spacing-medium)"
                        }}
                        className={styles.button}
                        variant="contained"
                        color="primary"
                        onClick={() => onLogin(username, password)}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} color="inherit" /> : "Login"}
                    </Button>
                </>
            ) : (
                <>
                    <Typography variant="h5">Welcome, you're logged in!</Typography>
                    <Button
                        sx={{
                            marginTop: "var(--spacing-medium)"
                        }}
                        className={styles.button}
                        variant="contained"
                        color="secondary"
                        onClick={onLogout}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} color="inherit" /> : "Logout"}
                    </Button>
                </>
            )}
            {error && (
                <Typography color="error" variant="body2" marginTop={2}>
                    {error}
                </Typography>
            )}
        </div>
    );
};

export default LoginBox;
