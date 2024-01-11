package lk.ijse.pos_back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_back_end.DBProcess.ItemDB;
import lk.ijse.pos_back_end.DBProcess.OrderDB;

import lk.ijse.pos_back_end.dto.OrderDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "order",urlPatterns = "/order", loadOnStartup = 5)
public class OrderAPI extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(OrderAPI.class);
    Jsonb jsonb = JsonbBuilder.create();
    OrderDB orderDB = new OrderDB();
    Connection connection;

    @Override
    public void init() throws ServletException {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String lastOrderId = orderDB.getAllOrders(connection, resp);
        writer.println(lastOrderId);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(),OrderDTO.class);
        orderDB.setOrderDetails(orderDTO,connection);


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(),OrderDTO.class);
        System.out.println(orderDTO);
        orderDB.deleteOrderDetails(orderDTO,connection);
    }
}
