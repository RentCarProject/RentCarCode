
// 본인인증 
export const performCertification = async () => {
  const { IMP } = window;
  IMP.init("imp31712014");

  return new Promise((resolve, reject) => {
    IMP.certification(
      {
        channelKey: "channel-key-350aeb28-4ddc-447c-ade8-be4a3b978300",
        min_age: 19,
      },
      function (response) {
        console.log("본인 인증 응답 데이터:", response);

        if (response.success) {
          resolve(response); // 본인 인증 성공 시 response 반환
        } else {
          reject(new Error("본인 인증 실패: " + response.error_msg));
        }
      }
    );
  });
};

//본인인증 성공 토큰 발급
export const fetchAccessToken = async () => {
  try {
    const tokenResponse = await fetch("http://localhost:8090/api/portone/getAccessToken");
    const tokenData = await tokenResponse.json();
    console.log("Access Token 발급 성공:", tokenData.access_token);
    return tokenData.access_token; // 발급받은 Access Token 반환
  } catch (error) {
    console.error("Access Token 발급 실패:", error);
    throw new Error("Access Token 발급 실패");
  }
};

// 본인인증정보 조회
export const fetchCertificationInfo = async (impUid, accessToken) => {
  try {
    const verificationResponse = await fetch(
      `http://localhost:8090/api/portone/authInfo/${impUid}`,
      {
        method: "GET",
        headers: {
          "IamportToken": accessToken, // 발급받은 Access Token
        },
      }
    );

    if (!verificationResponse.ok) {
      throw new Error("본인 인증 정보 조회 실패");
    }

    const verificationData = await verificationResponse.json();
    console.log("본인 인증 정보 조회 결과:", verificationData);

    return verificationData // 인증 정보 반환
  } catch (error) {
    console.error("본인 인증 정보 조회 에러:", error);
    throw new Error("본인 인증 정보 조회 에러: " + error.message);
  }
};

// 본인인증 전체수행
export const requestCertification = async () => {
  try {
    // 본인 인증 수행
    const certificationResponse = await performCertification();

    // Access Token 발급
    const accessToken = await fetchAccessToken();

    // 본인 인증 정보 조회
    const certificationInfo = await fetchCertificationInfo(certificationResponse.imp_uid, accessToken);
    console.log("본인 인증 정보:",certificationInfo);

    return {
      success: true,
      certificationData: {
        ...certificationResponse,
        name: certificationInfo.name, // 인증된 이름
        phone: certificationInfo.phone, // 인증된 전화번호
      },
    };
  } catch (error) {
    console.error("본인 인증 요청 실패:", error);
    return { success: false, message: error.message };
  }
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
  




