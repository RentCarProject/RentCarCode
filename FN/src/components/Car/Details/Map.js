import React, { useEffect, useRef } from "react";

const DEFAULT_LATITUDE = 35.870929;  // 기본 지도 중심 (대구 시청)
const DEFAULT_LONGITUDE = 128.595532;

const KakaoMap = ({ latitude = DEFAULT_LATITUDE, longitude = DEFAULT_LONGITUDE, markerPosition }) => {
  const mapContainer = useRef(null);
  const mapInstance = useRef(null);
  const markerInstance = useRef(null);

  // 스크립트 로드 여부 확인
  const isKakaoMapLoaded = useRef(false);

  useEffect(() => {
    const loadKakaoMap = () => {
      if (window.kakao && window.kakao.maps) {
        const options = {
          center: new window.kakao.maps.LatLng(latitude, longitude),
          level: 3,
        };

        // 지도 초기화 (중복 생성 방지)
        if (!mapInstance.current) {
          mapInstance.current = new window.kakao.maps.Map(mapContainer.current, options);
        }

        // 마커 초기화
        if (markerInstance.current) {
          markerInstance.current.setMap(null);
        }

        // 마커 추가
        if (markerPosition) {
          const markerLatLng = new window.kakao.maps.LatLng(markerPosition.lat, markerPosition.lng);
          markerInstance.current = new window.kakao.maps.Marker({
            position: markerLatLng,
          });
          markerInstance.current.setMap(mapInstance.current);
          mapInstance.current.setCenter(markerLatLng);  
        }
      } else {
        // 카카오맵 스크립트 로드
        const script = document.createElement("script");
        script.src = "https://dapi.kakao.com/v2/maps/sdk.js?appkey=a8306a7a56766cc9f5c5f6756029e841&autoload=false"; 
        script.async = true;  
        script.defer = true;  

        // 스크립트가 로드되었을 때 호출되는 함수
        script.onload = () => {
          window.kakao.maps.load(() => {
            loadKakaoMap();
            isKakaoMapLoaded.current = true;
          });
        };

        document.body.appendChild(script);
      }
    };

    // 카카오맵 API가 이미 로드된 경우 직접 호출
    if (!isKakaoMapLoaded.current) {
      loadKakaoMap();
    }

    return () => {
      // Cleanup (불필요한 리소스 해제)
      if (mapInstance.current) {
        mapInstance.current = null;
      }
      if (markerInstance.current) {
        markerInstance.current.setMap(null);
        markerInstance.current = null;
      }
    };
  }, [latitude, longitude, markerPosition]);

  return <div ref={mapContainer} style={{ width: "80%", height: "250px", margin: "0 auto", borderRadius: "10px" }}></div>;
};

export default KakaoMap;
