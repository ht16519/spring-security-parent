package com.xh.sso.server.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Name JsonUtil
 * @Description JsonUtil工具
 */
@Slf4j
public class JsonUtil {

    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        //反序列化的时候如果多了其他属性,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //仅序列化不为空的值
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * @param obj
     * @return java.lang.String
     * @Name serialize
     * @Description 序列化对象（转json）
     */
    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    /**
     * @param json
     * @param tClass
     * @return T
     * @Name parse
     * @Description 反序列化（json转为Bean）
     */
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
    * @param json
    * @param eClass
    * @return java.util.List<E>
    * @Name parseList
    * @Description 反序列化（json转List）
    */
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
    * @param json
    * @param kClass
    * @param vClass
    * @return java.util.Map<K, V>
    * @Name parseMap
    * @Description 反序列化（json转Map）
    */
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
    * @param json
    * @param type
    * @return T
    * @Name nativeRead
    * @Description json转复杂对象
    */
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

//============================测试使用========

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    static class User{
//        private Integer id;
//        private String name;
//        private Double age;
//    }
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    static class W{
//        private List<Object> lives;
//    }
//
//    public static void main(String[] args) {
//        User user = new User(1, "jack", 16.5);
//        //序列化对象（转json）
//        String json = serialize(user);
//        System.out.println(json);
//
//        //反序列化（json转为Bean）
//        User u = parse(json, User.class);
//        System.out.println(u);
//
//        //反序列化（json转List）
//        json = "[20, 15, -1, 22]";
//        System.out.println(parseList(json, String.class));
//        System.out.println(parseList(json, Integer.class));
//
//        //反序列化（json转Map）
//        json = "{\"name\" : \"历史\", \"id\":123, \"age\":12.2}";
//        Map<Object, Object> map = parseMap(json, Object.class, Object.class);
//        System.out.println(map);
//
//
//
//        String j = "{\"status\": \"1\",\"count\": \"1\",\"info\": \"OK\",\"infocode\": \"10000\",\"lives\": [{\"province\": \"河北\",\"city\": \"张家口市\",\"adcode\": \"130700\",\"weather\": \"晴\",\"temperature\": \"15\",\"winddirection\": \"西南\",\"windpower\": \"≤3\",\"humidity\": \"11\",\"reporttime\": \"2019-04-14 11:20:08\"}]}";
//        Map<String, Object> stringObjectMap = parseMap(j, String.class, Object.class);
//        System.out.println(stringObjectMap);
//
//        System.out.println(parse(j, W.class));
//
//
//        //json转复杂对象
//        json = "[{\"name\":\"李四\", \"age\":12.3}, {\"name\":\"王文\", \"age\":16.3}]";
//        List<Map<String, String>> maps = nativeRead(json, new TypeReference<List<Map<String, String>>>() {
//        });
//        System.out.println(maps);
//
//        System.err.println(serialize(null));
//    }
}
