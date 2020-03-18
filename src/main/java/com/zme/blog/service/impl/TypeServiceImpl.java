package com.zme.blog.service.impl;

import com.zme.blog.dao.BlogRepository;
import com.zme.blog.dao.TypeRepository;
import com.zme.blog.exception.NotFoundException;
import com.zme.blog.po.Type;
import com.zme.blog.service.TypeService;
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
 * @description 分类Service实现类
 * @date 2020/2/10 上午2:34
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private BlogRepository blogRepository;

    /**
     * @Author: timber
     * @Description: 新增分类
     * @Date: 2020/2/10 上午2:29
     * @param type 分类对象
     * @Return
     */
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    /**
     * @Author: timber
     * @Description: 根据分类id查询分类
     *               这里调用的findById方法基于Hibernate 的findOne()方法 findById返回一个Optional对象
     *               Optional对象里有一个get()方法 我们要返回的是Type 因此要.get()
     *               Hibernate 还有一个getOne()方法 但此方法是以懒加载模式查询
     *               这里由于Type对象与Blog对象建立了关联关系 用getOne()查询不出数据 因此这里只能使用findOne()
     * @Date: 2020/2/10 上午2:30
     * @param id 分类id
     * @Return
     */
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }

    /**
     * @Author: timber
     * @Description: 根据分类名称查询分类
     * @Date: 2020/2/10 下午4:04
     * @param name 分类名称
     * @Return
     */
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /**
     * @Author: timber
     * @Description: 分页分类列表
     * @Date: 2020/2/10 上午2:31
     * @param pageable 分页对象
     * @Return
     */
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    /**
     * @Author: timber
     * @Description: 查询所有分类
     * @Date: 2020/2/11 下午6:02
     * @param
     * @Return
     */
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    /**
     * @Author: timber
     * @Description: 根据分类下的博客数量倒序排序 取出size条分类数据的列表
     * @Date: 2020/2/15 下午4:22
     * @param size 博客分类的数量
     * @Return  分类数据的列表
     */
    @Override
    public List<Type> listTypeTop(Integer size) {
        // 定义排序规则 以该分类下的博客数量 从大到小 倒序
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        // 定义Page对象 取第一页 size条 sort 为排序规则
        Pageable pageable =  PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    /**
     * @Author: timber
     * @Description: 根据分类id更新分类
     * @Date: 2020/2/10 上午2:32
     * @param id 分类id
     * @param type 待更新分类
     * @Return
     */
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }

    /**
     * @Author: timber
     * @Description: 根据分类id删除分类
     * @Date: 2020/2/10 上午2:33
     * @param id 分类id
     * @Return
     */
    @Override
    public void deleteType(Long id) {
        Type delType = typeRepository.getOne(id);
        // 博客分类与博客相关联 要删除博客分类 要先删除与博客分类相关联的博客
        blogRepository.deleteAllByType(delType);
        typeRepository.deleteById(id);
    }
}
