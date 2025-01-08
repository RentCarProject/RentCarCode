import axios from "axios";

// ACCESS_TOKEN과 REFRESH_TOKEN을 가져옵니다.
let ACCESS_TOKEN = localStorage.getItem("accessToken");


/** CREATE CUSTOM AXIOS INSTANCE */
export const UserApi = axios.create({
    baseURL: 'http://localhost:8090',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${ACCESS_TOKEN}`,  
    },
});

// 토큰 갱신 후 Authorization 헤더를 업데이트하는 함수
const updateAuthHeader = () => {
    const ACCESS_TOKEN = localStorage.getItem("accessToken");
    if (ACCESS_TOKEN) {
        UserApi.defaults.headers.common['Authorization'] = `Bearer ${ACCESS_TOKEN}`;
    }
};

// 처음에 UserApi 인스턴스를 생성한 후 토큰이 있으면 Authorization 헤더를 설정합니다.
updateAuthHeader();

// 토큰 갱신 함수
const refreshAccessToken = async () => {
    const refreshToken = localStorage.getItem("refreshToken");
    if (!refreshToken) {
        console.error("Refresh Token is missing");
        window.location.href = '/home';
        return false;
    }

    try {
        const response = await UserApi.get(`/api/v1/auth/refresh`, {
            headers: {
                'Authorization': `Bearer ${refreshToken}`,  
            },
        });

        console.log("Refresh Token response:", response);  
        const newAccessToken = response.data.accesstoken;
        if (newAccessToken) {
            localStorage.setItem('accessToken', newAccessToken);
            updateAuthHeader();  
            console.log("New AccessToken:", newAccessToken);

            return true;
        } else {
            console.error("No access token in the response.");
            window.location.href = '/home';  
            return false;
        }
    } catch (error) {
        console.error("Error refreshing access token:", error);
        window.location.href = '/home';
        return false;
    }
};

// 401 Unauthorized 오류 발생 시 토큰 갱신 및 원래 요청 재시도
UserApi.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response && error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            const tokenRefreshed = await refreshAccessToken();  
            if (tokenRefreshed) {
                originalRequest.headers['Authorization'] = `Bearer ${localStorage.getItem("accessToken")}`;
                return UserApi(originalRequest);  
            } else {
                return Promise.reject(error);  
            }
        }
        return Promise.reject(error);
    }
);

// 회원조회 API
export const fetchUser = async () => {
    updateAuthHeader(); 

    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        throw new Error('No access token found');
    }

    try {
        const response = await UserApi.get('/api/v1/user');
        return response.data;
    } catch (error) {
        console.error("Error fetching user data:", error);
        throw error;
    }
};

// 회원수정 API
export const updateUser = async (data) => {
    const response = await UserApi.put(`/api/v1/user`, data);
    return response.data;
};

// 회원탈퇴 API
export const deleteUser = async () => {
    try {
      await UserApi.delete(`/api/v1/user/delete`);
    } catch (error) {
      console.error("회원탈퇴 요청 실패:", error);
      throw error;
    }
  };

//회원 면허,주소 등록
export const userAddrAndLicense = async (data)=>{
    const response = await UserApi.put(`/api/v1/user/license`, data);
    return response.data;
}
