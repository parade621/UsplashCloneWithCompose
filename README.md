# Unsplash Clone _ with compose

SDK Version Min 26 / Target 33

### 📢  API KEY 값을 포함하지 않으므로 참고 바랍니다.
- https://unsplash.com/developers 여기서 'Register as a developer'로 API ACCESS_KEY를 배정 받으신 후, local.properties에 ACCESSKEY = YOUR_ACCESS_KEY로 입력 

### 1. 프로젝트 설명
* 이미지를 검색해서 북마크로 수집하는 Unsplash 클론 앱
* ![photo](https://github.com/parade621/Usplash_clone_with_compose/assets/36446270/3ba67de8-5048-474f-975e-7d17cdfbed22)

* 이미지 검색 API (https://api.unsplash.com/search/photos)의 results 필드

* UI는 (Search, Detail, Bookmark)3개의 Screen을 사용
```
* 첫 번째 Screen : 검색 결과
- 검색어를 입력할 수 있다.
- 검색된 이미지 리스트가 그리드로 나타난다. 이때, 북마크가 된 아이템의 경우, 북마크 아이콘과 함께 표시
- 스크롤을 통해 다음 페이지를 불러온다.
- 리스트에서 특정 이미지를 선택하여 Detail Screen으로 이동할 수 있다.

* 두 번째 Screen : 상세 화면 
- 선택한 이미지의 상세 정보(id, author, width*height, create at)를 보여준다.
- 이미지를 북마크로 저장하거나 저장된 것을 제거할 수 있다.

* 세 번째 Screen : 북마크 리스트
- 북마크로 저장된 이미지들을 보여준다.
- 이미지를 선택하여 Detail Screen으로 이동할 수 있다.
```
---

### 2. 사용된 라이브러리
* Jetpack Compose (UI)
* Kotlin DSL (Gradle)
* Retrofit2 (API 통신)
* Room (SQLite DB)
* Paging3 (페이징 처리)
* Hilt (DI)
* Moshi (JSON 파싱)
* Glide for Compose (이미지 표시)
* Timber (로그)

---

### 3. 받은 피드백

**Feedback 1) ViewModel -> Repository 구조를 가져가여 도메인 로직이 VM 에서 처리되기에, 북마크 표시를 위한 별도의 로직을 각 VM 마다 작성해야할 것들을 고민해보면 좋을 것 같다.**
<br>
A: 북마크 추가 및 제거는 Detail Screen 한곳에서만 가능하다. 이 피드백은 사실 무슨 말인지 이해가 잘 안간다.
북마크가 사진 우측 상단에 표시되는 거라면, DTO에 존재하는 값에 의해 표시되는 것이지 VM에서 이미지를 표시하기 위해 수행되는 별도의 로직은 없다.
아니면 이것도 Screen State로 처리하는게 효율적일 수 있다는 것일까? 그럼 오히려 불필요한 state가 더 늘어나지 않을까라는 생각이 든다. 이 부분은 MVI 아키텍쳐를 사용하면 한번쯤 고려해 볼만한 요소이긴 하다. 좀더 생각해 보겠다.
<br/>
<br/>

**feedback 2) PhotoSearchRepo 의 설계가 Photo search 뿐 아니라 bookmark 처리 등 많은 역할을 담당하고 있는 것으로 보인다. 필요한 기능에 따라 조금더 세분화 하는 것도 고려해보면 좋을 것 같다.**
<br>
A: 이것도 나눌 수는 있지만, 주요 기능이 2개뿐인 단순한 앱에서 나누는 것은 파일 수만 증가시킬 뿐이라는 생각에 나누지 않았다. 물론 확장성을 고려하면 나누는 것이 맞다.
<br/>
<br/>

**feedback 3) 모든 데이터 모델을 통합하여 사용하고있어, API 및 DB 데이터 모델이 같아 과도한 데이터를 추가/삭제 하는부분도 고민해볼 포인트 같다.**
<br>
A: 결국 사용되는 데이터는 id, Author, width, height, url, bookmark 여부로 6개 뿐이다. 앱에서 반드시 필요한 데이터를 저장하는 것이 어느 부분에서 과도하게 느껴지는지 모르겠다.
<br/>
<br/>

**feedback 4) DI 를 Object 안에 구현하면 DI 라이브러리를 사용하는 의미가 많이 없어보인다.**
<br>
A: 이 피드백은 Hilt/Dagger에 대한 이해가 부족하다고 생각된다.<br/>
[공식문서](https://developer.android.com/training/dependency-injection/hilt-android?hl=ko#hilt-modules) 에서 확인 가능한 구글이 권장하는 방식<br/>
같이 보면 좋은 CodeLabs의 실습 예시: [https://developer.android.com/codelabs/android-hilt?hl=ko#6](https://developer.android.com/codelabs/android-hilt?hl=ko#6)
<br/>
<br/>
  
**feedback 5) MainActivity 내부에 모든 화면에 대한 UI 구현이 있는 부분도 아쉽다.**
<br>
A: Screen 3개있는 프로젝트에서 불필요하게 UI를 구분짓는 것을 오히려 불필요하게 생각했다. 나올 수 있는 피드백이라고 생각했지만, 이건 개인의 스타일 차이라고 생각된다.

