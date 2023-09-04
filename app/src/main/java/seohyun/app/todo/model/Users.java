package seohyun.app.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private String id;
    private String userId;
    private String password;
    private String username;
    private String phone;
    private String email;
}
