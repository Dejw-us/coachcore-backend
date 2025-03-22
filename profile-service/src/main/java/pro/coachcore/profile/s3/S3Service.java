package pro.coachcore.profile.s3;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
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
    log.debug("Saved {} with key {}", filePath, key);
  }
}
