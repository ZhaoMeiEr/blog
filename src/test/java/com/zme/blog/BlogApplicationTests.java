package com.zme.blog;

import com.zme.blog.dao.BlogRepository;
import com.zme.blog.po.Blog;
import com.zme.blog.po.Tag;
import com.zme.blog.po.Type;
import com.zme.blog.service.BlogService;
import com.zme.blog.service.TagService;
import com.zme.blog.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogRepository blogRepository;
    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback(false)
    public void BlogTagTest(){
//        Blog blog = new Blog();
//        String ids = "13,14";
//        blog.setTagIds(ids);
//        List<Tag> tagList = tagService.listTag(ids);
//        blog.setTags(tagList);
//        Type type = new Type();
//        type.setId(2L);

//        blogService.getBlog(30L);
//        blogService.deleteBlog(30L);
//        typeService.deleteType(2L);

//        Type type = new Type();
//        type.setId(3l);
//        List<Blog> list =  blogRepository.findAllByType(type);
//        for(Blog blogItem : list){
//            System.out.println(blogItem.getId());
//        }
//        Type type = new Type();
//        type.setId(3l);
//        blogRepository.deleteAllByType(type);

//        blogRepository.deleteById(33L);

//        blog.setType(type);
//        Blog b = blogService.saveBlog(blog);
//        List<String> years = blogRepository.findGroupYear();
////        for (String year : years){
////            System.out.println(year);
////        }
        Map<String, List<Blog>> map = blogService.archiveBlog();
        Set<String> set = map.keySet();
        for(String key : set){
//            map.get(key);
            System.out.println(key);
        }

    }

}
