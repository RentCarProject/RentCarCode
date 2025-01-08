import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createReview } from '../api/ReviewAPI';  
import { FaStar } from 'react-icons/fa';  // react-icons에서 FaStar를 가져옵니다
import 'bootstrap/dist/css/bootstrap.min.css';  

const ReviewAdd = () => {
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [rating, setRating] = useState(0); // 별점 상태를 숫자형으로 설정
  const [image, setImage] = useState(null); // 이미지 상태 추가

  const handleStarClick = (index) => {
    setRating(index + 1); // 클릭한 별 번호에 맞춰 rating 값을 업데이트
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0]; // 첫 번째 파일을 가져옵니다
    if (file) {
      setImage(file); // 이미지 파일 상태 업데이트
      console.log("선택된 이미지 파일:", file);
      console.log("선택된 이미지 파일:", file.name);
      console.log("이미지 파일 크기:", file.size);  // 이미지 파일 크기 (바이트 단위)
      if (file) {
        setImage(file); // 이미지 파일 상태 업데이트
    }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const reviewData = new FormData();
    reviewData.append('title', title);
    reviewData.append('content', content);
    reviewData.append('rating', rating);

    // 이미지 파일이 선택되었다면 reviewData에 추가
    if (image) {
      reviewData.append('image', image);
    }

    for (let pair of reviewData.entries()) {
      console.log(pair[0] + ': ' + pair[1]);
      
    }

    try {
      const response = await createReview(reviewData);
      console.log("리뷰 작성 성공:", response);
      navigate('/review');
    } catch (error) {
      console.error("리뷰 작성 실패:", error);
    }
  };

  const handleCancel = () => {
    navigate('/mypage'); // 취소 버튼 클릭 시 /mypage로 이동
  };

  return (
    <div className="container my-5">
      <h1 className="text-center mb-4">리뷰 작성</h1>
      <form onSubmit={handleSubmit} className="p-4 border rounded shadow-sm" encType="multipart/form-data">
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
          <div className="d-flex justify-content-start">
            {[...Array(5)].map((_, index) => (
              <FaStar
                key={index}
                className={`star-icon ${index < rating ? 'filled' : ''}`}
                onClick={() => handleStarClick(index)}
                style={{ color: index < rating ? '#FFD700' : '#ccc', cursor: 'pointer', fontSize: '1.5rem' }}
              />
            ))}
          </div>
        </div>

        {/* 이미지 업로드 섹션 */}
        <div className="mb-3">
          <label htmlFor="image" className="form-label">이미지 업로드</label>
          <input
            type="file"
            id="image"
            name="image"
            className="form-control"
            accept="image/*" // 이미지 파일만 허용
            onChange={handleImageChange}
          />
          {image && <p>선택한 이미지: {image.name}</p>} {/* 선택된 이미지 이름 표시 */}
        </div>

        <div className="d-flex justify-content-center">
          <button type="submit" className="btn btn-primary">리뷰 작성</button>
          <button type="button" onClick={handleCancel} className="btn btn-secondary ms-2">취소</button>
        </div>
      </form>
    </div>
  );
};

export default ReviewAdd;
