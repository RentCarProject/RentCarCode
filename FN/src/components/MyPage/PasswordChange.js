import React, { useState } from "react";
import { useNavigate } from "react-router-dom";  // useNavigate 훅 추가
import axios from "axios";
import "./MyPageCss/PasswordChange.css";  


const PasswordChange = () => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const navigate = useNavigate();  // useNavigate 훅으로 navigate 객체 생성

  const handleSubmit = async (e) => {
    e.preventDefault();

    const accessToken = localStorage.getItem("accessToken"); // 저장된 토큰을 가져옵니다. (로컬 스토리지 등에서 관리)

    if (!accessToken) {
      setErrorMessage("로그인 세션이 만료되었습니다.");
      return;
    }

    try {
      // 비밀번호 변경 API 요청
      const response = await axios.put(
        "http://localhost:8090/api/v1/user/password/update", // 여기서 서버의 API 엔드포인트 URL을 명확하게 지정
        {
          currentPassword,
          newPassword,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // Authorization 헤더에 access token 추가
          },
        }
      );

      // 성공적인 응답 처리
      if (response.status === 200) {
        setSuccessMessage("비밀번호가 성공적으로 변경되었습니다.");
        setErrorMessage(""); // 에러 메시지 초기화
      } else {
        setErrorMessage("알 수 없는 오류가 발생했습니다.");
        setSuccessMessage(""); // 성공 메시지 초기화
      }
    } catch (error) {
      // 에러 처리
      if (error.response) {
        // 서버 응답이 있을 경우
        setErrorMessage(error.response.data || "비밀번호 변경 중 오류가 발생했습니다.");
      } else if (error.request) {
        // 요청이 보내졌지만 응답이 없는 경우
        setErrorMessage("서버 응답이 없습니다. 다시 시도해주세요.");
      } else {
        // 기타 오류 처리
        setErrorMessage("비밀번호 변경 중 오류가 발생했습니다.");
      }
      setSuccessMessage(""); // 성공 메시지 초기화
    }
  };

  // 돌아가기 버튼 클릭 시 myinfo 페이지로 이동
  const handleGoBack = () => {
    navigate("/myinfo");  // myinfo 페이지로 이동
  };

  return (
    <div className="password-change-container">
      <h2>비밀번호 변경</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="currentPassword">현재 비밀번호</label>
          <input
            type="password"
            id="currentPassword"
            value={currentPassword}
            onChange={(e) => setCurrentPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="newPassword">새 비밀번호</label>
          <input
            type="password"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
        </div>
        <div className="button-container">
          <button type="submit">비밀번호 변경</button>
          <button type="button" onClick={handleGoBack}>돌아가기</button>
        </div>
      </form>

      {/* 성공/에러 메시지 표시 */}
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
    </div>
  );
};

export default PasswordChange;
