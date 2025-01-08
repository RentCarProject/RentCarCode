import { UserApi } from "./UserAPI";



// 리뷰 전체 조회
export const fetchReviews = async () => {
    const response = await UserApi.get('/api/v1/reviews');
    console.log(response.data);
    return response.data;
};

// 특정 리뷰 조회
export const fetchReviewById = async (id) => {
    const response = await UserApi.get(`/api/v1/reviews/user`);
    return response.data;
};

export const fetchReview = async (id) => {
    const response = await UserApi.get(`/api/v1/reviews/${id}`);
    return response.data;
};

// 리뷰 작성
export const createReview = async (reviewData) => {
    const response = await UserApi.post('/api/v1/reviews/add', reviewData, {
        headers: {
            'Content-Type': 'multipart/form-data',  // 여기서 multipart/form-data로 설정
        },
    });
    return response.data;
};

// 리뷰 수정
export const updateReview = async (id, reviewData) => {
    const response = await UserApi.put(`/api/v1/reviews/update/${id}`, reviewData);
    return response.data;
};

// 리뷰 삭제
export const deleteReview = async (id) => {
    await UserApi.delete(`/api/v1/reviews/delete/${id}`);
};
