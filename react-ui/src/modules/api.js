import axios from 'axios';

export function login(username, password) {
    return axios.post("/api/auth/login", {
        "username":username,
        "password":password
    }).then(response => {
        return response.data
    })
}

export function logout(username) {
    setAccessToken("")
    setRefreshToken("")
    return axios.post(`/api/auth/expire?username=${username}`)
}

export const api = axios.create({
    baseURL: '/api',
});

api.interceptors.request.use(
    (config) => {
        const token = getAccessToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response.status === 403 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const refreshToken = getRefreshToken();
                const response = await axios.post('api/auth/refresh', { refreshToken });
                const { accessToken } = response.data;
                setAccessToken(accessToken)
                originalRequest.headers.Authorization = `Bearer ${accessToken}`;
                return axios(originalRequest);
            } catch (error) {
                // Handle refresh token error or redirect to login
            }
        }

        return Promise.reject(error);
    }
);

function getAccessToken () {
    return localStorage.getItem("accessToken")
}

export function setAccessToken (accessToken) {
    localStorage.setItem("accessToken", accessToken)
}

function getRefreshToken () {
    return localStorage.getItem("refreshToken")
}

export function setRefreshToken (refreshToken) {
    localStorage.setItem("refreshToken", refreshToken)
}