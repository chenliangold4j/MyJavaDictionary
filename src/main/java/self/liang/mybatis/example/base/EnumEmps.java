package self.liang.mybatis.example.base;

public enum EnumEmps {
    LOGIN(100, "登录"), LOGOUT(200, "登出"), ONWORK(300, "工作中");

    Integer code;
    String message;

    EnumEmps(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static EnumEmps getByCode(Integer code) {
        switch (code) {
            case 100:
                return LOGIN;
            case 200:
                return LOGOUT;
            case 300:
                return ONWORK;
            default:
                return LOGOUT;
        }
    }
}
