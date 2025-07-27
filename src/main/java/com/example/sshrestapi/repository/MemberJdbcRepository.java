package com.example.sshrestapi.repository;

import com.example.sshrestapi.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        List<Member> result = jdbcTemplate.query(sql, memberRowMapper(), id);
        return result.stream().findFirst();
    }

    public Member save(Member member) {
        String sql = "INSERT INTO member (name, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getName(), member.getEmail());

        // 가장 최근 insert된 row 조회 (H2 DB 기준)
        return jdbcTemplate.queryForObject(
                "SELECT * FROM member ORDER BY id DESC LIMIT 1", memberRowMapper());
    }

    public Member update(Long id, Member updated) {
        String sql = "UPDATE member SET name = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, updated.getName(), updated.getEmail(), id);
        return findById(id).orElseThrow(() -> new RuntimeException("업데이트 실패"));
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM member WHERE id = ?", id);
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM member WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .build();
    }
}
