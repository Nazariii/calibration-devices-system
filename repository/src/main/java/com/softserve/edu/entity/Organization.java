package com.softserve.edu.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softserve.edu.entity.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "ORGANIZATION")
public class Organization {
	@Id
	@GeneratedValue
	@Column(name = "organizationId")
	private Long id;
	private String name;
	private String email;
	private String phone;
	private Integer employeesCapacity;
	private Integer maxProcessTime;
	
	@Embedded
	private Address address;

	/**
	 * Identification number of the certificate that allows this Usercalibrator
	 * to perform verifications.
	 */
	private String certificateNumber;

	/**
	 * Identification number of the certificate that allows this calibrator to
	 * perform verifications.
	 */
	private Date certificateGrantedDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
	private Set<User> users = new HashSet<User>(0);

	@OneToMany(mappedBy = "provider")
	private Set<Device> devices;

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	@JsonManagedReference
	@ManyToMany
	@JoinTable(name = "ORGANIZATIONS_TYPES", joinColumns = @JoinColumn(name = "organizationId"), inverseJoinColumns = @JoinColumn(name = "id"))
	private Set<OrganizationType> organizationTypes = new HashSet<OrganizationType>();

	public void addOrganizationType(OrganizationType organizationType) {
		this.organizationTypes.add(organizationType);
	}

	public Organization() {
	}

	public Organization(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public Organization(String name, String email, String phone, Integer employeesCapacity, Integer maxProcessTime, Address address) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.employeesCapacity = employeesCapacity;
		this.maxProcessTime = maxProcessTime;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<OrganizationType> getOrganizationTypes() {
		return organizationTypes;
	}

	public void setOrganizationTypes(Set<OrganizationType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEmployeesCapacity() {
		return employeesCapacity;
	}

	public void setEmployeesCapacity(Integer employeesCapacity) {
		this.employeesCapacity = employeesCapacity;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public Date getCertificateGrantedDate() {
		return certificateGrantedDate;
	}

	public void setCertificateGrantedDate(Date certificateGrantedDate) {
		this.certificateGrantedDate = certificateGrantedDate;
	}

	public Integer getMaxProcessTime() {
		return maxProcessTime;
	}

	public void setMaxProcessTime(Integer maxProcessTime) {
		this.maxProcessTime = maxProcessTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("name", name)
				.append("email", email)
				.append("phone", phone)
				.append("employeesCapacity", employeesCapacity)
				.append("")
				.append("address", address)
				.append("certificateNumber", certificateNumber)
				.append("certificateGrantedDate", certificateGrantedDate)
				.toString();
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder()
				.append(id)
				.append(name)
				.append(email)
				.append(phone)
				.append(employeesCapacity)
				.append(address)
				.append(certificateNumber)
				.append(certificateGrantedDate)
				.toHashCode();
	}

	@Override
	public boolean equals(final Object obj){
		if(obj instanceof Organization){
			final Organization other = (Organization) obj;
			return new EqualsBuilder()
					.append(id, other.id)
					.append(name, other.name)
					.append(email, other.email)
					.append(phone, other.phone)
					.append(employeesCapacity, other.employeesCapacity)
					.append(address, other.address)
					.append(certificateNumber, other.certificateNumber)
					.append(certificateGrantedDate, other.certificateGrantedDate)
					.isEquals();
		} else {
			return false;
		}
	}

}
