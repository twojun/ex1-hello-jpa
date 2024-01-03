package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // transaction start : jpa 내부 동작은 트랜잭션 범위 내에서 발생되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 추가
            Member member = new Member();
            member.setId(3L);
            member.setName("helloC");
            em.persist(member);

            // 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            // 삭제
//            em.remove(findMember);

            // 수정
            Member findMember2 = em.find(Member.class, 2L);
            findMember2.setName("HelloBBB");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
