package Android;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class SearchCamps extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        String cd, ci;
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        int c = 0;
        ci = request.getParameter("state");
        cd = request.getParameter("cd");
        Date d1 = sdf.parse(cd);
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        PreparedStatement pst = con.prepareStatement("select * from camp where state='"+ci+"' order by startday,starttime");
        ResultSet rs = pst.executeQuery();
        String res = "{'array':[";
        while (rs.next()) {
            String sd = rs.getString(3);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d2 = sdf.parse(sd);
            long age = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
             sd = change(rs.getString(3));
             String ed = change(rs.getString(5));
            if(age>=0)
            {
            c = 1;
            String name = rs.getString(2);
            String time =sd + "(" + rs.getString(4) + ")-" + ed + "(" + rs.getString(6) + ")";
            time = time.replace('/', '_');
            time = time.replace(':', '.');
            String venue = rs.getString(7) + "(" + rs.getString(8) + ")";
            venue = venue.replace(':', '.');
            res = res + "{'name':" + name + ",'time':" + time + ",'venue':" + venue + "},";
            }
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
     public String change(String d)
    {
        String [] rd = d.split("-");
        String s="";
        s = rd[2]+"/"+rd[1]+"/"+rd[0];
        return s;
    }
}
