from asyncore import read
import pandas as pd
from konlpy.tag import Okt
from tqdm import tqdm

df = pd.read_csv("./schedule_test.csv")

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
    if len(temp_kwords) == 0: # 만약 명사리스트가 비어 있다면
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
df.head()

df.to_csv(f'AD_schedule_test.csv', encoding='utf-8-sig', index=False)