
import java.sql.*;

import java.util.ArrayList;

public class DataService {
    ArrayList<Seller> sellerList = new ArrayList<>();

    private Connection connection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/ingatlan";
        String user = "root";
        String password = "";
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public ArrayList<Seller> getSellerList() throws SQLException {
        try {
            Connection conn = connection();
            String query = "SELECT * FROM sellers ORDER BY name";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                sellerList.add(new Seller(id, name, phone));
            }

        } finally {
            connection().close();
        }
        return sellerList;
    }

    public int getAdsNumber(int id) throws SQLException {
        try {
            Connection conn = connection();
            String query = "SELECT COUNT(*) FROM realestates WHERE sellerId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } finally {
            connection().close();
        }
        return id;
    }
}
