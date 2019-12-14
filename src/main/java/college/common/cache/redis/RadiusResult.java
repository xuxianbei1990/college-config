package college.common.cache.redis;

import lombok.Data;

import java.util.List;

/**
 * @Author :    sp
 * @createDate :    2019/1/10
 * @Description: redis地理半径处理对象集合DTO
 */
@Data
public class RadiusResult {
    /**
     * 平均距离
     */
    private Double avgDistance;
    private List<RadiusDTO> radiusDTOS;
    /**
     * redis地理半径处理对象DTO
     */
    @Data
    public static class RadiusDTO {
        /**
         * 坐标名称
         */
        private String member;
        /**
         * 平均距离
         */
        private Double X;
        private Double y;
        private Double distance;

    }

}