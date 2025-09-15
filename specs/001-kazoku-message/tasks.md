# Tasks: 家族間メッセージ共有

**Input**: Design documents from `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\`
**Prerequisites**: `plan.md` (required), `data-model.md`, `contracts/openapi.yaml`, `research.md`, `quickstart.md`

## Execution Flow (main)
Follow TDD: write failing tests first, then implement to make them pass. Use Maven only. H2 is used for both dev and production. Thymeleaf is the server-side template engine.

Format: `[ID] [P?] Description` — `[P]` means task can run in parallel (no shared file conflicts).

## Phase 3.1: Setup
- [ ] T001 Create Maven Spring Boot project skeleton for backend
  - Create `c:\Users\teeth\github\spec-kit-try\backend\pom.xml` with dependencies: `spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `mybatis-spring-boot-starter`, `com.h2database:h2`, `spring-boot-starter-test`.
  - Create directories: `backend/src/main/java/com/speckit/message/`, `backend/src/main/resources/templates/`, `backend/src/main/resources/static/js/`, `backend/src/main/resources/static/css/`, `backend/src/test/java/`.

- [ ] T002 [P] Add basic application entry and config
  - Add `backend/src/main/java/com/speckit/message/Application.java` (SpringBootApplication main).
  - Add `backend/src/main/resources/application.yml` with H2 config for default profile and server port 8080. (H2 used in prod/dev)

- [ ] T003 [P] Add CI/dev tooling placeholders
  - Add `backend/.gitignore`, `backend/mvnw` wrapper files (or instructions in README to run with local Maven).

## Phase 3.2: Tests First (TDD) ⚠️ MUST COMPLETE BEFORE CORE
**CRITICAL: These tests MUST be added and MUST FAIL before implementation.**

- [ ] T004 [P] Contract test: POST `/messages` expecting 201 and response schema
  - File: `backend/src/test/java/contract/ContractMessagesPostTest.java`
  - Test should POST JSON `{ "recipient_ids": ["<uuid>"], "body": "Hello" }` to `/messages` and assert `201` and response contains `id` and `created_at`.

- [ ] T005 [P] Contract test: POST `/invitations` expecting 201
  - File: `backend/src/test/java/contract/ContractInvitationsPostTest.java`
  - Test should POST JSON `{ "contact": "invitee@example.com", "target_participants": [] }` to `/invitations` and assert `201` and response contains `token`.

- [ ] T006 [P] Integration test: send message to recipients
  - File: `backend/src/test/java/integration/SendMessageIntegrationTest.java`
  - Flow: create sender User (insert), create recipient User, POST `/messages` with both ids, assert recipient can GET their messages and see the posted message.

- [ ] T007 [P] Integration test: invite unregistered recipient flow
  - File: `backend/src/test/java/integration/InviteFlowIntegrationTest.java`
  - Flow: create inviter User, POST `/invitations` with `contact` (not in users), assert invitation stored with token and `expires_at`, simulate accept (call invitation accept endpoint if implemented) and assert new User created and linked.

## Phase 3.3: Core Implementation (ONLY after tests are failing)

- [ ] T008 [P] Create domain POJOs
  - Files:
    - `backend/src/main/java/com/speckit/message/model/User.java`
    - `backend/src/main/java/com/speckit/message/model/Message.java` (fields: id, senderId, List<String> recipientIds, body, attachmentRef, createdAt, immutable)
    - `backend/src/main/java/com/speckit/message/model/Invitation.java`
  - Note: Use Lombok annotations (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`) to reduce boilerplate.

- [ ] T009 [P] Create MyBatis mappers and mapper XMLs
  - Files:
    - `backend/src/main/java/com/speckit/message/mapper/UserMapper.java`
    - `backend/src/main/java/com/speckit/message/mapper/MessageMapper.java`
    - `backend/src/main/java/com/speckit/message/mapper/InvitationMapper.java`
    - `backend/src/main/resources/mapper/UserMapper.xml`
    - `backend/src/main/resources/mapper/MessageMapper.xml`
    - `backend/src/main/resources/mapper/InvitationMapper.xml`

- [ ] T010 [P] Implement Services
  - Files:
    - `backend/src/main/java/com/speckit/message/service/UserService.java`
    - `backend/src/main/java/com/speckit/message/service/MessageService.java`
    - `backend/src/main/java/com/speckit/message/service/InvitationService.java`
  - Each service should have create/find methods and be unit-testable.

- [ ] T011 [P] Implement Controllers (endpoints)
  - Files:
    - `backend/src/main/java/com/speckit/message/controller/MessageController.java` (POST `/messages`)
    - `backend/src/main/java/com/speckit/message/controller/InvitationController.java` (POST `/invitations`)
  - Controllers should accept JSON and return appropriate status codes.

- [ ] T012 [P] Add Thymeleaf templates and views
  - Files:
    - `backend/src/main/resources/templates/messages/list.html` (recipient inbox)
    - `backend/src/main/resources/templates/messages/compose.html`
    - `backend/src/main/resources/templates/invitations/create.html`

- [ ] T013 [P] Add static JS/CSS stubs
  - Files:
    - `backend/src/main/resources/static/js/messages.js`
    - `backend/src/main/resources/static/css/style.css`

## Phase 3.4: Integration

- [ ] T014 Connect MyBatis to H2 and add schema
  - Files:
    - `backend/src/main/resources/application.yml` (ensure datasource URL points to H2 file or in-memory with persistent settings)
    - `backend/src/main/resources/schema.sql` (create tables for users, messages, invitations)
    - `backend/src/main/resources/data.sql` (optional seed data for tests)

- [ ] T015 [P] Create DB migration or initialization script
  - File: `backend/db/init.sql` (if using file-based H2) or use `schema.sql` in resources.

- [ ] T016 Add logging, structured logs, and request tracing
  - Files:
    - `backend/src/main/resources/logback-spring.xml` or update `application.yml` logging section

## Phase 3.5: Polish

- [ ] T017 [P] Unit tests for service layer
  - Files: `backend/src/test/java/unit/*ServiceTest.java`

- [ ] T018 [P] Update documentation and quickstart
  - Files: `specs/001-kazoku-message/README.md`, update `quickstart.md` if necessary

- [ ] T019 Run manual acceptance following `quickstart.md` and record results

## Dependencies and Ordering Notes
- Setup tasks (`T001`-`T003`) must run first.
- Tests (`T004`-`T007`) must exist and fail before implementation tasks (`T008` onward).
- Model creation tasks (`T008`,`T009`) must complete before service and controller implementation (`T010`-`T011`).
- DB schema (`T014`) must be available before integration tests that hit the DB.

## Parallel Execution Examples
- Parallel group A [P]: `T004`, `T005`, `T006`, `T007` (contract+integration tests) — different test files
- Parallel group B [P]: `T008`, `T009` (models and mappers) — different files

## Task agent example commands
- Run contract test task (example):
  - Agent action: create file `backend/src/test/java/contract/ContractMessagesPostTest.java` with failing test that posts to `/messages` and asserts `201`.
- Implement message model task (example):
  - Agent action: create `backend/src/main/java/com/speckit/message/model/Message.java` POJO with fields and getters/setters.

## Validation Checklist (gates)
- [ ] Every contract file in `contracts/` has a corresponding contract test (T004,T005)
- [ ] Every entity in `data-model.md` has a model task (T008)
- [ ] Tests exist before implementation tasks
- [ ] Tasks specify exact file paths
