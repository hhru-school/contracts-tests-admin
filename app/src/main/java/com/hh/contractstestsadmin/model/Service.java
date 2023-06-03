package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

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

@TypeDefs({
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
})
@Table(name = "service")
@Entity
public class Service {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "service_id")
  private Long id;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

  @Column(name = "service_name")
  private String serviceName;

  @Column(name = "stand_name")
  private String standName;

  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  @Column(name = "service_type")
  private ServiceType serviceType;

  @Column(name = "tag")
  private String tag;

  @Column(name = "released")
  private boolean isRelease;

  @Column(name = "expectation_link")
  private String expectationLink;
  @Column(name = "schema_link")
  private String schemaLink;

  @OneToMany(mappedBy = "consumer", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Expectation> expectationsConsumer = new ArrayList<>();

  @OneToMany(mappedBy = "producer", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Expectation> expectationsProducer = new ArrayList<>();


  public Service() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getStandName() {
    return standName;
  }

  public boolean isRelease() {
    return isRelease;
  }

  public void setRelease(boolean release) {
    isRelease = release;
  }

  public void setStandName(String standName) {
    this.standName = standName;
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

  public List<Expectation> getExpectationsConsumer() {
    return expectationsConsumer;
  }

  public void setExpectationsConsumer(List<Expectation> expectationsConsumer) {
    this.expectationsConsumer = expectationsConsumer;
  }

  public List<Expectation> getExpectationsProducer() {
    return expectationsProducer;
  }

  public void setExpectationsProducer(List<Expectation> expectationsProducer) {
    this.expectationsProducer = expectationsProducer;
  }
}
