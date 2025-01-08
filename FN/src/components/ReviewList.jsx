import React from "react";

const ReviewList = ({ reviews, onClick }) => {
    return (
        <div style={{
            display: "grid",
            gridTemplateColumns: "repeat(4, 1fr)", 
            gap: "20px",
            padding: "20px"
        }}>
            {reviews.length > 0 ? (
                reviews.map((review) => (
                    <div 
                        key={review.reviewNumber}  
                        style={{
                            border: "1px solid #ddd",
                            borderRadius: "10px",
                            padding: "15px",
                            backgroundColor: "#f9f9f9",
                            boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
                            cursor: "pointer",
                            textAlign: "center"
                        }}
                        onClick={() => onClick(review.reviewNumber)}  
                    >
                        <h3 style={{ margin: "10px 0", fontSize: "18px" }}>{review.title}</h3>
                        <p style={{ margin: "5px 0", color: "#888" }}>⭐ {review.rating} / 5</p>
                        <small style={{ display: "block", margin: "10px 0" }}>{review.date}</small>
                        <p style={{ margin: "5px 0", color: "#555" }}>작성자: {review.memberId}</p>
                    </div>
                ))
            ) : (
                <p style={{ gridColumn: "span 4", textAlign: "center" }}>등록된 리뷰가 없습니다.</p>
            )}
        </div>
    );
};

export default ReviewList;
