package com.demo.service;

import com.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserMapper {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int Add(String root, String user, String pass, String name, String head, String ip) {
        String sql = "INSERT INTO ".concat("`".concat(root).concat("` ")).concat("(user,pass,name,head,ip) ")
                .concat("VALUES ")
                .concat("('".concat(user)).concat("',")
                .concat("'".concat(pass).concat("',"))
                .concat("'".concat(name).concat("',"))
                .concat("'".concat(head).concat("',"))
                .concat("'".concat(ip).concat("');"));
        System.err.println("ADD:" + sql);
        return jdbcTemplate.update(sql);
    }

    @Override
    public int Delete(int id, String root) {
        String sql = "DELETE FROM "
                .concat(root).concat(" WHERE id = ?");
        System.err.println("DELETE:" + sql);
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int Modify(String root, int id, String user, String pass, String name, String head, String ip) {
        String sql = "UPDATE ".concat(root).concat(" SET user=?,pass=?,name=?,head=?,ip=? WHERE id=")
                .concat(String.valueOf(id));
        Object[] objects = {user, pass, name, head, ip};
        System.err.println("MODIFY:" + sql);
        return jdbcTemplate.update(sql, objects);
    }

    @Override
    public List<Map<String, Object>> Check(String root, int id) {
        String sql = "SELECT ".concat("*").concat(" FROM ")
                .concat(root).concat(" WHERE id=")
                .concat(String.valueOf(id));
        System.err.println("CHECK:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> List(String root) {
        String sql = "SELECT * FROM ".concat(root);
        System.err.println("LIST:" + sql);
        return jdbcTemplate.queryForList(sql);
    }
}
