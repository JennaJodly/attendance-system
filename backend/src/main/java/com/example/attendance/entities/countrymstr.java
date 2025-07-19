package com.example.attendance.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "countrymstr")
public class countrymstr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dialCode;
    private String code;
    private Boolean active = true;
    private String countryMasterCode;
    private String countryTaxName;
    private String recCreatedUserCode;
    private String recCreatedTransCode;
    private String recUpdatedUserCode;
    private String recUpdatedTransCode;

    private LocalDateTime recCreatedDateTime;
    private LocalDateTime recUpdatedDateTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ✅ GETTERS
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDialCode() { return dialCode; }
    public String getCode() { return code; }
    public Boolean getActive() { return active; }
    public String getCountryMasterCode() { return countryMasterCode; }
    public String getCountryTaxName() { return countryTaxName; }
    public String getRecCreatedUserCode() { return recCreatedUserCode; }
    public String getRecCreatedTransCode() { return recCreatedTransCode; }
    public String getRecUpdatedUserCode() { return recUpdatedUserCode; }
    public String getRecUpdatedTransCode() { return recUpdatedTransCode; }
    public LocalDateTime getRecCreatedDateTime() { return recCreatedDateTime; }
    public LocalDateTime getRecUpdatedDateTime() { return recUpdatedDateTime; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ✅ SETTERS
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDialCode(String dialCode) { this.dialCode = dialCode; }
    public void setCode(String code) { this.code = code; }
    public void setActive(Boolean active) { this.active = active; }
    public void setCountryMasterCode(String countryMasterCode) { this.countryMasterCode = countryMasterCode; }
    public void setCountryTaxName(String countryTaxName) { this.countryTaxName = countryTaxName; }
    public void setRecCreatedUserCode(String recCreatedUserCode) { this.recCreatedUserCode = recCreatedUserCode; }
    public void setRecCreatedTransCode(String recCreatedTransCode) { this.recCreatedTransCode = recCreatedTransCode; }
    public void setRecUpdatedUserCode(String recUpdatedUserCode) { this.recUpdatedUserCode = recUpdatedUserCode; }
    public void setRecUpdatedTransCode(String recUpdatedTransCode) { this.recUpdatedTransCode = recUpdatedTransCode; }
    public void setRecCreatedDateTime(LocalDateTime recCreatedDateTime) { this.recCreatedDateTime = recCreatedDateTime; }
    public void setRecUpdatedDateTime(LocalDateTime recUpdatedDateTime) { this.recUpdatedDateTime = recUpdatedDateTime; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
