import React, { useEffect, useState } from "react";
import Navi from '../api/Navigation';
import ReviewList from "../components/ReviewList";
import Footer from "./HeadFoot/footer/Footer";
import { useNavigate } from "react-router-dom";
import { fetchReviews } from "../api/ReviewAPI";  
import './css/ReviewPage.css'; 

const ReviewPage = () => {
    const [reviews, setReviews] = useState([]);
    const [currentPage, setCurrentPage] = useState(1); 
    const [reviewsPerPage] = useState(12); 
    const navigate = useNavigate();

    // 리뷰 데이터를 불러오는 함수
    const loadReviews = async () => {
        try {
            const data = await fetchReviews(); 
            setReviews(data); 
        } catch (error) {
            console.error("리뷰 불러오기 실패:", error);
        }
    };

    // 페이지 변경 처리 함수
    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // 페이지네이션 관련 데이터 계산
    const indexOfLastReview = currentPage * reviewsPerPage;
    const indexOfFirstReview = indexOfLastReview - reviewsPerPage;
    const currentReviews = reviews.slice(indexOfFirstReview, indexOfLastReview); 

    // 처음 페이지가 로드될 때 데이터 불러오기
    useEffect(() => {
        loadReviews();
    }, []);

    // 리뷰 클릭 시 상세 페이지로 이동
    const handleReviewClick = (id) => {
        navigate(`/reviews/${id}`);
    };

    return (
        <div className="review-page-container">
            <Navi />
            <div className="reviews">
                <h1 className="review-page-header">리뷰 게시판</h1>
                <ReviewList reviews={currentReviews} onClick={handleReviewClick} />
                <div className="pagination-buttons">
                    {/* 페이지네이션 버튼 */}
                    {[...Array(Math.ceil(reviews.length / reviewsPerPage)).keys()].map(pageNumber => (
                        <button
                            key={pageNumber + 1}
                            onClick={() => handlePageChange(pageNumber + 1)}
                        >
                            {pageNumber + 1}
                        </button>
                    ))}
                </div>
                
            </div>
            <Footer />
        </div>
    );
};

export default ReviewPage;
