import React, { useState, useEffect } from "react";
import { fetchReviewById } from "../api/ReviewAPI";
import { useParams, useNavigate } from "react-router-dom";
import { deleteReview } from "../api/ReviewAPI";

const ReviewFindById = () => {
    const { id } = useParams();  
    const [review, setReview] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadReview = async () => {
            try {
                const data = await fetchReviewById(id);
                setReview(data);
            } catch (error) {
                console.error("리뷰 불러오기 실패:", error);
            }
        };
        loadReview();
    }, [id]);

    const updateHandle = () => {
        navigate(`/reviews/update/${id}`);
    };

    const deleteHandle = async () => {
        const confirmDelete = window.confirm("정말로 삭제하시겠습니까?");
        if (confirmDelete) {
            try {
                await deleteReview(id);  
                alert("리뷰가 삭제되었습니다.");
                navigate("/review");  
            } catch (error) {
                console.error("삭제 실패:", error);
                alert("리뷰 삭제에 실패했습니다.");
            }
        }
    };

    if (!review) return <p>로딩 중...</p>;

    return (
        <div>
            <h1>{review.title}</h1>
            <p style={{ margin: "5px 0", color: "#888" }}>⭐ {review.rating} / 5</p><br/>
            <p>{review.content}</p>
            <p>{review.date}</p>
            <p>{review.memberId}</p>
            <button onClick={updateHandle}>수정</button>
            <button onClick={deleteHandle}>삭제</button>
        </div>
    );
};
export default ReviewFindById;
