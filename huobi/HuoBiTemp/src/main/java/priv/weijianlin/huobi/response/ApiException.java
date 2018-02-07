package priv.weijianlin.huobi.response;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 2135173444145939272L;
    final String errCode;

    public ApiException(String errCode, String errMsg) {
      super(errMsg);
      this.errCode = errCode;
    }

    public ApiException(Exception e) {
      super(e);
      this.errCode = e.getClass().getName();
    }

    public String getErrCode() {
      return this.errCode;
    }
    
}