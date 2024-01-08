package lk.ijse.pos_back_end.DBProcess;

import lk.ijse.pos_back_end.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDB {
    private static Logger logger = LoggerFactory.getLogger(ItemDB.class);


    String SAVE_ITEM = "INSERT INTO Item (item_id,item_description,item_unitPrice,item_qty) VALUES (?,?,?,?)";
    public void saveItem(ItemDTO itemDTO,Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ITEM);
            preparedStatement.setString(1,itemDTO.getItem_Id());
            preparedStatement.setString(2,itemDTO.getItem_Name());
            preparedStatement.setDouble(3,itemDTO.getItem_Price());
            preparedStatement.setInt(4,itemDTO.getItem_Qty());

            if (preparedStatement.executeUpdate() !=0){
                logger.info("Save Item");
            }else{
                logger.info("Not Save Item");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String UPDATE_ITEM = "UPDATE Item SET item.item_description = ?,item.item_unitPrice = ?,item.item_qty = ? WHERE item.item_id =?";
    public void updateItem(ItemDTO itemDTO,Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM);
            preparedStatement.setString(1,itemDTO.getItem_Name());
            preparedStatement.setDouble(2,itemDTO.getItem_Price());
            preparedStatement.setInt(3,itemDTO.getItem_Qty());
            preparedStatement.setString(4,itemDTO.getItem_Id());

            if (preparedStatement.executeUpdate() !=0){
                logger.info("Update Item");
            }else {
                logger.info("Not Update Item");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
