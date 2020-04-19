package com.xh.demo.commons.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Redis工具类
 *
 */
@SuppressWarnings("all")
@Component
public final class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 指定缓存失效时间
	 * 
	 * @param key
	 * @param time
	 * @return
	 */
	public boolean expire(String key, long timeout) {
		if (timeout > 0) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 根据key获取失效时间
	 * 
	 * @param key
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean hashKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public void delete(String... keys) {
		if (keys != null && keys.length > 0) {
			if (keys.length == 1) {
				redisTemplate.delete(keys[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(keys));
			}
		}
	}

	/********************************
	 * String
	 *******************************************/
	/**
	 * 普通缓存获取
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return null == key ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean set(String key, Object value, long timeout) {
		try {
			if (timeout > 0) {
				redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增
	 * 
	 * @param key
	 * @param delta
	 *            要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/*********************** Map **************************************/
	/**
	 * HashGet
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key
	 *            缓存中map的键值
	 * @param item
	 *            要新加到map中的key
	 * @param value
	 *            要加到map中的value
	 * @return
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建,并设置超时时间
	 * 
	 * @param key
	 *            缓存中map的键值
	 * @param item
	 *            要新加到map中的key
	 * @param value
	 *            要加到map中的value
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	public boolean hset(String key, String item, Object value, long timeout) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (timeout > 0) {
				expire(key, timeout);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 * 
	 * @param key
	 * @param map
	 *            对应多个键值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean hmset(String key, Map<Object, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 * 
	 * @param key
	 * @param map
	 * @param timeout
	 *            超时时间,注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return
	 */
	public boolean hmset(String key, Map<String, Object> map, long timeout) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (timeout > 0) {
				expire(key, timeout);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 * 
	 * @param key
	 * @param items
	 *            可以是多个 不能为null
	 */
	public void hdel(String key, Object... items) {
		redisTemplate.opsForHash().delete(key, items);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * 
	 * @param key
	 * @param item
	 * @param by
	 *            要增加几(大于0)
	 * @return
	 */
	public double hincr(String key, String item, long by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减
	 * 
	 * @param key
	 * @param item
	 * @param by
	 *            要减少记(小于0)
	 * @return
	 */
	public double hdecr(String key, String item, long by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	// *****************************set************************************
	/**
	 * 根据key获取Set中的所有值
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * 将set数据放入缓存,并设置失效时间
	 * 
	 * @param key
	 * @param timeout
	 * @param values
	 * @return
	 */
	public boolean sSetAndTime(String key, long timeout, Object... values) {
		try {
			redisTemplate.opsForSet().add(key, values);
			if (timeout > 0) {
				expire(key, timeout);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得set缓存的长度
	 * 
	 * @param key
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值为value的缓存
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long setRemove(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().remove(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	// =list*************************list*********************************
	/**
	 * 获取list缓存的内容
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * 
	 * @param key
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存,并设置过期时间
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean lSet(String key, Object value, long timeout) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (timeout > 0) {
				expire(key, timeout);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			return redisTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 将list放入缓存
	 * 
	 * @param key
	 *            键
	 * 
	 * @param value
	 *            值
	 * 
	 * @param time
	 *            时间(秒)
	 * 
	 * @return
	 * 
	 */

	public boolean lSetList(String key, List<Object> value) {

		try {

			redisTemplate.opsForList().rightPushAll(key, value);

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;

		}

	}

	/**
	 * 
	 * 将list放入缓存
	 * 
	 * @param key
	 *            键
	 * 
	 * @param value
	 *            值
	 * 
	 * @param time
	 *            时间(秒)
	 * 
	 * @return
	 * 
	 */

	public boolean lSetList(String key, List<Object> value, long time) {

		try {

			redisTemplate.opsForList().rightPushAll(key, value);

			if (time > 0)

				expire(key, time);

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;

		}

	}

	/**
	 * lpop (注意) 从右边弹出一个
	 * 
	 * @param key
	 * @return
	 */
	public Object lPop(String key) {
		try {

			return redisTemplate.opsForList().rightPop(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除键
	 * 
	 * @param key
	 * @return
	 */
	public boolean del(String key) {
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}