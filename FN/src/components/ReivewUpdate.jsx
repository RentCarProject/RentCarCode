import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchReview, updateReview } from "../api/ReviewAPI";
import { FaStar } from 'react-icons/fa';
import Header from "../HeadFoot/header/Header";  
import 'bootstrap/dist/css/bootstrap.min.css';  
import './css/ReviewUpdate.css';

const ReviewUpdate = () => {
    const { id } = useParams();  
    const [review, setReview] = useState({ title: "", content: "", rating: 0 });
    const navigate = useNavigate();

    useEffect(() => {
        const loadReview = async () => {
            try {
                const data = await fetchReview(id);
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
            navigate("/review");  
        } catch (error) {
            console.error("수정 실패:", error);
            alert("리뷰 수정에 실패했습니다.");
        }
    };

    const handleCancel = () => {
        navigate("/review");  
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setReview({ ...review, [name]: value });  
    };

    // 별점 클릭 핸들러
    const handleStarClick = (index) => {
        setReview({ ...review, rating: index + 1 });
    };

    return (
        <div className="review-update-page">
            <Header />
            <div className="container my-5">
                <h1 className="text-center mb-4">리뷰 수정</h1>
                <form onSubmit={handleSubmit} className="p-4 border rounded shadow-sm">
                    <div className="mb-3">
                        <label htmlFor="title" className="form-label">제목</label>
                        <input 
                            type="text" 
                            id="title"
                            name="title"
                            className="form-control"
                            value={review.title} 
                            onChange={handleChange} 
                            required 
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="content" className="form-label">내용</label>
                        <textarea 
                            id="content"
                            name="content"
                            className="form-control"
                            rows="5"
                            value={review.content} 
                            onChange={handleChange} 
                            required 
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="rating" className="form-label">별점</label>
                        <div className="d-flex justify-content-start">
                            {[...Array(5)].map((_, index) => (
                                <FaStar
                                    key={index}
                                    className={`star-icon ${index < review.rating ? 'filled' : ''}`}
                                    onClick={() => handleStarClick(index)}
                                    style={{ 
                                        color: index < review.rating ? '#FFD700' : '#ccc', 
                                        cursor: 'pointer', 
                                        fontSize: '1.5rem' 
                                    }}
                                />
                            ))}
                        </div>
                    </div>

                    <div className="d-flex justify-content-center">
                        <button type="submit" className="btn btn-primary">수정 완료</button>
                        <button type="button" onClick={handleCancel} className="btn btn-secondary ms-2">취소</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ReviewUpdate;
