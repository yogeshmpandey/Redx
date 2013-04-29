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

public class SearchDonor extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        String ci, bg;
        int c = 0;
        ci = request.getParameter("city");
        bg = request.getParameter("bdonor");
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        PreparedStatement pst = con.prepareStatement("select * from info where city='" + ci + "' and bg='" + bg + "'");
        ResultSet rs = pst.executeQuery();
        String res = "{'array':[";
        while (rs.next()) {
            c = 1;
            res = res + "{'user':" + rs.getString(1) + ",'name':" + rs.getString(3) + ",'phone':" + rs.getString(6) + ",'email':" + rs.getString(11) + "},";
        }
        if (c == 0) {
            out.print(0);
        } else {
            res = res + "]}";
            JSONObject json = new JSONObject(res);
            out.print(json);           
        }
         return mapping.findForward("pass");
    }
}
