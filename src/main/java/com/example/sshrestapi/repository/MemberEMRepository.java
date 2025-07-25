package com.example.sshrestapi.repository;

import com.example.sshrestapi.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberEMRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            em.persist(member);       // 신규 등록
            return member;
        } else {
            return em.merge(member);  // 변경 감지 후 업데이트
        }
    }

    public boolean existsById(Long id) {
        Long cnt = em.createQuery(
                        "SELECT COUNT(m) FROM Member m WHERE m.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return cnt > 0;
        // em.find(Member.class, id) != null;
    }

    @Transactional
    public void deleteById(Long id) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            em.remove(member);
        }
    }
}
