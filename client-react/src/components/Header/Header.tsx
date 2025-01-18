import { AppBar, Toolbar, Typography, Box } from '@mui/material';
import styles from './Header.module.css';

export interface HeaderProps {
    logoSrc: string;
    title: string;
}

const Header: React.FC<HeaderProps> = ({ logoSrc, title }) => {
    return (
        <AppBar position="static" sx={{ backgroundColor: "white", padding: "16px" }} className={styles.header}>
            <Toolbar className={styles.toolbar}>
                <Box className={styles.logoContainer}>
                    <img src={logoSrc} alt="Logo" className={styles.logo} />
                    <Typography variant="h6" className={styles.title}>
                        {title}
                    </Typography>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
