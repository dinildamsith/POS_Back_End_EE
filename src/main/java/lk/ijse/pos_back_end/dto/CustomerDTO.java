package lk.ijse.pos_back_end.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO implements Serializable {  //Follow Beans Pack
    private String customer_Id;
    private String customer_Name;
    private String customer_Mail;
    private String customer_Address;
    private String customer_Gender;
}
