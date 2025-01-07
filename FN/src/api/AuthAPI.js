import axios from "axios";
import {UserApi} from "./UserAPI";


export const AuthApi = axios.create({
    baseURL: 'http://localhost:8090',
    headers: {
        'Content-Type': 'application/json',
    },
});

/** LOGIN API - 로그인 시 토큰 발급 */
export const login = async ({ memberId, memberPw }) => {
    const data = { memberId, memberPw };
    try {
        const response = await AuthApi.post(`/api/v1/auth/login`, data);
        
        console.log("Login Response:", response.data);

        localStorage.setItem("tokenType", response.data.tokenType);
        localStorage.setItem("accessToken", response.data.accessToken);
        localStorage.setItem("refreshToken", response.data.refreshToken);
        return response.data;
    } catch (error) {
        console.error("Login failed:", error);
        throw error;
    }
};

/** SIGNUP API - 회원가입 */
export const signUp = async ({ memberId, memberPw,memberPwConfirm, memberNm, memberEmail, contact, role }) => {
    const data = { memberId: memberId,
                 memberPw: memberPw, 
                 memberPwConfirm : memberPwConfirm,
                 memberNm: memberNm, 
                 email: memberEmail, 
                 contact: contact, 
                 role: role };
    try {
        const response = await AuthApi.post(`/api/v1/auth/signup`, data);
        return response.data;
    } catch (error) {
        console.error("Signup failed:", error);
        throw error;
    }
};
// 토큰 자동 갱신 Interceptor 추가
AuthApi.interceptors.request.use(
  (config) => {
      const token = localStorage.getItem('accessToken');
      if (token) {
          config.headers['Authorization'] = `${localStorage.getItem('tokenType')} ${token}`;
      }
      return config;
  },
  (error) => {
      return Promise.reject(error);
  }
);

// 응답 인터셉터 - 401 발생 시 토큰 갱신
axios.interceptors.response.use(
  (response) => response,
  async (error) => {
      const originalRequest = error.config;
      if (error.response.status === 401 && !originalRequest._retry) {
          originalRequest._retry = true;

          try {
              const res = await axios.get('/api/v1/auth/refresh',{}, {
                  headers: {
                      'Authorization': `Bearer ${localStorage.getItem('refreshToken')}`,
                  }
              });

              localStorage.setItem('accessToken', res.data.accessToken);
              originalRequest.headers['Authorization'] = `Bearer ${res.data.accessToken}`;
              return UserApi(originalRequest);  
          } catch (refreshError) {
              localStorage.clear();
              window.location.href = '/login';
          }
      }
      return Promise.reject(error);
              
  }
);
  
