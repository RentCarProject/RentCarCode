import React, { useState } from "react";
import "./CarFilter.css";

const CarFilter = ({ setFilters, closeFilter }) => {
  const [filterValues, setFilterValues] = useState({
    carName: "",
    fuel: "",
    seatingCapacity: "",
    manufacturer: "",
    minPrice: "",
    maxPrice: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFilterValues({ ...filterValues, [name]: value });
  };

  const applyFilters = () => {
    const activeFilters = Object.fromEntries(
      Object.entries(filterValues).filter(([key, value]) => value !== "" && value !== null)
    );
    setFilters(activeFilters);
    closeFilter(); // 모달 닫기
  };

  const resetFilters = () => {
    setFilterValues({
      carName: "",
      fuel: "",
      seatingCapacity: "",
      manufacturer: "",
      minPrice: "",
      maxPrice: "",
    });
    setFilters({});
  };

  return (
    <div className="filter-modal">
      <div className="filter-modal-content">
        <h2>필터</h2>
        <div className="filter-group">
          <input
            type="text"
            name="carName"
            placeholder="차량 이름"
            value={filterValues.carName}
            onChange={handleInputChange}
          />
          <select name="fuel" value={filterValues.fuel} onChange={handleInputChange}>
            <option value="">연료</option>
            <option value="가솔린">가솔린</option>
            <option value="디젤">디젤</option>
            <option value="EV">전기</option>
          </select>
          <select
            name="seatingCapacity"
            value={filterValues.seatingCapacity}
            onChange={handleInputChange}
          >
            <option value="">좌석 수</option>
            <option value="5">5인 이하</option>
            <option value="7">7인 이상</option>
            <option value="9">9인 이상</option>
          </select>
          <input
            type="text"
            name="manufacturer"
            placeholder="제조사"
            value={filterValues.manufacturer}
            onChange={handleInputChange}
          />
          <input
            type="number"
            name="minPrice"
            placeholder="최소 가격"
            value={filterValues.minPrice}
            onChange={handleInputChange}
          />
          <input
            type="number"
            name="maxPrice"
            placeholder="최대 가격"
            value={filterValues.maxPrice}
            onChange={handleInputChange}
          />
        </div>
        <div className="filter-actions">
          <button onClick={resetFilters}>초기화</button>
          <button onClick={applyFilters}>적용</button>
          <button onClick={closeFilter}>닫기</button>
        </div>
      </div>
    </div>
  );
};

export default CarFilter;
