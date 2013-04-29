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

public class Login extends org.apache.struts.action.Action {
    
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        PrintWriter out=response.getWriter();
        String un,pw;
        un=request.getParameter("username");
        un = un.replace('\'', '_');
        pw=request.getParameter("password");
        pw = pw.replace('\'', '_');
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/redx","root","password");
        PreparedStatement pst = con.prepareStatement("select * from info where username='"+un+"' and password='"+pw+"'");
        ResultSet rs = pst.executeQuery();
        if(rs.next())
            out.print(1);
        else
            out.print(0);
         return mapping.findForward("pass");
    }
}
