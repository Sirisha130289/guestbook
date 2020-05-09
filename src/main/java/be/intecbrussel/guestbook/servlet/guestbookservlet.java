package be.intecbrussel.guestbook.servlet;

import be.intecbrussel.guestbook.dao.MessageDao;
import be.intecbrussel.guestbook.model.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/guestbook")
public class guestbookservlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        // JDBC driver name and database URL
////        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//        static final String DB_URL="jdbc:mariadb://noelvaes.eu/StudentDB";
//
//        //  Database credentials
//        static final String USER = "student";
//        static final String PASS = "student123";

        MessageDao messageDao = new MessageDao();

        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();
        String title = "Database Result";
        List<Message> allMessages = null;
        try {
            allMessages = messageDao.getAllMessages();
            System.out.println("All messages : " + allMessages);
            System.out.println(allMessages.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        writer.println("<HTML>\n" +
                " <HEAD>\n" +
                "  <TITLE>Guest book</TITLE>\n" +
                " </HEAD>\n" +
                " <BODY>\n" +
                "<H2> List of Messages retrieved from Database </H2>" +
                "</BR>" +
                "<TABLE border='1'>" +
                "<TR>" +
                "<TH>" + "Name" + "</TH>" +
                "<TH>" + "Date" + "</TH>" +
                "<TH>" + "Message" + "</TH>" +
                "</TR>");

        for (Message message : allMessages) {
            if (message.getMessage() != null && message.getMessage().length() < 40) {
                writer.println("<TR>");
                writer.println("<TD>");
                writer.println(message.getName());
                writer.println("</TD>");
                writer.println("<TD>");
                writer.println(message.getDate());
                writer.println("</TD>");
                writer.println("<TD>");
                writer.println(message.getMessage());
                writer.println("</TD>");
                writer.println("</TR>");
            }
        }

        writer.println("</TABLE>" +

                "  <H2>Enter a message:</H2>\n" +
                "  <FORM ACTION=\"guestbook\" METHOD=\"POST\">\n" +
                "  <TABLE>\n" +

                "   <TR><TD>Name:\n" +
                "   <TD><INPUT TYPE=\"TEXT\" NAME=\"username\">\n" +
                "   <TR><TD>Date:\n" +
                "   <TD><INPUT TYPE=\"datetime-local\" NAME=\"datetime\">\n" +
                "   <TR><TD VALIGN=\"TOP\">Message:\n" +
                "   <TD><TEXTAREA ROWS=\"5\" COLS=\"35\" NAME=\"message\"></TEXTAREA>\n" +
                "   <TR><TD COLSPAN=\"2\" ALIGN=\"CENTER\"><INPUT TYPE=\"SUBMIT\">\n" +
                "  </TABLE>\n" +
                "  </FORM>\n" +
                " </BODY>\n" +
                "</HTML>"
        );
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Message message = new Message();
        message.setName(req.getParameter("username"));
        message.setDate(LocalDateTime.parse(req.getParameter("datetime")));
        message.setMessage(req.getParameter("message"));

        System.out.println("Entered doPost");
        MessageDao dao = new MessageDao();

        try {
            dao.createMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.getWriter().println("Successfully inserted message to DB");
    }
    //    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding("UTF-8");
//
//        String name = req.getParameter("name") != null && !"".equals(req.getParameter("name")) ? req.getParameter("name") : "Guest";
//
//        PrintWriter writer = resp.getWriter();
//        writer.println("<html lang=\"en\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <title>getservlet</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<h1>" + "hello " +
//                name +
//                "<br></br>" + " You reached the POST Servlet" + "<br></br>" +
//                " <a href='getservlet'>Back to the GET Servlet</a>" +
//                "<br></br>" +
//                "  <input type=\"submit\" value=\"back to the GET Servlet\">\n"
//                + "</h1>\n" +
//                "</body>\n" +
//                "</html>");
//        writer.close();
//    }
}

