package com.softserve.edu.entity.enumeration.organization;

import com.softserve.edu.entity.enumeration.user.UserRole;

public enum OrganizationType {
    PROVIDER(){
        public UserRole getOrganizationAdminRole(){
            return UserRole.PROVIDER_ADMIN;
        }
    },
    CALIBRATOR() {
        public UserRole getOrganizationAdminRole() {
            return UserRole.CALIBRATOR_ADMIN;
        }
    },
    STATE_VERIFICATOR() {
        public UserRole getOrganizationAdminRole() {
            return UserRole.STATE_VERIFICATOR_ADMIN;
        }
    },
    NO_TYPE() {
            public UserRole getOrganizationAdminRole (){
                throw new IllegalArgumentException("No admin for organization with this type");
            }
    };

    public abstract UserRole getOrganizationAdminRole() throws IllegalArgumentException;
}
