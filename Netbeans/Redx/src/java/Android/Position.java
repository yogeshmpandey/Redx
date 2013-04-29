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

public class Position extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        String un,lat,lon;
        String st = "";
        un = request.getParameter("user");
        lat = request.getParameter("latitude");
         lon = request.getParameter("longitude");
        if (un.equals("") && lat.equals("")&&lat.equals("")) {
            out.print(3);
        } else {
            PreparedStatement pst = con.prepareStatement("select * from position where user='"+un+"'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pst = con.prepareStatement("update position set lat='" + lat + "', lon='"+lon+"' where user='" + un + "'");
                int status = pst.executeUpdate();
                if (status > 0) {
                    out.print(1);
                } else {
                    out.print(4);
                }

            } else {
                pst = con.prepareStatement("insert into position values(?,?,?)");
                pst.setString(1, un);
                pst.setString(2, lat);
                pst.setString(3, lon);
                int status = pst.executeUpdate();
                if (status > 0) {
                    out.print(1);
                } else {
                    out.print(2);
                }
            }
        }
        return mapping.findForward("pass");


    }
}
