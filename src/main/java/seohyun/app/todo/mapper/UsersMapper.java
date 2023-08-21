package seohyun.app.todo.mapper;

import org.apache.ibatis.annotations.*;
import seohyun.app.todo.model.Users;
import java.util.List;

@Mapper
public interface UsersMapper {

//    @Delete("delete from product where id=#{id} and seller=#{seller}")
//    int delete(String id, String seller);

    @Insert("insert into users (id, user_id, password, username, phone, email)" +
            "values (#{id}, #{userId}, #{password}, #{username}, #{phone}, #{email})")
    int create(Users users);

    @Select("select * from users where user_id=#{userId}")
    Users findUserId(Users users);
}
