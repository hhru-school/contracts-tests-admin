package com.hh.contractstestsadmin.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

import org.hibernate.Hibernate;
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

  @Column(name = "creation_date")
  private OffsetDateTime creationDate;

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

  @OneToMany(mappedBy = "consumer", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<Expectation> expectationsConsumer = new HashSet<>();

  @OneToMany(mappedBy = "producer", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<Expectation> expectationsProducer = new HashSet<>();


  public Service() {
  }

  public Service(String serviceName, String standName, String tag) {
    this.serviceName = serviceName;
    this.standName = standName;
    this.tag = tag;
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

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getStandName() {
    return standName;
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

  public Set<Expectation> getExpectationsConsumer() {
    return expectationsConsumer;
  }

  public void setExpectationsConsumer(Set<Expectation> expectationsConsumer) {
    this.expectationsConsumer = expectationsConsumer;
  }

  public Set<Expectation> getExpectationsProducer() {
    return expectationsProducer;
  }

  public void setExpectationsProducer(Set<Expectation> expectationsProducer) {
    this.expectationsProducer = expectationsProducer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Service service = (Service) o;
    return getId() != null && Objects.equals(getId(), service.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
