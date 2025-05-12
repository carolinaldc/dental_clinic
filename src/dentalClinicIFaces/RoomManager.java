package dentalClinicIFaces;

import java.util.List;
import dentalClinicPOJOS.Room;

public interface RoomManager {
    void addRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(int roomId);
    void deleteRoom(int roomId);
}