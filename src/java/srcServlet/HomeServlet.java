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
public class HomeServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            java.util.Enumeration<String> paramList = request.getParameterNames();
            int count =0;
            while (paramList.hasMoreElements()) {
                count++;
                String name = paramList.nextElement();
                if (name.equals("get")) {
                    String para = request.getParameter("get");
                    if (para.equals("Get")) {
                        get(request, response);
                        break;
                    }
                } else if (name.equals("set")) {
                    String para = request.getParameter("set");
                    if (para.equals("Set")) {
                        set(request, response);
                        break;
                    }
                }
             }
            if (count == 0)
            {
                get(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void get(HttpServletRequest request, HttpServletResponse response) {
        try {
            WebManagerSNMP.getAllValues();
            int power = Integer.parseInt(WebManagerSNMP.getPower());
            int temp = Integer.parseInt(WebManagerSNMP.getTemperature());
            int swing = Integer.parseInt(WebManagerSNMP.getSwing2());
            int fanSpeed = Integer.parseInt(WebManagerSNMP.getFanSpeed());
            new HomePage().showPage(response, power, temp, swing, fanSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(HttpServletRequest request, HttpServletResponse response) {
        try {
            WebManagerSNMP.setTemperature(request.getParameter("temp"));
            WebManagerSNMP.setPower(request.getParameter("power"));
            WebManagerSNMP.setFanSpeed(request.getParameter("fan"));
            WebManagerSNMP.setSwing2(request.getParameter("swing"));
            WebManagerSNMP.setAllValues();
            get(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
