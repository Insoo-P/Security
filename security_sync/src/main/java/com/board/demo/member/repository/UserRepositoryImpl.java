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
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int saveMember(Member member) {
        String sql = "INSERT INTO MEMBER (ID, PW, FULLNAME, EMAIL, ROLES) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, member.getId(), member.getPw(), member.getFullName(), member.getEmail(), member.getRoles());
    }

    @Override
    public int saveRole(Role role) {
        return 0;
    }

    @Override
    public int updateRole(Role role) {
        return 0;
    }

    @Override
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE ID = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    @Override
    public Member findById(String id) {
        try {
            String sql = "SELECT m.*, r.role_name\n" +
                    "FROM MEMBER m\n" +
                    "INNER JOIN ROLES r ON m.role_id = r.id\n" +
                    "WHERE m.id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Member>() {
                @Override
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = new Member();
                    member.setId(rs.getString("ID"));
                    member.setPw(rs.getString("PW"));
                    member.setFullName(rs.getString("FULLNAME"));
                    member.setEmail(rs.getString("EMAIL"));

                    List<Role> roles = new ArrayList<>();
                    do {
                        Role roleName = Role.valueOf(rs.getString("ROLE_NAME"));
                        roles.add(roleName);
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
