'''
두개씩 합쳐진 공통 키워드 + 최신순
'''

import requests
import pandas as pd
import re

client_id = "yFx13sgkunyG3xN0RW1u"
client_secret = "mczmG_0xkH"

encode_type = 'json'
sort = 'date' # 최신순 정렬
max_display = 100

headers = {'X-Naver-Client-Id' : client_id,
        'X-Naver-Client-Secret':client_secret
        }

# 공통 키워드, 산업군 키워드
c_words = ['IT 디지털', '웹 앱', '개발 소프트웨어'] # common
i_words = ['금융', '게임', '보안', 'IT서비스', '모바일'] # inderstry

# 산업군별 검색 키워드
finance_k = ['마이데이터', '핀테크', '테크핀', '은행', '증권']
security_k = ['암호', '개인정보', '해킹']
game_k = ['메타버스', '엔진', '모바일', '콘텐츠', 'P2E']
service_k = ['IOT', '플랫폼', 'SI', 'SM', 'solution']
mobile_k = ['웨어러블', '안드로이드', 'IOS']

search_word = []
for c in c_words:
    for i in i_words:
        if i == '금융':
            for k in finance_k:
                search_word.append(c + ' ' + i + ' ' + k)
                
        elif i == '게임':
            for k in game_k:
                search_word.append(c + ' ' + i + ' ' + k)
                
        if i == '보안':
            for k in security_k:
                search_word.append(c + ' ' + i + ' ' + k)
                
        if i == 'IT서비스':
            for k in service_k:
                search_word.append(c + ' ' + i + ' ' + k)
                
        if i == '모바일':
            for k in mobile_k:
                search_word.append(c + ' ' + i + ' ' + k)

# 검색어 설정
for sw in search_word:       
    for start in range(1, 1001, 100):
        url = f"https://openapi.naver.com/v1/search/news.{encode_type}?query={sw}&display={str(int(max_display))}&start={str(int(start))}&sort={sort}"


        #HTTP요청 
        r = requests.get(url, headers=headers)
        #요청 결과 : 200(정상)
        print(r)
        print(r.json())

        df = pd.DataFrame(r.json()['items'])

        def df_cleansing(x):
            x = re.sub("\&\w*\;","",x)
            x = re.sub("<.*?>","",x)
            return x

        # df['title'] = df['title'].apply(lambda x: df_cleansing(x))
        

# 파일 이름 지정
        df.to_csv(f'news_beforeD_2.csv', encoding='utf-8-sig', mode='a', index=False)