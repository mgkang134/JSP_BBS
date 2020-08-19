package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import sqlmap.SqlSessionManager;

enum Option {
	EntireSearch, QuerySearch
}

public class BbsDAO {

	public String getDate() {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			Optional<String> dateMaybe = Optional.ofNullable(session.selectOne("bbs.getDate"));
			return dateMaybe.orElse(""); // 만약 쿼리 결과가 null인 경우 ""를 리턴한다.
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public int getNext() {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			OptionalInt sequenceMaybe = OptionalInt.of(session.selectOne("bbs.getNext"));
			return sequenceMaybe.getAsInt(); // 만약 쿼리 결과가 없을 경우 예외 발생(NoSuchElementException)
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public int getTotalNum() {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			OptionalInt sequenceMaybe = OptionalInt.of(session.selectOne("bbs.getTotalNum"));
			return sequenceMaybe.getAsInt(); // 만약 쿼리 결과가 없을 경우 예외 발생(NoSuchElementException)
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public int getTotalNum(String query) {
		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("query", query);

			OptionalInt sequenceMaybe = OptionalInt.of(session.selectOne("bbs.getTotalNum", param));
			return sequenceMaybe.getAsInt(); // 만약 쿼리 결과가 없을 경우 예외 발생(NoSuchElementException)
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public int write(String bbsTitle, String userID, String bbsContent) {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("id", getNext());
			param.put("bbsTitle", bbsTitle);
			param.put("userID", userID);
			param.put("bbsDate", getDate());
			param.put("bbsContent", bbsContent);
			param.put("available", 1);

			int result = session.insert("bbs.write", param);
			session.commit();
			return result;
			
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public ArrayList<Bbs> getList(int pageNumber) {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {

			Map<String, Object> param = new HashMap<>();
			param.put("pageNumber", (pageNumber-1)*10);

			List<Bbs> list = session.selectList("bbs.getList", param);

			// List를 ArrayList로 변환
			ArrayList<Bbs> arrayList = new ArrayList<Bbs>();
			arrayList.addAll(list);

			return arrayList;
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public ArrayList<Bbs> getList(int pageNumber, String query) {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("pageNumber", (pageNumber-1)*10);
			param.put("query", query);

			List<Bbs> list = session.selectList("bbs.getList", param);

			// List를 ArrayList로 변환
			ArrayList<Bbs> arrayList = new ArrayList<Bbs>();
			arrayList.addAll(list);

			return arrayList;
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public Bbs getBbs(int bbsID) {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			return session.selectOne("bbs.getBbs", bbsID);
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public int update(int bbsID, String bbsTitle, String bbsContent) {

		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("bbsID", bbsID);
			param.put("bbsTitle", bbsTitle);
			param.put("bbsContent", bbsContent);

			int result = session.update("bbs.update", param);
			session.commit();
			return result;
		} finally {
			session.close(); // 세션 닫기
		}
	}

	public int delete(int bbsID) {
		// 세션 열기
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();

		try {
			int result = session.update("bbs.delete", bbsID);
			session.commit();
			return result;
		} finally {
			session.close(); // 세션 닫기
		}
	}
}
