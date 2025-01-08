import { useState } from "react";
import { signUp, checkDuplicateId } from "../api/AuthAPI";
import Header from "../HeadFoot/header/Header";
import Footer from "../HeadFoot/footer/Footer";
import "./css/SignUpPage.css";

export default function SignUpPage() {
    const [values, setValues] = useState({
        memberId: "",
        memberPw: "",
        memberPwConfirm: "",
        memberNm: "",
        memberEmail: "",
        contact: "",
    });
    const [isIdChecked, setIsIdChecked] = useState(false);  // 아이디 중복 체크 상태

    const handleChange = async (e) => {
        setValues({
            ...values,
            [e.target.id]: e.target.value,
        });
        if (e.target.id === "memberId") {
            setIsIdChecked(false);  // 아이디 수정 시 중복 체크 초기화
        }
    };

    const handleIdCheck = async () => {
        try {
            const responseData = await checkDuplicateId(values.memberId);
            const isDuplicate = responseData?.isDuplicate ?? false;  // responseData에서 안전하게 isDuplicate 추출
            if (isDuplicate) {
                alert("이미 사용 중인 아이디입니다.");
                setIsIdChecked(false);
            } else {
                alert("사용 가능한 아이디입니다.");
                setIsIdChecked(true);
            }
        } catch (error) {
            console.error("Error checking duplicate ID:", error);
            alert("아이디 중복 확인 중 오류가 발생했습니다.");
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!values.memberPw) {
            alert("비밀번호는 필수입니다.");
            return;
        }
        if (values.memberPw !== values.memberPwConfirm) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }
        if (!isIdChecked) {
            alert("아이디 중복 확인을 해주세요.");
            return;
        }

        // 이름 유효성 검사
        if (!values.memberNm) {
            alert("이름은 필수입니다.");
            return;
        }

        // 이메일 유효성 검사 (정규식을 사용하여 이메일 형식 체크)
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(values.memberEmail)) {
            alert("유효한 이메일 주소를 입력해주세요.");
            return;
        }

        // 연락처 유효성 검사 (예: 전화번호 형식 체크)
        const contactRegex = /^[0-9]{10,11}$/;  // 10자리 또는 11자리 숫자만 허용
        if (!contactRegex.test(values.contact)) {
            alert("유효한 연락처 형식이 아닙니다. (예: 01086350867)");
            return;
        }

        try {
            await signUp(values);
            alert("회원가입이 완료되었습니다!");
            window.location.href = `/signin`;
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div className="signup-page-container">
            <Header />
            <div className="signup-info">
                <h3><span className="cruvix-text2">Cruvix</span>와 함께하는 여행,<br />
                    회원가입으로 더 즐겁고 편리하게!</h3>
            </div>
            <form onSubmit={handleSubmit} className="signup-form">
                <div className="form-group">
                    <label htmlFor="memberId">아이디</label>
                    <div className="input-container"><input type="text" id="memberId"onChange={handleChange} value={values.memberId} className="input-field"/>
                        <button type="button" onClick={handleIdCheck} className="id-check-btn"> 중복 확인 </button>
                    </div>
                </div>
                <div className="form-group">
                    <label htmlFor="memberPw">비밀번호</label>
                    <input type="password" id="memberPw" onChange={handleChange} value={values.memberPw} />
                </div>
                <div className="form-group">
                    <label htmlFor="memberPwConfirm">비밀번호 확인</label>
                    <input type="password" id="memberPwConfirm" onChange={handleChange} value={values.memberPwConfirm} />
                </div>
                <div className="form-group">
                    <label htmlFor="memberNm">이름</label>
                    <input type="text" id="memberNm" onChange={handleChange} value={values.memberNm} />
                </div>
                <div className="form-group">
                    <label htmlFor="memberEmail">이메일</label>
                    <input type="email" id="memberEmail" onChange={handleChange} value={values.memberEmail} />
                </div>
                <div className="form-group">
                    <label htmlFor="contact">연락처</label>
                    <input type="text" id="contact" onChange={handleChange} value={values.contact} />
                </div>
                <div className="form-group">
                    <button type="submit">회원가입</button>
                </div>
            </form>
            <Footer className="custom-footer" />
        </div>
    );
}