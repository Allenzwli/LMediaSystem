package csu.lzw.lmediaserver.pojo;

/**
 * Created by allenzwli on 2017/4/10.
 */
public class Admin {

    private int id;

    private String account;

    private String nickName;

    private String encyptPassword;


    private String token;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private int isSuperAdmin;

    public void setIsSuperAdmin(int isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public int getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEncyptPassword() {
        return encyptPassword;
    }

    public void setEncyptPassword(String encyptPassword) {
        this.encyptPassword = encyptPassword;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
