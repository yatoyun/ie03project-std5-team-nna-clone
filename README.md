# ie03project-std5-team-nna

## Phase1

### task1:Product-information

203

### task2:Relevant-pairs-of-products

174

### task3:Route-simulation

180

## Phase2

### task4:Relevant pairs of products (large data)



### task5:Route simulation (generalized)



### task6:Route simulation (path information)



<br>
<br>

# 作りたいプロダクト　履修計画を提案するサービス

メーリングリストからデータベースを引っ張って卒業するための履修計画をサポートする

# システム構成

- アプリケーションシステム
  - アルゴリズムやデータベースをこねくり回すコアシステム
- APIプログラム
  - インターフェースとアプリケーションの橋渡し役
- Webフロント
  - インターフェースを提供　APIを利用

# アプリケーションシステム　機能案

## 必須実装

- 卒業単位を満たすコスパの良い履修計画組み合わせを提案。ナップサック問題のアイデアを利用。
-
## その他実装

- データベースから過去の学生の履修経歴と近い履修科目を提案
- 履修取り消し率で避けるべき科目を重みづけ
- 多くの学生が共通して履修している科目は優先すべき科目として重みづけ 