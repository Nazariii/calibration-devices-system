package com.softserve.edu.entity.enumeration.organization;

import com.softserve.edu.entity.enumeration.user.UserRole;

public enum OrganizationType {
    PROVIDER(){
        public UserRole getOrganizationAdminRole(OrganizationType type){
            return UserRole.PROVIDER_ADMIN;
        }
    },
    CALIBRATOR() {
        public UserRole getOrganizationAdminRole(OrganizationType type) {
            return UserRole.CALIBRATOR_ADMIN;
        }
    },
    STATE_VERIFICATOR() {
        public UserRole getOrganizationAdminRole(OrganizationType type) {
            return UserRole.STATE_VERIFICATOR_ADMIN;
        }
    },
    NO_TYPE() {
            public UserRole getOrganizationAdminRole (OrganizationType type){
                throw new IllegalArgumentException("No admin for organization with type : " + type);
            }
    };

    public abstract UserRole getOrganizationAdminRole(OrganizationType type) throws IllegalArgumentException;
}
