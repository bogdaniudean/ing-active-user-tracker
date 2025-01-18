import { Box, Typography, Link } from '@mui/material';
import styles from './Footer.module.css';

export interface FooterProps {
    creatorName: string;
    creatorUrl: string;
}

const Footer: React.FC<FooterProps> = ({ creatorName, creatorUrl }) => {
    return (
        <Box component="footer" className={styles.footer}>
            <Typography variant="body2" className={styles.text}>
                Made with ❤️ by{' '}
                <Link
                    href={creatorUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className={styles.link}
                >
                    {creatorName}
                </Link>
            </Typography>
        </Box>
    );
};

export default Footer;
