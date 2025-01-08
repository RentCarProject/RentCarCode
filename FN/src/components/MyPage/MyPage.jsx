import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { useNavigate } from "react-router-dom";
import Footer from "../HeadFoot/footer/Footer";
import RentList from "../Rent/RentList";
import ReviewDetail from "../fetchReview";  // ReviewDetail 컴포넌트 임포트
import "./MyPageCss/MyPage.css";
import logo from "../assets/Cruvix.png"; 
import { fetchUser } from '../../api/UserAPI';
import { fetchReviewById } from "../../api/ReviewAPI";

Modal.setAppElement('#root');  // 접근성 문제 방지 (root 요소 설정)

const MyPage = () => {
    const navigate = useNavigate(); 
    const [user, setUser] = useState({});
    const [reviews, setReviews] = useState([]);
    const [selectedReviewId, setSelectedReviewId] = useState(null);  // 선택된 리뷰 ID
    const [isModalOpen, setIsModalOpen] = useState(false);  // 모달 상태 관리

    const handleLogoClick = () => {
      navigate("/home"); 
    }

    const handleMyInfoClick = () => {
      navigate("/myinfo"); 
    }

    // 리뷰 클릭 핸들러
    const handleReviewClick = (reviewId) => {
        setSelectedReviewId(reviewId);  // 선택된 리뷰 ID 설정
        setIsModalOpen(true);  // 모달 열기
    };

    // 모달 닫기
    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedReviewId(null);  // 선택된 리뷰 초기화
    };

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (!token) {
            navigate('/home');  
        } else {
            fetchUser()
                .then((response) => {
                    setUser(response);
                })
                .catch((error) => {
                    console.log(error);
                });

            fetchReviewById()
                .then((response) => {
                    setReviews(response);
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }, [navigate]);

  return (
    <div className="myPage-container">
      <header className="myPage-header">
        <img src={logo} alt="Cruvix Logo" className="myPage-logo" onClick={handleLogoClick}/>
      </header>
      <main className="myPage-main">
        <h2>마이페이지</h2>
        <p>{user.memberNm}님 안녕하세요!<br/>언제나, 어디서나 Cruvix 렌터카와 함께해 주세요.</p>
        <button className="myPage-button" onClick={handleMyInfoClick}>내 정보 관리</button>
        
        <section className="myPage-section">
          <RentList />
        </section>

        <section className="myPage-section">
          <div className="myPage-card">
            <h3>나의 리뷰</h3>
            {reviews.length === 0 ? (
              <p>나의 리뷰가 없습니다.</p>
            ) : (
              <ul>
                {reviews.map((review) => (
                  <li key={review.reviewNumber} 
                      onClick={() => handleReviewClick(review.reviewNumber)} 
                      style={{ cursor: "pointer" }}>
                    <h4>{review.title}</h4>
                    <div className="rating">
                      {"★".repeat(review.rating)}
                    </div>
                    <p>별점: {review.rating}</p>
                    <p className="date">작성 날짜: {review.date}</p>
                  </li>
                ))}
              </ul>
            )}
            <a href="/review">자세히 보기</a>
          </div>
        </section>

        {/* 모달 컴포넌트 */}
        <Modal 
          isOpen={isModalOpen} 
          onRequestClose={closeModal} 
          contentLabel="리뷰 상세 보기"
          className="review-modal"
          overlayClassName="modal-overlay"
        >
          {selectedReviewId && <ReviewDetail reviewId={selectedReviewId} closeModal={closeModal} />}
        </Modal>
      </main>
      <Footer />
    </div>
  );
};

export default MyPage;
