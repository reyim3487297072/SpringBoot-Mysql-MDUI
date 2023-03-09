package com.demo.mapper;

import com.demo.entity.User;
import com.demo.util.ResultJson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 添加
     *
     * @param user
     * @param pass
     * @param name
     * @param head
     * @param ip
     * @return
     */
    int Add(String root, String user, String pass, String name, String head, String ip);

    /**
     * 删除
     *
     * @param id
     * @param root
     * @return
     */
    int Delete(int id, String root);

    /**
     * 修改
     *
     * @param id
     * @param user
     * @param pass
     * @param name
     * @param head
     * @param ip
     * @return
     */
    int Modify(String root, int id, String user, String pass, String name, String head, String ip);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> Check(String root, int id);

    /**
     * 列表
     *
     * @param root
     * @return
     */
    List<Map<String, Object>> List(String root);

}
