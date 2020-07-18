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
		

		if(session.getAttribute("userID") != null)		//�̹� �α��� ���¶��
		{
			htmlContent += "<script>\n";
			htmlContent += "alert('�̹� �α����� �Ǿ� �ֽ��ϴ�.')\n";
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
				htmlContent += "alert('��й�ȣ�� Ʋ���ϴ�.')\n";
				htmlContent += "history.back()\n";
				htmlContent += "</script>";
			}else if(result==-1){
				htmlContent += "<script>\n";
				htmlContent += "alert('���̵� �������� �ʽ��ϴ�.')\n";
				htmlContent += "history.back()\n";
				htmlContent += "</script>";
			}else if(result==-2){
				htmlContent += "<script>\n";
				htmlContent += "alert('�����ͺ��̽� ������ �߻��߽��ϴ�.')\n";
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
