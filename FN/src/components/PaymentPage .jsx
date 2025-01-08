import React from "react";

const PaymentPage  = ()=>{
    const handlePayment = ()=>{
        const{IMP} = window;
        IMP.init("imp31712014");

        const data = {
            pg: "html5_inicis",  // PG사 선택 (임시로 설정)
            pay_method: "card",  // 결제 수단 (예: 카드 결제)
            merchant_uid: "order-20231224-00001",  // 주문번호 (임시값)
            name: "상품명",  // 상품명 (임시값)
            amount: 100,  // 금액 (임시값)
        };

        IMP.request_pay(data, function(response){
            if(response.success){
                alert("결제가 완료되었습니다");
            }else{
                alert("결제가 실패되었습니다"+response.error_msg);
            }
        });
    }
    return (
        <div>
          <button onClick={handlePayment}>결제하기</button>
        </div>
      );
};
export default PaymentPage;