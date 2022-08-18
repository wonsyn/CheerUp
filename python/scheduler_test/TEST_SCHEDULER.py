
from bs4 import BeautifulSoup
import pandas as pd
import requests
import schedule
import time
import requests
import re
from konlpy.tag import Okt
from tqdm import tqdm

def every12h():
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
                for start in range(1, 5, 100):
                    url = f"https://openapi.naver.com/v1/search/news.{encode_type}?query={search_word}&display={str(int(max_display))}&start={str(int(start))}&sort={sort}"

                    r = requests.get(url, headers=headers)
                    # 요청 결과 : 200 (정상)
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
                    df.to_csv(f'schedule_test3.csv', encoding='utf-8-sig', mode='a', index=False)
                    
    df = pd.read_csv("./schedule_test3.csv")

    okt = Okt() 
    kwords_list = []
    for content in tqdm(df['title']): 
        nouns = okt.nouns(content) # 명사만
        # print(nouns)
        kwords_list.append(nouns)
        
    df['kwords'] = kwords_list
    # df.head()

    drop_index_list = []
    for i, row in df.iterrows():
        temp_kwords = row['kwords']
        if len(temp_kwords) == 0:
            drop_index_list.append(i) # 지울 index 추가
            
    df = df.drop(drop_index_list) 

    # index 재지정
    df.index = range(len(df))

    from sklearn.feature_extraction.text import TfidfVectorizer

    # 문서 리스트로 치환 (tfidfVectorizer 인풋 형태를 맞추기 위해)
    text = [" ".join(kwords) for kwords in df['kwords']]

    tfidf_vectorizer = TfidfVectorizer(min_df = 5, ngram_range=(1,5))
    tfidf_vectorizer.fit(text)
    vector = tfidf_vectorizer.transform(text).toarray()

    from sklearn.cluster import DBSCAN
    import numpy as np

    vector = np.array(vector) # Normalizer를 이용해 변환된 벡터
    model = DBSCAN(eps=0.35,min_samples=3, metric = "cosine")
    result = model.fit_predict(vector)

    df['result'] = result
    # df.head()

    # print(df.head)
    # print('중복기사 수: ', df['result'].max(), '노이즈기사 수:', len(df[df['result'] == -1]), '클러스터 1 기사 개수:', len(df[df['result'] == 1]))

    # c3_df = df[df['result'] == 3]

    df = df[df['link'].str.contains('https://n.news.naver.com/')]
    # if df['result'].apply(lambda x, re:x > re, re = 0):
    df.drop_duplicates(['result'], keep='first')
    

    # print('중복기사 수: ', df['result'].max(), '노이즈기사 수:', len(df[df['result'] == -1]), '클러스터 3 기사 개수:', len(df[df['result'] == 3]))
    # print(df.head)

    
    # 크롤링 : url 수정 필요
    # df = pd.read_csv('./c_test.csv')
    # urls = list(df['link'])
    url = 'https://n.news.naver.com/mnews/article/009/0004995624?sid=105'

    header = {
        'user-agent':
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36'}

    r = requests.get(url, headers=header).text
    html = BeautifulSoup(r, 'lxml')

    content = html.select_one('div#dic_area').text.strip()
    img = html.select_one('#img1')['data-src']

    df['content'] = content
    df['img'] = img
    
    df.to_csv(f'schedule_test_result3.csv', encoding='utf-8-sig', index=False)
    print("done")

# 실행코드
schedule.every().day.at("11:23").do(every12h)
while True:
    schedule.run_pending()
    time.sleep(1)