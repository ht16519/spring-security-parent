package com.xh.security.core.properties;


/**
 * @Name OAuth2SupportProperties
 * @Description 系统内部OAuth2.0支持配置
 * @Author wen
 * @Date 2020-04-19
 */
public class OAuth2SupportProperties {

    private String storeType;

    private String jwtSigningKey = "ht16519";

    private OAuth2ClientProperties[] clients;

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
