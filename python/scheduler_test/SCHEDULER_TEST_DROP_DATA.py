import requests
import pandas as pd
import re

'''
'금융': 1, '게임': 2, '보안': 3, 'IT서비스': 4, '모바일': 5
++ 이미지 링크/ 본문 추가 크롤링 필요
총 데이터 길이 : 21000
'''

client_id = "yFx13sgkunyG3xN0RW1u"
client_secret = "mczmG_0xkH"

encode_type = 'json'
sort = 'date' # 최신순
max_display = 100

headers = {'X-Naver-Client-Id' : client_id,
        'X-Naver-Client-Secret':client_secret
        }

# 공통 키워드, 산업군 키워드
c_words = ['IT | 디지털','개발 | 소프트웨어'] # common
i_words = ['금융', '게임', '보안', 'IT서비스', '모바일'] # inderstry
# 산업군별 검색 키워드
i_dict = {'금융': 1, '게임': 2, '보안': 3, 'IT서비스': 4, '모바일': 5}

s_words = [['마이데이터', '핀테크', '테크핀', '은행', '증권']
, ['암호', '개인정보', '해킹']
,['메타버스', '엔진', '모바일', '콘텐츠', 'P2E']
, ['IOT', '플랫폼', 'SI', 'SM', 'solution']
,['웨어러블', '안드로이드', 'IOS']]

search_word = []
for c in c_words:
    for i, v in i_dict.items():
        for s in s_words[v -1]:
            search_word = c + ' ' + i + ' ' + s
            for start in range(1, 10, 100):
                url = f"https://openapi.naver.com/v1/search/news.{encode_type}?query={search_word}&display={str(int(max_display))}&start={str(int(start))}&sort={sort}"

                r = requests.get(url, headers=headers)
                #요청 결과 : 200 (정상)
                print(r)
                print(r.json())

                df = pd.DataFrame(r.json()['items'])
                df['industry_num'] = v
                
                def df_cleansing(x):
                    x = re.sub("\&\w*\;","",x)
                    x = re.sub("<.*?>","",x)
                    return x

                try:
                    df['title'] = df['title'].apply(lambda x: df_cleansing(x))
                except KeyError:
                    continue
                
                # try:
                #     df['decription'] = df['decription'].apply(lambda x: df_cleansing(x))
                # except KeyError:
                #     continue

                # 파일 이름
                df.to_csv(f'schedule_test.csv', encoding='utf-8-sig', mode='a', index=False)
