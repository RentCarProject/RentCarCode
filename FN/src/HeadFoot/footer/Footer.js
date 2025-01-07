import React from 'react';
import "../footer/Footer.css";  

const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-container">
                <div className="footer-links">
                    <a href="/about">회사소개</a>
                    <a href="/privacy">개인정보처리방침</a>
                    <a href="/terms">위치정보이용약관</a>
                    <a href="/contact">고객센터</a>
                    <a href="/partnership">제휴문의</a>
                </div>
                <div className="footer-contact">
                    <p>주소: 대구광역시 수성구</p>
                    <p>전화: 010-8635-0867</p>
                </div>
                <div className="footer-text">
                    <p>© 2024 Cruvix.</p>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
