package seohyun.app.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.todo.mapper.UsersMapper;
import seohyun.app.todo.model.Users;
import seohyun.app.todo.service.UsersService;
import seohyun.app.todo.utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/users")
public class UsersController {

    private final Jwt jwt;
    private final Bcrypt bcrypt;
    private final UsersService usersService;
    private final UsersMapper usersMapper;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> Hello() {
        Map<String, String> map = new HashMap<>();
        map.put("result", "hello world");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody Users users) throws Exception {
        try{
            usersService.checkUserId(users);
            Map<String, String> create = usersService.create(users);
            return new ResponseEntity<>(create, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            Users findUserId = usersService.findUserId(users);
            Boolean compare = bcrypt.CompareHash(users.getPassword(), findUserId.getPassword());
            if (compare == false) {
                map.put("result", "failed 비밀번호가 일치하지 않습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            String authorization = jwt.CreateToken(users.getUserId());
            map.put("result", "success");
            map.put("authorization", authorization);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

}
