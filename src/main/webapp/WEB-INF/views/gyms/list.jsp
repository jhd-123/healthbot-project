<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>📍 내 위치 & 주변 헬스장 검색</title>
  <style>
    body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; }
    #search-container {
      padding: 10px;
      background: #f5f5f5;
      border-bottom: 1px solid #ddd;
    }
    #search-input {
      width: 200px;
      padding: 5px;
      font-size: 14px;
    }
    #search-btn {
      padding: 5px 10px;
      font-size: 14px;
      cursor: pointer;
    }
    #map { width: 100%; height: 500px; }
  </style>
</head>
<body>

<div id="search-container">
  <input type="text" id="search-input" placeholder="검색어 입력 (예: 헬스장)" />
  <button id="search-btn">검색</button>
</div>

<div id="map"></div>

<script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=709f8eb34b8860e51c2ed050bf8bc0f1&libraries=services"></script>

<script>
  window.onload = function () {
    let map, userLat, userLon;
    let openOverlay = null;

    if (!navigator.geolocation) {
      alert("❌ 위치 정보를 지원하지 않습니다.");
      return;
    }

    navigator.geolocation.getCurrentPosition(function(pos) {
      userLat = pos.coords.latitude;
      userLon = pos.coords.longitude;

      const userPosition = new kakao.maps.LatLng(userLat, userLon);

      map = new kakao.maps.Map(document.getElementById('map'), {
        center: userPosition,
        level: 4
      });

      new kakao.maps.Marker({
        map: map,
        position: userPosition,
        image: new kakao.maps.MarkerImage(
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png",
          new kakao.maps.Size(40, 42),
          { offset: new kakao.maps.Point(13, 42) }
        )
      });

      searchPlaces("헬스장");
    }, function(error) {
      alert("위치 정보를 가지오지 못했습니다.");
    });

    document.getElementById("search-btn").addEventListener("click", function() {
      const keyword = document.getElementById("search-input").value.trim();
      if (!keyword) {
        alert("검색어를 입력해주세요.");
        return;
      }
      searchPlaces(keyword);
    });

    function searchPlaces(query) {
      if (!userLat || !userLon) {
        alert("위치 정보가 없어 검색을 수행할 수 없습니다.");
        return;
      }

      const url = "/api/kakao/gyms?lat=" + encodeURIComponent(userLat) + "&lon=" + encodeURIComponent(userLon);

      fetch(url)
        .then(res => res.json())
        .then(data => {
          const places = data.documents || data;
          if (!Array.isArray(places)) {
            alert("헬스장 목록 형식이 잘못되어 수신할 수 없습니다.");
            return;
          }

          map.setCenter(new kakao.maps.LatLng(userLat, userLon));

          places.forEach(place => {
            const marker = new kakao.maps.Marker({
              map: map,
              position: new kakao.maps.LatLng(place.y, place.x),
              image: new kakao.maps.MarkerImage(
                "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png",
                new kakao.maps.Size(40, 42),
                { offset: new kakao.maps.Point(13, 42) }
              )
            });

            kakao.maps.event.addListener(marker, 'click', function() {
              const kakaoPlaceId = `${place.place_name}_${place.x}_${place.y}`;

              const gymData = {
                name: place.place_name,
                address: place.road_address_name || '',
                phone: place.phone || '',
                latitude: parseFloat(place.y),
                longitude: parseFloat(place.x),
                kakaoPlaceId: kakaoPlaceId
              };

              fetch('/api/gyms', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(gymData)
              })
              .then(res => res.json())
              .then(savedGym => {
                if (!savedGym.id) {
                  alert("헬스장 저장 실패");
                  return;
                }

                if (openOverlay) openOverlay.setMap(null);

                const content = document.createElement('div');
                content.style.padding = '10px';
                content.style.fontSize = '14px';
                content.style.background = 'white';
                content.style.border = '1px solid #ccc';
                content.style.borderRadius = '10px';
                content.style.position = 'relative';

                const closeBtn = document.createElement('div');
                closeBtn.innerText = '✖';
                closeBtn.style.position = 'absolute';
                closeBtn.style.top = '5px';
                closeBtn.style.right = '8px';
                closeBtn.style.cursor = 'pointer';
                closeBtn.onclick = function () {
                  if (openOverlay) openOverlay.setMap(null);
                };
                content.appendChild(closeBtn);

                const name = document.createElement('strong');
                name.innerText = savedGym.name;
                content.appendChild(name);
                content.appendChild(document.createElement('br'));

                const link = document.createElement('a');
                link.href = '/gyms/detail/' + savedGym.id;
                link.innerText = '상세정보 보기';
                link.style.color = 'blue';
                content.appendChild(link);

                const position = new kakao.maps.LatLng(savedGym.latitude, savedGym.longitude);

                const customOverlay = new kakao.maps.CustomOverlay({
                  map: map,
                  position: position,
                  content: content,
                  yAnchor: 1
                });

                customOverlay.setMap(map);
                openOverlay = customOverlay;
              })
              .catch(err => {
                console.error("❌ 저장 중 오류:", err);
              });
            });
          });
        })
        .catch(err => {
          console.error("❌ Kakao API 호출 실패:", err);
        });
    }
  };
</script>

</body>
</html>
