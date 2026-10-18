---
name: Academic Precision
colors:
  surface: '#faf8ff'
  surface-dim: '#d2d9f4'
  surface-bright: '#faf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f3ff'
  surface-container: '#eaedff'
  surface-container-high: '#e2e7ff'
  surface-container-highest: '#dae2fd'
  on-surface: '#131b2e'
  on-surface-variant: '#434655'
  inverse-surface: '#283044'
  inverse-on-surface: '#eef0ff'
  outline: '#747686'
  outline-variant: '#c4c5d7'
  surface-tint: '#2151da'
  primary: '#0037b0'
  on-primary: '#ffffff'
  primary-container: '#1d4ed8'
  on-primary-container: '#cad3ff'
  inverse-primary: '#b7c4ff'
  secondary: '#515f74'
  on-secondary: '#ffffff'
  secondary-container: '#d5e3fc'
  on-secondary-container: '#57657a'
  tertiary: '#004e47'
  on-tertiary: '#ffffff'
  tertiary-container: '#00685f'
  on-tertiary-container: '#7be7d9'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dce1ff'
  primary-fixed-dim: '#b7c4ff'
  on-primary-fixed: '#001551'
  on-primary-fixed-variant: '#0039b5'
  secondary-fixed: '#d5e3fc'
  secondary-fixed-dim: '#b9c7df'
  on-secondary-fixed: '#0d1c2e'
  on-secondary-fixed-variant: '#3a485b'
  tertiary-fixed: '#89f5e7'
  tertiary-fixed-dim: '#6bd8cb'
  on-tertiary-fixed: '#00201d'
  on-tertiary-fixed-variant: '#005049'
  background: '#faf8ff'
  on-background: '#131b2e'
  surface-variant: '#dae2fd'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 3.5rem
    fontWeight: '700'
    lineHeight: 4rem
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 2rem
    fontWeight: '600'
    lineHeight: 2.5rem
    letterSpacing: -0.015em
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 1.75rem
    fontWeight: '600'
    lineHeight: 2.25rem
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Inter
    fontSize: 1.5rem
    fontWeight: '600'
    lineHeight: 2rem
    letterSpacing: -0.01em
  title-lg:
    fontFamily: Inter
    fontSize: 1.25rem
    fontWeight: '600'
    lineHeight: 1.75rem
    letterSpacing: -0.005em
  title-md:
    fontFamily: Inter
    fontSize: 1rem
    fontWeight: '600'
    lineHeight: 1.5rem
    letterSpacing: '0'
  body-lg:
    fontFamily: Inter
    fontSize: 1.125rem
    fontWeight: '400'
    lineHeight: 1.75rem
    letterSpacing: '0'
  body-md:
    fontFamily: Inter
    fontSize: 0.9375rem
    fontWeight: '400'
    lineHeight: 1.5rem
    letterSpacing: '0'
  body-sm:
    fontFamily: Inter
    fontSize: 0.8125rem
    fontWeight: '400'
    lineHeight: 1.25rem
    letterSpacing: 0.005em
  label-lg:
    fontFamily: Inter
    fontSize: 0.875rem
    fontWeight: '500'
    lineHeight: 1.25rem
    letterSpacing: 0.01em
  label-md:
    fontFamily: Inter
    fontSize: 0.75rem
    fontWeight: '600'
    lineHeight: 1rem
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Inter
    fontSize: 0.6875rem
    fontWeight: '600'
    lineHeight: 0.875rem
    letterSpacing: 0.03em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-desktop: 1.5rem
  margin: 1rem
  margin-tablet: 1.5rem
  margin-desktop: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
  space-2xl: 3rem
---

## Brand & Style

This design system is engineered for structured academic workflows across Compose Multiplatform (Android and Desktop). Built on modern Material Design 3 foundations, it prioritizes clarity, informational density, and sustained cognitive focus over decorative embellishment. The visual style marries corporate structural discipline with restrained educational calm.

### Core Tenets
- **Cognitive Quietude:** Interfaces use a subdued slate canvas, letting text hierarchies and precise state tokens direct attention without visual noise.
- **Data Model Fidelity:** Strict adherence to exact API models. Avoid synthetic presentation layers—such as unbacked course ratings, instructor headshots, or extraneous tags—when the backend provides only structural schemas (identifiers, enrollment states, timestamps, and curricula).
- **Cross-Platform Parity:** Components preserve tactile, predictable ergonomics whether operated via pointer/keyboard on desktop or touch gestures on mobile.
- **Deterministic State Communication:** Critical enrollment stages must be immediately recognizable through reserved, single-purpose semantic status indicators.

## Colors

The palette establishes an authoritative, distraction-free environment anchored by an academic cobalt blue. The hierarchy segregates neutral foundational surfaces from high-signal status indicators.

### Light Mode Foundations
- **Canvas / Background:** `#F8FAFC` (Slate 50)
- **Surface / Cards:** `#FFFFFF` (Pure White)
- **Surface Variant / Hover:** `#F1F5F9` (Slate 100)
- **Structural Borders:** `#E2E8F0` (Slate 200)
- **Primary Text:** `#0F172A` (Slate 900)
- **Secondary / Supporting Text:** `#64748B` (Slate 500)
- **Muted Text / Disabled:** `#94A3B8` (Slate 400)

### Dark Mode Foundations
- **Canvas / Background:** `#0F172A` (Slate 900)
- **Surface / Cards:** `#1E293B` (Slate 800)
- **Surface Variant / Hover:** `#334155` (Slate 700)
- **Structural Borders:** `#334155` (Slate 700)
- **Primary Text:** `#F8FAFC` (Slate 50)
- **Secondary / Supporting Text:** `#94A3B8` (Slate 400)
- **Muted Text / Disabled:** `#64748B` (Slate 500)
- **Accent / Primary (Dark Mode Active):** `#60A5FA` (Blue 400)

### Strict Enrollment State Mappings
States use a two-tier color model: an opaque foreground paired with a 10%–14% alpha tinted background surface. Never use arbitrary shades outside this specification.

- **Submitted:** `#D97706` (Amber 600) / Background `#FEF3C7` (Light), `#78350F` at 25% (Dark)
- **AwaitingValidation:** `#6366F1` (Indigo 500) / Background `#EEF2FF` (Light), `#312E81` at 25% (Dark)
- **SeatsConfirmed:** `#0D9488` (Teal 600) / Background `#CCFBF1` (Light), `#134E4A` at 25% (Dark)
- **Paid:** `#059669` (Emerald 600) / Background `#D1FAE5` (Light), `#064E3B` at 25% (Dark)
- **Completed:** `#2563EB` (Blue 600) / Background `#DBEAFE` (Light), `#1E3A8A` at 25% (Dark)
- **Cancelled:** `#E11D48` (Rose 600) / Background `#FFE4E6` (Light), `#4C0519` at 25% (Dark)

## Typography

The typography scale uses Inter to deliver clear, uniform glyph rendering across both Latin-1 and Ukrainian Cyrillic character sets (`Є, І, Ї, Ґ`). 

### Hierarchy Guidelines
- **Academic Scannability:** Use `title-lg` and `title-md` for tabular data, course identifiers, and module rosters. 
- **Tabular Figures:** Configure font features to `tnum` (tabular numerals) for course codes, dates, timestamps, seat limits, and credit totals to guarantee strict vertical alignment.
- **Case Rules:** State badges (`label-md`, `label-sm`) use sentence-cased or capitalized tokens rather than all-caps, preventing visual friction in long lists.

## Layout & Spacing

Layouts follow an 8dp/4dp strict grid rhythm optimized for both Android phones and wide Desktop Compose windows.

### Form-Factor Adaptation
- **Mobile (< 600dp):** Single-column layout. Gutter `16px` (`space-md`), canvas margin `16px` (`margin`). Bottom-sheet modals for contextual inspection.
- **Tablet (600dp – 1023dp):** Split view or 8-column grid. Gutter `16px`, outer margins `24px` (`margin-tablet`). Master-detail navigation flows side by side.
- **Desktop (>= 1024dp):** Fluid 12-column grid constrained to a `1440px` maximum viewport container. Gutter `24px` (`gutter-desktop`), margin `32px` (`margin-desktop`). Side rails and multi-pane panes occupy fixed widths (`260px` sidebar, dynamic center canvas, `360px` contextual inspector).

### Spacing Rules
- Use `space-xs` (4px) and `space-sm` (8px) for internal pill chips, badge padding, and icon-to-text intervals.
- Use `space-md` (16px) for standard card padding and form row distributions.
- Use `space-lg` (24px) for distinct content groupings within cards and modal surfaces.

## Elevation & Depth

This system avoids heavy drop shadows, relying instead on structural borders and Material 3 surface tint tiers.

### Surface Hierarchy
- **Level 0 (Canvas):** Pure `#F8FAFC` (Light) or `#0F172A` (Dark). Used for base backgrounds and scaffolding.
- **Level 1 (Default Containers):** Card surfaces and top app bars use `#FFFFFF` (Light) or `#1E293B` (Dark) with a continuous `1px` outline of `#E2E8F0` (Light) or `#334155` (Dark). No elevation shadow on stationary elements.
- **Level 2 (Interactive Floating / Hover):** Transient cards on hover and dropdown menus. Light mode introduces an ambient shadow: `box-shadow: 0 4px 12px -2px rgba(15, 23, 42, 0.06), 0 2px 6px -1px rgba(15, 23, 42, 0.04)`. Dark mode shifts surface color to `#283548` with no shadow.
- **Level 3 (Modal / Dialogs / Sheets):** Dialogs and bottom sheets use level 3 overlays with `#0F172A` at 40% scrim in light mode, and 60% scrim in dark mode.

## Shapes

Corner radii adhere directly to Material Design 3 geometry conventions:

- **Buttons & Pill Chips:** Full pill radius (`9999px` / `rounded-full`). Provides immediate visual affordance for clickable action targets.
- **Status Badges & Enrollment Tags:** `8px` corner radius (`rounded-md`). Deliberately differentiated from interactive pill buttons to avoid confusion.
- **Cards, Input Containers & List Tiles:** `12px` corner radius (`rounded-lg`). Creates structured, stable rectangles that cleanly tile grids on both desktop and mobile.
- **Dialogs & Bottom Sheets:** `20px` corner radius (`rounded-xl`). Applied to all four corners of desktop dialogs, and the top-left and top-right of mobile bottom sheets.

## Components

### Buttons
- **Primary Action:** Pill-shaped (`rounded-full`), minimum height `40px` (Desktop) / `48px` (Touch target on Mobile). Solid `#1D4ED8` background with `#FFFFFF` text. Hover: `#1E40AF`.
- **Secondary / Outlined:** Pill-shaped, `1px` border of `#E2E8F0` (Light) / `#334155` (Dark). Text inherits `#0F172A` (Light) / `#F8FAFC` (Dark). Surface transparent.
- **Text Button:** Pill-shaped, zero outline, zero elevation. Primary blue text. For inline table actions and modal dismissal.

### Status Badges & Chips
- **Status Badges:** Read-only indicators. Exact dimensions: `24px` height, `8px` corner radius, `space-xs` vertical by `space-sm` horizontal padding. Font: `label-sm`. Colored strictly by the enrollment state matrix.
- **Filter Chips:** Pill-shaped, interactive. Default state: unselected with `1px` border. Selected state: solid neutral or accent surface with contrasting text.

### Cards & Grouping Containers
- **Course & Roster Cards:** `12px` corner radius, `1px` border `#E2E8F0` (Light) / `#334155` (Dark), background `#FFFFFF` (Light) / `#1E293B` (Dark). Padding: `space-md` (Mobile) / `space-lg` (Desktop). 
- Avoid placeholder iconography, fictional hero banners, and user-generated media wrappers. Display only raw verified record fields: Course Code, Course Title, Capacity, Enrollment Status, and Scheduled Period.

### Form Inputs
- **Text Fields:** `12px` corner radius, `1px` border (`#CBD5E1` Light, `#475569` Dark). Padding: `12px 16px`. Active/Focused state switches border color to `#1D4ED8` (or `#60A5FA` in dark mode) with a distinct `2px` border width, no outer glow.
- **Labeling:** Floating or persistent top label using `label-md` in secondary text color (`#64748B` / `#94A3B8`).

### Data Lists & Tables
- **Table Rows:** Minimum row height `48px` (Desktop) / `56px` (Mobile). Row divider `1px` border `#F1F5F9` (Light) / `#1E293B` (Dark).
- **Checkboxes & Radios:** `4px` corner radius for checkboxes, full circle for radios. Active state fills with `#1D4ED8` (Light) or `#60A5FA` (Dark) with a pure white check/dot icon.