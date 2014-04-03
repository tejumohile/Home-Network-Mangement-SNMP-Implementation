<%-- 
    Document   : index
    Created on : Jul 27, 2013, 12:57:15 PM
    Author     : TAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login to Home Appliance Management</title>
        <style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
        width: 50px;
        height: 100px;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
        text-align: center
}

h4 {
        background-color: #dedede;
        text-align: center;
}

</style>

    </head>
    <body>

        <h4> Login to Home Appliance Management</h4>
        <form method="POST" action="LoginServlet">
        <div class="table4">

        
            <table class="gridtable">
                <colgroup>
                    <col width="50%">
                    <col width="50%">
                </colgroup>
                <tr>
                    <th> Username  </th>
                    <td> <input type="text" name="user_name" autofill="off" ></td>
                </tr>
                <tr>
                    <th> Password </th>
                    <td> <input type="password" name="password" autofill="off"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="OK" size="70"
                                           align="center"></td>
                </tr>
            </table>
        

        </div>
            </form>
    </body>
</html>
