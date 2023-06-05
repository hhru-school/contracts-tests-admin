package com.hh.contractstestsadmin.model;

import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name = "validation")
public class Validation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "validation_id", nullable = false)
  private Long id;

  @Column(name = "stand_name")
  private String standName;

  @Column(name = "execution_date")
  private OffsetDateTime executionDate;

  @Column(name = "release_information_version")
  private String releaseInformationVersion;

  @Column(name = "creation_date")
  private OffsetDateTime creationDate;

  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @Column(name = "validation_status")
  private ValidationStatus status;

  @Column(name = "error_count")
  private int errorCount;

  @Type(type = "jsonb")
  @Column(name = "report", columnDefinition = "jsonb")
  private String report;

  @OneToMany(mappedBy = "validation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Expectation> expectations = new ArrayList<>();


  public Validation() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
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

  public String getStandName() {
    return standName;
  }

  public void setStandName(String standName) {
    this.standName = standName;
  }

  public OffsetDateTime getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(OffsetDateTime executionDate) {
    this.executionDate = executionDate;
  }

  public String getReleaseInformationVersion() {
    return releaseInformationVersion;
  }

  public void setReleaseInformationVersion(String releaseInformationVersion) {
    this.releaseInformationVersion = releaseInformationVersion;
  }

  public List<Expectation> getExpectations() {
    return expectations;
  }

  public void setExpectations(List<Expectation> expectations) {
    this.expectations = expectations;
  }

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }
}
