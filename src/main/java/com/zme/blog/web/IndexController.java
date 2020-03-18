package com.zme.blog.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zme.blog.service.BlogService;
import com.zme.blog.service.TagService;
import com.zme.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

   /**
    * @Author: timber
    * @Description: 博客首页展示
    * @Date: 2020/2/15 下午4:45
    * @param pageable 分页对象
    * @param model
    * @Return  博客分页列表
    */
    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        // 博客分类top列表
        model.addAttribute("types", typeService.listTypeTop(6));
        // 博客标签top列表
        model.addAttribute("tags", tagService.listTagTop(10));
        // 博客推荐top列表
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    /**
     * @Author: timber
     * @Description: 博客搜索分页列表 根据博客标题和博客内容模糊查询
     * @Date: 2020/2/16 下午9:46
     * @param pageable 分页对象
     * @param model
     * @param query 查询关键词
     * @Return  博客分页列表
     */
    @GetMapping("/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, @RequestParam String query){
        query = query.trim();
        if(!"".equals(query) && query != null){
            model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        }else{
            model.addAttribute("page", blogService.listBlog(pageable));
        }
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * @Author: timber
     * @Description: 查看博客详情
     * @Date: 2020/2/17 上午1:31
     * @param id 博客id
     * @param model
     * @Return  博客详情页面
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    /**
     * @Author: timber
     * @Description: 获取最新三条博客加载到footer中
     * @Date: 2020/2/28 下午11:16
     * @param model
     * @Return
     */
    @GetMapping("/footer/newblog")
    public String newBlog(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }

}
