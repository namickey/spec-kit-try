# Implementation Plan: 家族間メッセージ共有

**Branch**: `001-kazoku-message` | **Date**: 2025-09-15 | **Spec**: `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\spec.md`
**Input**: Feature specification from `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\spec.md`

## Execution Flow (/plan command scope)
1. Load feature spec from Input path — OK
2. Fill Technical Context — see below
3. Constitution Check — PASS with justifications
4. Execute Phase 0 → `research.md` (created)
5. Execute Phase 1 → `data-model.md`, `contracts/openapi.yaml`, `quickstart.md` (created)
6. Re-evaluate Constitution Check — PASS
7. Plan Phase 2 — described in section "Phase 2: Task Planning Approach"

**Note**: The environment could not run `scripts/setup-plan.sh` (no bash/WSL available). All paths below use absolute repository-root paths and artifacts were generated locally following the `templates/plan-template.md` guidance.

## Summary
- Primary requirement: allow family members to share messages and media references within family groups; invite unregistered users; do not allow message deletion/retraction after posting.
- Technical approach: Implement a web MPA with a Spring Boot backend (JDK21, Spring Boot 3.5, H2 for local/dev, MyBatis for persistence mapping) and server-rendered HTML pages using Thymeleaf with JavaScript and CSS for UI interactions. Invite flow implemented via tokenized invitation links (email primary; SMS optional).

## Technical Context
**Language/Version**: JDK 21
**Primary Dependencies**: Spring Boot 3.5, MyBatis, H2 (dev), (production DB TBD)
**Build System**: Maven only (Gradle will not be used)
**Code Generation**: Lombok will be used for POJOs to reduce boilerplate (getters/setters/constructors)
**Storage**: Relational (H2 for both development and production)
**Testing**: JUnit 5, integration tests using testcontainers or H2 for integration; contract tests generated from OpenAPI.
**Target Platform**: Server-side JVM (Linux/Cloud), client: modern browsers (MPA)
**Project Type**: Web application (backend + server-rendered frontend)
**Performance Goals**: Low-to-moderate traffic initially; p95 < 200ms for common read paths (recommendation)
**Constraints**: Simplicity and test-first development per constitution

## Constitution Check
- Simplicity: Projects limited to backend + frontend (2 projects) — PASS
- Architecture: Library-first not required for this small feature; prefer direct app code with well-scoped modules — ACCEPTABLE
- Testing: TDD required — tests written before implementation (contract & integration tests created)
- Observability: Structured logs from backend; server-side request tracing recommended
- Versioning: Start at 0.1.0 for feature branch

## Project Structure (decision)
backend/ (Spring Boot app using Thymeleaf for server-side templates)
  ├── src/main/java/...(models, controllers, services, mappers)
  └── src/test/java/...
frontend/ (server-rendered templates + static JS/CSS)
  ├── templates/ (Thymeleaf templates)
  └── static/ (js, css)

## Phase 0: Outline & Research (completed)
- See `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\research.md` for decisions and unresolved items. Key decisions:
  - Invite flow: implement email-based invitation with time-limited token; SMS optional (requires provider) — recommended
  - Auth: use simple account model (email as primary identifier). [NEEDS CONFIRMATION if external SSO required]
  - Data retention: default retention 1 year; archiving strategy TBD

## Phase 1: Design & Contracts (completed)
- Data model extracted to `data-model.md`.
- API contracts started at `contracts/openapi.yaml` (endpoints for groups, messages, invitations).
- Contract tests should be generated from `contracts/` (not executed here).

## Phase 2: Task Planning Approach
- Generate tasks from Phase 1 artifacts (models → mappers → services → controllers → templates → integration tests).
- TDD ordering: contract tests → integration tests → unit tests → implementation.

## Progress Tracking
- [x] Phase 0: Research complete
- [x] Phase 1: Design complete
- [x] Phase 2: Task planning described (not executed)
- [ ] Phase 3: Tasks generated (/tasks command)
- [ ] Phase 4: Implementation complete
- [ ] Phase 5: Validation passed

## Artifacts generated (absolute paths)
- `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\plan.md` (this file)
- `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\research.md`
- `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\data-model.md`
- `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\quickstart.md`
- `c:\Users\teeth\github\spec-kit-try\specs\001-kazoku-message\contracts\openapi.yaml`
