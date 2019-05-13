package io.ilss.dataobject;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author : feng
 * @description: User
 * @date : 2019-05-13 11:44
 * @version: : 1.0
 */
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

}
