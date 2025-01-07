import React, { useEffect, useState } from "react";
import CarCard from "./CarCard";

const CarList = () => {
  const [cars, setCars] = useState([]);

  useEffect(() => {
    // 백엔드 API 호출
    fetch("http://localhost:8090/api/cars/list")
      .then((res) => res.json())
      .then((data) => setCars(data))
      .catch((error) => console.error("Error fetching cars:", error));
  }, []);

  return (
    <div>
      {cars.map((car) => (
        <CarCard key={car.carId} car={car} />
      ))}
    </div>
  );
};

export default CarList;
