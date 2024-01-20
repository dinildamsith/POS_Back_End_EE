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

    String GET_ALL_ORDERS = "SELECT * FROM order_details";
    public String getAllOrders(Connection connection, HttpServletResponse resp){
        resp.setContentType("application/json"); // Set content type to JSON

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<OrderDTO> getAllOrders = new ArrayList<>();

            while (resultSet.next()){
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrder_Id(resultSet.getString(1));
                orderDTO.setCustomer_Id(resultSet.getString(2));
                orderDTO.setDate(resultSet.getString(3));

                getAllOrders.add(orderDTO);
            }


            // Convert the list of customers to JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String getAllOrders_Json = objectMapper.writeValueAsString(getAllOrders);
            return getAllOrders_Json;

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



    String SAVE_ORDER_DETAILS = "INSERT INTO order_details(order_id,customer_id,date) VALUES (?,?,?)";
    String PLACE_ORDER = "INSERT INTO orders (order_id,item_name,qty,total) VALUES (?,?,?,?)";
    @SneakyThrows
    public void setOrderDetails(OrderDTO orderDTO, Connection connection){

        try {
            // Disable auto-commit to start a transaction
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ORDER_DETAILS);
            preparedStatement.setString(1, orderDTO.getOrder_Id());
            preparedStatement.setString(2, orderDTO.getCustomer_Id());
            preparedStatement.setString(3, orderDTO.getDate());

            logger.info("Save Order Details");
            // Execute the first query
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement(PLACE_ORDER);
            preparedStatement1.setString(1, orderDTO.getOrder_Id());
            preparedStatement1.setString(2, orderDTO.getItem_Name());
            preparedStatement1.setInt(3, orderDTO.getQty());
            preparedStatement1.setDouble(4, orderDTO.getTotal());

            // Execute the second query
            preparedStatement1.executeUpdate();
            logger.info("Place Order");
            // If everything is successful, commit the transaction
            connection.commit();
        } catch (Exception e) {
            // If any exception occurs, rollback the transaction
            connection.rollback();
            e.printStackTrace();
        } finally {
            // Always reset auto-commit to true and close resources in the 'finally' block
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    String DELETE_ORDER = "DELETE FROM order_details WHERE order_id = ?";
    public void deleteOrderDetails(OrderDTO orderDTO,Connection connection){
        System.out.println(orderDTO.getOrder_Id());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER);
            preparedStatement.setString(1,orderDTO.getOrder_Id());

            if (preparedStatement.executeUpdate() !=0){
                logger.info("Delete");
            }else{
                logger.info("Not Delete");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
