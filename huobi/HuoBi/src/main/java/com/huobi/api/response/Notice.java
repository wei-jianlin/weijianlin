package com.huobi.api.response;

/**
 * <p>公告</p>
 * <p>功能详细描述</p>
 * @since 2018年1月25日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public class Notice {

    private Integer id;
    
    private String title;
    
    private Long created;
    
    private String source;
    
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
