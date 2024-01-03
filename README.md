# JPA 이론 학습

# Chapter 1. JPA Basic
1. EntityManager는 애플리케이션 로딩 시점에 1개만 생성해서 모든 영역에서 공유하며 사용한다.
2. EntityManager는 스레드 간 공유하지 않는다.
3. JPA 내부에서의 데이터 수정은 Transaction 내부에서 진행된다.


4. JPQL(Java Persistence Query Language)
- 실제 물리적 데이터베이스 테이블이 아닌 엔티티 객체를 대상으로 하는 객체지향 쿼리 언어를 의미힌다.
- 특정 데이터베이스 테이블을 대상으로 쿼리를 날리면 해당 데이터베이스에 종속적인 설계로 이루어진다 하지만 JPQL를
  사용함으로써 특정 DB에 종속적이지 않은 쿼리를 작성할 수 있다.
- JPQL를 통해 엔티티 객체를 중심으로 개발할 수 있다.





# Chapter 2. Persistence Context
1. EntityManagerFactory & EntityManager
- EntityManagerFactory를 통해 고객의 요청이 올 때마다 새로운 EntityManager를 생성한다.
- EntityManager는 내부적으로 데이터베이스 커넥션을 사용해서 데이터베이스와 통신하게 된다.



2. Persistence Context?
- 엔티티를 영구적으로 저장하는 환경이라는 뜻을 가지고 있으며 가시적이지 않은 논리적인 개념이다.
- EntityManager를 통해 영속성 컨텍스트에 접근할 수 있다.
- em.persist(entity); 코드의 경우 실제 데이터베이스에 저장한다는 것이 아니라 영속성 컨텍스트를 통해서
  해당 엔티티를 영속화(영속성 컨텍스트에 저장)한다는 의미를 가지고 있다.

