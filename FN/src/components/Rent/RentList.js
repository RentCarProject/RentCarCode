import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import { RentalList, extendRental } from "./RentAPI";
import { useNavigate } from "react-router-dom";
import { requestPayment } from "../Car/Details/Identity-pay";
import "./RentList.css";

Modal.setAppElement("#root");

const RentList = () => {
    const [rentals, setRentals] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedRental, setSelectedRental] = useState(null);
    const [extensionDate, setExtensionDate] = useState("");
    const [newPrice, setNewPrice] = useState(null);
    const navigate = useNavigate();

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

    const openModal = (rental) => {
        setSelectedRental(rental);
        setExtensionDate(rental.returnDate);
        setNewPrice(rental.price);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedRental(null);
        setExtensionDate("");
        setNewPrice(null);
    };

    const handleExtend = async () => {
        if (!extensionDate) {
            alert("연장 날짜를 선택해주세요.");
            return;
        }

        const currentReturnDate = new Date(selectedRental.returnDate);
        const newReturnDate = new Date(extensionDate);
        const daysDifference = Math.floor((newReturnDate - currentReturnDate) / (1000 * 60 * 60 * 24));
        const extendedPrice = selectedRental.price * daysDifference;

        if (daysDifference <= 0) {
            alert("반납일은 현재 반납일 이후로 선택해야 합니다.");
            return;
        }

        try {
            const paymentData = {
                pg: "html5_inicis",
                pay_method: "card",
                merchant_uid: `extend_${selectedRental.rentId}_${Date.now()}`,
                name: `${selectedRental.carname} 렌트 연장` ,
                amount: "100",
            };

            const paymentResponse = await requestPayment(paymentData);

            if (paymentResponse.success) {
                const result = await extendRental(selectedRental.rentId, { returnDate: extensionDate });
                if (result) {
                    alert(`연장이 완료되었습니다. 새로운 가격: ${extendedPrice} 원`);
                    closeModal();
                }
            } else {
                alert("결제가 완료되지 않았습니다.");
            }
        } catch (error) {
            console.error("연장 실패 또는 결제 오류:", error);
            alert("연장 또는 결제에 실패했습니다.");
        }
    };

    const handleDateChange = (e) => {
        setExtensionDate(e.target.value);

        if (selectedRental && e.target.value) {
            const newDate = new Date(e.target.value);
            const currentReturnDate = new Date(selectedRental.returnDate);
            const daysDifference = Math.floor((newDate - currentReturnDate) / (1000 * 60 * 60 * 24));

            if (daysDifference > 0) {
                setNewPrice(selectedRental.price * daysDifference);
            } else {
                setNewPrice(selectedRental.price);
            }
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
                        {rental.price && <p>차량 가격: {rental.price} 원</p>}
                        <div className="button-container">
                            <button onClick={() => openModal(rental)} className="extend-button">렌트 연장</button>
                            <button className="review-write-button" onClick={() => navigate("/reviews/add")}>리뷰 작성</button>
                        </div>
                    </div>
                ))
            ) : (
                <div className="myPage-card">
                    예약내역이 없어요
                    <br />
                    <a href="/rent-car">렌터카 예약하기</a>
                </div>
            )}

            <Modal
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                contentLabel="렌트 연장"
                className="modal-content"
                overlayClassName="modal-overlay"
            >
                <h2>렌트 연장</h2>
                <p>{selectedRental?.carname} 차량의 연장 반납일을 선택해주세요.</p>
                <input
                    type="date"
                    value={extensionDate}
                    onChange={handleDateChange}
                    min={selectedRental?.returnDate}
                />
                {newPrice && (
                    <p>연장된 가격: {newPrice} 원</p>
                )}
                <div className="modal-buttons">
                    <button onClick={handleExtend} className="modal-extend-button">결제 후 연장</button>
                    <button onClick={closeModal} className="modal-close-button">취소</button>
                </div>
            </Modal>
        </section>
    );
};

export default RentList;
