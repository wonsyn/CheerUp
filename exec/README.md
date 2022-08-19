<img src="img/main_1.PNG">
<img src="img/main_2.PNG">
<img src="img/main_3.PNG">

### SSAFY 공통프로젝트 B104 취UP팀

<img src="img/introduce.png">



# 빌드 및 배포 문서, 외부 서비스 정보

## Back-End

- #### JAVA

- #### SpringBoot

  - ######  ver: 2.7.1
  - ######  application.properties

```
server.servlet.context-path= /api/cheerup
server.port=3000

spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://i7b104.p.ssafy.io/cheerup?useUniCode=yes&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username=root
spring.datasource.password=b104

logging.level.root=WARN

logging.level.com.web.curation.mappers=TRACE

#mapper location settings
mybatis.type-aliases-package=com.web.curation.model.dto
mybatis.mapper-locations=classpath:/mappers/**/*.xml

#Failed to start bean 'documentationPluginsBootstrapper'; error
spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER

#SSL 

server.ssl.key-store=classpath:cert_and_key.p12
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=b104
```
- #### STS
	- ###### ver: 4.15.1
- #### MyBatis
	- ###### ver: 2.2.2
- #### MySQL WorkBench
	- ###### ver: 8.0.21
	- ###### DB 접속 정보
    	ID: root
    	PW: b104
- #### Socket
  
    - ###### ver : 2.7.2
- #### JWT
  
    - ###### ver : 0.9.1
- #### Swagger
  
    - ###### ver : 3.0.0



## Front-End

- #### Vue.js

  - ###### ver : 3.2.37

- #### Vuex

  - ###### ver : 4.0.0

- #### axios

  - ###### ver : 0.27.2

- #### VSCode

  - ###### ver : 1.70.0

- #### Node.js

  - ###### ver : v16.16.0

- #### V8

  - ###### ver : 10.0.139.17-electron.0

- #### BootStrap

  - ###### ver : 5.2.0

- #### fullcalendar.js

  - ###### ver. 5.11.2

- #### sockjs-client

  - ###### ver : 1.6.1

- #### pakage.json

```jsx
"dependencies": {
    "@fullcalendar/core": "^5.11.2",
    "@fullcalendar/daygrid": "^5.11.2",
    "@fullcalendar/interaction": "^5.11.2",
    "@fullcalendar/vue3": "^5.11.2",
    "@popperjs/core": "^2.11.6",
    "axios": "^0.27.2",
    "bootstrap": "^5.2.0",
    "core-js": "^3.8.3",
    "mitt": "^3.0.0",
    "sockjs-client": "^1.6.1",
    "vue": "^3.2.13",
    "vue-router": "^4.0.3",
    "vuex": "^4.0.0",
    "webstomp-client": "^1.2.6"
  },
  "devDependencies": {
    "@babel/core": "^7.12.16",
    "@babel/eslint-parser": "^7.12.16",
    "@vue/cli-plugin-babel": "~5.0.0",
    "@vue/cli-plugin-eslint": "~5.0.0",
    "@vue/cli-plugin-router": "~5.0.0",
    "@vue/cli-plugin-vuex": "~5.0.0",
    "@vue/cli-service": "~5.0.0",
    "eslint": "^7.32.0",
    "eslint-config-prettier": "^8.3.0",
    "eslint-plugin-prettier": "^4.0.0",
    "eslint-plugin-vue": "^8.0.3",
    "prettier": "^2.4.1"
  }
```

-  npm i 명령어를 통해 pakage.json에 있는 라이브러리를 이용해 node_modules 폴더 생성 후 실행 가능

  

## ML

- #### VSCode

  - ###### ver : 1.70.0

- #### Python

  - ###### ver : 3.7.9

- #### scikit-learn

  - ###### ver : 1.0.2



## Infra

- **OS**

  - ###### ver : Ubuntu 20.04 LTS

- **MobaXterm**

  - ###### Infra

    - **OS**

      - ###### ver : Ubuntu 20.04 LTS

    - **MobaXterm**

      - ###### ver : Personal Edition v22.1 Build 4888

    - **Nginx**

      - ###### ver : nginx/1.18.0 (Ubuntu)

      - ###### 설치

        ```python
        sudo apt-get install nginx
        ```

      - ###### 환경설정

        ```python
        server {
                # ---이따 인증서 발급받고나서 설정해야함---
                # SSL configuration
                listen 443 ssl default_server;
                listen [::]:443 ssl default_server;
                ssl_certificate /var/www/test/cheerup.kro.kr/certificate.crt;
                ssl_certificate_key /var/www/test/cheerup.kro.kr/private.key;
        
            # ---루트 디렉토리 하나 만들어 주세요! 도메인 이름으로---
            # ex) /var/www/naver.com
                root /home/ubuntu/dist/dist;
        
                index index.html index.htm index.nginx-debian.html;
        
                # ---www 붙은 & 안붙은 주소 모두 기입---
                server_name cheerup.kro.kr www.cheerup.kro.kr;
        
                location / {
                        try_files $uri $uri/ =404;
                }
        
                # ---naver.com/.well-known/pki-validation으로 요청오면---
            # txt 파일을 index.html 처럼 보여줘!
            # location안에 root 넣는 예시들이 많았는데, 저는 경로 오류떴어요
                location ~/.well-known/pki-validation {
                        default_type "text/plain";
                }
        }
        
        server {
                listen 80;
                listen [::]:80;
                #listen 3000;
                #listen [::]:3000;
        
                server_name cheerup.kro.kr www.cheerup.kro.kr;
                # ---- http로 들어온 요청도 https로 매핑! ----
                    return 301 https://$host$request_uri;
        
                # SSL configuration
                #
                # listen 443 ssl default_server;
                # listen [::]:443 ssl default_server;
                #
                # Note: You should disable gzip for SSL traffic.
                # See: <https://bugs.debian.org/773332>
                #
                # Read up on ssl_ciphers to ensure a secure configuration.
                # See: <https://bugs.debian.org/765782>
                #
                # Self signed certs generated by the ssl-cert package
                # Don't use them in a production server!
                #
                # include snippets/snakeoil.conf;
        
                root /var/www/html;
        
                # Add index.php to the list if you are using PHP
                index index.html index.htm index.nginx-debian.html;
        
                server_name _;
        
        location / {
                        # First attempt to serve request as file, then
                        # as directory, then fall back to displaying a 404.
                        try_files $uri $uri/ =404;
                        proxy_pass <https://cheerup.kro.kr:8080/>;
                        proxy_redirect off;
                        charset utf-8;
                        proxy_hide_header Access-Control-Allow-Origin;
                        add_header 'Access-Control-Allow-Origin' '*';
        
                        proxy_set_header X-Real-IP $remote_addr;
                        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                        proxy_set_header X-Fowarded-Potot $scheme;
                        proxy_set_header X-Nginx-Proxy true;
        
                }
        
        location api/ {
                        # First attempt to serve request as file, then
                        # as directory, then fall back to displaying a 404.
                        try_files $uri $uri/ =404;
                        proxy_pass <https://cheerup.kro.kr:3000/api>;
                        proxy_redirect off;
                        charset utf-8;
                        proxy_hide_header Access-Control-Allow-Origin;
                        add_header 'Access-Control-Allow-Origin' '*';
        
                        proxy_set_header X-Real-IP $remote_addr;
                        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                        proxy_set_header X-Fowarded-Potot $scheme;
                        proxy_set_header X-Nginx-Proxy true;
        
                }
        ```

    - **MySQL**

      - ###### ver : 8.0.30-0ubuntu0.20.04.2 for Linux on x86_64

    - **JAVA**

      - ###### ver : open jdk 1.8.0_342

      - ###### 환경변수 설정

        [Ubuntu에서 OpenJDK 설치 후 환경변수 설정하기](https://eodevelop.tistory.com/42)

      - ###### pip3 install로 머신러닝 모듈 install

    - **Python**

      - ###### ver : 3.8.10

      - ###### pip install

        ```python
        sudo apt-get install python3-pip
        ```

      - ###### 머신러닝을 위한 각종 라이브러리 import

        - ###### pip 설치를 위한 AWS CLI

          ```python
          pip3 install awscli
          ```

        - ###### pandas

          ```python
          pip3 install pandas
          ```

        - ###### konly

          ```python
          sudo pip3 install konlpy jpype1-py3
          ```

    - **SSL**

      - ###### ZeroSSL

## 외부서비스

- **네이버 뉴스 API**
  - 네이버 개발자센터 NAVER Developers에서 API 신청을 통해 API를 등록
  - 등록 후에 발급받은 Client ID와 Client Secret을 이용해 뉴스 검색 API 활용



# 개발 스프린트

| 스프린트                                | 기간                    | FE                                                           | BE                                                           | ML                                                           |
| --------------------------------------- | ----------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 프로젝트 기획 및 계획 수립 및 문서 작성 | 2022-07-05 ~ 2022-07-16 | 와이어프레임 작성<br />화면 컴포넌트 생성<br />기능 명세서 작성 | ERD 설계 및 작성<br />API 문서 작성<br />기능 명세서 작성    | 기능 명세서 작성<br />사용할 데이터 탐색 및 데이터 분석 설계 |
| 개발 도입                               | 2022-07-17 ~ 2022-07-23 | 개발 환경 세팅 및 통일<br />사용자 화면 UI 구현              | 개발 환경 세팅 및 통일<br />계획한 백엔드 API 구현           | 개발 환경 세팅 및 통일<br />네이버 API 사용법 학습<br />데이터 분석 알고리즘 선정 |
| 핵심 기능 개발 및 필요 기술 학습        | 2022-07-24 ~ 2022-08-06 | 미구현 화면 개발<br />백엔드 API를 활용한 기능 구현          | API 디버깅<br />JPA 학습<br />배포를 위한 AWS 사용법 학습    | 데이터 수집 및 가공                                          |
| 개발 심화                               | 2022-08-07 ~ 2022-08-15 | 구현 기능 디버깅<br />화면 디자인 업데이트                   | 알림 기능 구현을 위한 소켓 통신 학습 및 구현<br />AWS를 이용해 수동 배포, DB 연동 | 연동한 DB에 생성한 데이터 저장                               |
| 개발 마무리                             | 2022-08-16 ~ 2022-08-19 | 디버깅<br />문서 작업 진행                                   | 디버깅<br />UCC 제작<br />문서 작업                          | 문서 작업 및 발표 준비                                       |

