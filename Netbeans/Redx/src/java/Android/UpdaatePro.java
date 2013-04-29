package Android;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdaatePro extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        String un, cp, cd, pw, ci, fn, st, pn, em, dob, ld, bg;
        un = request.getParameter("username");
        un = un.replace('\'', '_');
        cp = request.getParameter("cpassword");
        pw = request.getParameter("password");
        ld = request.getParameter("ldate");
        dob = request.getParameter("dob");
        cd = request.getParameter("cdate");
        fn = request.getParameter("fullname");
        ci = request.getParameter("city");
        st = request.getParameter("state");
        pn = request.getParameter("phone");
        em = request.getParameter("email");
        bg = request.getParameter("bgroup");
        pw = pw.replace('\'', '_');
        int w = 0;
        if (un.equals("")) {
            w++;
        }
        if (fn.equals("")) {
            w++;
        }
        if (ci.equals("")) {
            w++;
        }
        if (st.equals("")) {
            w++;
        }
        if (pn.equals("")) {
            w++;
        }
        if (em.equals("")) {
            w++;
        }
        if (dob.equals("")) {
            w++;
        }
        if (pw.equals("")) {
            w++;
        }
        if (un.equals("")) {
            w++;
        }
        if (w != 0) {
            out.print(5);
        } else {
            if (!cp.equals(pw)) {
                out.print(1);
            } else {
                if (pn.length() != 10) {
                    out.print(6);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = sdf.parse(dob);
                    Date d2 = sdf.parse(cd);
                    long age = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
                    if (age <= 6222 || age > 21960) {
                        out.print(2);
                    } else {
                        if (!ld.equals("")) {
                            Date d3 = sdf.parse(ld);
                            age = (d2.getTime() - d3.getTime()) / (1000 * 60 * 60 * 24 * 365);
                            long diff = (d3.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24 * 365);
                            if (age < 0 && diff<=6222) {
                                w++;
                            }
                        }
                        if (w != 0) {
                            out.print(3);
                        } else {
                            String sql = "password='" + pw + "',fullname='" + fn + "',city='" + ci + "',state='" + st + "',phone='" + pn + "',dob='" + dob + "',ldate='" + ld + "',cdate='" + cd + "',bg='" + bg + "',email='" + em + "'";
                            PreparedStatement pst = con.prepareStatement("update info set " + sql + " where username='" + un + "'");
                            int status = pst.executeUpdate();
                            if (status > 0) {
                                out.print(4);
                            } else {
                                out.print(0);
                            }
                        }
                    }
                }
            }
        }
        return mapping.findForward("pass");

    }
}
