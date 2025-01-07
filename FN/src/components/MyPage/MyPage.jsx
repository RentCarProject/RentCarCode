import React,{useState,useEffect} from "react";
import { useNavigate } from "react-router-dom";
import Footer from "../HeadFoot/footer/Footer";
import RentList from "../Rent/RentList";
import "./MyPageCss/MyPage.css";
import logo from "../assets/Cruvix.png"; 
import { fetchUser } from '../../api/UserAPI';

const MyPage = () => {
    const navigate = useNavigate(); 
    const [user, setUser] = useState({});
    const handleLogoClick = () => {
      navigate("/home"); 
    }

    const handleMyInfoClick = () => {
      navigate("/myinfo"); 
    }

    useEffect(() => {
            const token = localStorage.getItem('accessToken');
            if (!token) {
                navigate('/home');  
            } else {
                fetchUser()
                    .then((response) => {
                        setUser(response);  
                        console.log(response)
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
            나의 리뷰가 없습니다.
            <a href="/reviews">자세히 보기</a>
          </div>
          <div className="myPage-card">
            <h3>1:1 문의 내역</h3>
            정확한 답변으로 안내해 드릴게요
            <a href="/inquire">문의하기</a>
          </div>
        </section>
        <p>
          저희는 <strong>Cruvix</strong>는 즐겁고 생생한 여행과 함께,<br/> 빠르고
          편안한 드라이브로 고객님께 편안하고 자유로운 이동을 제공합니다.
        </p>
      </main>
      <Footer />
      {/* <footer className="myPage-footer">
        <div className="footer-links">
          <a href="#">회사소개</a>
          <a href="#">이용약관</a>
          <a href="#">개인정보처리방침</a>
          <a href="#">위치정보 이용약관</a>
          <a href="#">고객센터</a>
          <a href="#">자주 묻는 질문</a>
        </div>
        <address>
          주소: 대구광역시 수성구<br />
          전화: 010-1111-1111
        </address>
      </footer> */}
    </div>
  );
};

export default MyPage;
