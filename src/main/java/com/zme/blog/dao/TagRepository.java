package com.zme.blog.dao;

import com.zme.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: timber
 * @Description: 标签DAO接口
 * @Date: 2020/2/10 下午6:42
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    /*
     * @Author: timber
     * @Description: 根据标签名查询标签
     * @Date: 2020/2/10 下午6:43
     * @param name 标签名
     * @Return  标签对象
     */
    Tag findByName(String name);

    /*
     * @Author: timber
     * @Description: 根据pageable对象查询标签 (pageable 对象中指定了查询条数 排序规则)
     * @Date: 2020/2/15 下午4:27
     * @param pageable 分页对象
     * @Return
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
