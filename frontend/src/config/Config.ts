export const config = {
    BACKEND_BASE_URL: import.meta.env.DEV 
        ? 'http://localhost:8080/api'
        : window.location.origin + '/api'
}