package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.Bbs;
import bbs.BbsDAO;

public class BUpdateCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String htmlContent = "";
		
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}

		if(userID == null){
			htmlContent += "<script>\n";
			htmlContent += "alert('�α����� �ϼ���')\n";
			htmlContent += "location.href = 'login_view.jsp'\n";
			htmlContent += "</script>";
		}
		
		int bbsID = 0;
		if(request.getParameter("bbsID") != null){
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
			htmlContent += "<script>\n";
			htmlContent += "alert('��ȿ���� ���� ���Դϴ�.')\n";
			htmlContent += "location.href = 'list.do'\n";
			htmlContent += "</script>";
		}
		
		Bbs bbs = new BbsDAO().getBbs(bbsID);
		if(!userID.equals(bbs.getUserID())){
			htmlContent += "<script>\n";
			htmlContent += "alert('������ �����ϴ�.')\n";
			htmlContent += "location.href = 'list.do'\n";
			htmlContent += "</script>";
		}else{
			if(request.getParameter("bbsTitle") == null || request.getParameter("bbsContent") == null
					|| request.getParameter("bbsTitle").equals("") || request.getParameter("bbsContent").equals("")){
				
						htmlContent += "<script>\n";
						htmlContent += "alert('�Է��� �ȵ� ������ �ֽ��ϴ�.')\n";
						htmlContent += "history.back()\n";
						htmlContent += "</script>";
					}else{
						BbsDAO bbsDAO = new BbsDAO();
						int result  = bbsDAO.update(bbsID, request.getParameter("bbsTitle"), request.getParameter("bbsContent"));
						if(result == -1){
							htmlContent += "<script>\n";
							htmlContent += "alert('�� ������ �����߽��ϴ�.')\n";
							htmlContent += "history.back()\n";
							htmlContent += "</script>";
						}else{
							htmlContent += "<script>\n";
							htmlContent += "location.href = 'list.do'\n";
							htmlContent += "</script>";
						}
					}
		}
		
		PrintWriter script;
		try {
			script = response.getWriter();
			script.write(htmlContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
