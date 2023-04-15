package com.hh.contractstestsadmin.model;

import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
})
@Table(name = "verification_history")
public class VerificationHistory {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "stand_id")
  private UUID standId;

  @Column(name = "name")
  private String name;

  @Column(name = "execute_date")
  private LocalDateTime executeDate;

  @Column(name = "release_information_version")
  private String releaseInformationVersion;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @Column(name = "validation_status")
  private ValidationStatus status;

  @Column(name = "error_count")
  private int errorCount;

  public VerificationHistory() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public ValidationStatus getStatus() {
    return status;
  }

  public void setStatus(ValidationStatus status) {
    this.status = status;
  }

  public int getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  public UUID getStandId() {
    return standId;
  }

  public void setStandId(UUID standId) {
    this.standId = standId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getExecuteDate() {
    return executeDate;
  }

  public void setExecuteDate(LocalDateTime executeDate) {
    this.executeDate = executeDate;
  }

  public String getReleaseInformationVersion() {
    return releaseInformationVersion;
  }

  public void setReleaseInformationVersion(String releaseInformationVersion) {
    this.releaseInformationVersion = releaseInformationVersion;
  }
}
