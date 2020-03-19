package com.zme.zmer_blog.service.impl;

import com.zme.zmer_blog.dao.BlogRepository;
import com.zme.zmer_blog.dao.TagRepository;
import com.zme.zmer_blog.exception.NotFoundException;
import com.zme.zmer_blog.po.Tag;
import com.zme.zmer_blog.service.TagService;
import com.zme.zmer_blog.util.TransforUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @authon timber
 * @description 标签Service实现类
 * @date 2020/2/10 下午6:48
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BlogRepository blogRepository;
    /**
     * @Author: timber
     * @Description: 新增标签
     * @Date: 2020/2/10 下午6:49
     * @param tag 标签对象
     * @Return 标签对象
     */
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * @Author: timber
     * @Description: 根据标签id查询标签
     * @Date: 2020/2/10 下午6:59
     * @param id 标签id
     * @Return  标签对象
     */
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    /**
     * @Author: timber
     * @Description: 根据标签名查询标签
     * @Date: 2020/2/10 下午7:00
     * @param name 标签名
     * @Return  标签对象
     */
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * @Author: timber
     * @Description: 分页查询标签列表
     * @Date: 2020/2/10 下午7:01
     * @param pageable 分页对象
     * @Return  分页标签列表
     */
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    /**
     * @Author: timber
     * @Description: 查询标签列表
     * @Date: 2020/2/11 下午8:20
     * @param
     * @Return
     */
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    /**
     * @Author: timber
     * @Description: 根据id字符串查询标签集合
     * @Date: 2020/2/11 下午11:59
     * @param ids id字符串
     * @Return  标签集合
     */
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(TransforUtils.convertToList(ids));
    }

    /**
     * @Author: timber
     * @Description: 根据标签下的博客数量倒序排序 取出size条标签数据的列表
     * @Date: 2020/2/15 下午4:29
     * @param size 博客标签的数量
     * @Return  标签数据的列表
     */
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    /**
     * @Author: timber
     * @Description: 根据标签id和标签对象更新标签
     * @Date: 2020/2/10 下午7:06
     * @param id 标签id
     * @param tag 待更新的标签对象
     * @Return  已更新的标签对象
     */
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag oldTag = tagRepository.getOne(id);
        if(oldTag == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, oldTag);
        return tagRepository.save(oldTag);
    }

    /**
     * @Author: timber
     * @Description: 根据标签id删除标签
     * @Date: 2020/2/10 下午7:07
     * @param id 标签id
     * @Return  void
     */
    @Override
    public void deleteTag(Long id) {
        Tag delTag = tagRepository.getOne(id);
        // 博客标签与博客相关联 要删除博客标签 要先删除博客中包含了要删除的博客标签的 博客
        blogRepository.deleteAllByTagsIsContaining(delTag);
        tagRepository.deleteById(id);
    }
}
