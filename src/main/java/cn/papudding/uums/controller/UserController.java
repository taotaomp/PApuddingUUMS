package cn.papudding.uums.controller;

import cn.papudding.uums.entity.Result;
import cn.papudding.uums.entity.User;
import cn.papudding.uums.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: uums
 * @description: 用户Controller
 * @author: Hu Songtao
 * @create: 2021-01-27 11:32
 **/
@RestController
@RequestMapping("/userManage")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@RequestBody @Valid User user, BindingResult bindingResult){
        for (ObjectError error : bindingResult.getAllErrors()) {
            return Result.failedWithData(error.getDefaultMessage(),null);
        }
        boolean result = userService.register(user);
        return result ? Result.success():Result.failed();
    }
}
