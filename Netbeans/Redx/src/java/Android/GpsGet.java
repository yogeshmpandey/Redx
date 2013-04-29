package Android;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

public class GpsGet extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        String us, bg,ulat="",ulon="";
        int c = 0;
        us = request.getParameter("user");
        bg = request.getParameter("bdonor");
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        PreparedStatement pst = con.prepareStatement("select * from position where user='"+us+"'");
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            ulat=rs.getString(2);
            ulon=rs.getString(3);
        }
        pst = con.prepareStatement("select * from info where bg='"+bg+"'");
        rs = pst.executeQuery();
        String res = "{'array':[";
        while (rs.next()) {
            pst = con.prepareStatement("select * from position where user='"+rs.getString(1)+"'");
            ResultSet rs1 = pst.executeQuery();
            if(rs1.next()){
            c = 1;
            res = res + "{'ulat':"+ulat+",'ulon':"+ulon+",'lat':"+rs1.getString(2)+",'lon':"+rs1.getString(3)+",'venue':" + rs.getString(4) + "("+rs.getString(5)+"),'name':" + rs.getString(3) + ",'phone':" + rs.getString(6) + ",'email':" + rs.getString(11) + "},";
            }
        }
        if (c == 0) {
            out.print(0);
        } else {
            res = res + "]}";
            JSONObject json = new JSONObject(res);
            out.print(json);
        }
         return mapping.findForward("null");
    }
}
