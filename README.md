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
8-1. JPA의 데이터 타입 분류(엔티티 타입) </br>
(1) JPA는 데이터 타입을 최상위 레벨로 보면 엔티티 타입과 값 타입으로 분류된다. </br></br>
(2) 엔티티 타입은 @Entity로 정의하는 객체를 말함</br></br>
(3) 엔티티 타입은 데이터가 변경되어도 식별자로 지속해서 추적 가능하다.</br></br></br>


8-2. JPA의 데이터 타입 분류(값 타입) </br>
(1) int, Integer, String처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체를 말한다.</br></br>
(2) 식별자가 존재하지 않으며 변경 시 추적 불가능하다.</br></br></br>



8-3. 값 타입의 분류 </br>
(1) 기본 값 타입 : 자바 기본 값 타입(int, double), 래퍼 클래스(Integer, Long), String</br></br>
(2) 임베디드 타입(복합 값 타입)</br></br>
(3) 컬렉션 값 타입</br></br></br>



8-4. 기본 값 타입 </br>
(1) 기본 값 타입은 생명주기를 엔티티 생명주기에 의존한다.</br>
- ex) 회원을 삭제하면 회원과 관련된 이름, 나이 필드도 함께 삭제된다.</br></br>

(2) 값 타입은 공유하면 안 된다.</br>
- 예를 들어 회원 이름 변경 시 다른 회원의 이름도 변경되면 안 된다.</br></br>

(3) 참고</br>
- 자바의 기본 타입(int, double ...등)은 절대 공유되지 않는다.</br>
- 기본 타입은 항상 값을 복사한다.</br>
- 래퍼 클래스, String과 같은 클래스들은 객체의 참조를 복사하기 때문에 서로 공유될 수 있는 성질을 가진다. </br></br></br>




8-5. 임베디드 타입</br>
(1) 새로운 값 타입을 직접 정의할 수 있다.</br></br>
(2) JPA에서는 임베디드 타입이라고 한다.</br></br>
(3) 주로 기본 값 타입을 모아서 복합 값 타입이라고도 부를 수 있다.</br></br>
(4) int, String처럼 임베디드 타입도 값 타입이다. 특정한 엔티티가 아니기 때문에 따로 추적할 수 없고 값 변경이 가능하다.</br></br>
(5) @Embeddable - 임베디드 타입을 정의하는 클래스(Default constructor 필수), @Embedded - 임베디드 타입을 사용하는 클래스</br></br></br>



8-6. 임베디드 타입의 장점</br>
(1) 재사용성이 좋고 높은 응집도를 가질 수 있게 된다. </br></br>
(2) 값 타입만 사용하는 의미 있는 메서드를 정의할 수 있다.</br></br>
(3) 임베디드 타입을 포함한 모든 값 타입은 값 타입을 소유한 엔티티에 생명주기를 의존하는 특징을 가진다. </br></br>
(4) 데이터베이스 테이블 입장에서는 테이블은 데이터를 잘 관리하는 것이 목적이다. 하지만 객체는 데이터(필드)뿐만 아니라
기능(메서드, 행위)까지 모두 가지고 있기 때문에 임베디드 타입으로 값들을 묶어줌으로써 가지는 장점이 많아진다.</br></br></br></br>




8-7. 임베디드 타입과 테이블 매핑</br>
(1) 임베디드 타입은 단지 엔티티의 값일 뿐이다.</br></br>
(2) 중요한 부분은 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.</br></br>
(3) 하지만 이를 통해 객체와 테이블을 아주 세밀하게(find-grainted) 매핑하는 것이 가능하다.</br></br>
(4) 임베디드 타입의 값이 null이면 매핑한 컬럼의 값은 모두 null로 설정된다.</br></br></br></br>



8-8. 한 엔티티에서 같은 값 타입을 사용한다면?</br>
(1) @AttributeOverride 어노테이션을 사용한다.</br></br>
(2) @AttributeOverrides, @AttributeOverride를 사용해서 컬럼명의 속성을 재정의할 수 있다.</br></br></br></br>



8-9. 값 타입과 불변 객체</br>
(1) 값 타입은 복잡한 객체 세상을 조금이라도 단순화하기 위해 만든 개념이다 따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다.</br></br>
(2) 임베디드 타입과 같은 값 타입을 여러 엔티티에서 공유하게 되면 side effect가 발생되어 위험하다(side effect로 생긴 버그는 대부분 추적하기 어렵다).</br></br></br></br>



8-10. 값 타입 복사, 객체 타입의 한계</br>
(1) 위에서 확인한 것처럼, 값 타입의 실제 인스턴스를 공유하는 것은 위험하다.</br></br>
(2) 따라서 값(인스턴스)을 복사해서 사용한다.</br></br>
(3) 값을 항상 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.</br></br>
(4) 문제는 임베디드 타입처럼 직접 정의한 값은 자바의 기본 타입이 아닌 객체 타입이라는 것이다.</br></br>
(5) 자바 기본 타입에 값을 대입하면 값을 복사하게 된다.</br></br>
(6) 객체 타입은 참조값을 직접 대입하는 것을 막을 방법이 존재하지 않기 때문에 객체의 공유 참조는 피할 수 없는 문제이다.</br></br></br></br>



8-11. 이 문제를 해결하기 위해서는? : 불변 객체로 설계한다.</br>
(1) 이러한 문제를 해결하기 위해 객체 타입을 수정할 수 없게 설계하면 side effect를 막을 수 있다.</br></br>
(2) 불변 객체(immutable object)는 생성 시점(초기화 시점) 이후부터는 값을 변경할 수 없는 객체를 말한다. </br></br>
(3) 생성자를 통해 초기화 시점에만 값을 설정하고 Setter 메서드를 만들지 않으면 된다.</br></br>
(4) Integer, String과 같은 객체들은 자바에서 기본적으로 제공하고 있는 불변 객체다.</br></br>
(5) 결론은 불변이라는 작은 제약으로 큰 Side effect를 막을 수 있다.</br></br></br></br>



8-12. 값 타입 비교하기</br>
(1) 값 타입은 인스턴스가 다르더라도 내부 값이 동일하면 같은 것으로 봐야 한다.</br></br>
(2) 동일성(identity) 비교 : 인스턴스의 참조값(주소값)을 비교한다. == 사용</br></br>
(3) 동등성(equivalence) 비교 : 인스턴스의 값 자체를 비교한다. equals() 사용</br>
(4) 따라서 값 타입의 equals() 메서드를 적절하게 재정의해야 한다 (기본적으로 Object 메서드의 equals()는 참조값을 비교하기 떄문). </br></br></br></br>



8-13. 값 타입 컬렉션</br>
(1) 값 타입 컬렉션이랑 값 타입을 컬렉션에 담아서 사용하는 것을 말하고 값 타입을 하나 이상 저장할 때 사용한다.</br></br>
(2) @ElementCollection, @CollectionTable을 사용해서 매핑한다.</br></br>
(3) 데이터베이스는 컬렉션 같은 자료구조를 테이블에 담을 수 없다. 따라서 컬렉션을 저장하기 위한 별도의 테이블이 필요하고,
일대다로 풀어서 별도의 테이블을 생성해야 하는데 이때  @ElementCollection, @CollectionTable을 사용한다.</br></br></br></br>



8-14. 값 타입 컬렉션 사용, 제약사항</br>
(1) 값 타입 조회 시, 값 타입도 지연 로딩 전략을 사용한다는 것을 확인할 수 있다.</br></br>
(2) 값 타입 수정 시, 중요한 건 새로운 인스턴스로 갈아끼워야 한다는 점이다.</br></br>
(3) 값 탕비은 엔티티와 다르게 식별자가 존재하지 않아서 값 변경 시 추적이 어렵다.</br></br>
(4) (중요) 값 타입 컬렉션에 변경사항 발생 시 주인 엔티티와 관련된 모든 데이터를 삭제하고 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.</br>
- 이 동작은 어떻게 보면 매우 위험한 부분이다. 따라서 아래와 같은 값 타입 컬렉션에 대한 대안이 있다.</br></br></br>

(5) 값 타입 컬렉션 대안 </br>
- 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 사용하는 것을 고려해볼 수 있다.</br></br>
- 일대다 관계를 위한 엔티티를 생성하고 여기에서 값 타입을 사용하는 것. </br></br>
- 영속성 전이 + 고아 객체 제거를 사용해서 값 타입 컬렉션처럼 사용 </br></br></br>

(6) 값 타입 컬렉션은 언제 쓸까? </br>
- 예를 들어, select box에 사용자가 좋아하는 옵션을 멀티로 체크할 수 있게끔 설계해야 할 때 이러한 단순한 용도의 비즈니스가
  필요할 때, 값이 바뀌어도 업데이트 쿼리가 나가지 않는 등의 단순한 상황, 추적이 필요하지 않은 단순한 도메인에서 사용한다.</br></br>
- 값 타입은 정말 값 타입이라고 사용될 때만 사용한다.</br></br>
- 엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 설계하지 않도록 한다.</br></br>
- 식별자가 필요하고 지속해서 값을 추적해야 한다면 그것은 값 타입이 아닌 엔티티다.</br></br></br>
  

(7) 정리 </br>
- 엔티티 타입의 특징</br>
a) 식별자가 존재하고 생명주기를 관리할 수 있다.</br></br>

- 값 타입의 특징</br>
a) 식별자가 존재하지 않고 생명주기가 엔티티에 의해 관리된다.</br>
b) 공유하지 않고 복사해서 사용하는 방법이 안전하다.</br>
c) 불변 객체로 설계해야 한다.</br></br></br></br></br>






# Chapter 9. 객체지향 쿼리 언어 I</br>
9-1. 소개</br>
(1) JPA는 쿼리를 다양하게 작성할 수 있는 방법을 지원한다.</br>
- JPQL(Java Persistence Query Language) : 엔티티 객체 자체를 대상으로 쿼리를 날릴 수 있는 객체지향 쿼리 언어를 말한다.
- JPA Criteria : 자바 코드로 쿼리를 짜서 JPQL를 빌드해주는 제너레이터 클래스의 모음 </br>
- QueryDSL</br> : 자바 기반의 타입 세이프한 도메인 특화 라이브러리로 실제 자바 코드로 쿼리를 쉽게 작성할 수 있게 해주며 런타임 시점에 타입 체크를 수행해 오류를 방지하는 장점이 있다. </br>
- Native SQL : JPA를 쓰더라도 DB에 종속적인 제한된 쿼리가 필요할 수도 있다. 그러한 상황에서 사용한다.</br>
- JDBC Template api를 직접 사용하는 것(SpringJdbcTemplate와 함께 사용된다.)</br></br></br>



9-2. JPQL</br>
(1) JPA는 일반 SQL을 표준화한 데이터베이스 테이블에 독립적인 엔티티 객체를 중심으로 개발할 수 있는 객체지향 쿼리 언어를 제공하는데 이를 JPQL이라고 한다.</br></br>
(2) 조회 시에도 테이블이 아닌 객체를 대상으로 검색한다.</br></br></br></br>


9-3. JPA Criteria</br>
(1) 문자가 아닌 실제 자바 코드로 JPQL을 빌드해주는 제너테이터 라이브러리</br>
(2) JPA 공식 스펙이지만 명확한 단점으로는 너무 복잡하고 실용성이 없다.</br>
(3) JPA Criteria 대신에 QueryDSL 사용이 권장된다.</br></br></br></br>


9-4. QueryDSL
(1) jpa criteria와 유사하게 자바 기반의 타입 세이프한 도메인 특화 라이브러리로 자바 코드로 JPQL을 작성할 수 있다.</br>
(2) 자바 코드로 작성되기 때문에 오류를 잡을 수 있다.</br>
(3) 동적 쿼리 작성이 수월하고 직관적이다.</br>
(4) 확실히 사용하기 위해 JPQL에 대한 학습이 선행되어야 한다. </br>
(5) 실무에서 사용이 권장된다.</br></br></br></br>
