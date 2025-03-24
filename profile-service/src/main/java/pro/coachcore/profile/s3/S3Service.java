package pro.coachcore.profile.s3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.GlobalHandlerRuntimeException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class S3Service {
  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  private final S3Client s3Client;

  public void uploadFile(String filePath, String key) {
    if (!Files.exists(Path.of(filePath))) {
      throw new RuntimeException(String.format("File %s does no exist", filePath));
    }
    s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(),
        RequestBody.fromFile(Path.of(filePath)));
  }

  public void uploadFile(MultipartFile file, String key) {
    if (file.isEmpty()) {
      throw GlobalHandlerRuntimeException.create("File is empty", HttpStatus.BAD_REQUEST,
          "EMPTY_FILE");
    }

    try {
      s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(),
          RequestBody.fromBytes(file.getBytes()));
    } catch (IOException exception) {
      throw GlobalHandlerRuntimeException.create("Failed to save mulitpart file",
          HttpStatus.INTERNAL_SERVER_ERROR, "IO_ERROR");
    }
  }

  public byte[] downloadFile(String key) {
    try {
      var getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

      var objectResponse = s3Client.getObject(getObjectRequest);

      byte[] content = objectResponse.readAllBytes();
      return content;
    } catch (IOException exception) {
      throw GlobalHandlerRuntimeException.create("Failed to download file from S3",
          HttpStatus.INTERNAL_SERVER_ERROR, "S3_ERROR");
    } catch (S3Exception exception) {
      throw GlobalHandlerRuntimeException.create("User does not exist or has no profile picture",
          HttpStatus.NOT_FOUND, "NOT_FOUND");
    }
  }
}
