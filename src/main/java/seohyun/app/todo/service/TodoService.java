package seohyun.app.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.todo.mapper.TodoMapper;
import seohyun.app.todo.model.Todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoMapper todoMapper;

    @Transactional
    public Map<String, String> createTodo(Todo todo, String userId) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            UUID uuid = UUID.randomUUID();
            todo.setId(uuid.toString());
            todo.setUserId(userId);
            int result = todoMapper.create(todo);
            if (result == 0) {
                throw new Exception("failed");
            }
            map.put("result", "success");
            return map;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Todo> getAllTodo() throws Exception {
        try{
            return todoMapper.getAllTodo();
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public Map<String, String> updateTodo(Todo todo, String userId) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            todo.setUserId(userId);
            int result = todoMapper.update(todo);
            if (result == 0) {
                throw new Exception("failed");
            }
            map.put("result", "success");
            return map;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public Map<String, String> deleteTodo(String id, String userId) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            int result = todoMapper.delete(id, userId);
            if (result == 0) {
                throw new Exception("failed");
            }
            map.put("result", "success");
            return map;
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
