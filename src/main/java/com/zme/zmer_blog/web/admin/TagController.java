package com.zme.zmer_blog.web.admin;

import com.zme.zmer_blog.po.Tag;
import com.zme.zmer_blog.service.TagService;
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
 * @description 标签控制器
 * @date 2020/2/10 下午8:38
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * @Author: timber
     * @Description: 标签分页列表
     * @Date: 2020/2/10 下午8:43
     * @param pageable 分页对象
     * @param model
     * @Return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
                       Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * @Author: timber
     * @Description: 跳转新增标签页面
     * @Date: 2020/2/10 下午9:10
     * @param model
     * @Return
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * @Author: timber
     * @Description: 跳转更新标签页面
     * @Date: 2020/2/10 下午11:21
     * @param model
     * @param id 标签id
     * @Return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(Model model, @PathVariable Long id){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * @Author: timber 
     * @Description: 提交新增标签
     * @Date: 2020/2/10 下午11:06
     * @param tag 标签对象
     * @param bindingResult
     * @param attributes
     * @Return  
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes){
        // 校验标签名是否存在
        if(tagService.getTagByName(tag.getName()) != null){
            bindingResult.rejectValue("name", "nameError", "不能添加重复标签");
        }
        // 检验结果为false 携带错误信息返回新增标签页面
        if(bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        Tag addTag = tagService.saveTag(tag);
        if(addTag != null){
            attributes.addFlashAttribute("message", "添加标签成功");
        }else{
            attributes.addFlashAttribute("message", "添加标签失败");
        }
        return "redirect:/admin/tags";
    }

    /**
     * @Author: timber
     * @Description: 提交更新标签
     * @Date: 2020/2/10 下午11:25
     * @param tag 标签对象
     * @param bindingResult
     * @param id 标签id
     * @param attributes
     * @Return
     */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes attributes){
        // 校验标签名是否存在
        if(tagService.getTagByName(tag.getName()) != null){
            bindingResult.rejectValue("name", "nameError", "不能添加重复标签");
        }
        // 检验结果为false 携带错误信息返回更新标签页面
        if(bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        Tag updateTag = tagService.updateTag(id, tag);
        if(updateTag != null){
            attributes.addFlashAttribute("message", "更新标签成功");
        }else{
            attributes.addFlashAttribute("message", "更新标签失败");
        }
        return "redirect:/admin/tags";
    }

    /**
     * @Author: timber
     * @Description: 根据标签id删除标签
     * @Date: 2020/2/10 下午11:45
     * @param id 标签id
     * @param attributes
     * @Return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除标签成功");
        return "redirect:/admin/tags";
    }
}
