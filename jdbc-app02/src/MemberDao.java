import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MemberDao {
	private String url;
	private String dbid;
	private String dbpw;
	
	private void dbProperitesInit() throws IOException{
		FileInputStream fis = new FileInputStream("d:\\db.properties");
		Properties pro = new Properties();
		pro.load(fis);
		this.url= pro.getProperty("url");
		this.dbid= pro.getProperty("dbid");
		this.dbpw= pro.getProperty("dbpw");
				
	}
	
	public Member selectMemberById(String id) throws ClassNotFoundException, SQLException, IOException{
		this.dbProperitesInit();
		//properties db정보 가져온다 (내부적으로는 input/output stream 방식으로 )
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection conn = DriverManager.getConnection(this.url,this.dbid,this.dbpw);
		String sql = "SELECT* FROM ORACLE_MEMBER WHERE ora_id =?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		Member member = null;
		if(rs.next()){
			member = new Member();
			member.setOra_id(rs.getString("ora_id"));
			member.setOra_name(rs.getString("ora_name"));
		}
		return member;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		MemberDao md = new MemberDao();
		Member m = md.selectMemberById("id001");
		System.out.println(m.getOra_id());
	}
	
}
