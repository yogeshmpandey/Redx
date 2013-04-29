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

public class DataCamp extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        int c=0;
        Class.forName("com.mysql.jdbc.Driver");
        String user = request.getParameter("user");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        PreparedStatement pst = con.prepareStatement("select campname from camp where username='"+user+"'");
        ResultSet rs = pst.executeQuery();
        String res = "{'array':[";
        while (rs.next()) {
            c = 1;
            res = res + "{'camp':" + rs.getString(1) + "},";
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
