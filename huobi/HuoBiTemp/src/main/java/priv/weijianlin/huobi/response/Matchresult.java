package priv.weijianlin.huobi.response;

import java.util.List;

import priv.weijianlin.huobi.model.Matchresults;

public class Matchresult {

    public String status;
    
    public List<Matchresults> data;

    /** 
     * <p>一句话功能简述</p>
     * <p>功能详细描述</p>
     * @return
     */
    public List<Matchresults> checkAndReturn() {
        if ("ok".equals(status)) {
            return data;
        }
        throw new ApiException("", "");
    }
}
