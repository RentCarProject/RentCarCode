import React, { useState, useEffect } from "react";
import { fetchReview, deleteReview } from "../api/ReviewAPI";
import { useNavigate } from "react-router-dom";
import "./css/fetchReviews.css";

const ReviewDetail = ({ reviewId, closeModal }) => {
    const [review, setReview] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadReview = async () => {
            try {
                const data = await fetchReview(reviewId);
                setReview(data);
            } catch (error) {
                console.error("리뷰 불러오기 실패:", error);
            }
        };
        loadReview();
    }, [reviewId]);

    const deleteHandle = async () => {
        const confirmDelete = window.confirm("정말로 삭제하시겠습니까?");
        if (confirmDelete) {
            try {
                await deleteReview(reviewId);
                alert("리뷰가 삭제되었습니다.");
                closeModal();
            } catch (error) {
                console.error("삭제 실패:", error);
                alert("리뷰 삭제에 실패했습니다.");
            }
        }
    };

    const updateHandle = () => {
        navigate(`/reviews/update/${reviewId}`);
        closeModal();
    };

    if (!review) return <p>로딩 중...</p>;

    return (
        <div className="modal-overlay">
            <div className="review-modal">
                <div className="review-modal-header">
                    <span>{review.title}</span>
                    <button className="close-button" onClick={closeModal}>×</button>
                </div>
                <div className="review-modal-content">
                    <p className="rating">⭐ {review.rating} / 5</p>
                    <p>{review.content}</p>
                    <p>{review.date}</p>
                </div>
                <div className="review-modal-footer">
                    <button className="update-button" onClick={updateHandle}>수정</button>
                    <button className="delete-button" onClick={deleteHandle}>삭제</button>
                    
                </div>
            </div>
        </div>
    );
};

export default ReviewDetail;
