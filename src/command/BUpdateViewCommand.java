package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.Bbs;
import bbs.BbsDAO;

public class BUpdateViewCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int bbsID = Integer.parseInt(request.getParameter("bbsID"));
		BbsDAO dao = new BbsDAO();
		Bbs bbs = dao.getBbs(bbsID);
		
		//��¥ ��� ���� ����
		String bbsDate = bbs.getBbsDate();
		bbs.setBbsDate(bbsDate.substring(0, 11) + bbsDate.substring(11, 13) + "��" + bbsDate.substring(14, 16) + "��");
		
		//���� ��� ���� ����(����)
		String bbsContent = bbs.getBbsContent();
		bbsContent = bbsContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
		bbs.setBbsContent(bbsContent);
		
		//���� ��� ���� ����(����)
		String bbsTitle = bbs.getBbsTitle();
		bbsTitle = bbsTitle.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
		bbs.setBbsTitle(bbsTitle);
		
		request.setAttribute("bbs", bbs);
	}

}
