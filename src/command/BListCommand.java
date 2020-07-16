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
		int pageNumber = 1;												//�⺻ �������� 1������
		if(request.getAttribute("pageNumber")!=null) {					//������ �ѹ��� ��õ� ��� 
			pageNumber = (Integer)request.getAttribute("pageNumber");	//������ �ѹ� ����
		}else {															//������ �ѹ��� ��õ��� ���� ���
			request.setAttribute("pageNumber", 1);  					//������ �ѹ� Attribute�� �߰��Ѵ�.
		}
		
		ArrayList<Bbs> bbss = dao.getList(pageNumber);
							
		for(Bbs bbs : bbss) { 											//list.jsp�� �°� �����Ѵ�.
			
			//��¥ ��� ���� ����
			String bbsDate = bbs.getBbsDate();
			bbs.setBbsDate(bbsDate.substring(0, 11) + bbsDate.substring(11, 13) + "��" + bbsDate.substring(14, 16) + "��");
			
			//���� ��� ���� ����(����)
			String bbsContent = bbs.getBbsContent();
			bbsContent = bbsContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
			bbs.setBbsContent(bbsContent);
		}
		
		
		request.setAttribute("list", bbss);
		
		
	}

}
