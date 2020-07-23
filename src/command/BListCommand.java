package command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.Bbs;
import bbs.BbsDAO;
import paging.Paging;

public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		BbsDAO dao = new BbsDAO();
		int pageNumber = 1;														//기본 페이지는 1페이지
		if(request.getParameter("pageNumber")!=null) {							//페이지 넘버가 명시된 경우(URL get방식으로)
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));	//페이지 넘버 적용
		}
		request.setAttribute("pageNumber", pageNumber);
		
		String query = null;													//검색 쿼리가 파라미터로 넘어왔을 경우
		query = request.getParameter("query");
		
		//페이징 처리
		Paging paging;
		
		if(query==null) {
			paging = new Paging(pageNumber, dao.getTotalNum());
		}else {
			paging = new Paging(pageNumber, dao.getTotalNum(query));
		}
				
		request.setAttribute("paging", paging);
		
		ArrayList<Bbs> bbss;
		
		if(query==null) {
			bbss = dao.getList(pageNumber);
		}else {
			bbss = dao.getList(pageNumber, query);
		}
							
		for(Bbs bbs : bbss) { 											//list.jsp에 맞게 가공한다.
			
			//날짜 출력 형식 변경
			String bbsDate = bbs.getBbsDate();
			bbs.setBbsDate(bbsDate.substring(0, 11) + bbsDate.substring(11, 13) + "시" + bbsDate.substring(14, 16) + "분");
			
			//내용 출력 형식 변경(보안)
			String bbsContent = bbs.getBbsContent();
			bbsContent = bbsContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
			bbs.setBbsContent(bbsContent);
			
			//제목 출력 형식 변경(보안)
			String bbsTitle = bbs.getBbsTitle();
			bbsTitle = bbsTitle.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
			bbs.setBbsTitle(bbsTitle);
		}
		
		
		request.setAttribute("list", bbss);
		
		
	}

}
