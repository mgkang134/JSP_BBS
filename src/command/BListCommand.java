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
		int pageNumber = 1;														//�⺻ �������� 1������
		if(request.getParameter("pageNumber")!=null) {							//������ �ѹ��� ��õ� ���(URL get�������)
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));	//������ �ѹ� ����
		}
		request.setAttribute("pageNumber", pageNumber);
		
		String query = null;													//�˻� ������ �Ķ���ͷ� �Ѿ���� ���
		query = request.getParameter("query");
		
		//����¡ ó��
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
							
		for(Bbs bbs : bbss) { 											//list.jsp�� �°� �����Ѵ�.
			
			//��¥ ��� ���� ����
			String bbsDate = bbs.getBbsDate();
			bbs.setBbsDate(bbsDate.substring(0, 11) + bbsDate.substring(11, 13) + "시" + bbsDate.substring(14, 16) + "분");
			
			//���� ��� ���� ����(����)
			String bbsContent = bbs.getBbsContent();
			bbsContent = bbsContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
			bbs.setBbsContent(bbsContent);
			
			//���� ��� ���� ����(����)
			String bbsTitle = bbs.getBbsTitle();
			bbsTitle = bbsTitle.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
			bbs.setBbsTitle(bbsTitle);
		}
		
		
		request.setAttribute("list", bbss);
		
		
	}

}
