import React, { useState } from 'react';
import AddressInput from './addr';  // AddressInput 컴포넌트 불러오기

const AddressSearch = () => {
  const [postcode, setPostcode] = useState('');
  const [roadAddress, setRoadAddress] = useState('');
  const [detailedAddress, setDetailedAddress] = useState('');
  const [isPopupOpen, setIsPopupOpen] = useState(false);  // 팝업 열고 닫기 위한 상태

  // 부모에게 주소 선택 결과를 전달받는 함수
  const handleAddressSelect = (address, zonecode) => {
    setPostcode(zonecode);
    setRoadAddress(address);
  };

  // 팝업 닫기 함수
  const closePopup = () => {
    setIsPopupOpen(false);
  };

  return (
    <div>
      <button 
  onClick={() => setIsPopupOpen(true)} 
  style={{
    padding: '5px 10px',  // 내부 여백을 줄여 버튼 크기 조절
    fontSize: '14px',     // 글자 크기 조정
    backgroundColor: '#ccc',  // 회색 배경
    color: '#333',        // 글자 색을 어두운 회색으로 설정
    border: '1px solid #aaa',  // 테두리를 약간 어둡게
    borderRadius: '4px',  // 모서리를 둥글게
    cursor: 'pointer'     // 마우스를 올렸을 때 손가락 모양으로 변경
  }}
>
  주소 찾기
</button><br />
      <input
        type="text"
        value={postcode}
        placeholder="우편번호"
        readOnly
      />
      <input
        type="text"
        value={roadAddress}
        placeholder="도로명주소"
        readOnly
      />
      <input
        type="text"
        value={detailedAddress}
        onChange={(e) => setDetailedAddress(e.target.value)}
        placeholder="상세주소"
      />
      

      {isPopupOpen && (
        <AddressInput
          onAddressSelect={handleAddressSelect}
          onClose={closePopup}
        />
      )}
    </div>
  );
};

export default AddressSearch;
