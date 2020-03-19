package com.zme.zmer_blog.service.impl;

import com.zme.zmer_blog.dao.CommentRepository;
import com.zme.zmer_blog.po.Comment;
import com.zme.zmer_blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @authon timber
 * @description 博客评论Service实现类
 * @date 2020/2/22 下午8:48
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * @Author: timber
     * @Description: 根据博客id查询评论信息列表
     * @Date: 2020/2/22 下午9:21
     * @param blogId 博客id
     * @Return  评论信息列表
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    /**
     * @Author: timber
     * @Description: 保存评论信息
     * @Date: 2020/2/22 下午9:25
     * @param comment 评论对象
     * @Return  保存后的评论对象
     */
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * @Author: timber 
     * @Description: 循环各个顶级评论节点
     * @Date: 2020/2/22 下午9:18
     * @param comments 顶级评论节点集合
     * @Return  
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments){
            Comment tempComment = new Comment();
            BeanUtils.copyProperties(comment, tempComment);
            commentsView.add(tempComment);
        }
        // 合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @Author: timber
     * @Description: 将顶级节点下的各级子代节点统一放在一级子代节点集合中
     * @Date: 2020/2/22 下午9:14
     * @param comments 顶级节点集合
     * @Return
     */
    private void combineChildren(List<Comment> comments){
        for(Comment comment : comments){
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1){
                // 循环迭代 找出顶级节点的所有子代节点 存放在tempReplys中
                recursively(reply1);
            }
            // 修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            // 清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    // 存放迭代出的所有子代集合的临时存放区
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * @Author: timber 
     * @Description: 递归迭代子代节点的所有孙子代节点
     * @Date: 2020/2/22 下午9:09
     * @param comment 子代节点
     * @Return  
     */
    private void recursively(Comment comment){
        // 子代节点添加到临时存放集合
        tempReplys.add(comment);
        if(comment.getReplyComments().size() > 0){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
            }
        }
    }
}
