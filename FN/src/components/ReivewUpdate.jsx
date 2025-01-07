import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchReviewById, updateReview } from "../api/ReviewAPI";
import Header from "../HeadFoot/header/Header";  

const ReviewUpdate = () => {
    const { id } = useParams();  
    const [review, setReview] = useState({ title: "", content: "", rating: 0 });
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

    const handleSubmit = async (event) => {
        event.preventDefault();  
        try {
            await updateReview(id, review);  
            alert("리뷰가 수정되었습니다.");
            navigate(`/reviews/${id}`);  
        } catch (error) {
            console.error("수정 실패:", error);
            alert("리뷰 수정에 실패했습니다.");
        }
    };

    const handleCancel = () => {
        navigate(`/reviews/${id}`);  
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setReview({ ...review, [name]: value });  
    };

    return (
        <div>
            <Header />
            <h2>리뷰 수정</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>제목:</label>
                    <input 
                        type="text" 
                        name="title" 
                        value={review.title} 
                        onChange={handleChange} 
                        required 
                    />
                </div>
                <div>
                    <label>내용:</label>
                    <textarea 
                        name="content" 
                        value={review.content} 
                        onChange={handleChange} 
                        required 
                    />
                </div>
                <div>
                    <label>평점:</label>
                    <input 
                        type="number" 
                        name="rating" 
                        value={review.rating} 
                        onChange={handleChange} 
                        min="1" max="5" 
                        required 
                    />
                </div>
                <div>
                    <button type="submit">수정 완료</button>
                    <button type="button" onClick={handleCancel}>취소</button>
                </div>
            </form>
        </div>
    );
};

export default ReviewUpdate;
