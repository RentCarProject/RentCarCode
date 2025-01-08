import React, { useState } from 'react';
import { userAddrAndLicense } from '../../api/UserAPI';
import AddressInput from './addr';
import "./MyPageCss/AdditionalInfoModal.css";

const AdditionalInfoModal = ({ isOpen, onRequestClose,onSave }) => {
  const [postcode, setPostcode] = useState('');
  const [roadAddress, setRoadAddress] = useState('');
  const [detailedAddress, setDetailedAddress] = useState('');
  const [licenseType, setLicenseType] = useState('');
  const [licenseNumber, setLicenseNumber] = useState('');
  const [licenseIssueDate, setLicenseIssueDate] = useState('');
  const [licenseExpiryDate, setLicenseExpiryDate] = useState('');
  const [isAddressModalOpen, setIsAddressModalOpen] = useState(false);

  const handleAddressSelect = (address, zonecode) => {
    setPostcode(zonecode);
    setRoadAddress(address);
    setIsAddressModalOpen(false);
  };

  const handleSave = async () => {
    const additionalInfo = {
      postcode,
      roadAddress,
      detailedAddress,
      licenseType,
      licenseNumber,
      licenseIssueDate,
      licenseExpiryDate,
    };

    try {
      // Userlicense API 호출
      const response = await userAddrAndLicense(additionalInfo);
      console.log(response);
      alert('정보가 성공적으로 등록되었습니다.');
      onSave(additionalInfo);
      onRequestClose();
    } catch (error) {
      console.error('Error updating license and address:', error);
      alert('등록에 실패했습니다.');
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">

        {/* 주소 입력 섹션 */}
        <div>
          <h3>주소</h3>
          <div style={{ marginBottom: '10px' }}>
            <input
              type="text"
              value={postcode}
              placeholder="우편번호"
              readOnly
            />
            <button onClick={() => setIsAddressModalOpen(true)}>
              주소 찾기
            </button>
          </div>
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
        </div>
        <br />
        {/* 면허 정보 입력 */}
        <div>
          <h3>면허 정보</h3>
          <input
            type="text"
            value={licenseType}
            onChange={(e) => setLicenseType(e.target.value)}
            placeholder="면허 종류"
          /><br/>
          <input
            type="text"
            value={licenseNumber}
            onChange={(e) => setLicenseNumber(e.target.value)}
            placeholder="면허 번호"
          /><br/>
          <input
            type="date"
            value={licenseIssueDate}
            onChange={(e) => setLicenseIssueDate(e.target.value)}
            placeholder="발급일자"
          /><br/>
          <input
            type="date"
            value={licenseExpiryDate}
            onChange={(e) => setLicenseExpiryDate(e.target.value)}
            placeholder="만료일자"
          />
        </div>

        {/* 저장 및 닫기 버튼 */}
        <div className="modal-actions">
          <button className="modal-button" onClick={handleSave}>등록</button>
          <button className="modal-button" onClick={onRequestClose}>닫기</button>
        </div>
      </div>

      {/* 주소 검색 모달 */}
      {isAddressModalOpen && (
        <div className="address-modal-overlay">
          <div className="address-modal-content">
            <AddressInput
              onAddressSelect={handleAddressSelect}
              onClose={() => setIsAddressModalOpen(false)}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default AdditionalInfoModal;
