package com.zme.zmer_blog.dao;

import com.zme.zmer_blog.po.Blog;
import com.zme.zmer_blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: timber
 * @Description: 博客评论DAO接口
 * @Date: 2020/2/22 下午8:40
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /*
     * @Author: timber
     * @Description: 根据博客id查询顶级评论信息列表
     * @Date: 2020/2/22 下午8:43
     * @param blogId 博客id
     * @param sort 排序规则
     * @Return  评论信息列表
     */
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    /*
     * @Author: timber
     * @Description: 根据博客对象删除所有跟当前博客相关联的评论信息
     * @Date: 2020/3/2 上午12:44
     * @param blog
     * @Return
     */
    void deleteAllByBlog(Blog blog);
}
