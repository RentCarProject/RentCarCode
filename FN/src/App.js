import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SignInPage from "./components/SignInPage";
import SignUpPage from "./components/SignUpPage";
import HomePage from "./components/Homepage";
import PaymentPage from "./components/PaymentPage ";
import IdentityPage from "./components/IdentityPage";
import Reviewpage from "./components/ReviewPage";
import ReviewAdd from "./components/ReviewAdd";
import ReviewDetails from "./components/ReviewDetails";
import ReviewUpdate from "./components/ReivewUpdate";
import CarMain from "./components/Car/CarMain";
import CarDetail from "./components/Car/Details/CarDetail"
import MyPage from "./components/MyPage/MyPage"
import MyInfo from "./components/MyPage/MyInfo";
import PasswordChange from "./components/MyPage/PasswordChange";



function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/signin" element={<SignInPage />} />
        <Route path="/signup" element={<SignUpPage />}/>
        <Route path="/home" element={<HomePage />}/>
        <Route path="/Pay" element={<PaymentPage />} />
        <Route path="/identity" element={<IdentityPage />} />
        <Route path="/review" element={<Reviewpage />} />
        <Route path="/reviews/:id" element={<ReviewDetails/>}/>
        <Route path="/reviews/add" element={<ReviewAdd />} />
        <Route path="/reviews/update/:id" element={<ReviewUpdate />} />
        <Route path="/car" element={<CarMain/>} />
        <Route path="/car-detail/:carId" element={<CarDetail />} />
        <Route path="/mypage" element={<MyPage/>}/>
        <Route path="/myinfo" element={<MyInfo/>}/>
        <Route path="/password-change" element={<PasswordChange/>} />
        {/* 다른 라우트들 추가 */}
      </Routes>
    </Router>
  );
}

export default App;
