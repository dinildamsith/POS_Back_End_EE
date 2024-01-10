package lk.ijse.pos_back_end.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO implements Serializable {
    private String customer_Id;
    private String order_Id;
    private String date;
    private String item_Name;
    private int    qty;
    private Double total;








}
