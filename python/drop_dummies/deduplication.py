import pandas as pd
import re

df = pd.read_csv("./result_data_2.csv")

def df_cleansing(x):
    x = re.sub("\&\w*\;","",x)
    x = re.sub("<.*?>","",x)
    return x

df['title'] = df['title'].apply(lambda x: df_cleansing(x))
df['description'] = df['description'].apply(lambda x: df_cleansing(x))

# print(df.head)
print('중복기사 수: ', df['result'].max(), '노이즈기사 수:', len(df[df['result'] == -1]), '클러스터 1 기사 개수:', len(df[df['result'] == 1]))

c3_df = df[df['result'] == 3]
# 왜 안됨...?
# 네이버면 -1로 바꾸고 for문탈출, -1만 남길까?
# print(c3_df['link'])

df = df[df['link'].str.contains('https://n.news.naver.com/')]

# 수정예정
df.drop_duplicates(subset=['result'], keep='first')

# 인덱스 재정렬
df = df.reset_index(drop=True)
print('중복기사 수: ', df['result'].max(), '노이즈기사 수:', len(df[df['result'] == -1]), '클러스터 3 기사 개수:', len(df[df['result'] == 3]))
print(df.head)
df.to_csv(f'news_afterD_2.csv', encoding='utf-8-sig', index=False)