package college.common.result;

import lombok.Data;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-21
 * VersionV1.0
 * @description
 */
@Data
//作废；使用ResultVO 就可以实现了。
@Deprecated
public class PageResultVO {
    private int code = 200;

    private String msg = "";

    private PageResult results;
}
