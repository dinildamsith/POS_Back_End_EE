package lk.ijse.pos_back_end.DBProcess;
import lk.ijse.pos_back_end.api.CustomerAPI;
import lk.ijse.pos_back_end.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {
    private static Logger logger = LoggerFactory.getLogger(CustomerDB.class);

    String SAVE_CUSTOMER = "INSERT INTO Customer (customer_id,customer_name,customer_mail,customer_address,customer_gender) VALUES (?,?,?,?,?)";
public void saveCustomer(CustomerDTO customerDTO, Connection connection){
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_CUSTOMER);
        preparedStatement.setString(1, customerDTO.getCustomer_Id());
        preparedStatement.setString(2, customerDTO.getCustomer_Name());
        preparedStatement.setString(3, customerDTO.getCustomer_Mail());
        preparedStatement.setString(4, customerDTO.getCustomer_Address());
        preparedStatement.setString(5, customerDTO.getCustomer_Gender());

        if (preparedStatement.executeUpdate() !=0){
            logger.info("Save Customer");
        }else{
            logger.info("Not Save Customer");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    String UPDATE_CUSTOMER ="UPDATE Customer SET customer.customer_name = ?,customer.customer_mail = ?,customer.customer_address = ?,customer.customer_gender=? WHERE customer.customer_id =?";
public void updateCustomer(CustomerDTO customerDTO,Connection connection){
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
        preparedStatement.setString(1, customerDTO.getCustomer_Name());
        preparedStatement.setString(2,customerDTO.getCustomer_Mail());
        preparedStatement.setString(3,customerDTO.getCustomer_Address());
        preparedStatement.setString(4,customerDTO.getCustomer_Gender());
        preparedStatement.setString(5,customerDTO.getCustomer_Id());

        if (preparedStatement.executeUpdate() !=0){
            logger.info("Update Customer");
        }else{
            logger.info("Not Update Customer");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    String DELETE_CUSTOMER = "DELETE FROM customer WHERE customer_id = ?";
public void deleteCustomer(CustomerDTO customerDTO,Connection connection){
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
        preparedStatement.setString(1,customerDTO.getCustomer_Id());

        if (preparedStatement.executeUpdate() !=0){
            logger.info(" Delete Customer");
        }else{
            logger.info(" Not Delete Customer");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



private void getAllCustomer(){

}


}
