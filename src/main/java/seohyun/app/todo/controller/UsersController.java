package seohyun.app.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.todo.mapper.UsersMapper;
import seohyun.app.todo.model.Users;
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
            Map<String, String> map = new HashMap<>();

            Users findUserId = usersMapper.findUserId(users);
            if (findUserId != null) {
                map.put("result", "failed 이미 존재하는 아이디 입니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            UUID uuid = UUID.randomUUID();
            users.setId(uuid.toString());

            String hashPassword = bcrypt.HashPassword(users.getPassword());
            users.setPassword(hashPassword);

            int result = usersMapper.create(users);
            if (result == 0) {
                map.put("result", "failed");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            map.put("result", "success");
            return new ResponseEntity<>(map, HttpStatus.OK);
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

            Users findUserId = usersMapper.findUserId(users);
            if (findUserId == null) {
                map.put("result", "failed 일치하는 아이디가 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
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
