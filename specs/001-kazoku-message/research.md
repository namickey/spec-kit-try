# Research: 家族間メッセージ共有

## Unknowns extracted from spec
- Invite channels: email preferred, SMS optional (requires provider)
- Authentication model: email-based accounts assumed; SSO not requested
- Data retention: default to 1 year (archive after) — policy needed
- Search capabilities: basic filtering by sender/date; full-text search optional later

## Decisions
- Invite flow: implement email invitation with time-limited token that creates an account when accepted. SMS is optional and documented as an extension requiring external provider.
- Auth: Email as primary identifier; minimal account (email, display name, password hash). Optional: social SSO later.
- Message deletion: NOT allowed for senders — messages are immutable after posting.
- Retention: Keep messages for 1 year by default; provide admin tools for archival and export.
- Storage decision: Use H2 for both development and production. Note: using H2 in production affects scalability and operational concerns; ensure appropriate backup and monitoring are in place.

## Rationale
- Email invites are broadly supported and simplest to implement without external dependencies. SMS introduces cost and provider integration.
- Immutable messages simplify consistency, audit, and reduce accidental data loss risk in family communication context.

## Alternatives considered
- Allow sender deletion/editing: rejected due to audit/safety concerns.
- Use third-party identity (SSO) initially: rejected to keep MVP simple.

## Research tasks (next steps)
- Confirm preferred invite channels with stakeholders.
- Decide production DB (Postgres recommended) and migration plan.
- Define retention policy and legal/compliance constraints if any.
