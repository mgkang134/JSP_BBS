package command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.BbsDAO;
import user.UserDAO;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String htmlContent = "";
		String bbsTitle = request.getParameter("bbsTitle");
		String bbsContent = request.getParameter("bbsContent");
		String userID = (String)session.getAttribute("userID");
		

		if(session.getAttribute("userID") == null)		//로그인 상태가 아니라면
		{
			htmlContent += "<script>\n";
			htmlContent += "alert('로그인을 하세요.')\n";
			htmlContent += "location.href = 'login_view.do'\n";
			htmlContent += "</script>";
			
		}else {
			BbsDAO bbsDAO = new BbsDAO();
			int result  = bbsDAO.write(bbsTitle, userID, bbsContent);
			
			if(result == -1){
				htmlContent += "<script>\n";
				htmlContent += "alert('글쓰기에 실패했습니다.')\n";
				htmlContent += "history.back()\n";
				htmlContent += "</script>";
			}
			else{
				htmlContent += "<script>\n";
				htmlContent += "location.href ='list.do'\n";
				htmlContent += "</script>";
			}
			
			
		}
		
		try {
			PrintWriter script = response.getWriter();
			script.print(htmlContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
