// ParentComponent.jsx
import React, { useEffect, useState } from "react";
import CarFilter from "./CarFilter";
import axios from "axios";
import CarList from "./CarList"; // 필터링된 차량 목록을 표시하는 컴포넌트

const ParentComponent = () => {
  const [allCars, setAllCars] = useState([]); // 모든 차량 데이터를 저장
  const [filters, setFilters] = useState({});
  const [filteredCars, setFilteredCars] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // 모든 차량 데이터를 가져오는 useEffect
  useEffect(() => {
    const fetchAllCars = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await axios.get("/api/cars/list");
        setAllCars(response.data);
        setFilteredCars(response.data); // 초기에는 모든 차량을 표시
      } catch (err) {
        console.error("Failed to fetch all cars:", err);
        setError("차량 데이터를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchAllCars();
  }, []);

  // 필터가 변경될 때마다 필터링을 수행하는 useEffect
  useEffect(() => {
    if (Object.keys(filters).length === 0) {
      setFilteredCars(allCars); // 필터가 없으면 모든 차량을 표시
    } else {
      const applyFilters = () => {
        let filtered = allCars;

        // fuel 필터 적용
        if (filters.fuel) {
          filtered = filtered.filter(
            (car) =>
              car.fuel.toLowerCase() === filters.fuel.toLowerCase()
          );
        }

        // 다른 필터도 추가 가능 (예: carName, seatingCapacity 등)

        setFilteredCars(filtered);
      };

      applyFilters();
    }
  }, [filters, allCars]);

  return (
    <div>
      <CarFilter setFilters={setFilters} />
      {loading && <div>차량 데이터를 로딩 중입니다...</div>}
      {error && <div>{error}</div>}
      {!loading && !error && <CarList cars={filteredCars} />}
    </div>
  );
};

export default ParentComponent;
