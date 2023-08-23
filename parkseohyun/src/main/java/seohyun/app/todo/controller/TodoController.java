package seohyun.app.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.todo.models.Todo;
import seohyun.app.todo.service.TodoService;
import seohyun.app.todo.utils.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // restApi를 작성할 수 있는 컨트롤러
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
@CrossOrigin(origins = "*", allowedHeaders = "*") // cors 허용
@RequestMapping("/api/v1/todo") // url을 api로 지정
public class TodoController {
    private final TodoService todoService;
    private final Jwt jwt;

    @PostMapping("/createTodo")
    public ResponseEntity<Object> createTodo(
            @RequestHeader String authorization, @RequestBody Todo todo
    ) throws Exception {
        try{
            String decoded = jwt.VerifyToken(authorization);
            Map<String, String> create = todoService.createTodo(todo, decoded);
            return new ResponseEntity<>(create, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/getalltodo")
    public ResponseEntity<Object> getAllTodo() throws Exception {
        try{
            List<Todo> todoList = todoService.getAllTodo();
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
            String decoded = jwt.VerifyToken(authorization);
            Map<String, String> update = todoService.updateTodo(todo, decoded);
            return new ResponseEntity<>(update, HttpStatus.OK);
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
            String decoded = jwt.VerifyToken(authorization);
            Map<String, String> delete = todoService.deleteTodo(id, decoded);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}