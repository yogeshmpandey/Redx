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

public class GerPro extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        int c=0;
        Class.forName("com.mysql.jdbc.Driver");
        String user = request.getParameter("user");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        PreparedStatement pst = con.prepareStatement("select * from info where username='"+user+"'");
        ResultSet rs = pst.executeQuery();
        String res = "{'array':[";
        while (rs.next()) {
            c = 1;
            String dob=rs.getString(7);
            dob=dob.replace('/','_');
            String ld=rs.getString(8);
            if(ld.equals(""))
                ld="_";
            else
            ld=ld.replace('/','_');
            res = res + "{'name':" + rs.getString(1) + ",'pass':"+rs.getString(2)+",'fn':"+rs.getString(3)+",'city':"+rs.getString(4)+",'state':"+rs.getString(5)+",'phone':"+rs.getString(6)+",'dob':"+dob+",'ld':"+ld+",'bg':"+rs.getString(10)+",'em':"+rs.getString(11)+"},";
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
