package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.RoomManager;
import dentalClinicPOJOS.Room;


public class JDBCRoomManager {
	
	   //private Connection c;
	    private JDBCManager conMan;

	    public JDBCRoomManager(JDBCManager connectionManager) {
	        this.conMan = connectionManager;
	        //this.c = connectionManager.getConnection();
	    }
	    
	
	    public void addRoom(Room room) {
	        try {
	            String sql = "INSERT INTO Rooms (status) VALUES (?)";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ps.setString(1, room.getDescription());
	            ps.setString(1, room.getStatus().toString());
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error inserting room");
	            e.printStackTrace();
	        }
	    }
	    
	
	    //TODO esto tampoco se si esta bien
	    public List<Room> getAllRooms() {
	        List<Room> rooms = new ArrayList<>();
	        try {
	            String sql = "SELECT room_id, description, status FROM Rooms";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                int id = rs.getInt("room_id");
	                String description = rs.getString("description");
	                String status = rs.getString("status");
	                //Room room_id = new Room(room_id, description, status);
	                //rooms.add(room_id);
	            }

	            rs.close();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error retrieving rooms");
	            e.printStackTrace();
	        }
	        return rooms;
	    }


	    public void updateRoom(Room room) {
	        try {
	        	String sql = "UPDATE Rooms SET description = ?, status = ? WHERE room_id = ?";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ps.setString(1, room.getDescription());
	            ps.setString(2, room.getStatus().toString());
	            ps.setInt(3, room.getId_room());
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error updating room");
	            e.printStackTrace();
	        }
	    }

	  
	    public void deleteRoom(int roomId) {
	        try {
	            String sql = "DELETE FROM Rooms WHERE room_id = ?";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ps.setInt(1, roomId);
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error deleting room");
	            e.printStackTrace();
	        }
	    }

	

}
