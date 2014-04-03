/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package srcServlet;

import javax.servlet.http.*;
import java.util.*;

/**
 *
 * @author TAM
 */
class HomePage {

    public HomePage() {
    }

    void showPage(HttpServletResponse response, int power, int temp, int swing,int fanSpeed) {
        try {
            
            response.setContentType("text/html");
            
            String html=  "<html><head><title>Welcome</title>"
                      //Style
                    + "<style> "
                    + "body {"
                            + " margin: 0px;"
                            + " background: url(img/top_bg.gif);"
                            + " background-repeat: repeat-x;"
                            + " font-family: Verdana, Arial, sans-serif;"
                            + " font-size: .6em;"
                            + "}"
                    + "p {"
                            + " line-height: 17px;"
                            + "	margin: 11px 0 10px 0;"
                            + "	padding: 0px;"
                            + "}"
                    + "h2 {"
                            + " color: #73353A;"
                            + " margin:0px;"
                            + " padding:0px;"
                            + " font-size: 15px;"
                            + "}"
                    + "ul {"
                            + "	font-size: 10px;"
                            + " margin:0; "
                            + "	padding:0; "
                            + "	list-style-image: url(img/bullet.gif);"
                            + " }"
                    + "a {"
                            + " color: #8B0000;"
                            + "}"
                    + "a:hover {"
                            + "text-decoration: none;"
                            + "}"
                    + "blockquote{"
                            + "	background: #F7FDE3;"
                            + "	color: #606060;"
                            + " padding: 10px;"
                            + "}"
                  /**** Main Container ***/
                    + "#wrap { "
                            + "margin-left: auto;" 
                            + "margin-right: auto;"
                            + "width: 730px;"
                            + "} "
                  /**** Top ***/
                    + "#top { 	width: 100%; height: 88px; color: #fff;"
                            + "	background: #000 url(img/top_bg.gif);"
                            + "	overflow:hidden;"
                            + "}"
                    + "#top h2 { color: White; letter-spacing: 3px; font-size: 2.4em;"
                            + " font-weight: normal; position: relative; margin: 0px;"
                            + "	top:33px; display:block; float:left; "
                            + "	background: url(img/logo.jpg) no-repeat;"
                            + " padding-left: 40px;"
                            + " }"
                    + "#top h2 a { color: white; text-decoration: none;"
                            + "}"
                    + "#top h2 a:hover { color: #FF5938; }"
                    /**** Main Menu ***/
                    + "#menu { display: block; float:right; }"
                    + "#menu ul { margin: 0; list-style: none; }"
                    + "#menu li { display: block; float: left; white-space: nowrap;"
                                + "}"
                    + "#menu li a { display: block; padding: 55px 20px 12px 20px;"
                                + " text-decoration: none; color: #fff;}"
                    + "* html #menu a {width:1%;} #menu li a:hover {"
                                    + "	background: #FF5938;}"
                    + "#menu li a.current { letter-spacing: 1px; color: gray;}"
                    + "#menu li a.current:hover { color: #fff; }"
                    /**** Content Container ***/
                    + "#content { width: 100%; margin-top:30px; }"
                    + "#content h2 { margin: 0; padding: 10px 0 10px 0;}"
                    /**** Content ***/
                    + "#left ul { padding: 15px 0 15px 35px; margin:0; }"
                    + "#left li { margin-bottom:5px; }"
                    + "#left { width: 350px; float:left; display: block;"
                                + " margin-left: 20px; display: inline;}"
                    /**** Clear Div ***/
                    + "#clear { display: block; clear: both; width: 100%;"
                                + " height:1px; overflow:hidden; }"
                    /**** Footer ***/
                    + "#footer { margin: 40px auto 0 auto; text-align: center;"
                                + " border-top: dotted 1px gray;"
                                + " padding: 20px 0 20px 0; width: 70%; }"
                    + "#footer p { margin: 0px; padding: 0; }"

                    /****For Table*****/

                    + ".table4 { margin:0px;padding:0px; width:500; height:200; "
                                + "border:1px solid #000000; "
                                + "-moz-border-radius-bottomleft:0px; "
                                + "-webkit-border-bottom-left-radius:0px;"
                                + "-moz-border-radius-bottomright:0px;"
                                + "-webkit-border-bottom-right-radius:0px;"
                                + "-moz-border-radius-topright:0px;"
                                + "-webkit-border-top-right-radius:0px;"
                                + "-moz-border-radius-topleft:0px;"
                                + "-webkit-border-top-left-radius:0px;"
                                + "}"
                    + ".table4 table{ width:100%; height:100%; "
                                + "margin:0px;padding:0px; "
                                + "}"
                    + ".table4 tr:last-child td:last-child {"
                                + "-moz-border-radius-bottomright:0px;"
                                + "-webkit-border-bottom-right-radius:0px;}"
                    + ".table4 table tr:first-child td:first-child {"
                                + "-moz-border-radius-topleft:0px;"
                                + "-webkit-border-top-left-radius:0px;}"
                    + ".table4 table tr:first-child td:last-child {"
                                + "-moz-border-radius-topright:0px;"
                                + "-webkit-border-top-right-radius:0px;"
                    + "}"
                    + ".table4 tr:last-child td:first-child{"
                                + "-moz-border-radius-bottomleft:0px;"
                                + "-webkit-border-bottom-left-radius:0px;"
                                + "}"
                    + ".table4 tr:hover td{"
                                + "}"
                    + ".table4 tr:nth-child(odd){ background-color:#e5e5e5; }"
                    + ".table4 tr:nth-child(even) { background-color:#ffffff; }"
                    + ".table4 td{"
                                + "vertical-align:middle;"
                                + "border:1px solid #000000;"
                                + "border-width:0px 1px 1px 0px;"
                                + "text-align:left; padding:7px; font-size:10px;"
                                + "font-family:arial; font-weight:normal;"
                                + "color:#000000; "
                                + "}"
                    + ".table4 tr:last-child td{border-width:0px 1px 0px 0px;"
                                + "}"
                    + ".table4 tr td:last-child{ border-width:0px 0px 1px 0px;"
                    + "}"
                    + ".table4 tr:last-child td:last-child{border-width:0px 0px 0px 0px;}"
                    + ".table4 tr:first-child td{ background-color:#4c4c4c;"
                    + "border:0px solid #000000; text-align:center;"
                    + "border-width:0px 0px 1px 1px; font-size:14px; font-family:arial;"
                    + "font-weight:bold; color:#ffffff; }"
                    + ".table4 tr:first-child:hover td{background-color:#4c4c4c;"
                    + "}"
                    + ".table4 tr:first-child td:first-child{border-width:0px 0px 1px 0px;"
                    + "}"
                    + ".table4 tr:first-child td:last-child{border-width:0px 0px 1px 1px;}" 

                    + ".table5 table{ width:100%; height:20%; "
                                + "margin:0px;padding:0px; "
                                + "}"

                    +"</style>"
                    +"<script>"
                    +"function exit()"
                        +"{"
                        +" window.open('E:\\TNM\\SNMPWebProject\\web\\login.jsp')"
                        +"}"
                    +"</script>"
                    +"</head>"
                    //Body
                    + "<body><h1>Welcome to the Air Conditioner Management "
                    + "Portal!</h1><form method='POST' action='HomeServlet'"
                    + "<div class='table4'><table>"
                    + "<caption>Home Air System</caption>"
                    + "<tr><th>Power</th><td>" + "<input type='radio'" +
                    " name='power' value='1' ";

         
          if (power == 1 )
            html = html +" checked";
          html = html + ">ON" + "<input type='radio'" +
                    " name='power' value='0'";
          if (power == 0 )
            html = html +" checked";
          html = html + ">OFF";
          html = html + "</td></tr>"


                    + "<tr><th>Temperature</th><td align='center'>"

                    + "<select name='temp'>";
          String temphtml = "";
          for(int i=60 ; i<=90; i++)
          {
                temphtml = temphtml + "<option value='" + i+"'";
                if(temp==i )
                    temphtml = temphtml + " selected ";
                temphtml = temphtml +">"+i+"</option>"   ;
          }
          html = html + temphtml + "</select>F</td></tr>"
                    + "<tr><th>Fan Speed</th><td> " ;
                    
              String subhtml = "";
          for (int i =  0 ; i <=3 ;  i++)
          {
              subhtml= subhtml + "<input type='radio' name='fan' value='";
              switch(i)
           {
              case 0:

                  subhtml = subhtml + i+"' ";
                  if(fanSpeed == 0)
                      subhtml = subhtml + " checked";
                  subhtml =subhtml + ">"+"OFF ";
                  break;
              case 1:
                  subhtml = subhtml + i+"' ";
                  if(fanSpeed == 1)
                      subhtml = subhtml + " checked";
                  subhtml =subhtml + ">"+"LOW ";
                  break;
              case 2:
                  subhtml = subhtml + i+"' ";
                  if(fanSpeed == 2)
                      subhtml = subhtml + " checked";
                  subhtml =subhtml + ">"+"MEDIUM ";
                  break;
              case 3:
                  subhtml = subhtml + i+"' ";
                  if(fanSpeed == 3)
                      subhtml = subhtml + " checked";
                  subhtml =subhtml + ">"+"HIGH ";
                  break;
              }
          }
           html = html + subhtml + "</td></tr>"
                    + "<tr><th>Swing</th><td align=center>"+ "<input type='radio'" +
                    " name='swing' value='1' ";

          if (swing == 1 )
            html = html +" checked";
          html = html + ">ON" + "<input type='radio'" +
                    " name='swing' value='0' ";
          if (swing == 0 )
            html = html +" checked";
          html = html + ">OFF";
          html = html + "</td></tr></table></div>"
                  +"<div class='table5'><table><tr colspan=2><td align=center> " +
                  "<input type='submit' name='get' value='Get' size='60' height='20' >" +
                  " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                  "<input type='submit' name='set' value='Set' size='60' height='20' ></td> </tr>"
                    + "</table></div>"
                    + "</body></html>";
         
        response.getWriter().println(html);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
