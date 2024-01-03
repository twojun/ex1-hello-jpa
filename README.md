# JPA 이론 학습

# Chapter 1. JPA BASIC
1. EntityManager는 애플리케이션 로딩 시점에 1개만 생성해서 모든 영역에서 공유하며 사용한다.
2. EntityManager는 스레드 간 공유하지 않는다.
3. JPA 내부에서의 데이터 수정은 Transaction 내부에서 진행된다.
