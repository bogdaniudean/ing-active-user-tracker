
import styles from "./ActiveUsersBox.module.css";

export interface ActiveUsersBoxProps {
    count: number;
}

const ActiveUsersBox: React.FC<ActiveUsersBoxProps> = ({ count }) => (
    <div className={styles.box}>
        <h3>We are currently having:</h3>
        <p className={styles.number}>{count}</p>
        <p className={styles.label}>active users.</p>
        <p className={styles.label}>Log right in to join us!</p>
    </div>
);

export default ActiveUsersBox;