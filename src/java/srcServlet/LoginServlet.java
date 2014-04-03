/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package srcServlet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author TAM
 */
public class LoginServlet extends HttpServlet{

    private String username = "admin";
    private String password = "12345";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {

            java.util.Enumeration<String> paramList = request.getParameterNames();
            int count =0;
            while (paramList.hasMoreElements()) {
                count++;
                String name = paramList.nextElement();
                if (name.equals("user_name")) {
                    response.setContentType("text/html");
                    if (request.getParameter("user_name").equals(username) &&
                            request.getParameter("password").equals(password))
                    {
                        WebManagerSNMP.getAllValues();
                        int power = Integer.parseInt(WebManagerSNMP.getPower());
                        int temp =Integer.parseInt(WebManagerSNMP.getTemperature());
                        int swing =Integer.parseInt(WebManagerSNMP.getSwing2());
                        int fanSpeed=Integer.parseInt(WebManagerSNMP.getFanSpeed());
                        new HomePage().showPage(response, power, temp, swing, fanSpeed);
                    }
                    else if (request.getParameter("power").equals("0")
                            ||request.getParameter("power").equals("1") )
                    {
                        WebManagerSNMP.getAllValues();
                        int power = Integer.parseInt(WebManagerSNMP.getPower());
                        int temp =Integer.parseInt(WebManagerSNMP.getTemperature());
                        int swing =Integer.parseInt(WebManagerSNMP.getSwing2());
                        int fanSpeed=Integer.parseInt(WebManagerSNMP.getFanSpeed());
                        new HomePage().showPage(response, power, temp, swing, fanSpeed);
                    }
            }
            }
            if (count == 0)
                response.sendRedirect("/SNMPWebProject/HomeServlet");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendRedirect("/SNMPWebProject/");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
