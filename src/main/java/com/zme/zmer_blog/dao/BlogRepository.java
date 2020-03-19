package com.zme.zmer_blog.dao;

import com.zme.zmer_blog.po.Blog;
import com.zme.zmer_blog.po.Tag;
import com.zme.zmer_blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: timber
 * @Description: 博客DAO接口
 * @Date: 2020/2/11 下午4:11
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    /*
     * @Author: timber
     * @Description: 根据博客分类删除博客
     * @Date: 2020/2/15 下午4:38
     * @param type
     * @Return
     */
    void deleteAllByType(Type type);

    /*
     * @Author: timber
     * @Description: 根据包含的博客标签删除博客
     * @Date: 2020/2/15 下午4:37
     * @param tag
     * @Return
     */
    void deleteAllByTagsIsContaining(Tag tag);

    /*
     * @Author: timber
     * @Description: 根据pageable对象查询博客 (pageable 对象中指定了查询条数 排序规则)
     * @Date: 2020/2/15 下午4:37
     * @param pageable
     * @Return
     */
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    /*
     * @Author: timber
     * @Description: 根据关键词检索博客分页列表
     * @Date: 2020/2/16 下午1:47
     * @param query 关键词
     * @param pageable 分页对象
     * @Return  博客分页列表
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    /*
     * @Author: timber
     * @Description: 更新博客浏览次数
     * @Date: 2020/2/22 下午10:10
     * @param id 博客id
     * @Return
     */
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    /*
     * @Author: timber
     * @Description: 查询博客年份倒序列表
     * @Date: 2020/2/28 下午9:18
     * @param
     * @Return 博客年份列表
     */
    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by year desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);
}
