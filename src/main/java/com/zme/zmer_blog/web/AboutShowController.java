package com.zme.zmer_blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @authon timber
 * @description 关于我展示控制器
 * @date 2020/2/28 下午11:09
 */
@Controller
public class AboutShowController {
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
