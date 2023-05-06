package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

  @JoinColumn(name = "created_time")
  private LocalDateTime createdTime;

  @JoinColumn(name = "service_name")
  private String serviceName;

  @JoinColumn(name = "stand_name")
  private String StandName;

  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @JoinColumn(name = "service_type")
  private ServiceType serviceType;

  @JoinColumn(name = "tag")
  private String tag;

  @JoinColumn(name = "expectation_link")
  private String expectationLink;
  @JoinColumn(name = "shema_link")
  private String shemaLink;

  public Service() {
  }

  public Long getServiceId() {
    return serviceId;
  }

  public void setServiceId(Long serviceId) {
    this.serviceId = serviceId;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
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

  public String getShemaLink() {
    return shemaLink;
  }

  public void setShemaLink(String shemaLink) {
    this.shemaLink = shemaLink;
  }
}
