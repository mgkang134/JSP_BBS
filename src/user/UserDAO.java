package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import sqlmap.SqlSessionManager;

public class UserDAO {
	
	public int Login(String userID, String userPassword) {
		
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("userID", userID);
			Optional<Object> passwordMaybe = Optional.ofNullable(session.selectOne("user.getPassword", param));
			String realPassword = passwordMaybe.map(Object::toString).orElse("");
			if (realPassword.equals(userPassword)) {
				return 1; //비밀번호 일치
			} else {
				return 0; //비밀번호 불일치
			} 
		} finally {
			session.close();
		}
	}
	
	public int join(User user) {
		
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			int result = session.insert("user.join", user);
			session.commit();
			return result;
		} finally {
			session.close();
		}
	}
	
}
