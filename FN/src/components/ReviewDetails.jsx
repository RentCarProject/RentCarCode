import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Footer from "./HeadFoot/footer/Footer"; // 푸터 컴포넌트 임포트
import { fetchReview } from "../api/ReviewAPI"; // fetchReview 함수 임포트
import "./css/ReviewDetails.css"; 
import logo from "./assets/Cruvix.png";

const ReviewDetails = () => {
    const { id } = useParams();  // URL에서 id 추출
    const [review, setReview] = useState(null);
    const navigate = useNavigate();

    const ReviewLogoClick = () => {
        navigate("/home"); 
    }

    useEffect(() => {
        const loadReview = async () => {
            try {
                const data = await fetchReview(id);  // fetchReview 함수 호출
                console.log(data)
                setReview(data);
            } catch (error) {
                console.error("리뷰 불러오기 실패:", error);
            }
        };
        loadReview();
    }, [id]);

    const goBack = () => {
        navigate("/review");  // "/review" 경로로 리다이렉트
    };

    if (!review) return <p>로딩 중...</p>;

    return (
        <div className="review-details-page">
            {/* 헤더 */}
            <header className="review-details-header">
                <img src={logo} alt="Cruvix Logo" className="ReviewDetails-logo" onClick={ReviewLogoClick}/>
            </header>

            {/* 리뷰 내용 */}
            <div className="review-details-container">
                <div className="review-header">
                    <h2 className="review-title">{review.title}</h2>
                    <p className="review-rating">⭐ {review.rating} / 5</p>
                </div>

                <div className="review-body">
                    <div className="review-content-section">
                        <strong>내용: </strong>
                        <p className="review-content">{review.content}</p>
                    </div>
                    <div className="review-meta">
                        <span><strong>작성 날짜: </strong>{review.date}</span> | 
                        <span><strong>작성자: </strong>{review.memberId}</span>
                    </div>
                    {review.imagePath && (
                        <div className="review-image">
                            <img 
                            src={review.imagePath}  
                            alt="Review" 
                            className="review-image" 
                            />
                        </div>
                    )}
                </div>

                <button className="go-back-button" onClick={goBack}>뒤로가기</button>
            </div>

            {/* 푸터 */}
            <Footer />
        </div>
    );
};

export default ReviewDetails;
