package com.zme.blog.web;

import com.zme.blog.po.Type;
import com.zme.blog.service.BlogService;
import com.zme.blog.service.TypeService;
import com.zme.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @authon timber
 * @description 博客分类展示控制器
 * @date 2020/2/26 下午10:45
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("types/{id}")
    public String types(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                        @PathVariable  Long id, Model model){
        List<Type> types = typeService.listTypeTop(10000);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
