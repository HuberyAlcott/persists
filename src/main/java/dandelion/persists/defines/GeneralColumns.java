package dandelion.persists.defines;

import dandelion.wrapper.enums.DeleteStatus;
import dandelion.wrapper.enums.EnableStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** @author Marcus */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class GeneralColumns implements Serializable {

  @Column(name = "enable_status")
  private Integer enableStatus;

  @Column(name = "delete_status")
  private Integer deleteStatus;

  @Column(name = "create_timing")
  private Timestamp createTiming;

  @Column(name = "update_timing")
  private Timestamp updateTiming;

  @PrePersist
  public void ensureCreate() {
    final var timestamp = Timestamp.valueOf(LocalDateTime.now());
    if (ObjectUtils.isEmpty(enableStatus)) {
      this.enableStatus = EnableStatus.enabled.getFlag();
    }
    if (ObjectUtils.isEmpty(deleteStatus)) {
      this.deleteStatus = DeleteStatus.enabled.getFlag();
    }
    if (ObjectUtils.isEmpty(createTiming)) {
      this.createTiming = timestamp;
    }
    if (ObjectUtils.isEmpty(updateTiming)) {
      this.updateTiming = timestamp;
    }
  }

  @PreUpdate
  public void ensureUpdate() {
    final var timestamp = Timestamp.valueOf(LocalDateTime.now());
    if (ObjectUtils.isEmpty(updateTiming)) {
      this.updateTiming = timestamp;
    }
  }
}
