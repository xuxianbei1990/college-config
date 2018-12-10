package college.simple;

import college.DTO.SimpleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-06
 * VersionV1.0
 * @description
 */
@RestController
@Api("swaggerDemoController相关的api")
public class SimpleController {

    @PostMapping("/test/json")
    public int test(@RequestBody @Valid SimpleDTO simpleDTO) {
        return simpleDTO.getId();
    }

    @PostMapping("/test/obj")
    public int testObj(@Valid SimpleDTO simpleDTO) {
        return simpleDTO.getId();
    }

    @ApiOperation(value = "homeUser", notes = "homeUser")
    @ApiImplicitParam(name = "name", value = "学生ID", paramType = "query", required = true, dataType = "String")
    @GetMapping("/home")
    public String homeUser(@RequestParam(name = "name", required = true) String userName) {
        if (null == userName || userName.trim() == "") {
            return "you are nobody...";
        }
        return "This is  " + userName + "'s home...";
    }


}
