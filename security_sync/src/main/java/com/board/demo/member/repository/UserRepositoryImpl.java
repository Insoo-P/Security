package com.board.demo.member.repository;
import com.board.demo.security.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.board.demo.security.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int saveMember(Member member) {
        String sql = "INSERT INTO MEMBER (MEMBER_ID, PW, FULLNAME, EMAIL) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, member.getId(), member.getPw(), member.getFullName(), member.getEmail());
    }

    @Override
    public int saveRole(Role role) {
        String seqSql = "SELECT NEXT VALUE FOR ROLES_SEQ";
        long rolesId = jdbcTemplate.queryForObject(seqSql, Long.class);

        String sql = "INSERT INTO ROLES (ROLES_ID, MEMBER_ID, ROLE) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, rolesId, role.getId(), role.getRole());
    }

    @Override
    public int updateRole(Role role) {
        return 0;
    }

    @Override
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    @Override
    public Member findById(String id) {
        try {
            String sql = "SELECT m.*, r.role\n" +
                    "FROM MEMBER m\n" +
                    "INNER JOIN ROLES r ON m.member_id = r.member_id\n" +
                    "WHERE m.member_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Member>() {
                @Override
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = new Member();
                    member.setId(rs.getString("MEMBER_ID"));
                    member.setPw(rs.getString("PW"));
                    member.setFullName(rs.getString("FULLNAME"));
                    member.setEmail(rs.getString("EMAIL"));

                    Set<String> roles = new HashSet<>();
                    do {
                        roles.add(rs.getString("ROLE_NAME"));
                    } while (rs.next());

                    member.setRoles(roles);
                    return member;
                }
            });
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int updateMember(Member member) {
        return 0;
    }
}
