package priv.weijianlin.huobi.model;

import java.util.List;

public class Account {
    
    private Long id;         //账户ID
    
    private String state;    //账户状态    working：正常, lock：账户被锁定
    
    private String type;     //账户类型   spot：现货账户
    
    //子账户数组
    private List<Balance> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Balance> getList() {
        return list;
    }

    public void setList(List<Balance> list) {
        this.list = list;
    }
    
    
}
