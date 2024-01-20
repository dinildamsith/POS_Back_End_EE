package lk.ijse.pos_back_end.DBProcess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_back_end.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDB{

    String GET_USER_DETAILS = "SELECT * FROM user";

    public String getAll(Connection connection, HttpServletResponse resp) {
        resp.setContentType("application/json"); // Set content type to JSON
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_DETAILS);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<UserDTO> userDTO = new ArrayList<>();

            while (resultSet.next()){
                UserDTO userDTO1 = new UserDTO();
                userDTO1.setMail(resultSet.getString(1));
                userDTO1.setPassword(resultSet.getString(2));

                userDTO.add(userDTO1);
            }

            // Convert the list of customers to JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String userData = objectMapper.writeValueAsString(userDTO);
            return userData;

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }


}
