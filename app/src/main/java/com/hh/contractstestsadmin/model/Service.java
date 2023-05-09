package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
})
@Table(name = "service")
@Entity
public class Service {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long serviceId;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "service_name")
  private String serviceName;

  @Column(name = "stand_name")
  private String StandName;

  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @Column(name = "service_type")
  private ServiceType serviceType;

  @Column(name = "tag")
  private String tag;

  @Column(name = "expectation_link")
  private String expectationLink;
  @Column(name = "schema_link")
  private String schemaLink;
  public Service() {
  }

  public Long getServiceId() {
    return serviceId;
  }

  public void setServiceId(Long serviceId) {
    this.serviceId = serviceId;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdTime) {
    this.createdDate = createdTime;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getStandName() {
    return StandName;
  }

  public void setStandName(String standName) {
    StandName = standName;
  }

  public ServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getExpectationLink() {
    return expectationLink;
  }

  public void setExpectationLink(String expectationLink) {
    this.expectationLink = expectationLink;
  }

  public String getSchemaLink() {
    return schemaLink;
  }

  public void setSchemaLink(String schemaLink) {
    this.schemaLink = schemaLink;
  }
}
