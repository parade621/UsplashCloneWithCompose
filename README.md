# Unsplash Clone _ with compose

## API KEY 값을 포함하지 않으므로 참고 부탁드립니다.

2023년 12월 윌로그 Android 개발자 채용 코딩테스트 과제에 참여할 기회를 주신 것에 감사드립니다.


### 1. 과제 설명
* 이미지를 검색해서 북마크로 수집하는 Unsplash 클론 앱을 만들어 주세요.

* 이미지 검색 API (https://api.unsplash.com/search/photos)의 results 필드

* UI는 3개의 Screen을 사용합니다.
* 첫 번째 Screen : 검색 결과
검색어를 입력할 수 있습니다.
검색된 이미지 리스트가 그리드로 나타납니다.북마크가 된 아이템의 경우, 북마크 아이콘과 함께 표시됩니다.
스크롤을 통해 다음 페이지를 불러옵니다.
리스트에서 특정 이미지를 선택하여 Detail Screen으로 이동할 수 있습니다.

* 두 번째 Screen : 상세 화면 
선택한 이미지의 상세 정보를 보여줍니다.
이미지를 북마크로 저장하거나 저장된 것을 제거할 수 있습니다.

* 세 번째 Screen : 북마크 리스트
북마크로 저장된 이미지들을 보여줍니다.
이미지를 선택하여 Detail Screen으로 이동할 수 있습니다.


### 2. 사용된 라이브러리
* Retrofit2 (API 통신)
* Room (SQLite)
* Paging3 (페이징 처리)
* Hilt (DI)
* Moshi (JSON 파싱)
* Glide for Compose (이미지 표시)
* Timber (로그)
