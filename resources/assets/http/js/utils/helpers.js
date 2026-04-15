/**
 * Helper utility functions
 */

const Utils = {
    clamp: (v, min, max) => Math.max(min, Math.min(max, v)),
    
    debounce: (fn, delay) => {
        let t;
        return (...args) => {
            clearTimeout(t);
            t = setTimeout(() => fn(...args), delay);
        };
    },
    
    throttle: (fn, delay) => {
        let last = 0;
        return (...args) => {
            const now = Date.now();
            if(now - last >= delay) {
                last = now;
                fn(...args);
            }
        };
    }
};

