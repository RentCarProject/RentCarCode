import React from "react";
import { useNavigate } from "react-router-dom";
import "./CarCard.css";

const CarCard = ({ car, startDate, endDate }) => {
  const navigate = useNavigate();

  // 날짜 범위에 따른 총 금액 계산
  const calculateTotalPrice = () => {
    if (!startDate || !endDate) {
      return car.price; // 날짜 선택이 없을 경우 기본 가격 반환
    }

    const start = new Date(startDate);
    const end = new Date(endDate);

    const diffInTime = end.getTime() - start.getTime();
    const days = Math.ceil(diffInTime / (1000 * 3600 * 24));

    return car.price * days;
  };

  const goDetail = () => {
    const totalPrice = calculateTotalPrice();
    navigate(`/car-detail/${car.carId}`, { state: { totalPrice } });
  };

  return (
    <div className="car-card">
      <img src={car.imageUrl} alt={car.carName} className="car-image" />
      <div className="car-details">
        <div className="car-name-container">
          <div className="car-name">{car.carName}</div>
          <img src={car.carLogo} alt={car.carName} className="car-image-logo" />
        </div>
        <div className="car-description">{car.fuel}</div>
        <div className="car-bottom">
          <div className="car-price">{calculateTotalPrice().toLocaleString()}원</div>
          <button className="reserve-button" onClick={goDetail}>
            예약
          </button>
        </div>
      </div>
    </div>
  );
};

export default CarCard;
