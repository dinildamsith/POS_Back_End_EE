package lk.ijse.pos_back_end.DBProcess;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_back_end.dto.OrderDTO;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDB {

    private static Logger logger = LoggerFactory.getLogger(OrderDB.class);

    String GET_LAST_ORDER_ID = "SELECT order_Id FROM order_details ORDER BY order_Id DESC LIMIT 1";
    public String getLastOrderId(Connection connection, HttpServletResponse resp){
        resp.setContentType("application/json"); // Set content type to JSON

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ORDER_ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<String> lastOrderId = new ArrayList<>();

            while (resultSet.next()){
                lastOrderId.add(resultSet.getString(1));
            }

            // Convert the list of customers to JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String lastOrder_Id_Json = objectMapper.writeValueAsString(lastOrderId);
            return lastOrder_Id_Json;

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



    String SAVE_ORDER_DETAILS = "INSERT INTO order_details(order_id,customer_id,date) VALUES (?,?,?)";
    String PLACE_ORDER = "INSERT INTO orders (order_id,item_name,qty,total) VALUES (?,?,?,?)";
    @SneakyThrows
    public void setOrderDetails(OrderDTO orderDTO, Connection connection){

        System.out.println(orderDTO);
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ORDER_DETAILS);
        preparedStatement.setString(1,orderDTO.getOrder_Id());
        preparedStatement.setString(2,orderDTO.getCustomer_Id());
        preparedStatement.setString(3,orderDTO.getDate());

        if (preparedStatement.executeUpdate() !=0){
            logger.info("Save");
            PreparedStatement preparedStatement1 = connection.prepareStatement(PLACE_ORDER);
            preparedStatement1.setString(1,orderDTO.getOrder_Id());
            preparedStatement1.setString(2,orderDTO.getItem_Name());
            preparedStatement1.setInt(3,orderDTO.getQty());
            preparedStatement1.setDouble(4,orderDTO.getTotal());

            if (preparedStatement1.executeUpdate() !=0){
                logger.info("Save");
            }else {
                logger.info("Not Save");
            }
        }else{

        }



    }


}
