package com.zme.zmer_blog.service;

import com.zme.zmer_blog.po.Comment;

import java.util.List;

/**
 * @Author: timber
 * @Description: 博客评论Service接口
 * @Date: 2020/2/22 下午8:45
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
