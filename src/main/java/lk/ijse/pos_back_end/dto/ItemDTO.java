package lk.ijse.pos_back_end.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDTO implements Serializable { // Follow Beans Pack
    private  String  item_Id;
    private  String  item_Name;
    private  Double  item_Price;
    private  int     item_Qty;
}
