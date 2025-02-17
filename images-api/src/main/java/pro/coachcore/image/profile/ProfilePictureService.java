package pro.coachcore.image.profile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfilePictureService {
  private static final String STORAGE_PATH = "app-data/images/profile-pictures/";

  public ProfilePictureService() {
    var path = Paths.get(STORAGE_PATH);

    try {
      if (!Files.exists(path)) {
        Files.createDirectories(path);
        System.out.println("created file");
      }
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  public Path getProfilePicture(String userId) {
    return Paths.get(STORAGE_PATH.concat(userId).concat(".png"));
  }

  public boolean saveProfilePicture(String userId, MultipartFile file) {
    try {
      var path = getProfilePicturePath(userId);
      file.transferTo(path);
      return true;
    } catch (IOException ignore) {
      return false;
    }
  }

  private Path getProfilePicturePath(String userId) {
    return Paths.get(STORAGE_PATH.concat(userId).concat(".png"));
  }
}
