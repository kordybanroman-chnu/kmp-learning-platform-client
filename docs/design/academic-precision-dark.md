---
name: Academic Precision Dark
colors:
  surface: '#0f172a'
  surface-dim: '#0b1326'
  surface-bright: '#31394d'
  surface-container-lowest: '#060e20'
  surface-container-low: '#131c31'
  surface-container: '#1e293b'
  surface-container-high: '#334155'
  surface-container-highest: '#475569'
  on-surface: '#f8fafc'
  on-surface-variant: '#94a3b8'
  inverse-surface: '#dae2fd'
  inverse-on-surface: '#283044'
  outline: '#334155'
  outline-variant: '#475569'
  surface-tint: '#a4c9ff'
  primary: '#a4c9ff'
  on-primary: '#081b4b'
  primary-container: '#1d4ed8'
  on-primary-container: '#003a6b'
  inverse-primary: '#0060ac'
  secondary: '#b9c8de'
  on-secondary: '#233143'
  secondary-container: '#39485a'
  on-secondary-container: '#a7b6cc'
  tertiary: '#3cddc7'
  on-tertiary: '#003731'
  tertiary-container: '#00b6a3'
  on-tertiary-container: '#004039'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#d4e3ff'
  primary-fixed-dim: '#a4c9ff'
  on-primary-fixed: '#001c39'
  on-primary-fixed-variant: '#004883'
  secondary-fixed: '#d4e4fa'
  secondary-fixed-dim: '#b9c8de'
  on-secondary-fixed: '#0d1c2d'
  on-secondary-fixed-variant: '#39485a'
  tertiary-fixed: '#62fae3'
  tertiary-fixed-dim: '#3cddc7'
  on-tertiary-fixed: '#00201c'
  on-tertiary-fixed-variant: '#005047'
  background: '#0b1326'
  on-background: '#dae2fd'
  surface-variant: '#2d3449'
  primary-accent: '#60a5fa'
  state-submitted-text: '#fcd34d'
  state-submitted-bg: '#451a03'
  state-awaiting-text: '#a5b4fc'
  state-awaiting-bg: '#1e1b4b'
  state-confirmed-text: '#5eead4'
  state-confirmed-bg: '#134e4a'
  state-paid-text: '#6ee7b7'
  state-paid-bg: '#064e3b'
  state-completed-text: '#93c5fd'
  state-completed-bg: '#172554'
  state-cancelled-text: '#fda4af'
  state-cancelled-bg: '#4c0519'
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

This design system forms the dedicated dark mode counterpart to structured academic workflows across Compose Multiplatform (Android, Web, and Desktop). Anchored in modern Material Design 3 and purposeful minimalism, it delivers uncompromising clarity, sustained cognitive calm, and structural rigor during prolonged nocturnal or low-light study sessions.

### Core Tenets
- **Cognitive Quietude:** Interfaces transition from bright canvases to deep slate foundations, eliminating display glare while preserving crisp hierarchy, deliberate typographical cadence, and minimal visual noise.
- **Data Model Fidelity:** Strict alignment with canonical backend schemas. Visual ornamentation—such as synthetic star ratings, illustrative badges, or fictitious media banners—is rejected in favor of verified course identifiers, credit metrics, structural enrollment states, and academic timelines.
- **Multiplatform Ergonomic Parity:** The interface respects pointer fidelity, tactile touch feedback, and keyboard tab-stops equally across mobile phones, tablets, and wide-screen desktop displays.
- **Deterministic State Communication:** Critical enrollment lifecycle stages map to designated dark-mode tint surfaces, ensuring immediate accessibility without alarming luminance shifts.

## Colors

The color palette shifts the daylight academic aesthetic into a low-luminance, high-contrast dark space without compromising semantic meaning or WCAG AAA readability.

### Structural Foundations
- **Canvas / Background (`#0F172A`):** Deep slate canvas serving as the base layer for main viewports and root scaffolding.
- **Surface / Cards (`#1E293B`):** Rich slate container for modular content groupings, persistent sidebar navigation panels, and interactive cards.
- **Surface Variant / Hover (`#334155`):** Structural interactive tier for item hover states, selected rows, and interactive container backgrounds.
- **Borders & Dividers (`#334155`):** 1px structural outlines defining boundaries between cards, tables, and header rails without harsh visual demarcation.
- **Primary Text (`#F8FAFC`):** Crisp, anti-aliased off-white offering optimal contrast across all container elevations.
- **Secondary / Supporting Text (`#94A3B8`):** Calibrated muted slate for secondary metadata, column headers, and labels.
- **Disabled Text & Muted Icons (`#64748B`):** Non-interactive placeholders and inactive tab indicators.
- **Primary Accent (`#60A5FA`):** Vibrant academic blue, tuned to remain crisp and strain-free against dark surfaces.

### Academic Lifecycle State Mappings
Enrollment status badges use a dual-tier dark formulation pairing saturated, lightened foreground text (300-level tints) with deep semantic container backdrops (950-level tones):
- **Submitted:** Foreground `#FCD34D` (Amber 300) on `#451A03` (Amber 950).
- **AwaitingValidation:** Foreground `#A5B4FC` (Indigo 300) on `#1E1B4B` (Indigo 950).
- **SeatsConfirmed:** Foreground `#5EEAD4` (Teal 300) on `#134E4A` (Teal 900).
- **Paid:** Foreground `#6EE7B7` (Emerald 300) on `#064E3B` (Emerald 950).
- **Completed:** Foreground `#93C5FD` (Blue 300) on `#172554` (Blue 950).
- **Cancelled:** Foreground `#FDA4AF` (Rose 300) on `#4C0519` (Rose 950).

## Typography

Typography retains the exact structural hierarchy of the academic scale, rendering Inter across Latin, Ukrainian Cyrillic (`Є, І, Ї, Ґ`), and technical figures with balanced sub-pixel rendering on dark surfaces.

### Rules of Usage
- **Tabular Numerals (`tnum`):** All course IDs, credit scores, capacity meters, timestamps, and registration totals must activate `font-feature-settings: "tnum" 1` to ensure aligned numeric columns.
- **Antialiasing in Dark Mode:** Font weights on `#0F172A` and `#1E293B` must apply `-webkit-font-smoothing: antialiased` to prevent visual blooming or optical weight gain.
- **Display and Headings:** Reserved for top-level academic dashboards, schedule titles, and modal headers. `headline-lg-mobile` replaces `headline-lg` under `600dp` breakpoints.
- **Badges & Metadata:** State indicators and contextual pills leverage `label-md` and `label-sm` with capital/sentence casing. Never apply full uppercase tracking to enrollment badges.

## Layout & Spacing

Layout geometry follows an exact 8dp base grid with a 4dp micro-step, preserving structural predictability across touch and pointer surfaces.

### Responsive Breakpoints
- **Mobile (< 600dp):** Fluid single-column layout. Margin `1rem` (`margin`), gutter `1rem` (`gutter`). Modals display as bottom sheets.
- **Tablet (600dp – 1023dp):** 8-column layout. Margin `1.5rem` (`margin-tablet`), gutter `1rem` (`gutter`). Splits views into master-detail side panes.
- **Desktop (>= 1024dp):** 12-column layout capped at `1440px`. Margin `2rem` (`margin-desktop`), gutter `1.5rem` (`gutter-desktop`). Employs a persistent navigation rail (`260px`), dynamic center workspace, and collateral detail inspector (`360px`).

### Spatial Harmony
- `space-xs` (4px) and `space-sm` (8px): Badge interior padding, icon gaps, and input field micro-spacing.
- `space-md` (16px): Internal padding for mobile cards, standard grid gaps, and stacked form fields.
- `space-lg` (24px): Desktop card padding, header separations, and modal content segments.
- `space-xl` (32px) and `space-2xl` (48px): Major section boundaries and academic curriculum divisions.

## Elevation & Depth

In dark mode, diffuse drop shadows are removed in favor of structural outlines, tonal luminance tiers, and semi-opaque backdrop overlays.

### Surface Tiers
- **Tier 0 (Root Canvas):** `#0F172A`. Base layer for background windows, shell rails, and desktop window scaffolds.
- **Tier 1 (Stationary Containers):** `#1E293B`. Card surfaces, data tables, and persistent app bars. Boundaries are framed by a continuous `1px` outline of `#334155`. Stationary components feature no drop shadow.
- **Tier 2 (Interactive Floating & Hover):** When hovering over actionable cards or invoking dropdown menus, the surface shifts to `#27354A` with a crisp `1px` border of `#475569`.
- **Tier 3 (Modals & Sheets):** Dialogs, contextual popovers, and mobile bottom sheets utilize `#1E293B` elevated with a 60% alpha scrim (`#030712` at 0.60) over underlying tiers.

## Shapes

Corner radii adhere directly to modern Material 3 conventions, balancing actionable curves with structured data containment.

- **Action Controls & Filter Chips:** Full pill radius (`9999px` / `rounded-full`) distinguishes interactive action elements from passive cards.
- **Status Badges & Enrollment Tags:** `8px` (`rounded-md`). Distinctly rectangular to indicate non-clickable categorical data.
- **Cards, Modules & Input Fields:** `12px` (`rounded-lg`). Provides balanced boundaries for tiled grid items and form elements.
- **Modals & Dialogs:** `20px` (`rounded-xl`). Applied to all four corners of desktop dialogs, and the top-left and top-right of mobile bottom sheets.

## Components

### Buttons
- **Primary Action:** Pill-shaped (`rounded-full`), minimum height `40px` (Desktop) / `48px` (Mobile touch). Solid `#60A5FA` background with `#081B4B` high-contrast bold typography. Hover state transitions to `#93C5FD`.
- **Secondary / Outlined:** Pill-shaped, `1px` border of `#334155`. Background is transparent with `#F8FAFC` label text. Hover state introduces `#1E293B` surface fill and `#475569` border.
- **Text Button:** Pill-shaped, zero border, zero surface. `#60A5FA` label text with a `space-sm` horizontal pad.

### Enrollment Badges & Filter Chips
- **Status Badges:** Read-only indicators. Exact dimensions: `24px` height, `8px` radius (`rounded-md`), `space-xs` (4px) vertical by `space-sm` (8px) horizontal padding. Type: `label-sm`. Applied colors match the dark state matrix without variance.
- **Filter Chips:** Interactive pills (`rounded-full`), minimum height `32px`. Unselected: `#1E293B` surface, `1px` `#334155` border, `#94A3B8` text. Selected: `#334155` surface, `1px` `#60A5FA` border, `#F8FAFC` text.

### Cards & Curriculum Containers
- **Course & Roster Cards:** `12px` corner radius, `1px` border in `#334155`, `#1E293B` surface. Interior padding is `space-md` (Mobile) / `space-lg` (Desktop). Contains verified curriculum records: Course ID, Course Name, Credits, and Registration Badges.

### Form Inputs
- **Text Fields:** `12px` corner radius, `1px` border of `#334155`, `#0F172A` interior fill. Padding: `12px 16px`. Active focused state applies a `2px` crisp border of `#60A5FA` with no outer glow.
- **Labels & Helper Text:** Top persistent or floating labels use `label-md` in `#94A3B8`. Error text maps to `#FDA4AF` with an active `#F43F5E` border.

### Data Tables & Roster Lists
- **Rows:** Minimum height `48px` (Desktop) / `56px` (Mobile). Row bottom divider uses `1px` border in `#334155`. Hover on row applies `#243044` surface fill.
- **Selection Controls:** Checkboxes use `4px` corner radius; radio buttons are circular. Checked state fills with `#60A5FA` containing `#081B4B` icon marks.