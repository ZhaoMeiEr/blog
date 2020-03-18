package com.zme.blog.dao;

import com.zme.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: timber
 * @Description: 分类DAO接口
 * @Date: 2020/2/10 上午2:36
 */
public interface TypeRepository extends JpaRepository<Type, Long> {
    /*
     * @Author: timber
     * @Description: 根据分类名称查询分类
     * @Date: 2020/2/10 下午4:07
     * @param name 分类名称
     * @Return
     */
    Type findByName(String name);

    /*
     * @Author: timber
     * @Description: 根据pageable对象查询分类 (pageable 对象中指定了查询条数 排序规则)
     * @Date: 2020/2/15 下午3:13
     * @param pageable
     * @Return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
