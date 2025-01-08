import React, { useEffect, useState } from "react";
import "./MyPageCss/MyInfo.css";
import logo from "../assets/Cruvix.png";
import { useNavigate } from "react-router-dom";
import { fetchUserInfo } from "../../api/MyInfo";
import Footer from "../HeadFoot/footer/Footer";
import AdditionalInfoModal from "./AdditionalInfoModal";
import Modal from "react-modal"; 
import { deleteUser } from "../../api/UserAPI";

const MyInfo = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState(null);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [additionalInfo, setAdditionalInfo] = useState(null);
  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate("/home");
  };

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
    navigate("/password-change");  
  }; 

  const handleSaveAdditionalInfo = (info) => {
    setAdditionalInfo(info);
  };

  const handleDeleteAccount = async () => {
    const confirmDelete = window.confirm("정말로 회원탈퇴를 하시겠습니까?");
    if (confirmDelete) {
      try {
        await deleteUser(); 
        alert("회원탈퇴가 완료되었습니다.");
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('tokenType');
        localStorage.clear(); 
  
        navigate("/home"); 
      } catch (err) {
        console.error("회원탈퇴 오류:", err);
        alert("회원탈퇴에 실패했습니다.");
      }
    }
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
        <img src={logo} alt="Cruvix Logo" className="myPage-logo" onClick={handleLogoClick} />
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
            {additionalInfo ? (
              <div>
                <p>우편번호: {additionalInfo.postcode}</p>
                <p>도로명주소: {additionalInfo.roadAddress}</p>
                <p>상세주소: {additionalInfo.detailedAddress}</p>
                <p>면허종류: {additionalInfo.licenseType}</p>
                <p>면허번호: {additionalInfo.licenseNumber}</p>
              </div>
            ) : (
              <button onClick={() => setModalIsOpen(true)}>추가정보 등록</button>
            )}
          </div>

          <div className="info-box">
            <h3>계정관리</h3>
            <p>로그아웃</p>
            <button onClick={handleDeleteAccount}>회원탈퇴</button>
          </div>
        </section>
      </main>

      <Footer />

      {/* React Modal 추가 */}
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="추가정보 등록"
        ariaHideApp={false}  
        className="modal-content"  
        overlayClassName="modal-overlay"  
      >
        <AdditionalInfoModal
          isOpen={modalIsOpen}
          onRequestClose={() => setModalIsOpen(false)}
          onSave={handleSaveAdditionalInfo}
        />
      </Modal>
    </div>
  );
};

export default MyInfo;
