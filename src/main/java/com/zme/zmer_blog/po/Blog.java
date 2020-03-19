package com.zme.zmer_blog.po;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @authon timber
 * @description 博客-实体类
 * @date 2020/2/8 下午10:47
 */
@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    // 博客标题
    private String title;
    // 博客内容
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    // 博客首图
    private String firstPicture;
    // 标记
    private String flag;
    // 浏览次数
    private Integer views;
    // 赞赏是否开启
    private boolean appreciation;
    // 转载声明是否开启
    private boolean shareStatement;
    // 版权是否开启
    private boolean copyright;
    // 评论是否开启
    private boolean commentabled;
    // 是否发布
    private boolean published;
    // 是否推荐
    private boolean recommend;
    // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    // 更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    // 博客分类
    @ManyToOne
    private Type type;

    // 博客标签集合
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    // 博客用户
    @ManyToOne
    private User user;

    // 博客评论集合
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    // @Transient 不会将字段与数据库中的字段映射
    @Transient
    private String tagIds;

    // 博客描述
    private String description;


    public Blog() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean apreciation) {
        this.appreciation = apreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @Author: timber
     * @Description: 将标签结合中的id组装成 1,2,3 的字符串形式 提供给外部的接口
     * @Date: 2020/2/13 下午8:56
     * @param
     * @Return
     */
    public void initTagIds(){
        this.tagIds = tagsToIds(this.getTags());
    }

    /**
     * @Author: timber
     * @Description: 从标签集合中取出标签id并组装成 1,2,3 的字符串形式 Blog内部私有方法
     * @Date: 2020/2/13 下午8:54
     * @param tags 标签集合
     * @Return  1,2,3 的字符串形式
     */
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tagItem: tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tagItem.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", copyright=" + copyright +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
