package lk.ijse.pos_back_end.DBProcess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_back_end.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    String DELETE_ITEM = "DELETE FROM item WHERE item_id = ?";
    public void deleteItem(ItemDTO itemDTO,Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM);
            preparedStatement.setString(1,itemDTO.getItem_Id());
            if (preparedStatement.executeUpdate() !=0){
                logger.info("Delete Item");
            }else{
                logger.info("Not Delete Item");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    String GET_ALL_ITEMS = "SELECT * FROM Item";
    public String getAllItems(Connection connection, HttpServletResponse resp){
        resp.setContentType("application/json"); // Set content type to JSON

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ITEMS);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<ItemDTO> allItems = new ArrayList<>();

            while (resultSet.next()){
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setItem_Id(resultSet.getString(1));
                itemDTO.setItem_Name(resultSet.getString(2));
                itemDTO.setItem_Price(resultSet.getDouble(3));
                itemDTO.setItem_Qty(resultSet.getInt(4));

                allItems.add(itemDTO);
            }
            // Convert the list of customers to JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String allItemJson = objectMapper.writeValueAsString(allItems);

            return allItemJson;

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
