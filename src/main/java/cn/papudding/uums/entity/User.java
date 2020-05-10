package cn.papudding.uums.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String phoneNum;
    private Integer isEnable;
    private Integer isDelete;
}
