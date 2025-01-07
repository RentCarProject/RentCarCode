import React from "react";
import Navi from "../api/Navigation"; // 네비게이션 바 컴포넌트
import logo from "./assets/Cruvix.png";
import roadImage from "./assets/road.jpg"; // 배경 이미지
import bookingImage from "./assets/booking.jpg";
import carsImage from "./assets/cars.jpg";
import Footer from "./HeadFoot/footer/Footer";
import "./css/MainPage.css"; // 메인 페이지 스타일

export default function HomePage() {
 

    return (
        <div>
            {/* 네비게이션 바 */}
            <Navi />

            {/* 로드 섹션 - 로고와 텍스트 오버레이 */}
            <header className="main-header">
                <div className="road-overlay">
                    <img src={roadImage} alt="Road" className="main-road-image" />
                    <div className="logo-overlay">
                        <img src={logo} alt="Cruvix Logo" className="logo-image" />
                    </div>
                </div>
            </header>

                <section className="info-section">
                    <div className="road-text">
                        <h1>떠나고 싶을 때 언제나</h1>
                             <p>
                                떠나는 순간, 편안한 길.<br />
                                자유롭고 신속한 이동.<br />
                                <strong>Cruvix</strong>와 함께하세요.
                            </p>
                    </div>
                </section>
            <main>
                {/* 섹션 2 */}
                <section className="info-section">
                <img src={bookingImage} alt="Booking" className="main-image booking-image" />
                    <div className="booking-text">
                        <h1>간편한 예약 시스템</h1>
                        <p>
                            웹페이지에서 쉽고 빠르게 예약하세요.
                            <br />
                            언제 어디서나 손쉽게 가능합니다.
                        </p>
                    </div>
                </section>

                {/* 섹션 3 */}
                <section className="info-section">
                <img src={carsImage} alt="Cars" className="main-image cars-image" />
                    <div className="cars-text">
                        <h1>다양한 차종과 함께</h1>
                        <p>
                            사계절 어느 계절에도
                            <br />
                            편안한 차량으로 떠나세요.
                        </p>
                    </div>
                </section>
            </main>
            <Footer />
            {/* <footer className="main-footer">
                <div className="footer-links">
                    <a href="/about">회사소개</a>
                    <a href="/terms">이용약관</a>
                    <a href="/privacy">개인정보처리방침</a>
                    <a href="/location">위치정보 이용약관</a>
                    <a href="/support">고객센터</a>
                    <a href="/faq">제품 문의</a>
                </div>
                <address>
                    주소: 대구광역시 수성구<br />
                    전화: 010-1234-5678
                </address>
            </footer> */}
        </div>
    );
}
