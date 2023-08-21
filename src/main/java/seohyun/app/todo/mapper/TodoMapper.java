package seohyun.app.todo.mapper;

import org.apache.ibatis.annotations.*;
import seohyun.app.todo.model.Todo;

import java.util.List;

@Mapper
public interface TodoMapper {

    @Insert("insert into todo (id, user_id, content) values (#{id}, #{userId}, #{content})")
    int create(Todo todo);

    @Select("select * from todo")
    List<Todo> getAllTodo();

    @Update("update todo set content = #{content} where id = #{id} and user_id = #{userId}")
    int update(Todo todo);

    @Delete("delete from todo where id = #{id} and user_id = #{userId}")
    int delete(String id, String userId);
}
