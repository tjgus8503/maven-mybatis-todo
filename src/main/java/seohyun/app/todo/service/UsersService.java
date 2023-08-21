package seohyun.app.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.todo.mapper.UsersMapper;
import seohyun.app.todo.model.Users;
import seohyun.app.todo.utils.Bcrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersMapper usersMapper;
    private final Bcrypt bcrypt;

    @Transactional
    public Map<String, String> create(Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            UUID uuid = UUID.randomUUID();
            users.setId(uuid.toString());

            String hashPassword = bcrypt.HashPassword(users.getPassword());
            users.setPassword(hashPassword);

            int result = usersMapper.create(users);
            if (result == 0) {
                throw new Exception("failed");
            }
            map.put("result", "success");
            return map;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void checkUserId(Users users) throws Exception {
        try{
            Users checkUserId = usersMapper.findUserId(users);
            if (checkUserId != null) {
                throw new Exception("failed 이미 존재하는 아이디 입니다.");
            }
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Users findUserId(Users users) throws Exception {
        try{
            Users findUserId = usersMapper.findUserId(users);
            if (findUserId == null) {
                throw new Exception("failed 일치하는 아이디가 없습니다.");
            }
            return findUserId;
        } catch (Exception e){
            throw new Exception(e);
        }
    }


}
