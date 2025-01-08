package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.Rent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Rent r SET r.returnDate = :returnDate WHERE r.rentId = :rentId")
    int updateReturnDate(@Param("returnDate") LocalDate returnDate, @Param("rentId") Long rentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Rent r WHERE r.rentId = :rentId")
    void deleteCar(@Param("rentId") Long rentId);

    @Query("SELECT c.carId, u.memberId, r.returnDate FROM Rent r JOIN r.car c JOIN r.user u WHERE r.user.memberId = :memberId")
    List<Object[]> findByUserAndCar(@Param("memberId") String memberId);

    @Query("SELECT r FROM Rent r " +
            "JOIN FETCH r.car c " +
            "JOIN FETCH r.user u " +
            "WHERE u.memberId = :memberId")
    List<Rent> findByUserAndCarWithPrice(@Param("memberId") String memberId);




}
