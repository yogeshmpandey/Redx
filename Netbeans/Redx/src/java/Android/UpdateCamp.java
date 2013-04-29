package Android;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateCamp extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        String un, cn, sd, st, ed, et, ci, state;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        cn = request.getParameter("campname");
        un = request.getParameter("username");
            sd = request.getParameter("startday");
            st = request.getParameter("starttime");
            ci = request.getParameter("city");
            state = request.getParameter("state");
            ed = request.getParameter("endday");
            et = request.getParameter("etime");
           int w=0;
         if (un.equals("")) {
            w++;
        }
         if (cn.equals("")) {
            w++;
        }
         if (sd.equals("")) {
            w++;
        }
         if (st.equals("")) {
            w++;
        }
         if (ed.equals("")) {
            w++;
        }
         if (et.equals("")) {
            w++;
        }
         if (ci.equals("")) {
            w++;
        }
         if (state.equals("")) {
            w++;
        }
        if (w!=0) {
            out.print(4);
        }   else{
        PreparedStatement pst = con.prepareStatement("select * from camp where campname='"+cn+"'");
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            out.print(0);
        } else {
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date d1 = sdf.parse(sd);
            Date d2 = sdf.parse(ed);
            long age = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24 * 365);
            if (age < 0) {
                out.print(1);
            } else {
                String sql ="startday='"+sd+"' , starttime='"+st+"' , endday='"+ed+"' , endtime='"+et+"' , city='"+ci+"' , state='"+state+"'";
                pst = con.prepareStatement("update camp set "+sql+" where username='"+un+"' and campname='"+cn+"'");
                int status = pst.executeUpdate();
                if (status > 0) {
                    out.print(2);
                } else {
                    out.print(3);
                }

            }
                }
        }
        return mapping.findForward("pass");
    }
}
