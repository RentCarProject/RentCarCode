import React, { useState } from 'react';
import { Userlicense } from '../../api/UserAPI'; 

const UpdateLicense = () => {
  const [licenseType, setLicenseType] = useState('');
  const [licenseNumber, setLicenseNumber] = useState('');
  const [licenseIssueDate, setLicenseIssueDate] = useState('');
  const [licenseExpiryDate, setLicenseExpiryDate] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const licenseData = {
      licenseType,
      licenseNumber,
      licenseIssueDate,
      licenseExpiryDate,
    };

    try {
      const response = await Userlicense(licenseData); 
      alert('License updated successfully');
      
    } catch (error) {
      console.error('Error updating license:', error);
      alert('Failed to update license');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        면허증 종류:
        <input
          type="text"
          value={licenseType}
          onChange={(e) => setLicenseType(e.target.value)}
        />
      </label>
      <br />
      <label>
        면허 번호:
        <input
          type="text"
          value={licenseNumber}
          onChange={(e) => setLicenseNumber(e.target.value)}
        />
      </label>
      <br />
      <label>
        발급일자 :
        <input
          type="date"
          value={licenseIssueDate}
          onChange={(e) => setLicenseIssueDate(e.target.value)}
        />
      </label>
      <br />
      <label>
        만료일자 :
        <input
          type="date"
          value={licenseExpiryDate}
          onChange={(e) => setLicenseExpiryDate(e.target.value)}
        />
      </label>
      <br />
      <button type="submit">운전면허 등록</button>
    </form>
  );
};

export default UpdateLicense;
