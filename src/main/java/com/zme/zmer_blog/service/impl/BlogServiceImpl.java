package com.zme.zmer_blog.service.impl;

import com.zme.zmer_blog.dao.BlogRepository;
import com.zme.zmer_blog.dao.CommentRepository;
import com.zme.zmer_blog.exception.NotFoundException;
import com.zme.zmer_blog.po.Blog;
import com.zme.zmer_blog.po.Type;
import com.zme.zmer_blog.service.BlogService;
import com.zme.zmer_blog.util.MarkdownUtils;
import com.zme.zmer_blog.util.MyBeanUtils;
import com.zme.zmer_blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @authon timber
 * @description 博客Service实现类
 * @date 2020/2/11 下午4:09
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CommentRepository commentRepository;

    /**
     * @Author: timber
     * @Description: 根据博客id查询博客
     * @Date: 2020/2/11 下午4:14
     * @param id 博客id
     * @Return  博客对象
     */
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    /**
     * @Author: timber
     * @Description: 查看博客详情 并将博客内容从markdown文本格式转换为Html格式
     * @Date: 2020/2/17 上午1:32
     * @param id 博客id
     * @Return  博客内容从markdown文本格式转换为Html格式后的博客详情对象
     */
    @Override
    public Blog getAndConvert(Long id) {
        Blog repositoryBlog = blogRepository.getOne(id);
        if(repositoryBlog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog voBLog = new Blog();
        BeanUtils.copyProperties(repositoryBlog, voBLog);
        String content = voBLog.getContent();
        // 将转换后的博客内容放入博客对象中
        voBLog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // 更新博客浏览次数(每刷新一次 累加1)
        blogRepository.updateViews(id);
        return voBLog;
    }

    /**
     * @Author: timber
     * @Description: 动态分页查询博客列表
     * @Date: 2020/2/11 下午5:15
     * @param pageable 分页对象
     * @param blogQuery 博客VO对象
     * @Return  分页博客列表
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             * @Author: timber
             * @Description: 定义动态查询条件
             * @Date: 2020/2/11 下午4:59
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @Return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 判断博客标题是否为空 若不为空 加入查询条件的集合
                if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blogQuery.getTitle() + "%"));
                }
                // 判断博客分类是否为空 若不为空 加入查询条件的集合
                if(blogQuery.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                }
                // 判断博客推荐是否勾选 若勾选 加入查询条件的集合
                if(blogQuery.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    /**
     * @Author: timber
     * @Description: 查询博客分页列表
     * @Date: 2020/2/15 下午3:08
     * @param pageable 分页对象
     * @Return  博客分页列表
     */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * @Author: timber
     * @Description: 根据标签id关联查询博客列表
     * @Date: 2020/2/28 下午6:26
     * @param tagId 标签id
     * @param pageable
     * @Return  博客列表
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    /**
     * @Author: timber
     * @Description: 查询更新时间倒序的博客列表
     * @Date: 2020/2/15 下午4:43
     * @param size 查询数量
     * @Return  博客列表
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * @Author: timber
     * @Description: 根据关键词检索博客分页列表
     * @Date: 2020/2/16 下午1:49
     * @param query 关键词
     * @param pageable 分页对象
     * @Return  博客分页列表
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    /**
     * @Author: timber
     * @Description: 查询博客归档列表
     * @Date: 2020/2/28 下午9:24
     * @param
     * @Return  博客归档列表
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new TreeMap<>(Comparator.reverseOrder());
        for(String year : years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    /**
     * @Author: timber
     * @Description: 查询总博客数量
     * @Date: 2020/2/28 下午9:25
     * @param
     * @Return  博客总数量
     */
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    /**
     * @Author: timber
     * @Description: 新增博客
     * @Date: 2020/2/11 下午4:15
     * @param blog 博客对象
     * @Return  博客对象
     */
    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    /**
     * @Author: timber
     * @Description: 更新博客
     * @Date: 2020/2/11 下午4:18
     * @param id 博客id
     * @param blog 待更新博客对象
     * @Return  已更新博客对象
     */
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        // 查询出数据库中的旧的数据
        Blog tempBlog = blogRepository.getOne(id);
        if(tempBlog == null){
            throw new NotFoundException("不存在该博客");
        }
        // 将新数据中没有的字段 从旧数据中拷贝
        BeanUtils.copyProperties(blog, tempBlog, MyBeanUtils.getNullPropertyNames(blog));
        // 更新 更新时间
        tempBlog.setUpdateTime(new Date());
        return blogRepository.save(tempBlog);
    }

    /**
     * @Author: timber
     * @Description: 根据博客id删除博客
     * @Date: 2020/2/11 下午4:19
     * @param id 博客id
     * @Return
     */
    @Override
    public void deleteBlog(Long id) {
        Blog blog = blogRepository.getOne(id);
        // 先删除跟当前博客相关联的评论信息集合 再删除博客
        commentRepository.deleteAllByBlog(blog);
        blogRepository.deleteById(id);
    }
}
