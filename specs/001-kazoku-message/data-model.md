# Data Model: 家族間メッセージ共有

## Entities

- User
  - id: UUID
  - email: string (unique)
  - display_name: string
  - password_hash: string
  - created_at: timestamp

- (Removed) FamilyGroup and FamilyGroupMember are not used for this implementation. Communication is managed via explicit participant lists on Message/Invitation entities.

- Invitation
  - id: UUID
  - inviter_id: User.id
  - invitee_contact: string (email or phone)
  - token: string
  - expires_at: timestamp
  - accepted_at: timestamp nullable
  - target_participants: array of User.id (optional) — list of intended participants for the invitation

- Message
  - id: UUID
  - sender_id: User.id
  - recipient_ids: array of User.id
  - body: text
  - attachment_ref: string nullable
  - created_at: timestamp
  - immutable: boolean (true)

## Notes
- Messages are immutable by design (no deletion/edit by senders).
- Invitations create a pending join; accepting creates User (if needed) and FamilyGroupMember.
