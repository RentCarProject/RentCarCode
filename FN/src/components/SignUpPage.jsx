import { useState } from "react";
import { signUp } from "../api/AuthAPI";
import Header from "../HeadFoot/header/Header";
import Footer from "../HeadFoot/footer/Footer";
import "./css/SignUpPage.css";

export default function SignUpPage() {
    const [values, setValues] = useState({
        memberId: "",
        memberPw: "",
        memberPwConfirm: "",
        memberNm: "",
        memberEmail:"",
        contact:"",
        role: "USER"
    });

    const handleChange = async (e) => {
        setValues({...values,
            [e.target.id]: e.target.value,
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!values.memberPw) {
            alert("비밀번호는 필수입니다.");
            return;
        }

        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if (values.memberPw !== values.memberPwConfirm) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }
        
        try {
            await signUp(values);
            alert("회원가입이 완료되었습니다!");
            window.location.href = `/signin`;
        } catch (error) {
            console.log(error);
        }
        console.log(values);
    }

    return (
        <div className="signup-page-container">
            <Header />
            <div className="signup-info">
                <h3><span className="cruvix-text2">Cruvix</span>와 함께하는 여행,<br/>
                회원가입으로 더 즐겁고 편리하게!</h3>
            </div>
            <form onSubmit={handleSubmit} className="signup-form">
                <div className="form-group">
                    <label htmlFor="memberId">아이디</label>
                    <input type="text" id="memberId" onChange={handleChange} value={values.memberId} />
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
                    <label htmlFor="role">권한</label>
                    <select id="role" onChange={handleChange} value={values.role}>
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <div className="form-group">
                    <button type="submit">회원가입</button>
                </div>
            </form>
            <Footer className="custom-footer"/>
        </div>
    );
}
