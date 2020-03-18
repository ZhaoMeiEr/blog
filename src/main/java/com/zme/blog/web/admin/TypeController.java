package com.zme.blog.web.admin;

import com.zme.blog.po.Type;
import com.zme.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @authon timber
 * @description 分类控制器
 * @date 2020/2/10 上午3:08
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * @Author: timber
     * @Description: 分类分页列表
     * @Date: 2020/2/10 上午3:23
     * @param pageable 分页对象
     * @param model
     * @Return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * @Author: timber
     * @Description: 跳转新增分类页面
     * @Date: 2020/2/10 上午4:20
     * @param
     * @Return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * @Author: timber
     * @Description: 跳转更新分类页面
     * @Date: 2020/2/10 下午4:31
     * @param model
     * @param id
     * @Return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(Model model, @PathVariable Long id){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * @Author: timber
     * @Description: 提交新增分类
     * @Date: 2020/2/10 上午4:23
     * @param type @Valid 规定type受校验约束
     * @param bindingResult 存放 @NotBlank校验后的信息
     * @Return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes attributes){
        // 校验分类名是否已经存在
        if(typeService.getTypeByName(type.getName()) != null){
            bindingResult.rejectValue("name", "nameError", "不能添加重复分类");
        }
        // 校验结果为false 携带错误信息返回新增分类页面
        if(bindingResult.hasErrors()){
            return "admin/types-input";
        }
        Type addType = typeService.saveType(type);
        if(addType != null){
            attributes.addFlashAttribute("message", "新增成功");
        }else{
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * @Author: timber
     * @Description: 提交更新分类
     * @Date: 2020/2/10 下午5:23
     * @param type 待更新的分类对象
     * @param bindingResult
     * @param id 待更新分类的id
     * @param attributes
     * @Return
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult bindingResult,
                           @PathVariable Long id,
                           RedirectAttributes attributes){
        // 校验分类名是否已经存在
        if(typeService.getTypeByName(type.getName()) != null){
            bindingResult.rejectValue("name", "nameError", "不能添加重复分类");
        }
        // 校验结果为false 携带错误信息返回修改分类页面
        if(bindingResult.hasErrors()){
            return "admin/types-input";
        }
        Type updateType = typeService.updateType(id, type);
        if(updateType != null){
            attributes.addFlashAttribute("message", "更新成功");
        }else{
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * @Author: timber
     * @Description: 根据分类id删除分类
     * @Date: 2020/2/10 下午6:02
     * @param id 分类id
     * @param attributes
     * @Return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
