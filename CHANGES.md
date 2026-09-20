# UI/UX Upgrade

Updated the Deepfolio portfolio with a more polished, professional visual system while preserving the existing Spring Boot + Thymeleaf architecture and backend functionality.

## Public portfolio
- Premium glassmorphism-style navigation and panels.
- Improved visual hierarchy, spacing, typography and responsive behavior.
- More polished hero section with ambient lighting, status badge and portrait interactions.
- Improved project, certificate, education, experience and GitHub cards.
- Better form focus states and contact-panel presentation.
- Hover micro-interactions for buttons, tags, cards and repository links.
- Added a floating Back to Top control.
- Kept dark/light theme support.
- Kept reduced-motion accessibility support.

## Admin panel
- Refined sidebar, header, cards, tables and form controls.
- Added subtle hover/focus feedback and improved depth/shadows.
- Preserved existing admin routes and functionality.

## Files changed
- `src/main/resources/static/css/style.css`
- `src/main/resources/static/css/admin.css`
- `src/main/resources/static/js/main.js`
- `src/main/resources/templates/index.html`

### Contact alert fix — 20 Sep 2026
- Fixed the contact form so success and error messages cannot render together.
- Replaced query-parameter alerts (`?success` / `?error`) with mutually exclusive Spring flash attributes.
- Successful submission redirects to `/#contact` with only the success alert.
- Validation failure redirects to `/#contact` with only the error alert while preserving the entered form values and validation errors.
- Refined alert styling for a cleaner professional UI.

### Contact popup fix — 21 Sep 2026
- **Root cause:** `th:if` and `th:replace` were on the same `<div>`. Thymeleaf evaluates `th:replace` first, so the `th:if` was ignored and *both* alerts always rendered. They are now wrapped in `<th:block th:if>`.
- The success / error message now appears as a floating popup (toast) at the top-right, only after a real submission:
  - valid message saved → green "Message sent" popup
  - validation failed → red "Could not send" popup
- Popup auto-closes (6s success / 8s error), pauses on hover, and has a close button and Escape-key dismiss. It does not reappear on page refresh (flash attributes).
- Invalid fields now get a red border (matching the "fix the highlighted fields" message), and the first invalid field is focused.
- Works in light/dark themes, on mobile, and with reduced-motion.

Files changed: `templates/index.html`, `templates/fragments/public.html`, `static/css/style.css`, `static/js/main.js`
