package com.keepitup.magjobbackend.smoketests;

import com.keepitup.magjobbackend.invitation.dto.AcceptInvitationRequest;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationsResponse;
import com.keepitup.magjobbackend.invitation.dto.PostInvitationRequest;
import com.keepitup.magjobbackend.member.dto.PatchMemberRequest;
import com.keepitup.magjobbackend.member.dto.PostMemberRequest;
import com.keepitup.magjobbackend.organization.dto.PatchOrganizationRequest;
import com.keepitup.magjobbackend.organization.dto.PostOrganizationRequest;
import com.keepitup.magjobbackend.user.dto.PatchUserRequest;
import com.keepitup.magjobbackend.user.dto.PostUserRequest;
import com.keepitup.magjobbackend.user.dto.PutPasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Tag(name = "Smoke Tests Controller")
@RestController
public class SmokeTests {
    private static final String USER_FIRSTNAME_TEST_VALUE = "testFirstName";
    private static final String USER_LASTNAME_TEST_VALUE = "testLastName";
    private static final String USER_EMAIL_TEST_VALUE = "test@magjob.test";
    private static final String USER_PASSWORD_TEST_VALUE = "password";
    private static final String USER_PASSWORD_UPDATED_TEST_VALUE = "passwordUpdate";
    private static final String USER_FIRSTNAME_UPDATED_TEST_VALUE = "testFirstNameUpdate";
    private static final String USER_LASTNAME_UPDATED_TEST_VALUE = "testLastNameUpdate";

    private static final String USER_MEMBER_CREATION_EMAIL_TEST_VALUE = "testUserMember@magjob.test";
    private static final String USER_INVITATION_CREATION_EMAIL_TEST_VALUE = "testUserInvitation@magjob.test";
    private static final String USER_INVITATION_ACCEPT_EMAIL_TEST_VALUE = "testUserInvitationAccept@magjob.test";
    private static final String USER_INVITATION_REJECT_EMAIL_TEST_VALUE = "testUserInvitationReject@magjob.test";

    private static final String ORGANIZATION_NAME_TEST_VALUE = "testOrganization";
    private static final String ORGANIZATION_PROFILE_BANNER_URL_TEST_VALUE = "https://magjob.com.default_profile_banner_url_valu";
    private static final String ORGANIZATION_NAME_UPDATED_TEST_VALUE = "testOrganizationUpdated";
    private static final String ORGANIZATION_PROFILE_BANNER_URL_UPDATED_TEST_VALUE = "https://magjob.com.default_profile_banner_url_updt";

    private static final String MEMBER_PSEUDONYM_TEST_VALUE = "testMember";

    private static final String MEMBER_PSEUDONYM_UPDATED_TEST_VALUE = "OwnerUpdate";

    private static final String USER_INVITATION_ACCEPT_PSEUDONYM_TEST_VALUE = "testInvitationMember";



    private final RestTemplate restTemplate;

    private BigInteger testUserId;
    private BigInteger testUserMemberId;
    private BigInteger testUserInvitationId;
    private BigInteger testUserInvitationAcceptId;
    private BigInteger testUserInvitationRejectId;
    private String testUserPassword;
    private BigInteger testOrganizationId;
    private BigInteger testMemberId;

    private static final List<HttpStatus> successfulResponseCodes = Arrays.asList(
            HttpStatus.OK,
            HttpStatus.CREATED,
            HttpStatus.NO_CONTENT
    );
    private final String[] endpointsToCheck = {
            "/healthcheck/loginTest",
            "/healthcheck/getUsersTest",
            "/healthcheck/getUserTest",
            "/healthcheck/updateUserPasswordTest",
            "/healthcheck/createOrganizationTest",
            "/healthcheck/getOrganizationsTest",
            "/healthcheck/getOrganizationTest",
            "/healthcheck/getOrganizationsByUserTest",
            "/healthcheck/updateOrganizationTest",
            "/healthcheck/getMembersTest",
            "/healthcheck/getMembersByOrganizationTest",
            "/healthcheck/getMemberTest",
            "/healthcheck/updateMemberTest",
            "/healthcheck/createMemberTest",
            "/healthcheck/sendInvitationTest",
            "/healthcheck/getInvitationsByOrganizationTest",
            "/healthcheck/getInvitationsByUserTest",
            "/healthcheck/getInvitationTest",
            "/healthcheck/deleteInvitationTest",
            "/healthcheck/acceptInvitationTest",
            "/healthcheck/rejectInvitationTest",
            "/healthcheck/updateUserTest",
            "/healthcheck/deleteMemberTest",
            "/healthcheck/deleteOrganizationTest",
            "/healthcheck/deleteUserTest"
    };

    @Autowired
    public SmokeTests(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Operation(summary = "Create User Test")
    @GetMapping("/healthcheck/createUserTest")
    public ResponseEntity<String> createUserTest() throws JSONException {
        PostUserRequest postUserRequest = new PostUserRequest();
        postUserRequest.setEmail(USER_EMAIL_TEST_VALUE);
        postUserRequest.setFirstname(USER_FIRSTNAME_TEST_VALUE);
        postUserRequest.setLastname(USER_LASTNAME_TEST_VALUE);
        //postUserRequest.setPassword(USER_PASSWORD_TEST_VALUE);

        testUserPassword = USER_PASSWORD_TEST_VALUE;

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users", postUserRequest, String.class);
        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        testUserId = "null".equals(jsonObject.getString("id")) ? null : new BigInteger(jsonObject.getString("id"));

        return response;
    }

    /*@Operation(summary = "Login Test")
    @GetMapping("/healthcheck/loginTest")
    public ResponseEntity<String> loginTest() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(USER_EMAIL_TEST_VALUE);
        authenticationRequest.setPassword(testUserPassword);

        return restTemplate.postForEntity("/api/users/login", authenticationRequest, String.class);
    }*/

    /*@Operation(summary = "Get all Users Test")
    @GetMapping("/healthcheck/getUsersTest")
    public ResponseEntity<String> getUsersTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/users", HttpMethod.GET, entity, String.class);
    }*/

    /*@Operation(summary = "Get User Test")
    @GetMapping("/healthcheck/getUserTest")
    public ResponseEntity<String> getUserTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/users/" + testUserId, HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Update User Test")
    @GetMapping("/healthcheck/updateUserTest")
    public ResponseEntity<String> updateUserTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PatchUserRequest patchUserRequest = new PatchUserRequest();
        patchUserRequest.setEmail(USER_MEMBER_CREATION_EMAIL_TEST_VALUE);
        patchUserRequest.setFirstname(USER_FIRSTNAME_UPDATED_TEST_VALUE);
        patchUserRequest.setLastname(USER_LASTNAME_UPDATED_TEST_VALUE);

        HttpEntity<PatchUserRequest> requestEntity = new HttpEntity<>(patchUserRequest, entity.getHeaders());

        return restTemplate.exchange("/api/users/" + testUserMemberId, HttpMethod.PATCH, requestEntity, String.class);
    }

    @Operation(summary = "Update User Password Test")
    @GetMapping("/healthcheck/updateUserPasswordTest")
    public ResponseEntity<String> updateUserPasswordTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PutPasswordRequest putPasswordRequest = new PutPasswordRequest();
        putPasswordRequest.setPassword(USER_PASSWORD_UPDATED_TEST_VALUE);

        testUserPassword = USER_PASSWORD_UPDATED_TEST_VALUE;

        HttpEntity<PutPasswordRequest> requestEntity = new HttpEntity<>(putPasswordRequest, entity.getHeaders());

        return restTemplate.exchange("/api/users/" + testUserId, HttpMethod.PUT, requestEntity, String.class);
    }

    @Operation(summary = "Delete User Test")
    @GetMapping("/healthcheck/deleteUserTest")
    public ResponseEntity<String> deleteUserTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/users/" + testUserMemberId, HttpMethod.DELETE, entity, String.class);
    }

    @Operation(summary = "Create Organization Test")
    @GetMapping("/healthcheck/createOrganizationTest")
    public ResponseEntity<String> createOrganizationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PostOrganizationRequest postOrganizationRequest = new PostOrganizationRequest();
        postOrganizationRequest.setName(ORGANIZATION_NAME_TEST_VALUE);
        //postOrganizationRequest.setProfileBannerUrl(ORGANIZATION_PROFILE_BANNER_URL_TEST_VALUE);
        postOrganizationRequest.setUser(testUserId);

        HttpEntity<PostOrganizationRequest> requestEntity = new HttpEntity<>(postOrganizationRequest, entity.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange("/api/organizations", HttpMethod.POST, requestEntity, String.class);

        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        testOrganizationId = "null".equals(jsonObject.getString("id")) ? null : new BigInteger(jsonObject.getString("id"));

        return response;
    }

    @Operation(summary = "Update Organization Test")
    @GetMapping("/healthcheck/updateOrganizationTest")
    public ResponseEntity<String> updateOrganizationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PatchOrganizationRequest patchOrganizationRequest = new PatchOrganizationRequest();
        patchOrganizationRequest.setName(ORGANIZATION_NAME_UPDATED_TEST_VALUE);
        //patchOrganizationRequest.setProfileBannerUrl(ORGANIZATION_PROFILE_BANNER_URL_UPDATED_TEST_VALUE);

        HttpEntity<PatchOrganizationRequest> requestEntity = new HttpEntity<>(patchOrganizationRequest, entity.getHeaders());

        return restTemplate.exchange("/api/organizations/" + testOrganizationId, HttpMethod.PATCH, requestEntity, String.class);
    }

    @Operation(summary = "Get all Organizations Test")
    @GetMapping("/healthcheck/getOrganizationsTest")
    public ResponseEntity<String> getOrganizationsTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/organizations", HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Get Organization Test")
    @GetMapping("/healthcheck/getOrganizationTest")
    public ResponseEntity<String> getOrganizationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/organizations/" + testOrganizationId, HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Get Organizations By User Test")
    @GetMapping("/healthcheck/getOrganizationsByUserTest")
    public ResponseEntity<String> getOrganizationsByUserTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/organizations/users/" + testUserId, HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Delete Organization Test")
    @GetMapping("/healthcheck/deleteOrganizationTest")
    public ResponseEntity<String> deleteOrganizationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/organizations/" + testOrganizationId, HttpMethod.DELETE, entity, String.class);
    }

    @Operation(summary = "Get all Members Test")
    @GetMapping("/healthcheck/getMembersTest")
    public ResponseEntity<String> getMembersTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/members", HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Get Member Test")
    @GetMapping("/healthcheck/getMemberTest")
    public ResponseEntity<String> getMemberTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/members/" + testMemberId, HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Update Member Test")
    @GetMapping("/healthcheck/updateMemberTest")
    public ResponseEntity<String> updateMemberTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PatchMemberRequest patchMemberRequest = new PatchMemberRequest();
        patchMemberRequest.setPseudonym(MEMBER_PSEUDONYM_UPDATED_TEST_VALUE);

        HttpEntity<PatchMemberRequest> requestEntity = new HttpEntity<>(patchMemberRequest, entity.getHeaders());

        return restTemplate.exchange("/api/members/" + testMemberId, HttpMethod.PATCH, requestEntity, String.class);
    }

    @Operation(summary = "Get Members By Organization Test")
    @GetMapping("/healthcheck/getMembersByOrganizationTest")
    public ResponseEntity<String> getMembersByOrganizationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        ResponseEntity<String> response = restTemplate.exchange("/api/organizations/" + testOrganizationId + "/members", HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);

        if (jsonObject.has("members")) {
            JSONArray membersArray = jsonObject.getJSONArray("members");

            if (membersArray.length() > 0) {
                JSONObject firstMember = membersArray.getJSONObject(0);
                testMemberId = firstMember.isNull("id") ? null : new BigInteger(firstMember.getString("id"));
            }
        }

        return response;
    }

    @Operation(summary = "Create Member Test")
    @GetMapping("/healthcheck/createMemberTest")
    public ResponseEntity<String> createMemberTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PostUserRequest postUserRequest = new PostUserRequest();
        postUserRequest.setEmail(USER_MEMBER_CREATION_EMAIL_TEST_VALUE);
        postUserRequest.setFirstname(USER_FIRSTNAME_TEST_VALUE);
        postUserRequest.setLastname(USER_LASTNAME_TEST_VALUE);
        postUserRequest.setPassword(USER_PASSWORD_TEST_VALUE);

        ResponseEntity<String> userResponse = restTemplate.postForEntity("/api/users", postUserRequest, String.class);
        String userResponseBody = userResponse.getBody();
        JSONObject userJsonObject = new JSONObject(userResponseBody);
        testUserMemberId = "null".equals(userJsonObject.getString("id")) ? null : new BigInteger(userJsonObject.getString("id"));

        PostMemberRequest postMemberRequest = new PostMemberRequest();
        postMemberRequest.setPseudonym(MEMBER_PSEUDONYM_TEST_VALUE);
        postMemberRequest.setOrganization(testOrganizationId);
        postMemberRequest.setUser(testUserMemberId);

        HttpEntity<PostMemberRequest> requestEntity = new HttpEntity<>(postMemberRequest, entity.getHeaders());

        return restTemplate.exchange("/api/members", HttpMethod.POST, requestEntity, String.class);
    }

    @Operation(summary = "Delete Member Test")
    @GetMapping("/healthcheck/deleteMemberTest")
    public ResponseEntity<String> deleteMemberTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/members/" + testMemberId, HttpMethod.DELETE, entity, String.class);
    }

    @Operation(summary = "Send Invitation Test")
    @GetMapping("/healthcheck/sendInvitationTest")
    public ResponseEntity<String> sendInvitationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        PostUserRequest postUserRequest = new PostUserRequest();
        postUserRequest.setEmail(USER_INVITATION_CREATION_EMAIL_TEST_VALUE);
        postUserRequest.setFirstname(USER_FIRSTNAME_TEST_VALUE);
        postUserRequest.setLastname(USER_LASTNAME_TEST_VALUE);
        postUserRequest.setPassword(USER_PASSWORD_TEST_VALUE);

        ResponseEntity<String> userResponse = restTemplate.postForEntity("/api/users", postUserRequest, String.class);
        String userResponseBody = userResponse.getBody();
        JSONObject userJsonObject = new JSONObject(userResponseBody);
        testUserInvitationId = "null".equals(userJsonObject.getString("id")) ? null : new BigInteger(userJsonObject.getString("id"));

        PostInvitationRequest postInvitationRequest = new PostInvitationRequest();
        postInvitationRequest.setUser(testUserInvitationId);
        postInvitationRequest.setOrganization(testOrganizationId);

        HttpEntity<PostInvitationRequest> requestEntity = new HttpEntity<>(postInvitationRequest, entity.getHeaders());

        return restTemplate.exchange("/api/invitations", HttpMethod.POST, requestEntity, String.class);
    }

    @Operation(summary = "Get Invitations By Organization Test")
    @GetMapping("/healthcheck/getInvitationsByOrganizationTest")
    public ResponseEntity<String> getInvitationsByOrganizationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/organizations/" + testOrganizationId + "/invitations", HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Get Invitations By User Test")
    @GetMapping("/healthcheck/getInvitationsByUserTest")
    public ResponseEntity<String> getInvitationsByUserTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/users/" + testUserInvitationId + "/invitations", HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Get Invitation Test")
    @GetMapping("/healthcheck/getInvitationTest")
    public ResponseEntity<String> getInvitationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/invitations/" + testUserInvitationId + "/" + testOrganizationId, HttpMethod.GET, entity, String.class);
    }

    @Operation(summary = "Delete Invitation Test")
    @GetMapping("/healthcheck/deleteInvitationTest")
    public ResponseEntity<String> deleteInvitationTest() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        return restTemplate.exchange("/api/invitations/" + testUserInvitationId + "/" + testOrganizationId, HttpMethod.DELETE, entity, String.class);
    }

    @Operation(summary = "Accept Invitation Test")
    @GetMapping("/healthcheck/acceptInvitationTest")
    public ResponseEntity<String> acceptInvitationTest() throws JSONException {
        PostUserRequest postUserRequest = new PostUserRequest();
        postUserRequest.setEmail(USER_INVITATION_ACCEPT_EMAIL_TEST_VALUE);
        postUserRequest.setFirstname(USER_FIRSTNAME_TEST_VALUE);
        postUserRequest.setLastname(USER_LASTNAME_TEST_VALUE);
        postUserRequest.setPassword(USER_PASSWORD_TEST_VALUE);

        ResponseEntity<String> userResponse = restTemplate.postForEntity("/api/users", postUserRequest, String.class);
        String userResponseBody = userResponse.getBody();
        JSONObject userJsonObject = new JSONObject(userResponseBody);
        testUserInvitationAcceptId = "null".equals(userJsonObject.getString("id")) ? null : new BigInteger(userJsonObject.getString("id"));

        PostInvitationRequest postInvitationRequest = new PostInvitationRequest();
        postInvitationRequest.setUser(testUserInvitationAcceptId);
        postInvitationRequest.setOrganization(testOrganizationId);

        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_INVITATION_ACCEPT_EMAIL_TEST_VALUE, USER_PASSWORD_TEST_VALUE);

        HttpEntity<PostInvitationRequest> postInvitationEntity = new HttpEntity<>(postInvitationRequest, entity.getHeaders());

        restTemplate.exchange("/api/invitations", HttpMethod.POST, postInvitationEntity, String.class);

        AcceptInvitationRequest acceptInvitationRequest = new AcceptInvitationRequest();
        acceptInvitationRequest.setPseudonym(USER_INVITATION_ACCEPT_PSEUDONYM_TEST_VALUE);
        acceptInvitationRequest.setUser(testUserInvitationAcceptId);
        acceptInvitationRequest.setOrganization(testOrganizationId);

        HttpEntity<AcceptInvitationRequest> acceptInvitationEntity = new HttpEntity<>(acceptInvitationRequest, entity.getHeaders());

        return restTemplate.exchange("/api/invitations/accept", HttpMethod.POST, acceptInvitationEntity, String.class);
    }

    @Operation(summary = "Reject Invitation Test")
    @GetMapping("/healthcheck/rejectInvitationTest")
    public ResponseEntity<String> rejectInvitationTest() throws JSONException {
        PostUserRequest postUserRequest = new PostUserRequest();
        postUserRequest.setEmail(USER_INVITATION_REJECT_EMAIL_TEST_VALUE);
        postUserRequest.setFirstname(USER_FIRSTNAME_TEST_VALUE);
        postUserRequest.setLastname(USER_LASTNAME_TEST_VALUE);
        postUserRequest.setPassword(USER_PASSWORD_TEST_VALUE);

        ResponseEntity<String> userResponse = restTemplate.postForEntity("/api/users", postUserRequest, String.class);
        String userResponseBody = userResponse.getBody();
        JSONObject userJsonObject = new JSONObject(userResponseBody);
        testUserInvitationRejectId = "null".equals(userJsonObject.getString("id")) ? null : new BigInteger(userJsonObject.getString("id"));

        PostInvitationRequest postInvitationRequest = new PostInvitationRequest();
        postInvitationRequest.setUser(testUserInvitationRejectId);
        postInvitationRequest.setOrganization(testOrganizationId);

        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_INVITATION_REJECT_EMAIL_TEST_VALUE, USER_PASSWORD_TEST_VALUE);

        HttpEntity<PostInvitationRequest> postInvitationEntity = new HttpEntity<>(postInvitationRequest, entity.getHeaders());

        restTemplate.exchange("/api/invitations", HttpMethod.POST, postInvitationEntity, String.class);

        PostInvitationRequest rejectInvitationRequest = new PostInvitationRequest();
        rejectInvitationRequest.setUser(testUserInvitationRejectId);
        rejectInvitationRequest.setOrganization(testOrganizationId);

        HttpEntity<PostInvitationRequest> rejectInvitationEntity = new HttpEntity<>(rejectInvitationRequest, entity.getHeaders());

        return restTemplate.exchange("/api/invitations/reject", HttpMethod.POST, rejectInvitationEntity, String.class);
    }

    @Operation(summary = "Healthcheck", description = "Perform app healthcheck by executing all test endpoints")
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
        try {
            ResponseEntity<String> createUserResponse = createUserTest();
            if (!successfulResponseCodes.contains(createUserResponse.getStatusCode())) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Endpoint: " + "/healthcheck/createUserTest" + " is not working properly");
            }

            HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);
            for (String endpoint : endpointsToCheck) {
                try {
                    ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);
                    if (!successfulResponseCodes.contains(response.getStatusCode())) {
                        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Endpoint: " + endpoint + " is not working properly");
                    }
                } catch (HttpClientErrorException | HttpServerErrorException e) {
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .body("Endpoint: " + endpoint + " returned an error - " + e.getStatusCode() + ": " + e.getStatusText());
                } catch (ResourceAccessException e) {
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .body("Endpoint: " + endpoint + " is not reachable - " + e.getMessage());
                }
            }

            clearTestData();
            return ResponseEntity.ok("All endpoints work properly");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void clearTestData() throws JSONException {
        HttpEntity<String> entity = createHttpEntityWithAuthenticatedTestUserCredentials(USER_EMAIL_TEST_VALUE, testUserPassword);

        restTemplate.exchange("/api/users/" + testUserInvitationRejectId, HttpMethod.DELETE, entity, String.class);
        restTemplate.exchange("/api/users/" + testUserInvitationAcceptId, HttpMethod.DELETE, entity, String.class);
        restTemplate.exchange("/api/users/" + testUserInvitationId, HttpMethod.DELETE, entity, String.class);
        restTemplate.exchange("/api/users/" + testUserId, HttpMethod.DELETE, entity, String.class);
    }

    private HttpEntity<String> createHttpEntityWithAuthenticatedTestUserCredentials(String email, String password) throws JSONException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login", authenticationRequest, String.class);
        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);

        String jwt =  jsonObject.getString("jwt");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }*/
}
