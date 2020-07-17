package paging;

public class Paging {
	private final static int pageCount = 5;
	private int blockStartNum = 0; 
	private int blockLastNum = 0; 
	private int lastPageNum = 0;
	
	private int curPage;
	private int totalNum;
	
	public Paging(int curPage, int totalNum) {
		this.curPage = curPage;
		this.totalNum = totalNum;
		makeBlock();
		makeLastPageNum();
	}
	
	public int getBlockStartNum() {
		return blockStartNum;
	}

	public int getBlockLastNum() {
		return blockLastNum;
	}

	public int getLastPageNum() {
		return lastPageNum;
	}
	
	public int getCurPage() {
		return curPage;
	}

	public static int getPagecount() {
		return pageCount;
	} 
	
	private void makeBlock() {
		int blockNum=0;
		blockNum = (curPage-1)/pageCount;
		blockStartNum = (pageCount * blockNum) + 1;
		blockLastNum = blockStartNum + (pageCount - 1);
	}
	
	private void makeLastPageNum() {
		lastPageNum = ((totalNum-1)/pageCount) + 1;
	}

}
