package college.common.cache.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author :    sp
 * @createDate :    2019/1/8
 * @Description:    RedisTemplate配置,在容器中注入一个自定义的redisTemplate,用于set Object
 */
@Configuration
public class TemplateConfig {

    /**
     * 重写Redis序列化方式，使用Json方式:
     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
     * Spring Data JPA为我们提供了下面的Serializer：
     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
     * 在此我们将自己配置RedisTemplate并定义Serializer。
     * springboot 2.0以上版本默认使用 lettue连接池
     *
     * @param redisConnectionFactory
     * @Description:
     * @return
     */
    @Bean("redisObjectOperation")
    public RedisTemplate generateRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //在工厂默认配置之后更新配置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * FastJson 自动转换类型支持
     * 用于把jsonArray反序列化为list
     * 不配置此项,FastJson无法将jsonArray反序列化为list
     */
    @Bean
    public void fastJsonSerializerConfig(){
        //全开启
        //ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //开启指定包
        ParserConfig.getGlobalInstance().addAccept("yidao.com.entity");
    }


}
