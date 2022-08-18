import requests
import pandas as pd
import re

'''
4: 70000
'''

client_id = "yFx13sgkunyG3xN0RW1u"
client_secret = "mczmG_0xkH"

encode_type = 'json'
sort = 'sim' # 관련도 순
max_display = 100

headers = {'X-Naver-Client-Id' : client_id,
        'X-Naver-Client-Secret':client_secret
        }


c_words = ['IT | 디지털','개발 | 소프트웨어'] # common
i_words = ['금융', '게임', '보안', 'IT서비스', '모바일'] # inderstry
f_words = []
for c in c_words:
    for i in i_words:
        f_words.append(c + ' ' + i)

s_words = ['빅데이터', '블록체인','클라우드', '프론트엔드', '백엔드', 'DT', 'AI']

for i in f_words:
    for j in s_words:
        # 검색어
        search_word = i + ' ' + j
        print(search_word)
        for start in range(1, 1001, 100):
            url = f"https://openapi.naver.com/v1/search/news.{encode_type}?query={search_word}&display={str(int(max_display))}&start={str(int(start))}&sort={sort}"

            #HTTP요청 보기  
            r = requests.get(url, headers=headers)
            #요청 결과 보기 : 200(정상)
            print(r)
            print(r.json())

            df = pd.DataFrame(r.json()['items'])

            def df_cleansing(x):
                x = re.sub("\&\w*\;","",x)
                x = re.sub("<.*?>","",x)
                return x

            try:
                df['title'] = df['title'].apply(lambda x: df_cleansing(x))
                df['description'] = df['description'].apply(lambda x: df_cleansing(x))
            except KeyError:
                continue

            # 파일 이름 지정
            df.to_csv(f'news_beforeD_3.csv', encoding='utf-8-sig', mode='a', index=False)