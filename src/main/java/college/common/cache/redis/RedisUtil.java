package college.common.cache.redis;


import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author :    sp
 * @createDate :    2019/1/7
 * @Description:    redis set/get Object
 *                  redis geo(地理计算) 描述:
 *                  存放形式 X 纬度 Y 经度 member 坐标点名称(自定义), 一个key可以存放多个坐标信息
 *                  存入redis 的经纬度信息会转换为长整型 ,geo提供获取经纬度范围内其他坐标信息,计算两点距离返回单位为米的值
 *                  支持计算给定坐标点一定范围内的其他已存储坐标点
 */
@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static StringRedisTemplate stringRedisTemplate;
    private static RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate s;
    @Autowired
    @Qualifier("redisObjectOperation")
    private RedisTemplate r;

    @PostConstruct
    public void init() {
        RedisUtil.stringRedisTemplate = s;
        RedisUtil.redisTemplate = r;
    }

    //--------------------------------------------静态方法--------------------------------------------

    /**
     * get
     *
     * @param key
     * @Description: 如果存入数据为Object, 需要自己转换实际类型
     */
    public static String get(String key) {
        String s = stringRedisTemplate.opsForValue().get(key);
        getLogger(key, s);
        return s;
    }

    /**
     * set String
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        setLogger(key, value);
    }

    /**
     * set String 过期 默认为秒
     *
     * @param key
     * @param value
     * @param expire
     */
    public static void set(String key, String value, long expire) {
        set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * set String 过期 自定义时间类型
     *
     * @param key
     * @param value
     * @param expire
     */
    public static void set(String key, String value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
        setLogger(key, value);
    }

    /**
     * set Object
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        setLogger(key, value);
    }

    /**
     * set Object 过期 默认为秒
     *
     * @param key
     * @param value
     * @param expire
     */
    public static void set(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * set Object 过期 自定义时间类型
     *
     * @param key
     * @param value
     * @param expire
     */
    public static void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
        setLogger(key, value);
    }


    /**
     * get list
     */
    public static List getList(String key, Class clazz) {
        assert (clazz != null) : "10000 指定泛型不能为空";
        String s = stringRedisTemplate.opsForValue().get(key);
        List list = JSON.parseArray(s, clazz);
        assert (list != null) : "10000 redis取值转换list为空";
        getLogger(key, list);
        return list;
    }

    /**
     * 删除key
     */
    public static boolean delete(String key) {
        Boolean status = stringRedisTemplate.delete(key);
        deleteLogger(key);
        return status;
    }

    /**
     * 增加key
     * 所输入key对应的value必须为数字
     * 返回该key 对应的 value 的当前值
     * 如果该key 为空,num为当前值
     */
    public static Long increment(String key, long num) {
        Long increment = stringRedisTemplate.opsForValue().increment(key, num);
        incrementLogger(key, num);
        return increment;
    }

    /**
     * 检测key是否存在
     */
    public static boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * set地理信息
     *
     * @param key
     * @param x      经度
     * @param y      纬度
     * @param member 名称 例如: 杭州 或 HangZhou
     * @return
     */
    public static Long setGeo(String key, double x, double y, String member) {
        assert(x>180 || x<-180 || y > 85 || y <-85):"10002  经纬度范围  x (180 至 -180 ) y ( 85 至 -85 )";
        Long add = redisTemplate.opsForGeo().add(key, new Point(x, y), member);
        return add;
    }

    /**
     * set地理信息  过期  默认为分钟
     *
     * @param key
     * @param x      经度
     * @param y      纬度
     * @param member 名称 例如: 杭州 或 HangZhou
     * @param time   过期时间,单位 秒
     * @return
     */
    public static Long setGeo(String key, double x, double y, String member, long time) {
        Long aLong = setGeo(key, x, y, member);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.MINUTES);
        }
        return aLong;
    }

    /**
     * set地理信息  过期  时间类型自定义
     *
     * @param key
     * @param x      经度
     * @param y      纬度
     * @param member 名称 例如: 杭州 或 HangZhou
     * @param time   过期时间,单位 秒
     * @return
     */
    public static Long setGeo(String key, double x, double y, String member, long time,TimeUnit unit) {
        Long aLong = setGeo(key, x, y, member);
        if (time > 0) {
            redisTemplate.expire(key, time, unit);
        }
        return aLong;
    }

    /**
     * set地理信息  过期  默认为秒
     *
     * @param key
     * @param location 定位对象(包含point信息,地点名称)
     * @param time     过期时间,单位 秒
     * @return
     */
    public static Long setGeo(String key, RedisGeoCommands.GeoLocation location, long time) {
        assert (location != null && location.getPoint() != null && location.getName() != null) : "10001 地址对象不完整,缺少名称或point数据";
        double x = location.getPoint().getX();
        double y = location.getPoint().getY();
        assert(x>180 || x<-180 || y > 85 || y <-85):"10002  经纬度范围  x (180 至 -180 ) y ( 85 至 -85 )";
        Long add = redisTemplate.opsForGeo().add(key, location);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return add;
    }

    /**
     * 根据name移除key中某个地理信息
     *
     * @param key
     * @param members
     */
    public static Long removeGeo(String key, String... members) {
        Long remove = redisTemplate.opsForGeo().remove(key, members);
        return remove;
    }

    /**
     * 计算两点距离
     *
     * @param member1
     * @param member2
     * @return 返回值示例:96034.6386 METERS
     */
    public static Distance distanceGeo(String key, String member1, String member2) {
        assert (key != null && member1 != null && member2 != null) : "10003 缺少必要数据";
        return redisTemplate.opsForGeo().distance(key, member1, member2);
    }

    /**
     * 根据key获得geo定位信息
     *
     * @param key
     * @param members
     * @return 返回值示例:[Point [x=120.135501, y=30.297019]]
     */
    public static List<Point> getGeo(String key, String... members) {
        assert (members != null) : "10004 必须给定至少一个member";
        assert (members.length < 2) : "10004 请不要超过1个member";
        return redisTemplate.opsForGeo().position(key, members);
    }

    /**
     * 获取指定坐标半径范围内的坐标点  默认距离单位为米,默认返回100个坐标点,默认按最近距离s排序
     * @param key
     * @param x 纬度
     * @param y 经度
     * @param distance 距离
     * @return
     */
    public RadiusResult radiusGeo(String key, double x, double y, double distance) {
        radiusGeo(key, x, y, distance, true, 100, RedisGeoCommands.DistanceUnit.METERS);
        return null;
    }

    /**
     * 获取指定坐标半径范围内的坐标点
     * @param key
     * @param x 纬度
     * @param y 经度
     * @param distance 距离
     * @param sort 方向 (排序) true 正序,最近的 false 倒序,最远的
     * @param limit 限制查询数量
     * @param distanceUnit 距离单位
     * @return
     */
    public RadiusResult radiusGeo(String key, double x, double y, double distance, boolean sort, long limit,RedisGeoCommands.DistanceUnit distanceUnit) {
        try {
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs geoRadiusArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
            geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance();
            //查询返回结果包括距离和坐标
            if (sort) {
                //按查询出的坐标距离中心坐标的距离进行排序
                geoRadiusArgs.sortAscending();
            } else {
                geoRadiusArgs.sortDescending();
            }
            //限制查询数量
            geoRadiusArgs.limit(limit);
            GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo = geoOps.radius(key, new Circle(new Point(x, y), new Distance(distance, distanceUnit)), geoRadiusArgs);
            return copyGeoResults(radiusGeo);
        } catch (Throwable t) {
            logger.error("通过坐标[" + x + "," + y +"]获取范围[" + distance + "km的其它坐标失败]" + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 复制GeoResults到RadiusResult
     * @param geoLocationGeoResults
     * @return
     */
    public RadiusResult copyGeoResults(GeoResults<RedisGeoCommands.GeoLocation<String>> geoLocationGeoResults){
        Distance averageDistance = geoLocationGeoResults.getAverageDistance();
        RadiusResult radiusResult = new RadiusResult();
        radiusResult.setAvgDistance(averageDistance.getValue());
        List<RadiusResult.RadiusDTO> radiusDTOS = new ArrayList<>();
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = geoLocationGeoResults.getContent();
        for(GeoResult<RedisGeoCommands.GeoLocation<String>> geoLocationGeoResult:content){
            RedisGeoCommands.@NonNull GeoLocation<String> locationContent = geoLocationGeoResult.getContent();
            RadiusResult.RadiusDTO radiusDTO = new RadiusResult.RadiusDTO();
            radiusDTO.setX(locationContent.getPoint().getX());
            radiusDTO.setY(locationContent.getPoint().getY());
            radiusDTO.setDistance(geoLocationGeoResult.getDistance().getValue());
            radiusDTO.setMember(locationContent.getName());
            radiusDTOS.add(radiusDTO);
        }
        radiusResult.setRadiusDTOS(radiusDTOS);
        return radiusResult;
    }

    //--------------------------------------------私有方法--------------------------------------------

    private static void setLogger(Object key, Object value) {
        if (value == null) {
            logger.info("redis set{ key :" + key.toString() + " value : null}");
        }
        logger.info("redis set{ key :" + key.toString() + " value : " + value.toString() + "}");
    }

    private static void getLogger(Object key, Object value) {
        if (value == null) {
            logger.info("redis get{ key :" + key.toString() + "  : null}");
        }
        logger.info("redis get{ key :" + key.toString() + " value : " + value.toString() + "}");
    }

    private static void incrementLogger(Object key, Object num) {
        if (num == null) {
            logger.info("redis increment{ key :" + key.toString() + "  : null}");
        }
        logger.info("redis increment{ key :" + key.toString() + " value : " + num.toString() + "}");
    }

    private static void deleteLogger(Object key) {
        logger.info("redis Delete{ key :" + key.toString());
    }


}
