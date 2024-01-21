# JPA 이론 학습

# Chapter 1. JPA Basic
1. EntityManager는 애플리케이션 로딩 시점에 1개만 생성해서 모든 영역에서 공유하며 사용한다.
2. EntityManager는 스레드 간 공유하지 않는다.
3. JPA 내부에서의 데이터 수정은 Transaction 내부에서 진행된다.</br></br></br>


4. JPQL(Java Persistence Query Language)
- 실제 물리적 데이터베이스 테이블이 아닌 엔티티 객체를 대상으로 하는 객체지향 쿼리 언어를 의미힌다.
- 특정 데이터베이스 테이블을 대상으로 쿼리를 날리면 해당 데이터베이스에 종속적인 설계로 이루어진다 하지만 JPQL를
  사용함으로써 특정 DB에 종속적이지 않은 쿼리를 작성할 수 있다.
- JPQL를 통해 엔티티 객체를 중심으로 개발할 수 있다.</br></br></br></br>





# Chapter 2. Persistence Context
1. EntityManagerFactory & EntityManager
- EntityManagerFactory를 통해 고객의 요청이 올 때마다 새로운 EntityManager를 생성한다.
- EntityManager는 내부적으로 데이터베이스 커넥션을 사용해서 데이터베이스와 통신하게 된다.</br></br></br>



2. Persistence Context?
- 엔티티를 영구적으로 저장하는 환경이라는 뜻을 가지고 있으며 가시적이지 않은 논리적인 개념이다.</br></br>
- EntityManager를 통해 영속성 컨텍스트에 접근할 수 있다.</br></br>
- em.persist(entity); 코드의 경우 실제 데이터베이스에 저장한다는 것이 아니라 영속성 컨텍스트를 통해서
  해당 엔티티를 영속화(영속성 컨텍스트에 저장)한다는 의미를 가지고 있다.</br></br></br>



3. 엔티티의 생명주기</br>
(1) 비영속(new/transient) 상태
- 영속성 컨텍스트와 전혀 연관 없는 상태</br></br>
(2) 영속(managed) 상태
- 영속성 컨텍스트에 의해 관리되는 상태</br></br>
(3) 준영속(detached) 상태
- 영속성 컨텍스트에서 저장되었다가 분리된 상태</br></br>
(4) 삭제(Removed) 상태
- 삭제된 상태</br></br></br>


4. Persistence Context의 이점</br>
4-1. 1차 캐시</br></br
(1) 엔티티를 영속화하거나 처음 조회한다면 영속성 컨텍스트 내부에 1차 캐시로 해당 엔티티에 대한 스냅샷 정보를 저장해둔다. </br></br>
(2) 1차 캐시에 엔티티가 없다면 직접 데이터베이스를 조회하여 관련 엔티티를 1차 캐시에 저장한다.</br></br></br>


4-2. 영속 엔티티의 동일성 보장</br>
(1) JPA가 동일 트랜잭션 내부에서 영속화된 엔티티의 동일성을 보장해준다. 마치 자바 컬렉션에서 똑같은 참조 주소를 가지고 있는 객체에 대해 == 비교를 한 것과 동일한 상황이다.</br></br>


4-3. 트랜잭션을 지원하는 쓰기 지연</br>
(1) 영속성 컨텍스트 내부엔 쓰기 지연 저장소라는 공간이 존재한다. </br></br>
(2) 엔티티가 영속화되는 순간 연관된 INSERT SQL를 쓰기 지연 저장소에 보관한다. </br></br>
(3) 트랜잭션을 커밋하는 순간 flush가 발생되어 쓰기 지연 저장소에 보관되어 있던 INSERT SQL이 실제 DB로 전송되고 커밋이 진행된다. </br></br></br>


4-4. 엔티티 수정 시 발생하는 변경 감지(Dirty checking)</br>
(1) 트랜잭션 커밋 시점에 내부적으로 Flush가 호출된다.</br></br>
(2) Flush가 발생하면, 해당 엔티티의 초기 스냅샷(영속성 컨텍스트에 처음 영속화됐을 당시의 데이터)과 엔티티의 수정 사항을 비교한다.</br></br>
(3) 엔티티의 스냅샷과 수정 사항을 비교해서 변경사항이 존재한다면 쓰기 지연 저장소에 UPDATE SQL를 저장해두고 이를 실제 DB에 보내면서 커밋이 진행된다.</br></br>
(4) 이러한 일련의 과정들을 변경 감지(Dirty checking)이라고 한다.</br></br></br></br></br>




5. Flush(플러시)란 무엇일까?</br>
(1) 플러시는 영속성 컨텍스트의 변경 내용과 실제 물리적 데이터베이스의 내용이 일치하도록 반영하는 작업을 의미하고 있다.</br></br>
(2) 일반적으로 데이터베이스 커밋 시점에 플러시가 발생하게 된다.</br></br>
(3) 트랜잭션이라는 작업 단위가 중요하고, 커밋 직전에만 데이터베이스와 동기화해주면 된다는 특징이 있다.</br></br></br>


5-1. 영속성 컨텍스트를 플러시하는 방법?</br>
(1) em.flush()</br></br>
(2) 트랜잭션 커밋 시점</br></br>
(3) JPQL 쿼리를 날린 경우 </br></br></br>


5-2. 플러시 모드 옵션</br>
(1) FlushModeType.AUTO (기본값)
- 커밋, 쿼리를 실행할 때 플러시 실행 </br></br>

(2) FlushModeType.COMMIT
- 커밋 시점에서만 플러시 실행</br></br></br></br></br>




6. 준영속 상태</br>
(1) 영속 상태의 엔티티가 영속성 컨텍스트에서 분리되는 것을 말한다. 이럴 경우 영속성 컨텍스트가 제공하는 기능(변경 감지 등)을 사용할 수 없게 된다. </br></br>

6-1. 준영속 상태로 전환하는 방법</br>
(1) em.detach(entity);
- 특정 엔티티만 준영속 상태로 전환 </br></br>
(2) em.clear();
- 영속성 컨텍스트를 완전히 초기화</br></br>
(3) em.close();
- 영속성 컨텍스트를 종료한다.</br></br></br></br></br>






# Chapter 3. Entity Mapping
JPA에서 중요한 부분 2가지 : JPA 동작 방식(영속성 컨텍스트) 이해, 설계적인 측면에서 객체와 데이터베이스 테이블을 정확히 매핑하는 것 </br></br>
객체와 테이블 매핑 : @Entity, @Table</br>
필드와 컬럼 매핑 : @Column</br>
기본 키 매핑 : @Id</br>
연관관계 매핑 : @ManyToOne, @JoinColumn ... 등</br></br>

6-1. 객체와 테이블 매핑</br>
(1) @Entity가 붙은 클래스는 JPA가 관리하는 엔티티라고 부른다.</br></br>
(2) JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 어노테이션이 필수이다.</br></br>
(3) JPA 스펙 상, 기본 생성자(public, protected)가 필수적이다.</br></br></br></br>






# Chapter 4. 연관관계 Mapping : 1:1 1:n n:1 n:n
(1) 연관관계 매핑에서 가장 중요한 것? : 객체와 테이블 연관관계의 차이를 이해, 객체의 참조와 테이블 외래 키를 정확히 매핑하는 것</br></br>
(2) 연관관계란? : 객체 또는 테이블이 서로 논리적인 의미를 갖고 양쪽을 서로 참조하는 것을 말함</br></br>
(3) 주요 용어 : 단방향, 양방향,  1:1 1:n n:1 n:n, 연관관계의 주인 설정</br></br></br></br>



4-1. 객체를 테이블에 맞추어 데이터 중심으로 모델링 시 객체 간의 협력 관계를 만들 수 없다.</br></br>
(1) 테이블은 외래 키로 JOIN을 통해 연관 테이블을 탐색한다.</br></br>
(2) 하지만 객체는 참조를 사용해서 연관된 객체를 탐색하게 된다(객체와 테이블 간의 패러다임 불일치라는 큰 간격이 발생한다)</br></br>
(3) 어떻게 해결할 수 있을까? : 객체의 참조와 데이터베이스 테이블의 외래 키를 매핑해서 연관관계를 설정함으로써 패러다임 불일치를 해결한다.</br></br></br></br>



4-2. 연관관계의 주인 : mappedBy </br></br>
(1) 객체와 테이블 간의 연관관계를 맺는 차이를 이해해야 함</br></br>
(2) 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개가 존재하는 것</br></br>
(3) 양방향 연관관계에서는 객체의 두 관계 중 하나를 연관관계의 주인으로 설정해야 한다. </br></br>
(4) 연관관계의 주인이 된 객체만이 외래 키를 관리(등록, 수정)한다. 주인이 아닌 쪽은 조회만 가능.</br></br>
(5) mappedBy가 적용된 객체는 조회만 가능, 등록, 수정, 삭제의 경우 연관관계의 주인이 된 객체에서만 가능하다.</br></br></br></br>



4-3. 누구를 연관관계의 주인으로 설정할까?</br></br> 
(1) 외래 키를 가지고 있는 객체를 연관관계의 주인으로 설정한다. (1:N 관계에서 N을 가지는 객체)</br></br></br></br>




4-4. 양방향 연관관계 주의사항</br></br>
(1) 순수 객체 상태를 고려해서 객체 양방향에 값을 설정</br></br>
(2) 1 또는 N쪽에 연관관계 편의 메서드를 별도로 설정한다.</br></br>
(3) 양방향 매핑 시 무한 루프를 주의한다.</br></br></br></br>



4-5. 양방향 매핑 정리 </br></br>
(1) 단방향 매핑 관계를 설정하는 것만으로도 사실상 연관관계 매핑은 완료된다.</br></br>
(2) 양방향 매핑은 단지 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐이다.</br></br>
(3) 단방향 매핑을 올바르게 지정하고 이후 필요 시 양방향 매핑을 추가한다.</br></br>
(4) 핵심은 단방향 매핑을 올바르게 지정하는 것이다. 실무에서는 복잡한 JPQL(조회)을 많이 설계해야 하는 일이 있는데 이럴 때 단방향 연관관계보다 양방향 연관관계가 필요한 경우가 많다.
가능하면 최대한 단방향으로 설계하며 코드를 조금 더 객체지향적으로 설계해야 하는 일이 생기다 보면 비즈니스상 양방향 연관관계가 더 수월한 경우가 있을 때
양방향 연관관계를 설정한다. </br></br></br></br>




4-6. 연관관계의 주인을 정하는 기준</br></br>
(1) 비즈니스 로직을 기준으로 정하는 것이 외래 키의 위치를 기준으로 정해야 한다.</br></br>
(2) JPA보다 중요한 부분은 사실상 관계형 데이터베이스의 근본적인 이해가 선행되어어 한다.</br></br></br></br></br>






# Chapter 5. 다양한 연관관계 Mapping</br>
5-1. 연관관계 매핑 시 고려할 사항 3가지</br>
(1) 다중성 : @ManyToOne, @OneToMany, @OneToOne, @ManyToMany (JPA의 어노테이션은 DB와의 매핑을 위해 사용된다. 데이터베이스의 다중성 관점에서 생각한다.)</br></br></br>


(2) 단방향과 양방향</br>
- 테이블 : 외래 키 하나로 양쪽을 모두 JOIN 할 수 있다. 따라서 방향이라는 개념이 존재하지 않는다.</br></br>
- 객체 : 참조 필드가 있는 쪽으로만 참조가 가능하다. 한 쪽만 참조하면 단방향, 양쪽이 서로 참조하면 양방향 관계(단방향이 양 쪽으로 모두 존재하는 것) </br></br></br>


(3) 연관관계의 주인</br>
- 테이블은 외래 키 하나로 두 테이블이 모두 연관관계를 구성한다.</br></br>
- 객체의 양방향 관계는 A -> B, B -> A처럼 참조 영역이 2개</br></br>
- 객체의 양방향 관계는 참조가 2개 존재, 둘 중 테이블의 외래 키를 관리할 곳을 지정해야 한다.</br></br>
- 연관관계의 주인은 외래 키를 관리하는 참조를 가진 객체가 연관관계의 주인이 된다.</br></br>
- 주인의 반대편은 외래 키에 영향을 주지 않고 단순 조회만 가능하다.</br></br></br></br>




5-2. 다대일(N:1) 단방향</br>
(1) 테이블 설계상, N쪽에 외래 키가 존재해야 한다.</br></br>
(2) 외래 키가 존재하는 곳에 참조를 걸고 연관관계 매핑을 설정해 준다.</br></br>
(3) 가장 많이 사용되는 연관관계로 반대는 1:N</br></br></br></br>



5-3. 다대일(N:1) 양방향</br>
(1) 외래 키가 존재하는 곳이 연관관계의 주인이 된다.(N쪽)</br></br>
(2) 양쪽을 서로 참조하도록 개발할 때 필요하다.</br></br></br></br>



5-4. 일대다(1:N) 단방향</br>
(1) 해당 모델은 실무에서 대부분 권장되지 않는 매핑 방법</br></br>
(2) 실무에서는 수 십개 이상의 테이블이 엮여서 돌아가는데 의도하지 않은 테이블에서 뜻 밖의 쿼리가 발생할 수 있다.</br></br>
(3) 따라서 다대일 단방향 매핑을 기준으로 필요하면 양방향을 추가하는 전략으로 개발한다.</br></br>
(4) 정리를 하자면 1이 연관관계의 주인으로 설정되지만 실제 외래 키는 N에 존재한다.</br></br>
(5) 단점으로는 엔티티가 관리하는 외래 키가 다른 테이블에 존재한다는 것이고, 연관관계 관리를 위해 추가적인 UPDATE SQL이 실행된다.</br></br>
(6) 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하는 것이 권장된다.</br></br></br></br>



5-5. 일대다(1:N) 양방향</br>
(1) JPA 스펙상 공식적으로는 존재하지 않는 매핑 방법</br></br>
(2) @JoinColumn(insertable = false, updatable = false) 사용</br></br>
(3) 읽기 전용 필드를 사용해서 양방향처럼 사용하는 방법이지만 권장되지 않음, 다대일 양방향 매핑을 사용하는 것이 권장된다.</br></br></br></br>




5-6. 일대일(1:1) 단방향</br>
(1) 1:1 관계의 반대도 항상 1:1이 성립한다.</br></br>
(2) 다대일 관계의 단방향 매핑과 유사한 매핑 방법 </br></br></br></br>




5-7. 다대다(N:M) </br>
(1) 실무에서 가급적이면 사용되지 않는 매핑 방법 </br></br>
(2) 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 풀어낼 수 없음.</br></br>
(3) 중간에 연결 테이블을 두어 일대다, 다대일 관계로 풀어내야 함</br></br></br>

(4) 하지만 객체는 컬렉션을 사용해서 객체 2개로 다대다 관계로 풀어낼 수 있다.</br></br>
(5) @ManyToMany, @JoinTable 사용</br></br>
(6) 매우 편리해 보이지만 실무에서 사용할 수 있는 매핑 방법이 아니다.</br></br>
(7) 실무에선 단순 Hello world 수준이 아닌 적어도 수 십개의 테이블들이 복잡하게 연결되어 돌아간다 </br></br>
(8) 중간 연결 테이블에는 실시간 추가되는 데이터를 넣을 수 없고, 의도치 않은 쿼리가 나갈 수 있다.</br></br></br>

(9) 따라서 이러한 한계는 중간 연결 테이블을 엔티티로 승격해서 사용한다.</br></br>
(10) @ManyToMany -> @OneToMany + @ManyToOne</br></br></br></br></br></br>









# Chapter 7. 프록시와 연관관계 관리(Lazy loading, Eager loading, 영속성 CASCADE 등)</br>
7-1. 프록시</br>
(1) 이후에 나올 지연 로딩(Lazy loading)을 이해하기 위해 프록시를 반드시 이해해야 한다.</br></br>
(2) em.find() : 데이터베이스를 통해서 실제 엔티티를 조회한다.</br></br>
(3) em.getReference() : 데이터베이스 조회를 미루는 프록시(가짜) 엔티티 객체를 조회한다.</br></br></br></br>



7-2. 프록시 객체의 초기화 과정</br>
(1) em.getReference()를 통해 가져온 프록시 객체에 대해 영속성 컨텍스트를 통해서 초기화를 요청하면 영속성 컨텍스트에서
실제 데이터베이스에 조회 쿼리를 날려서 실제 객체를 만들어내는 과정을 프록시 객체의 초기화라고 한다.</br></br>

(2) 초기 프록시 객체 내부엔 target 참조값이 비어있기 때문에 처음 em.getReference()를 통해 가져온 값 말고도 다른 값을 
조회하려면 target값이 반드시 있어야 한다. 해당 target값을 얻기 위해 프록시 객체를 초기화하는 과정을 거치게 된다.</br></br>

(3) 이후 참조값을 얻은 상태이기 때문에 해당 참조값을 통해 실제 객체의 다른 값도 얻어올 수 있는 것이다.</br></br></br></br>




7-3. 프록시 객체의 특징 </br>
(1) 실제 클래스를 상속받아서 만들어진다.</br></br>
(2) 실제 클래스와 겉 모양은 동일하다.</br></br>
(3) 이러한 프록시 객체는 실제 객체의 참조(target)을 보관하고 있다.</br></br></br>

(4) 프록시 객체는 실제 사용될 때 한 번만 초기화한다.</br></br>
(5) 프록시 객체 초기화할 때, 프록시 객체가 실제 엔티티로 변환되는 것은 아니고 초기화되면 프록시 객체의 참조값을 통해
실제 엔티티에 접근하는 개념이다.</br></br>
(6) 프록시 객체는 원본 객체를 상속받는다. 따라서 타입 체크 시 == 대신 instanceof를 사용한다.</br></br></br>

(7) 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티를 반환하게 된다.</br>
- 이유1 : 영속성 컨텍스트에 실제 객체가 있다면 실제 객체를 반환하는 것이 최적화 입장에서 더 유리하기 때문</br>
- 이유2 : (중요) jpa에서는 동일 트랜잭션 내부에서 == 조회 시 true값 반환을 보장하기 때문</br></br>

(8) 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때 프록시 초기화 시 문제가 발생한다.</br>
Hibernate의 경우 org.hibernate.LazyInitializationException 발생</br></br></br></br>




7-5. 영속성 전이, 고아 객체</br>
(1) 영속성 전이 </br>
- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만드는 것을 영속성 CASCADE라고 한다.</br>
- ex) 부모 엔티티를 영속화할 때 자식 엔티티도 함께 영속화하는 것.</br>
- 연관관계 매핑과 아무런 연관이 없다. 엔티티 영속화 시 연관 엔티티도 함께 영속화하는 편리함을 제공할 뿐이다.</br></br>

(2) 영속성 전이의 종류 
- cascade = CascadeType.ALL : 모두 적용 </br>
- cascade = CascadeType.PERSIST : 영속화 </br>
- cascade = CascadeType.REMOVE : 삭제 </br> </br>

(3) 영속성 전이를 그래서 언제 사용하는데?
- 부모 엔티티가 모든 자식들을 관리할 때 의미가 있다.</br>
- 게시판, 첨부파일, 첨부파일의 데이터, 경로 등 서로의 연관성이 있을 때 사용 </br>
- 단일 엔티티에 대해 종속적일 때, 객체 간의 라이프 사이클이 동일할 때 (등록, 수정 삭제 시), 특정 객체를 소유하는 객체가 1개일 때 사용할 수 있다. </br></br></br>




7-6. 고아 객체</br>
(1) 고아 객체의 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제하는 것</br>
- 옵션 : orphanRemoval = true </br></br>

(2) 고아 객체의 주의사항</br>
- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능이다.</br>
- 마찬가지로 참조하는 곳이 하나일 때 사용한다. </br>
- 특정 엔티티가 개인 소유할 때 사용한다 (중요). </br>
- 개념적으로 부모 객체 제거 시 해당 자식 객체는 고아 상태가 된다. 해당 기능 활성화 시 부모를 제거하면
  자식도 함께 제거되는데 cascade에서 CascadeType.REMOVE처럼 동작한다.</br></br>


(3) 영속성 전이 + 고아 객체, 생명 주기</br>
- cascade = CascadeType.ALL + orphanRemoval = true </br>
- 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거한다.</br>
- 두 옵션을 모두 활성화해서 부모 엔티티를 통해 자식의 생명주기를 관리할 수 있다.</br></br></br>





# Chapter 8. 값 타입, 임베디드 타입 </br>
8-1. 값 타입 , 임베디드 타입
