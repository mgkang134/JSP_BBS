package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;

public class BLoginCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response){
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String htmlContent = "";
		

		if(session.getAttribute("userID") != null)		//이미 로그인 상태라면
		{
			htmlContent += "<script>\n";
			htmlContent += "alert('이미 로그인이 되어 있습니다.')\n";
			htmlContent += "history.back()\n";
			htmlContent += "</script>";
			
		}else {
			UserDAO userDAO = new UserDAO();
			int result  = userDAO.Login(request.getParameter("userID"), request.getParameter("userPassword"));
			
			if(result == 1){
				session.setAttribute("userID", request.getParameter("userID"));

				htmlContent += "<script>\n";
				htmlContent += "location.href ='main.jsp'\n";
				htmlContent += "</script>";
			}else if(result==0){
				htmlContent += "<script>\n";
				htmlContent += "alert('비밀번호가 틀립니다.')\n";
				htmlContent += "history.back()\n";
				htmlContent += "</script>";
			}else if(result==-1){
				htmlContent += "<script>\n";
				htmlContent += "alert('아이디가 존재하지 않습니다.')\n";
				htmlContent += "history.back()\n";
				htmlContent += "</script>";
			}else if(result==-2){
				htmlContent += "<script>\n";
				htmlContent += "alert('데이터베이스 오류가 발생했습니다.')\n";
				htmlContent += "history.back()\n";
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
