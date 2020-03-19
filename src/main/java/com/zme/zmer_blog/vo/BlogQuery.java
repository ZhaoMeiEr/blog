package com.zme.zmer_blog.vo;

/**
 * @authon timber
 * @description 博客查询条件vo
 * @date 2020/2/11 下午6:11
 */
public class BlogQuery {
    private String title;
    // 分类id
    private Long typeId;
    // 是否推荐
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
