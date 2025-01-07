import React, { useEffect, useState } from "react";
import "./MyPageCss/MyInfo.css";
import logo from "../assets/Cruvix.png";
import { useNavigate } from "react-router-dom";
import { fetchUserInfo } from "../../api/MyInfo";
import Footer from "../HeadFoot/footer/Footer";
import AddressSearch from "./addrdetail";

const MyInfo = () => {
  const [userInfo, setUserInfo] = useState(null); 
  const [error, setError] = useState(null); 
  const [address] = useState(''); 
  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate("/home"); 
  }

  useEffect(() => {
    const getUserInfo = async () => {
      try {
        const data = await fetchUserInfo();
        setUserInfo(data);
      } catch (err) {
        setError("사용자 정보를 가져오는 데 실패했습니다.");
      }
    };

    getUserInfo();
  }, []);

  const handlePasswordChange = () => {
    // 비밀번호 변경
  };

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  if (!userInfo) {
    return <div className="loading-message">로딩 중...</div>;
  }

  return (
    <div className="myInfo-container">
      <header className="myInfo-header">
        <img src={logo} alt="Cruvix Logo" className="myPage-logo" onClick={handleLogoClick}/>
      </header>

      <main className="myInfo-main">
        <section className="myInfo-section">
          <h2>내 정보 관리</h2>

          <div className="info-box">
            <h3>기본정보</h3>
            <p>아이디: <span className="info-value">{userInfo.memberId}</span></p>
            <p>비밀번호: <span className="info-value">개인정보 보호를 위해 주기적으로 변경해주세요</span> 
              <button onClick={handlePasswordChange}>변경</button></p>
            <p>휴대폰 번호: <span className="info-value">{userInfo.contact}</span></p>
            <p>이메일: <span className="info-value">{userInfo.email}</span></p>
          </div>

          <div className="info-box">
            <h3>추가정보</h3>
            <AddressSearch />
            <p>{address && `선택된 주소: ${address}`}</p>
            <p>운전면허증 등록</p>
          </div>

          <div className="info-box">
            <h3>계정관리</h3>
            <p>로그아웃</p>
            <p>회원탈퇴</p>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
};

export default MyInfo;
