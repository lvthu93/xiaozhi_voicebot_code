/**
 * Configuration constants
 */

const CONFIG = {
    HTTP_PORT: 8081, // Port cho HTTP Control Web Server
    WS_PORT: 8080, // Port cho WebSocket server (điều khiển loa)
    TIMEOUT: 12000,
    MAX_VOL: 15,
    LIGHT_MAIN_ID: '7fffff0000',
    LIGHT_EDGE_ID: '7fffff8000',
    KILL_CMD: "PIDS=$(ps -A | grep 'light_loop' | grep -v grep | awk '{print $2}'); for PID in $PIDS; do kill -9 $PID; done"
};

