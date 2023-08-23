package seohyun.app.todo.mapper;

import org.apache.ibatis.annotations.*;
import seohyun.app.todo.models.Todo;

import java.util.List;

@Mapper // 마커 인터페이스. 매퍼임을 표시.
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
