package college.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-06
 * VersionV1.0
 * @description
 */
@Data
public class SimpleDTO {
    private int id;
    private String name;
    @NotNull(message = "不能为空")
    @NotBlank(message = "不能为空")
    private String testNotNull;
}
