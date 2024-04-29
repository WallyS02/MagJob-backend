package com.keepitup.magjobbackend.configuration;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.keycloak.admin.client.CreatedResponseUtil;


import java.util.HashMap;
import java.util.Map;

@Component
public class KeycloakController {
    final KeycloakSecurityUtil keycloakUtil;

    @Value("${realm}")
    private String realm;

    public KeycloakController(KeycloakSecurityUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }


    public Map<String, String> createGroupRepresentation(String organizationName, String userExternalId) {
        GroupRepresentation parentGroupRepresentation = new GroupRepresentation();
        Map<String, String> roleName2ExternalId = new HashMap<>();

        Keycloak keycloak = keycloakUtil.getKeycloakInstance();

        parentGroupRepresentation.setName(organizationName);

        addGroupToKeycloak(parentGroupRepresentation, keycloak);

        for (String childGroupName : Constants.DEFAULT_ROLE_NAMES) {
            roleName2ExternalId.put(
                    childGroupName,
                    addChildGroupToKeycloak(keycloak, parentGroupRepresentation.getId(), childGroupName, userExternalId)
            );
        }

        return roleName2ExternalId;
    }

    public void addUserToKeycloakGroup(String organizationName, String userExternalId) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + Constants.ROLE_NAME_MEMBER);
        try {
            keycloak.realm(realm).users().get(userExternalId).joinGroup(groupRepresentation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Modify method to remove from all subgroups in Keycloak
    public void removeUserFromKeycloakGroup(String organizationName, String userExternalId) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(organizationName + "/" + Constants.ROLE_NAME_MEMBER);
        try {
            keycloak.realm(realm).users().get(userExternalId).leaveGroup(groupRepresentation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGroupToKeycloak(GroupRepresentation groupRepresentation, Keycloak keycloak) {
        try (Response response = keycloak.realm(realm).groups().add(groupRepresentation)) {
            String groupId = CreatedResponseUtil.getCreatedId(response);
            groupRepresentation.setId(groupId);
        }
    }

    private String addChildGroupToKeycloak(Keycloak keycloak, String parentGroupId, String childGroupName, String userExternalId) {
        GroupRepresentation childGroup = new GroupRepresentation();
        String childGroupId;
        childGroup.setName(childGroupName);
        try (Response response = keycloak.realm(realm).groups().group(parentGroupId).subGroup(childGroup)){
            childGroupId = CreatedResponseUtil.getCreatedId(response);
            if (childGroup.getName().equals(Constants.ROLE_NAME_OWNER)) {
                keycloak.realm(realm).users().get(userExternalId).joinGroup(childGroupId);
            }
        }

        return childGroupId;
    }
}
