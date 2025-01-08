import React, { useState, useEffect } from "react";
import Navi from "../../api/Navigation"; // 네비게이션 바 컴포넌트
import CarCard from "./CarCard";
import CarFilter from "./CarFilter";
import Pagination from "./Pagination";
import DateSelector from "./DateSelector";
import Footer from "../../HeadFoot/footer/Footer";
import "./CarMain.css";

const CarMain = () => {
  const [cars, setCars] = useState([]);
  const [filteredCars, setFilteredCars] = useState([]);
  const [filters, setFilters] = useState({});
  const [currentPage, setCurrentPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [showFilterModal, setShowFilterModal] = useState(false);

  const carsPerPage = 10;
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const toggleFilterModal = () => setShowFilterModal((prev) => !prev);
  const resetFilters = () => {
    setFilters({});
    setFilteredCars(cars);
  };

  const handleDateChange = (start, end) => {
    setStartDate(start);
    setEndDate(end);
  };

  useEffect(() => {
    const fetchCars = async () => {
      setLoading(true);
      setError(null);
      try {
        let url = "http://localhost:8090/api/cars/list";
        if (startDate && endDate) {
          url = `http://localhost:8090/api/cars/available?startDate=${startDate}&endDate=${endDate}`;
        }

        const response = await fetch(url);
        if (!response.ok) throw new Error("Failed to fetch cars");
        const data = await response.json();
        setCars(data);
        setFilteredCars(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };
    fetchCars();
  }, [startDate, endDate]);

  useEffect(() => {
    const filtered = cars.filter((car) => {
      return Object.entries(filters).every(([key, value]) => {
        if (!value) return true;
        if (key === "minPrice") return car.price >= Number(value);
        if (key === "maxPrice") return car.price <= Number(value);
        if (key === "carName") return car.carName.toLowerCase().includes(value.toLowerCase());
        if (key === "fuel") return car.fuel.toLowerCase() === value.toLowerCase();
        if (key === "seatingCapacity") return car.seatingCapacity >= Number(value);
        if (key === "manufacturer") return car.manufacturer.toLowerCase().includes(value.toLowerCase());
        return true;
      });
    });
    setFilteredCars(filtered);
    setCurrentPage(1);
  }, [filters, cars]);

  const handleSort = (order) => {
    const sortedCars = [...filteredCars].sort((a, b) => (order === "asc" ? a.price - b.price : b.price - a.price));
    setFilteredCars(sortedCars);
  };

  const indexOfLastCar = currentPage * carsPerPage;
  const indexOfFirstCar = indexOfLastCar - carsPerPage;
  const currentCars = filteredCars.slice(indexOfFirstCar, indexOfLastCar);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <>
      {/* 헤더 */}
      <div className="navigation-container">
        <Navi />
      </div>

      {/* 메인 컨텐츠 */}
      <div className="app-container">
        <div className="main-content">
          <div className="car-section">
            <div className="sort-buttons">
              <div className="left-group">
                <button onClick={() => handleSort("asc")}>금액 낮은 순</button>
                <button onClick={() => handleSort("desc")}>금액 높은 순</button>
              </div>
              <div className="right-group">
                <button onClick={resetFilters}>필터 초기화</button>
                <button onClick={toggleFilterModal}>필터</button>
              </div>
            </div>
            {showFilterModal && (
              <CarFilter
                setFilters={setFilters}
                closeFilter={toggleFilterModal}
              />
            )}
            {loading && <div>로딩 중...</div>}
            {error && <div style={{ color: "red" }}>{error}</div>}
            <div className="car-list">
              {currentCars.map((car) => (
                <CarCard key={car.carId} car={car} startDate={startDate} endDate={endDate} />
              ))}
            </div>
            <Pagination
              carsPerPage={carsPerPage}
              totalCars={filteredCars.length}
              paginate={paginate}
              currentPage={currentPage}
            />
          </div>
          <div className="right-section">
            <DateSelector onDateChange={handleDateChange} />
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

export default CarMain;
