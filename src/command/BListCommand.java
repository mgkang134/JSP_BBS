package command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.Bbs;
import bbs.BbsDAO;

public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		BbsDAO dao = new BbsDAO();
		int pageNumber = 1;												//기본 페이지는 1페이지
		if(request.getAttribute("pageNumber")!=null) {					//페이지 넘버가 명시된 경우 
			pageNumber = (Integer)request.getAttribute("pageNumber");	//페이지 넘버 적용
		}else {															//페이지 넘버가 명시되지 않은 경우
			request.setAttribute("pageNumber", 1);  					//페이지 넘버 Attribute를 추가한다.
		}
		
		ArrayList<Bbs> bbss = dao.getList(pageNumber);
							
		for(Bbs bbs : bbss) { 											//list.jsp에 맞게 가공한다.
			
			//날짜 출력 형식 변경
			String bbsDate = bbs.getBbsDate();
			bbs.setBbsDate(bbsDate.substring(0, 11) + bbsDate.substring(11, 13) + "시" + bbsDate.substring(14, 16) + "분");
			
			//내용 출력 형식 변경(보안)
			String bbsContent = bbs.getBbsContent();
			bbsContent = bbsContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
			bbs.setBbsContent(bbsContent);
		}
		
		
		request.setAttribute("list", bbss);
		
		
	}

}
