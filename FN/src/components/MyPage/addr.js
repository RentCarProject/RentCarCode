import React, { useState } from 'react';
import DaumPostcode from 'react-daum-postcode';

const AddressInput = ({ onAddressSelect, onClose }) => {
  const [zonecode, setZonecode] = useState('');
  const [address, setAddress] = useState('');
  const [detailedAddress, setDetailedAddress] = useState('');
  const [isOpen, setIsOpen] = useState(false);

  const themeObj = {
    bgColor: '#FFFFFF', 
    pageBgColor: '#FFFFFF', 
    postcodeTextColor: '#C05850',
    emphTextColor: '#222222',
  };

  const postCodeStyle = {
    width: '370px',
    height: '480px',
  };

  const completeHandler = (data) => {
    const { address, zonecode } = data;
    setZonecode(zonecode);
    setAddress(address);
    onAddressSelect(address, zonecode);  // 부모에게 선택된 주소 전달
    onClose();  // 주소 선택 후 팝업 닫기
  };

  const closeHandler = () => {
    setIsOpen(false);
    onClose();  // 팝업 닫기
  };

  const toggleHandler = () => {
    setIsOpen((prevOpenState) => !prevOpenState);
  };

  const inputChangeHandler = (event) => {
    setDetailedAddress(event.target.value);
  };

  return (
    <div style={overlayStyle}>
      <div style={popupStyle}>
        <div>
          <strong>주소</strong>
        </div>
        <div>
          <div>
            <div>{zonecode}</div>
            <button type="button" onClick={toggleHandler}>
              주소 찾기
            </button>
          </div>
          {isOpen && (
            <div>
              <DaumPostcode
                theme={themeObj}
                style={postCodeStyle}
                onComplete={completeHandler}
                onClose={closeHandler}
              />
            </div>
          )}
          <div>{address}</div>
          <input
            value={detailedAddress}
            onChange={inputChangeHandler}
          />
        </div>
        <button onClick={closeHandler} style={closeButtonStyle}>
          닫기
        </button>
      </div>
    </div>
  );
};

// 팝업을 위한 오버레이 스타일
const overlayStyle = {
  position: 'fixed',
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  backgroundColor: 'rgba(0, 0, 0, 0.5)',  // 반투명 배경
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  zIndex: 9999,  // 다른 콘텐츠보다 위에 표시
};

// 팝업창 스타일
const popupStyle = {
  backgroundColor: 'white',
  padding: '20px',
  borderRadius: '8px',
  width: '400px',
  height: '500px',
  boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)',
  overflow: 'auto',
  position: 'relative',
};

// 닫기 버튼 스타일
const closeButtonStyle = {
  position: 'absolute',
  top: '10px',
  right: '10px',
  backgroundColor: '#C05850',
  color: 'white',
  border: 'none',
  padding: '5px 10px',
  borderRadius: '4px',
  cursor: 'pointer',
};

export default AddressInput;
