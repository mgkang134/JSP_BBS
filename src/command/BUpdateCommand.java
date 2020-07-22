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
			htmlContent += "alert('로그인을 하세요')\n";
			htmlContent += "location.href = 'login_view.jsp'\n";
			htmlContent += "</script>";
		}
		
		int bbsID = 0;
		if(request.getParameter("bbsID") != null){
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
			htmlContent += "<script>\n";
			htmlContent += "alert('유효하지 않은 글입니다.')\n";
			htmlContent += "location.href = 'list.do'\n";
			htmlContent += "</script>";
		}
		
		Bbs bbs = new BbsDAO().getBbs(bbsID);
		if(!userID.equals(bbs.getUserID())){
			htmlContent += "<script>\n";
			htmlContent += "alert('권한이 없습니다.')\n";
			htmlContent += "location.href = 'list.do'\n";
			htmlContent += "</script>";
		}else{
			if(request.getParameter("bbsTitle") == null || request.getParameter("bbsContent") == null
					|| request.getParameter("bbsTitle").equals("") || request.getParameter("bbsContent").equals("")){
				
						htmlContent += "<script>\n";
						htmlContent += "alert('입력이 안된 사항이 있습니다.')\n";
						htmlContent += "history.back()\n";
						htmlContent += "</script>";
					}else{
						BbsDAO bbsDAO = new BbsDAO();
						int result  = bbsDAO.update(bbsID, request.getParameter("bbsTitle"), request.getParameter("bbsContent"));
						if(result == -1){
							htmlContent += "<script>\n";
							htmlContent += "alert('글 수정에 실패했습니다.')\n";
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
