package login;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class loginaction extends org.apache.struts.action.Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out=response.getWriter();
        String un,pw;
        un=request.getParameter("username");
        pw=request.getParameter("password");

        if(un.equalsIgnoreCase("prashant") && pw.equals("sharma"))
            out.print(1);
        else
            out.print(0);
        return mapping.findForward("pass");
    }
}
