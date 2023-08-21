package seohyun.app.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.todo.mapper.TodoMapper;
import seohyun.app.todo.model.Todo;
import seohyun.app.todo.utils.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/todo")
public class TodoController {
    private final TodoMapper todoMapper;
    private final Jwt jwt;

    @PostMapping("/createtodo")
    public ResponseEntity<Object> createTodo(
            @RequestHeader String authorization, @RequestBody Todo todo
            ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            UUID uuid = UUID.randomUUID();
            todo.setId(uuid.toString());
            todo.setUserId(decoded);
            int result = todoMapper.create(todo);
            if (result == 0) {
                map.put("result", "failed");
            }
            else {
                map.put("result", "success");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/getalltodo")
    public ResponseEntity<Object> getAllTodo() throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            List<Todo> todoList = todoMapper.getAllTodo();
            return new ResponseEntity<>(todoList, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("/updatetodo")
    public ResponseEntity<Object> updateTodo(
            @RequestHeader String authorization, @RequestBody Todo todo
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            todo.setUserId(decoded);
            int result = todoMapper.update(todo);
            if (result == 0) {
                map.put("result", "failed");
            } else {
                map.put("result", "success");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("deletetodo")
    public ResponseEntity<Object> deleteTodo(
            @RequestHeader String authorization, @RequestParam String id
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            int result = todoMapper.delete(id, decoded);
            if (result == 0) {
                map.put("result", "failed");
            } else {
                map.put("result", "success");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
