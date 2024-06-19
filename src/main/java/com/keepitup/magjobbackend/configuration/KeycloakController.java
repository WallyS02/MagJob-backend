package com.keepitup.magjobbackend.configuration;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.keycloak.admin.client.CreatedResponseUtil;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class KeycloakController {
    final KeycloakSecurityUtil keycloakUtil;

    @Value("${realm}")
    private String realm;

    public KeycloakController(KeycloakSecurityUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }


    public Map<String, String> createGroupRepresentation(String organizationName, UUID userId) {
        GroupRepresentation parentGroupRepresentation = new GroupRepresentation();
        Map<String, String> roleName2ExternalId = new HashMap<>();

        Keycloak keycloak = keycloakUtil.getKeycloakInstance();

        parentGroupRepresentation.setName(organizationName);

        addParentGroupToKeycloak(parentGroupRepresentation, keycloak);

        for (String childGroupName : Constants.DEFAULT_ROLE_NAMES) {
            roleName2ExternalId.put(
                    childGroupName,
                    addChildGroupToKeycloak(keycloak, parentGroupRepresentation.getId(), childGroupName, userId)
            );
        }

        return roleName2ExternalId;
    }

    public void addChildGroupToKeycloak(String organizationName, String roleName) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation childGroup = new GroupRepresentation();

        childGroup.setName(roleName);

        GroupRepresentation parentGroup = keycloak.realm(realm).getGroupByPath(organizationName);

        keycloak.realm(realm).groups().group(parentGroup.getId()).subGroup(childGroup);
    }

    public void deleteChildGroupFromKeycloak(String organizationName, String roleName) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + roleName);
        try {
            keycloak.realm(realm).groups().group(groupRepresentation.getId()).remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserToKeycloakGroup(String organizationName, UUID userId) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + Constants.ROLE_NAME_MEMBER);
        try {
            keycloak.realm(realm).users().get(String.valueOf(userId)).joinGroup(groupRepresentation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserToKeycloakGroup(String organizationName, UUID userId, String roleName) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + roleName);
        try {
            keycloak.realm(realm).users().get(String.valueOf(userId)).joinGroup(groupRepresentation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserFromKeycloakGroup(String organizationName, UUID userId, String roleName) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + roleName);
        try {
            keycloak.realm(realm).users().get(String.valueOf(userId)).leaveGroup(groupRepresentation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Modify method to remove from all subgroups in Keycloak
    public void removeUserFromKeycloakGroup(String organizationName, UUID userId) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + Constants.ROLE_NAME_MEMBER);
        try {
            keycloak.realm(realm).users().get(String.valueOf(userId)).leaveGroup(groupRepresentation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addParentGroupToKeycloak(GroupRepresentation groupRepresentation, Keycloak keycloak) {
        try (Response response = keycloak.realm(realm).groups().add(groupRepresentation)) {
            String groupId = CreatedResponseUtil.getCreatedId(response);
            groupRepresentation.setId(groupId);
        }
    }
    private String addChildGroupToKeycloak(Keycloak keycloak, String parentGroupId, String childGroupName, UUID userId) {
        GroupRepresentation childGroup = new GroupRepresentation();
        String childGroupId;
        childGroup.setName(childGroupName);
        try (Response response = keycloak.realm(realm).groups().group(parentGroupId).subGroup(childGroup)){
            childGroupId = CreatedResponseUtil.getCreatedId(response);
            if (childGroup.getName().equals(Constants.ROLE_NAME_OWNER)) {
                keycloak.realm(realm).users().get(String.valueOf(userId)).joinGroup(childGroupId);
            }
        }

        return childGroupId;
    }
}
