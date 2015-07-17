package com.softserve.edu.service.admin;

import com.softserve.edu.entity.*;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.entity.user.UserRole;
import com.softserve.edu.repository.OrganizationRepository;
import com.softserve.edu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class OrganizationsService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addOrganizationWithAdmin(String name, String email,
			String phone, String[] types, String username, String password,
			Address address) {

		Organization organization = new Organization(name, email, phone,
				address);
		String passwordEncoded = new BCryptPasswordEncoder().encode(password);
		User employeeAdmin = new User(username, passwordEncoded, organization);

		for (String strType : types) {
			OrganizationType type = organizationRepository
					.getOrganizationType(strType);
			organization.addOrganizationType(type);
			String strRole = strType + "_ADMIN";
			UserRole userRole = userRepository.getUserRole(strRole);
			employeeAdmin.addUserRole(userRole);
		}

		organizationRepository.save(organization);
		userRepository.save(employeeAdmin);
	}

	@Transactional
	public Page<Organization> getOrganizationsBySearchAndPagination(
			int pageNumber, int itemsPerPage, String search) {
		/* pagination starts from 1 at client side, but Spring Data JPA from 0 */
		PageRequest pageRequest = new PageRequest(pageNumber - 1, itemsPerPage);
		return search == null ? organizationRepository.findAll(pageRequest)
				: organizationRepository.findByNameLikeIgnoreCase("%" + search
						+ "%", pageRequest);
	}

	@Transactional
	public Organization getOrganizationById(Long id) {
		Address address = organizationRepository.getOrganizationAddressById(id);
		String name = organizationRepository.getOrganizationNameById(id);
		String email = organizationRepository.getOrganizationEmailById(id);
		String phone = organizationRepository.getOrganizationPhoneById(id);
		Organization organization = new Organization(name, email, phone,
				address);
		return organization;
		// return organizationRepository.findOne(id);
	}

	@Transactional
	public String getOrganizationType(Organization organization) {
		Long id = organization.getId();
		Set<String> types = organizationRepository.getOrganizationTypeById(id);
		String result = "";
		for (String s : types) {
			result += (s + " ");
		}
		return result;
	}

	@Transactional
	public void editOrganization(Long organizationId, String name,
			String phone, String email, Address address) {
		Organization organization = organizationRepository
				.findOne(organizationId);
		organization.setName(name);
		organization.setPhone(phone);
		organization.setEmail(email);
		organization.setAddress(address);
	}
}
