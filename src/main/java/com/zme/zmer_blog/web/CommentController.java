package com.zme.zmer_blog.web;

import com.zme.zmer_blog.po.Comment;
import com.zme.zmer_blog.po.User;
import com.zme.zmer_blog.service.BlogService;
import com.zme.zmer_blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @authon timber
 * @description 博客评论控制层
 * @date 2020/2/22 下午9:27
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * @Author: timber
     * @Description: 加载评论信息列表
     * @Date: 2020/2/22 下午9:31
     * @param blogId 博客id
     * @param model
     * @Return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * @Author: timber
     * @Description: 保存评论信息
     * @Date: 2020/2/22 下午10:06
     * @param comment 评论信息对象
     * @param session
     * @Return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
