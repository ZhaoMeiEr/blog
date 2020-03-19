package com.zme.zmer_blog.web.admin;

import com.zme.zmer_blog.po.Blog;
import com.zme.zmer_blog.po.User;
import com.zme.zmer_blog.service.BlogService;
import com.zme.zmer_blog.service.TagService;
import com.zme.zmer_blog.service.TypeService;
import com.zme.zmer_blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @authon timber
 * @description 博客管理控制器
 * @date 2020/2/10 上午12:26
 */
@Controller
@RequestMapping("/admin")
public class BlogsController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * @Author: timber
     * @Description: 博客分页动态列表
     * @Date: 2020/2/11 下午5:22
     * @param pageable 分页对象
     * @param blogQuery 博客vo对象
     * @param model
     * @Return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blogQuery,
                        Model model){
        // 查询所有分类 将所有分类填充到分类查询条件的下拉列表
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return LIST;
    }

    /**
     * @Author: timber
     * @Description: 根据条件检索博客分页列表 (局部刷新)
     * @Date: 2020/2/11 下午6:51
     * @param pageable
     * @param blogQuery
     * @param model
     * @Return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blogQuery,
                         Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs :: blogList";
    }

    /**
     * @Author: timber
     * @Description: 跳转新增博客页面
     * @Date: 2020/2/11 下午8:24
     * @param model
     * @Return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        Blog blankBlog = new Blog();
        // 默认新增的博客 勾选上推荐
        blankBlog.setRecommend(true);
        // 默认新增博客 flag的值为原创
        blankBlog.setFlag("原创");
        model.addAttribute("blog", blankBlog);
        return INPUT;
    }

    /**
     * @Author: timber
     * @Description: 跳转更新博客页面
     * @Date: 2020/2/13 下午8:59
     * @param id 博客id
     * @param model 返回给前端的model对象
     * @Return  跳转 INPUT
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.initTagIds();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    /**
     * @Author: timber
     * @Description: 提交新增博客
     * @Date: 2020/2/13 下午11:28
     * @param blog
     * @param session
     * @param attributes
     * @Return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        // 设置用户
        blog.setUser((User) session.getAttribute("user"));
        // 设置分类
        blog.setType(typeService.getType(blog.getType().getId()));
        // 设置标签集合
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog addBlog = blogService.saveBlog(blog);
        if(addBlog != null){
            attributes.addFlashAttribute("message", "博客添加成功");
        }else{
            attributes.addFlashAttribute("message", "博客添加失败");
        }
        return REDIRECT_LIST;
    }

    /**
     * @Author: timber
     * @Description: 提交更新博客
     * @Date: 2020/2/13 下午11:27
     * @param blog
     * @param id
     * @param attributes
     * @param session
     * @Return
     */
    @PostMapping("/blogs/{id}")
    public String editPost(Blog blog, @PathVariable Long id, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog updateBlog = blogService.updateBlog(id, blog);
        if(updateBlog != null){
            attributes.addFlashAttribute("message", "博客更新成功");
        }else{
            attributes.addFlashAttribute("message", "博客更新失败");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除博客成功");
        return REDIRECT_LIST;
    }

    /**
     * @Author: timber 
     * @Description: 将分类集合和标签集合存入Model对象
     * @Date: 2020/2/13 下午8:40
     * @param model
     * @Return  
     */
    private void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }
}
