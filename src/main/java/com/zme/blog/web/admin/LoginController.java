package com.zme.blog.web.admin;

import com.zme.blog.po.User;
import com.zme.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @authon timber
 * @description 登录控制器
 * @date 2020/2/9 下午11:27
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * @Author: timber
     * @Description: 跳转到登录页面
     * @Date: 2020/2/9 下午11:28
     * @param
     * @Return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * @Author: timber
     * @Description: 登录
     * @Date: 2020/2/9 下午11:30
     * @param
     * @Return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }
        attributes.addFlashAttribute("message", "用户名或密码错误");
        return "redirect:/admin";
    }

    /**
     * @Author: timber
     * @Description: 注销
     * @Date: 2020/2/9 下午11:41
     * @param session
     * @Return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
