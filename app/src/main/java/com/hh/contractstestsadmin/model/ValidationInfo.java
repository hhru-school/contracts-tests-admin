package com.hh.contractstestsadmin.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "validation_info")
@Entity
public class ValidationInfo {

  @Id
  @Column(name = "validation_id")
  private Long validationId;

  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "validation_id")
  private Validation validation;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "consumer_id")
  private Service consumer;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "producer_id")
  private Service producer;

  @OneToMany(mappedBy = "validationInfo", cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ErrorInfo> errorsInfo = new ArrayList<>();


  public ValidationInfo() {
  }


  public Validation getValidation() {
    return validation;
  }

  public void setValidation(Validation validation) {
    this.validation = validation;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public Long getValidationId() {
    return validationId;
  }

  public void setValidationId(Long validationId) {
    this.validationId = validationId;
  }


  public Service getConsumer() {
    return consumer;
  }

  public void setConsumer(Service consumer) {
    this.consumer = consumer;
  }

  public Service getProducer() {
    return producer;
  }

  public void setProducer(Service producer) {
    this.producer = producer;
  }
}
