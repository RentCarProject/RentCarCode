import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createReview } from '../api/ReviewAPI';  
import 'bootstrap/dist/css/bootstrap.min.css';  


const ReviewAdd = () => {
  const navigate = useNavigate();

  
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [rating, setRating] = useState('');

  
  const handleSubmit = async (e) => {
    e.preventDefault();

    
    const reviewData = {
      title,
      content,
      rating,
    };

    try {
    
      const response = await createReview(reviewData);
      console.log("리뷰 작성 성공:", response);

   
      navigate('/review');
    } catch (error) {
      console.error("리뷰 작성 실패:", error);
    }
  };

  return (
    <div className="container my-5">
      <h1 className="text-center mb-4">리뷰 작성</h1>
      <form onSubmit={handleSubmit} className="p-4 border rounded shadow-sm">
        <div className="mb-3">
          <label htmlFor="title" className="form-label">제목</label>
          <input
            type="text"
            id="title"
            name="title"
            className="form-control"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="content" className="form-label">내용</label>
          <textarea
            id="content"
            name="content"
            className="form-control"
            rows="5"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="rating" className="form-label">별점</label>
          <input
            type="number"
            id="rating"
            name="rating"
            className="form-control"
            min="1"
            max="5"
            value={rating}
            onChange={(e) => setRating(e.target.value)}
            required
          />
        </div>

        <div className="d-flex justify-content-center">
          <button type="submit" className="btn btn-primary">리뷰 작성</button>
        </div>
      </form>
 
    </div>
  );
};

export default ReviewAdd;

