import React from "react";

const IdentityPage = ()=>{
    const handleIdentity = ()=>{
        const{IMP} = window;
        IMP.init("imp31712014");

        const data={
            channelKey : "channel-key-350aeb28-4ddc-447c-ade8-be4a3b978300",
            
        }

        IMP.certification(data, function(response){
            if(response.success){
                alert("본인인증이 성공되었습니다");
            }else{
                alert("본인인증이 실패되었습니다"+response.error_msg);
            }
        })
    }


    return(
        <div>
            <button onClick={handleIdentity}>본인인증</button>
        </div>
    )
}
export default IdentityPage