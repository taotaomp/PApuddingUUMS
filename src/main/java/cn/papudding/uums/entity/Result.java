package cn.papudding.uums.entity;

import lombok.Data;

/**
 * @program: uums
 * @description: 返回结果
 * @author: Hu Songtao
 * @create: 2021-01-27 11:34
 **/
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static <T>Result<T> success(){
        return successWithData("成功",null);
    }

    public static <T>Result<T> successWithData(String message,T data){
        return new Result<T>(200, message, data);
    }

    public static <T>Result<T> failed(){
        return failedWithData("失败",null);
    }

    public static <T>Result<T> failedWithData(String message,T data){
        return new Result<T>(-1, message, data);
    }
}
