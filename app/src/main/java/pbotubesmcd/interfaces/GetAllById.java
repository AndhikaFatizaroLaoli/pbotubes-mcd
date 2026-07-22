package pbotubesmcd.interfaces;

import java.util.List;

public interface GetAllById<T> {
    List<T> getAllById(int id);
}
