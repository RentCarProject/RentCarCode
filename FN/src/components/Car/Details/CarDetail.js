// CarDetail.js
import React, { useEffect, useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import Navi from "../../../api/Navigation"; // 네비게이션 바 컴포넌트
import axios from "axios";
import "./CarDetail.css";
import KakaoMap from "./Map";
import { requestCertification, requestPayment } from "./Identity-pay";
import { carRental } from "../../Rent/RentAPI";
import { fetchUserInfo } from "../../../api/MyInfo";
import Footer from "../../HeadFoot/footer/Footer";

const DEFAULT_LATITUDE = 35.86595;
const DEFAULT_LONGITUDE = 128.5936;

const CarDetail = () => {
  const { carId } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const [car, setCar] = useState(null);
  const [loading, setLoading] = useState(true);
  const [adjustedPrice, setAdjustedPrice] = useState(null); // 리스트에서 전달받은 금액 상태 추가

  useEffect(() => {
    axios
      .get(`http://localhost:8090/api/cars/view/${carId}`)
      .then((response) => {
        const carData = response.data;

        if (!carData.latitude || !carData.longitude) {
          carData.latitude = DEFAULT_LATITUDE;
          carData.longitude = DEFAULT_LONGITUDE;
        }

        setCar(carData);
        setLoading(false);

        // 리스트 페이지에서 전달된 조정된 금액 설정
        if (location.state && location.state.totalPrice) {
          setAdjustedPrice(location.state.totalPrice);
        }
      })
      .catch((error) => {
        console.error("Error fetching car detail:", error);
        setLoading(false);
      });
  }, [carId, location.state]);

  const handleReservation = async () => {
    try {
      // 1. 본인 인증 진행
      const { certificationData } = await requestCertification();
      console.log("인증정보 2 : ",certificationData)
      const certifiedName = certificationData.name; // 인증된 이름
      const certifiedPhone = certificationData.phone; // 인증된 전화번호
  
      // 로그인된 사용자 정보와 비교
      const userInfo = await fetchUserInfo();
      console.log(userInfo);
      console.log("인증된 이름:", certifiedName, "사용자 이름:", userInfo.memberNm);
      console.log("인증된 전화번호:", certifiedPhone, "사용자 연락처:", userInfo.contact);
  
      if (certifiedName !== userInfo.memberNm || certifiedPhone !== userInfo.contact) {
        alert("본인 인증 정보와 로그인된 정보가 일치하지 않습니다.");
        return;
      }
  
      alert("본인 인증이 성공했습니다.");
  
      // 2. 결제 진행
      try {
        const paymentData = {
          pg: "html5_inicis",
          pay_method: "card",
          merchant_uid: `car_${car.carId}_${new Date().getTime()}`, // 고유 결제 ID
          name: userInfo.memberNm, // 상품명
          amount: "100", // 결제 금액 (임시값)
        };
        const paymentResponse = await requestPayment(paymentData);
  
        alert("결제가 완료되었습니다");
  
        // 3. 결제 정보 서버로 전송
        const paymentDetails = {
          impUid: paymentResponse.imp_uid,
          merchantUid: paymentResponse.merchant_uid,
          name: paymentResponse.name,
          phone: paymentResponse.phone,
          pgProvider: paymentResponse.pg_provider,
          pgTid: paymentResponse.pg_tid,
          certifiedAt: paymentResponse.certified_at,
          certified: paymentResponse.success,
        };
  
        const paymentSaveResponse = await fetch("http://localhost:8090/api/portone/payments/save", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(paymentDetails),
        });
  
        if (!paymentSaveResponse.ok) {
          throw new Error("결제 정보 저장 실패");
        }
  
        // 4. 렌탈 정보 저장
        const rentalData = {
          carname: car.carName, // 차량명
        };
  
        const carRentalResponse = await carRental(rentalData);
  
        if (carRentalResponse) {
          alert("렌탈이 완료되었습니다!");
        }
      } catch (paymentError) {
        console.error("결제 또는 정보 저장 중 오류:", paymentError);
        alert("결제 또는 정보 저장 중 문제가 발생했습니다: " + paymentError.message);
      }
    } catch (error) {
      console.error("예약 실패:", error);
      alert("본인 인증 또는 결제 처리 중 오류가 발생했습니다: " + error.message);
    }
  };
  

  if (loading) {
    return <div className="car-detail-container">Loading...</div>;
  }

  if (!car) {
    return (
      <div className="car-detail-container">
        <p>차량 정보를 불러올 수 없습니다.</p>
      </div>
    );
  }

  const markerPosition = {
    lat: car.latitude || DEFAULT_LATITUDE,
    lng: car.longitude || DEFAULT_LONGITUDE,
  };

  return (
    <>
      {/* 헤더 */}
      <div className="navigation-container">
        <Navi />
      </div>
    <div className="car-detail-container">
      <header className="car-detail-header">
        <h1 className="car-detail-title">Cruvix</h1>
        <h2 className="car-detail-subtitle">차량 상세</h2>
      </header>

      <div className="car-detail-card">
        <div className="car-image-container">
          <img
            src={car.imageUrl || "/images/no_image_available.png"}
            alt={car.carName || "No Car Name"}
            className="car-detail-image"
          />
        </div>
        <div className="car-info-container">
          <div className="car-logo">
            <img
              src={car.carLogo || "/images/no_logo_available.png"}
              alt={`${car.manufacturer || "No Manufacturer"} Logo`}
              className="car-logo-image"
            />
          </div>
          <h3 className="car-detail-name">
            {car.manufacturer} {car.carName}
          </h3>
          <div className="car-price-reservation" onClick={handleReservation}>
            {`${(adjustedPrice || car.price).toLocaleString()}원으로 예약하기`}
          </div>
        </div>
      </div>

      <div className="car-specs-container">
        {[
          { icon: "💧", label: "연료 방식", value: car.fuel },
          { icon: "👥", label: "승차인원", value: `${car.seatingCapacity}명` },
          { icon: "🚗", label: "연식", value: car.year },
          { icon: "🎓", label: "운전자 나이", value: `${car.driverAgeRequirement}` },
          { icon: "🕒", label: "운전 경력", value: `${car.drivingExperience}` },
        ].map((spec, index) => (
          <div key={index} className="car-spec">
            <span className="car-spec-icon">{spec.icon}</span>
            <span className="car-spec-value">{spec.label}</span>
            <p>{spec.value}</p>
          </div>
        ))}
      </div>

      <div className="car-options">
        <h4 className="options-title">차량 옵션</h4>
        <p className="options-list">{car.option1 || "옵션 정보 없음"}</p>
      </div>

      <div className="car-map-container">
        <h4>차량 위치</h4>
        <KakaoMap
          latitude={DEFAULT_LATITUDE}
          longitude={DEFAULT_LONGITUDE}
          markerPosition={markerPosition}
        />
      </div>

      <div className="car-location-info">
        <h3 className="location-title">대구지점</h3>
        <div className="location-content">
          <p>
            <strong>주소:</strong> 대구 중구 중앙대로 366 9층,10층
          </p>
          <p>
            <strong>전화:</strong> 064-726-6460
          </p>
          <button
            className="location-map-button"
            onClick={() => {
              const naverMapUrl = `https://map.naver.com/v5/search/${encodeURIComponent(
                "대구 코리아 it아카데미"
              )}`;
              window.open(naverMapUrl, "_blank");
            }}
          >
            네이버 지도로 보기
          </button>
        </div>

        <hr className="divider" />

        <div className="location-hours">
          <h4>이용 가능한 시간</h4>
          <p>
            <strong>인수:</strong> 07:30 ~ 22:00
          </p>
          <p>
            <strong>반납:</strong> 06:00 ~ 21:00
          </p>
        </div>

        <hr className="divider" />

        <div className="location-rules">
          <h4>인수 및 반납 규정</h4>
          <ul>
            <li>인수 및 반납은 지점별 이용 가능한 시간 내 가능합니다.</li>
            <li>예약자뿐 아니라 등록된 제2운전자와 제3운전자도 인수 및 반납이 가능합니다.</li>
            <li>차량 인수 시, 운전면허 지참은 필수입니다.</li>
          </ul>
        </div>

        <hr className="divider" />

        <div className="location-notice">
          <h4>유의사항</h4>
          <ul>
            <li>이미지와 실제 차량은 사항 및 색상이 다를 수 있습니다.</li>
            <li>100% 금연차량으로 운영하고 있습니다.</li>
            <li>반려동물은 같이 탈 수 없습니다.</li>
            <li>낚시용품은 실을 수 없습니다.</li>
          </ul>
        </div>
      </div>

      <div className="back-button-wrapper">
        <div className="back-button-container">
          <button className="back-button" onClick={() => navigate(-1)}>
            목록으로 돌아가기
          </button>
        </div>
      </div>
    </div>
         {/* 푸터 */}
         <div className="footer">
        <Footer />
      </div>
    </>
  );
};

export default CarDetail;
