package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UserDAO;

public class BJoinCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		
		String htmlContent = "";
		
		String sessionID = null;
		if(session.getAttribute("userID") != null){
			sessionID = (String) session.getAttribute("userID");
		}

		if(sessionID != null){
			
			htmlContent += "<script>\n";
			htmlContent += "alert('이미 로그인이 되어 있습니다.')\n";
			htmlContent += "location.href = 'main.jsp'\n";
			htmlContent += "</script>";
		}
		
		if(userID=="" || userPassword == "" || userName == "" || userGender == "" || userEmail == ""){
			
			htmlContent += "<script>\n";
			htmlContent += "alert('입력이 안된 사항이 있습니다.')\n";
			htmlContent += "history.back()\n";
			htmlContent += "</script>";
		}else{
			UserDAO userDAO = new UserDAO();
			
			User user = new User();
			user.setUserEmail(userEmail);
			user.setUserGender(userGender);
			user.setUserID(userID);
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			
			int result  = userDAO.join(user);
			if(result == -1){
				
				htmlContent += "<script>\n";
				htmlContent += "alert('이미 존재하는 아이디입니다.')\n";
				htmlContent += "history.back()\n";
				htmlContent += "</script>";

			}else{
				session.setAttribute("userID", userID);
				
				htmlContent += "<script>\n";
				htmlContent += "location.href = 'main.jsp'\n";
				htmlContent += "</script>";
			}
		}
		
		try {
			PrintWriter writer = response.getWriter();
			writer.print(htmlContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
