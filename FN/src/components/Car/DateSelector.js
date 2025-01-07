import React, { useState } from "react";


const DateSelectorImproved = ({ onDateChange }) => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth();
  const [displayMonth, setDisplayMonth] = useState(currentMonth);
  const [displayYear, setDisplayYear] = useState(currentYear);

  const daysInMonth = (year, month) => new Date(year, month + 1, 0).getDate();

  const handleDateClick = (date) => {
    if (!startDate || (startDate && endDate)) {
      setStartDate(date);
      setEndDate(null);
    } else if (startDate && !endDate) {
      if (date < startDate) {
        setStartDate(date);
      } else {
        setEndDate(date);
      }
    }
  };

  const handleReset = () => {
    setStartDate(null);
    setEndDate(null);
    if (onDateChange) {
      onDateChange(null, null); // 초기화 시 부모 컴포넌트에 null 전달
    }
  };

  const handleApply = () => {
    if (startDate && endDate && onDateChange) {
      onDateChange(startDate.toISOString().split("T")[0], endDate.toISOString().split("T")[0]);
    }
  };

  const renderDays = () => {
    const totalDays = daysInMonth(displayYear, displayMonth);
    const firstDay = new Date(displayYear, displayMonth, 1).getDay();
    const daysArray = [];

    for (let i = 0; i < firstDay; i++) {
      daysArray.push(<div key={`empty-${i}`} className="day empty" />);
    }

    for (let day = 1; day <= totalDays; day++) {
      const fullDate = new Date(displayYear, displayMonth, day);
      const isSelected =
        (startDate && endDate && fullDate >= startDate && fullDate <= endDate) ||
        (startDate && !endDate && fullDate.getTime() === startDate.getTime());

      const isStartDate = startDate && fullDate.getTime() === startDate.getTime();
      const isEndDate = endDate && fullDate.getTime() === endDate.getTime();

      daysArray.push(
        <div
          key={`day-${day}`}
          className={`day ${isSelected ? "selected" : ""} ${
            isStartDate ? "start" : ""
          } ${isEndDate ? "end" : ""}`}
          onClick={() => handleDateClick(fullDate)}
        >
          {day}
        </div>
      );
    }
    return daysArray;
  };

  const handlePrevMonth = () => {
    if (displayMonth === 0) {
      setDisplayMonth(11);
      setDisplayYear(displayYear - 1);
    } else {
      setDisplayMonth(displayMonth - 1);
    }
  };

  const handleNextMonth = () => {
    if (displayMonth === 11) {
      setDisplayMonth(0);
      setDisplayYear(displayYear + 1);
    } else {
      setDisplayMonth(displayMonth + 1);
    }
  };

  return (
    <div className="date-selector-container">
      <h3 className="title">언제 갈까요?</h3>
      <div className="calendar-header">
        <button className="nav-button" onClick={handlePrevMonth}>
          이전
        </button>
        <div className="month-year">
          {displayYear}년 {displayMonth + 1}월
        </div>
        <button className="nav-button" onClick={handleNextMonth}>
          다음
        </button>
      </div>
      <div className="days-of-week">
        {["일", "월", "화", "수", "목", "금", "토"].map((day, index) => (
          <div key={`day-of-week-${index}`} className="day-of-week">
            {day}
          </div>
        ))}
      </div>
      <div className="calendar-days">{renderDays()}</div>
      <div className="selected-dates">
        <p>시작 날짜: {startDate ? startDate.toISOString().split("T")[0] : "선택되지 않음"}</p>
        <p>종료 날짜: {endDate ? endDate.toISOString().split("T")[0] : "선택되지 않음"}</p>
      </div>
        <button
          className={`button apply-button ${!startDate || !endDate ? "disabled" : ""}`}
          onClick={handleApply}
          disabled={!startDate || !endDate}
        >
          적용
        </button>
        <div className="button-group">
        <button className="button reset-button" onClick={handleReset}>
          초기화
        </button>
      </div>
    </div>
  );
};

export default DateSelectorImproved;
