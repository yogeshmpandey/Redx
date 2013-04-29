package Android;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Register extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        String un, cp, cd, pw, ci, fn, pn, em, dob, ld="", bg;
        String st = "";
        int w=0;
        un = request.getParameter("username");
        fn = request.getParameter("fullname");
        ci = request.getParameter("city");
        st = request.getParameter("state");
        pn = request.getParameter("phone");
        em = request.getParameter("email");
        bg = request.getParameter("bgroup");
        cp = request.getParameter("cpassword");
        pw = request.getParameter("password");
        ld = request.getParameter("ldate");
        dob = request.getParameter("dob");
        cd = request.getParameter("cdate");
        pw = pw.replace('\'', '_');
        un = un.replace('\'', '_');
        if(un.equals(""))
            w++;
        if(fn.equals(""))
            w++;
        if(ci.equals(""))
            w++;
        if(st.equals(""))
            w++;
        if(pn.equals(""))
            w++;
        if(em.equals(""))
            w++;
        if(dob.equals(""))
            w++;
        if(pw.equals(""))
            w++;
        if(un.equals(""))
            w++;
        if (w!=0){
            out.print(5);
        } else {
            if(pn.length()!=10)
            {
                out.print(6);
            }
            else{
            PreparedStatement pst = con.prepareStatement("select * from info where username='" + un + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                out.print(0);
            } else {

                if (!cp.equals(pw)) {
                    out.print(1);
                } else {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = sdf.parse(dob);
                    Date d2 = sdf.parse(cd);
                    Date d3;
                    long age = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
                    if (age <= 6222 || age > 21960) {
                        out.print(2);
                    } else {
                        if(!ld.equals(""))
                        {
                            d3 = sdf.parse(ld);
                        age = (d2.getTime() - d3.getTime()) / (1000 * 60 * 60 * 24 * 365);
                        long diff = (d3.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24 * 365);
                        if (age < 0 && diff<=6222) {
                            w++;
                            }
                        }
                         if(w!=0)
                            out.print(3);
                        else {
                           
                            pst = con.prepareStatement("insert into info values(?,?,?,?,?,?,?,?,?,?,?)");
                            pst.setString(1, un);
                            pst.setString(2, pw);
                            pst.setString(3, fn);
                            pst.setString(4, ci);
                            pst.setString(5, st);
                            pst.setString(6, pn);
                            pst.setString(7, dob);
                            pst.setString(8, ld);
                            pst.setString(9, cd);
                            pst.setString(10, bg);
                            pst.setString(11, em);
                            int status = pst.executeUpdate();
                            if (status > 0) {
                                out.print(4);
                            } else {
                                out.print(6);
                            }
                        }
                    }
                }
                }
            }
        }
        return mapping.findForward("pass");

    }
}
