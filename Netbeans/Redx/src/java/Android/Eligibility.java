package Android;
import java.util.Date;
import java.text.SimpleDateFormat;
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

public class Eligibility extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        int c=0;
        Class.forName("com.mysql.jdbc.Driver");
        String user = request.getParameter("user");
        String cd = request.getParameter("cd");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        PreparedStatement pst = con.prepareStatement("select ldate from info where username='"+user+"'");
        ResultSet rs = pst.executeQuery();
        String message="";
        String res = "{'array':[";
        if (rs.next()) {
            c = 1;
            String ld=rs.getString(1);
            if(ld.equals(""))
                ld="Blood Not Donated.";
            else
            {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = sdf.parse(ld);
                    Date d2 = sdf.parse(cd);
                    long diff = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
                    if(diff>=56)
                    {
                        diff = diff -56;
                       message = diff+" days exceded..";
                    }
                    else
                    {
                        diff = 56-diff;
                        message = diff+" days left";
                    }
            }
            res = res + "{'elig':" +message+ "},";
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
