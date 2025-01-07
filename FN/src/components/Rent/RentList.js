import React, { useEffect, useState } from "react";
import { RentalList, extendRental } from "./RentAPI";

const RentList = () => {
    const [rentals, setRentals] = useState([]);
    const [loading, setLoading] = useState(true);
    const [extensionDate, setExtensionDate] = useState(""); 

    useEffect(() => {
        const fetchRentals = async () => {
            try {
                const data = await RentalList();
                const rentalArray = Array.isArray(data) ? data : data.data || [];
                setRentals(rentalArray);
            } catch (error) {
                console.error("렌트 내역 불러오기 실패:", error);
                setRentals([]);  
            } finally {
                setLoading(false);
            }
        };

        fetchRentals();
    }, []);

   
    const updatehandle = async (rentId) => {
        if (!extensionDate) {
            alert("연장 날짜를 선택해주세요.");
            return;
        }
        console.log("선택한 연장 날짜: ", extensionDate);
        try {
            
            const extensionData = { rentId: rentId,returnDate: extensionDate };

            // 연장 API 호출
            const result = await extendRental(rentId, extensionData);
            if (result) {
                alert("연장이 완료되었습니다.");
                
            }
        } catch (error) {
            console.error("연장 실패:", error);
            alert("연장에 실패했습니다.");
        }
    };

    if (loading) {
        return <p>로딩 중...</p>;
    }

    return (
        <section className="myPage-section">
            <h3>나의 단기렌트</h3>
            {rentals.length > 0 ? (
                rentals.map((rental) => (
                    <div key={rental.rentId} className="myPage-card">
                        <p>차량 : {rental.carname}</p>
                        <p>대여일: {rental.rentDate}</p>
                        <p>반납일: {rental.returnDate}</p>

                        
                        <input
                            type="date"
                            value={extensionDate}
                            onChange={(e) => setExtensionDate(e.target.value)}
                            min={rental.returnDate} 
                        />
                        <button onClick={() => updatehandle(rental.rentId)}>렌트 연장</button>
                    </div>
                ))
            ) : (
                <div className="myPage-card">
                    예약내역이 없어요
                    <br />
                    <a href="/rent-car">렌터카 예약하기</a>
                </div>
            )}
        </section>
    );
};

export default RentList;
