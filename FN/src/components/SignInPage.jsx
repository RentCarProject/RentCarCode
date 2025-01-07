import { useState } from "react";
import { login } from "../api/AuthAPI";
import { useNavigate } from "react-router-dom";
import Header from "../HeadFoot/header/Header";
import Footer from "../HeadFoot/footer/Footer";
import "./css/SignInPage.css";

export default function SignInPage() {
    const [values, setValues] = useState({
        memberId: "",  
        memberPw: "",  
    });

    const navigate = useNavigate();

    const handleChange = async (e) => {
        setValues({
            ...values,
            [e.target.id]: e.target.value,
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();  
        login(values)
        .then((response) => {
            localStorage.setItem('tokenType', response.tokenType);
            localStorage.setItem('accessToken', response.accessToken);
            localStorage.setItem('refreshToken', response.refreshToken);
            console.log(response);
            console.log("Access Token saved:", localStorage.getItem('accessToken'));
            navigate('/home');
        }).catch((error) => {
            console.log(error);
        });
    }

    return (
        <div>
            {/* Header 추가 */}
            <Header />
            <div className="signIn-info">
                <h3>안녕하세요!<br/>
                    <span className="cruvix-text">Cruvix</span> 렌터카 로그인 입니다
                </h3>
            </div>

            <div className="form-container">
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="memberId">아이디</label>
                        <input type="text" className="form-control" id="memberId" onChange={handleChange} value={values.memberId} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="memberPw">비밀번호</label>
                        <input type="password" className="form-control" id="memberPw" onChange={handleChange} value={values.memberPw} />
                    </div>
                    <div className="form-group button-group">
                        <button type="submit" className="submit-button">로그인</button>
                        <button type="button" className="sign-up-button" onClick={() => navigate('/signup')}>회원가입</button>
                    </div>
                </form>
            </div>

            <div className="signIn-text">
                <h4>저희 <span className="cruvix-text">Cruvix</span> 는 즐겁고 생생한 여행과 함께<br/> 
                빠르고 편안한 드라이브로 고객님께 <br/>
                편안하고 자유로운 이동을 제공합니다.</h4>
            </div>

            {/* Footer 추가 */}
            <Footer />
        </div>
    );
}
