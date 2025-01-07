import React from "react";
import "./Pagination.css";

const Pagination = ({ carsPerPage, totalCars, paginate, currentPage }) => {
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(totalCars / carsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <nav className="pagination">
      <ul>
        <li onClick={() => paginate(currentPage - 1)}>&lt;</li>
        {pageNumbers.map((number) => (
          <li
            key={number}
            className={number === currentPage ? "active" : ""}
            onClick={() => paginate(number)}
          >
            {number}
          </li>
        ))}
        <li onClick={() => paginate(currentPage + 1)}>&gt;</li>
      </ul>
    </nav>
  );
};

export default Pagination;
