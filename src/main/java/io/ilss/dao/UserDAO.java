package io.ilss.dao;

import io.ilss.dataobject.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : feng
 * @description: UserDAO
 * @date : 2019-05-13 11:46
 * @version: : 1.0
 */
@Repository
public interface UserDAO {

    List<User> listAll();
}
