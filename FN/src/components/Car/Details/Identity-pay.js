

// 본인 인증 API 호출
export const requestCertification = async () => {
  const { IMP } = window;
  IMP.init("imp31712014");

  // 서버에서 액세스 토큰 받기
  const tokenResponse = await fetch("http://localhost:8090/api/portone/getAccessToken");
  const tokenData = await tokenResponse.json(); 
  const accessToken = tokenData.access_token; 

  const data = {
    channelKey: "channel-key-350aeb28-4ddc-447c-ade8-be4a3b978300",
    accessToken: accessToken,  
  };

  return new Promise((resolve, reject) => {
    IMP.certification(data, function (response) {
      if (response.success) {
        resolve({ certificationData: response, accessToken: accessToken }); 
      } else {
        reject(new Error("본인 인증 실패: " + response.error_msg));
      }
    });
  });
};


// 결제

export const requestPayment = (paymentData) => {
    return new Promise((resolve, reject) => {
      const { IMP } = window;
      IMP.init("imp31712014");  
  
      IMP.request_pay(paymentData, function(response) {
        if (response.success) {
          resolve(response);
        } else {
          reject(new Error("결제 실패: " + response.error_msg));
        }
      });
    });
  };
  




