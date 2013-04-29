package Android;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DelCamp extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = response.getWriter();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx", "root", "password");
        String un,ci;
        un = request.getParameter("user");
        ci = request.getParameter("camp");
        un = un.replace('\'', '_');
        PreparedStatement pst = con.prepareStatement("delete from camp where username='"+un+"'and campname='"+ci+"'");
        int status = pst.executeUpdate();
        if(status>0)
            out.print(1);
        else
            out.print(0);
        return mapping.findForward("pass");

    }
}
