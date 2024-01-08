package lk.ijse.pos_back_end.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_back_end.DBProcess.CustomerDB;
import lk.ijse.pos_back_end.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "customer",urlPatterns = "/customer",loadOnStartup = 5)
public class CustomerAPI extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(CustomerAPI.class);
    CustomerDB customerDB = new CustomerDB();
    Connection connection;



    @Override
    public void init() throws ServletException {
        logger.info("Init Lode");
        try {
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            System.out.println(pool);
            this.connection = pool.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
        customerDB.saveCustomer(customerDTO ,connection);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
        customerDB.updateCustomer(customerDTO,connection);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
        customerDB.deleteCustomer(customerDTO,connection);
    }


    String GET_ALL_CUSTOMER = "SELECT * FROM CUSTOMER";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json"); // Set content type to JSON
        PrintWriter writer = resp.getWriter();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CUSTOMER);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<CustomerDTO> allCustomer = new ArrayList<>();

            while (resultSet.next()){

                CustomerDTO customerDTO = new CustomerDTO();

                customerDTO.setCustomer_Id(resultSet.getString(1));
                customerDTO.setCustomer_Name(resultSet.getString(2));
                customerDTO.setCustomer_Mail(resultSet.getString(3));
                customerDTO.setCustomer_Address(resultSet.getString(4));
                customerDTO.setCustomer_Gender(resultSet.getString(5));

                allCustomer.add(customerDTO);

            }
            System.out.println(allCustomer);
            // Convert the list of customers to JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String allCustomerJson = objectMapper.writeValueAsString(allCustomer);

            // Write the JSON data to the response
            writer.println(allCustomerJson);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
