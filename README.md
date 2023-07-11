<h1>🧩 PUZZLING_Server 🧩</h1>
<br>

<p align="center">
<img width="700px" height="250px" src="https://github.com/Team-Puzzling/Puzzling_Android/assets/62274335/ee3e0528-b70c-4f64-ac16-3bd41b4e7c2c" />
</p>

<center>

**팀과 함께 나만의 회고를 작성하며 퍼즐을 완성하고, 그 과정에서 시각적 성취감과 회고의 동기부여를 이끌어내는 서비스, 퍼즐링**
</center>

<br>

## 💻 Team-Puzzling Server Developers

| 조예슬 | 정홍준 |
| :---------:|:----------:|
|<img width="330" height="350" alt="image" src="https://user-images.githubusercontent.com/81394850/210397458-13875d52-7081-4f5b-9c65-a558b8efa57b.jpg"> | <img width="330" height="350" alt="image" src="https://github.com/Team-Puzzling/Puzzling_Server/assets/68415644/51bde41d-f223-44bc-b84b-fe47bb09fdde"> | 
| [yeseul106](https://github.com/yeseul106) | [Hong0329](https://github.com/Hong0329) |
<br>

## 🙋🏻‍♀️ 역할 분담

<div markdown="1">  
 
| 기능명 | 담당자 | 완료 여부 |
| :-----: | :---: | :---: |
| 프로젝트 세팅 | `예슬🐼` | 완료 |
| EC2 세팅 | `예슬🐼` | 완료 |
| RDS 세팅 | `홍준🐥` | 완료 |
| DB 설계 | `예슬🐼` `홍준🐥` | 완료 |
| API 명세서 작성 | `예슬🐼` `홍준🐥` | 완료 |
| Nginx 세팅 및 CI/CD 구축 | `예슬🐼` | 완료 |
| 소셜 로그인 구현 | `홍준🐥` | 진행중 |
| API 개발 | `예슬🐼` `홍준🐥` | 진행중 |
| 테스트 코드 작성 | `예슬🐼` `홍준🐥` | 진행중 |
<br>

## 📖 Development Tech
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br>
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<br>
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<br>
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<br>
<img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
<br>
<br>


## 📂 Project Foldering

```
com
 ㄴ puzzling
     ㄴ puzzlingServer
         ㄴ api
         |   ㄴ member
         |   |   ㄴ auth
         |   |   ㄴ controller
         |   |   ㄴ service
         |   |   ㄴ repository
         |   |   ㄴ domain
         |   |   ㄴ dto
         |   ㄴ project
         |   |   ㄴ controller
         |   |   ㄴ service
         |   |   ㄴ repository
         |   |   ㄴ domain
         |   |   ㄴ dto
         |   ...
         ㄴ common
         |   ㄴ advice
         |   ㄴ config
         |   |    ㄴ jwt
         |   ㄴ entity
         |   ㄴ exception
         |   ㄴ response
         ㄴ global
         |   ㄴ api
         |   ㄴ HealthCheckController
         |   ㄴ ServerProfileController
         ㄴ PuzzlingServerApplication

```
<br>

## 🚀 Server Architecture
![image](https://github.com/Team-Puzzling/Puzzling_Server/assets/68415644/0f632c3d-eb02-4774-8143-75cf6762e260)



