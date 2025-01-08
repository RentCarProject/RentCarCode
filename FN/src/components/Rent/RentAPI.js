import { UserApi } from "../../api/UserAPI";

  // 렌탈 정보 조회
  export const RentalDetails = async (rentalId) => {
    const response = await UserApi.get(`/api/v1/rentals/${rentalId}`);
    return response.data;
  };

  export const RentalList = async () =>{
    const response = await UserApi.get('/api/v1/rental/carList')
    console.log(response.data);
    return response.data;
  }
  
  // 렌탈 시작
  export const carRental = async (rentalData) => {
    const response = await UserApi.post("/api/v1/rentals/rentcar", rentalData);
    return response.data;
  };
  
  // 렌탈 종료
  export const endRental = async (rentalId) => {
    const response = await UserApi.post(`/api/v1/rentals/end/${rentalId}`);
    return response.data;
  };
  
  // 렌탈 연장
  export const extendRental = async (rentalId, extensionData) => {
    const response = await UserApi.put(`/api/v1/rentals/extend/${rentalId}`, extensionData);
    return response.data;
  };