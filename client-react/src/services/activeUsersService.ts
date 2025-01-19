import {CONFIGS} from "../configs.ts";

export const subscribeToActiveUsers = (
    onMessage: (count: number) => void,
    onError: (error: string) => void
) => {
    const eventSource = new EventSource(`${CONFIGS.API_ENDPOINT}/active-users-stream`);
    eventSource.onmessage = (event) => {
        const count = parseInt(event.data.replace("Active Users: ", ""), 10);
        onMessage(count);
    };
    eventSource.onerror = () => {
        onError("Unable to fetch active user count. Please try again later.");
        eventSource.close();
    };
    return () => eventSource.close();
};