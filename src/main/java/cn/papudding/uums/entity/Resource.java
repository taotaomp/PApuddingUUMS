package cn.papudding.uums.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Resource implements Serializable {
    private Integer id;
    private String resourceName;
    private String resourceUrl;
    private Character resourceType;
}
