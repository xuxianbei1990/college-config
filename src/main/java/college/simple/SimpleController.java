package college.simple;

import college.DTO.SimpleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicInteger;

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

    private AtomicInteger iPut = new AtomicInteger(0);

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

    @GetMapping(value = "/test", produces = "application/text;charset=utf-8")
    public String test() {
        return "success乱码";
    }

    @GetMapping("/test/exception")
    public String testException() {
        throw new NullPointerException("又翻车啦");
    }

    @GetMapping("/test/runtime/exception")
    public String testRuntimeException() {
        throw new RuntimeException("翻车军降世");
    }

    @PutMapping("/test/put")
    public String testPut() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return String.valueOf(iPut.incrementAndGet());
    }

}
