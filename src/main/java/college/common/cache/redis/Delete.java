package college.common.cache.redis;

import java.lang.annotation.*;

/**
 * Name
 *
 * @author xuxb
 * Date 2019-07-11
 * VersionV1.0
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
//@Repeatable(Delete.class)
public @interface Delete {
    String id();
}
