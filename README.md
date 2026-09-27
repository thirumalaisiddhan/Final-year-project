# CallGuard AI — Real-Time Context-Aware Spam Call Detection and Intelligent Call Decision System

> **Final-Year Computer Science & Engineering (CSE) Project**  
> An intelligent Android application for behavioral risk analysis of incoming calls and context-aware call decision making.

---

## 1. Project Overview

Traditional spam blockers rely on crowd-sourced binary phone number blacklists ("Unknown = Spam"). This leads to catastrophic false positives where legitimate delivery associates (Amazon, Flipkart, Swiggy, Zomato), home service technicians, and emergency service providers are blocked simply because they call from rotating, unfamiliar numbers.

**CallGuard AI** solves this problem by combining:
1. **Behavioral Telephony Telemetry**: Dialing frequency, short-call ratio, burst pattern rates, and repeat call patterns.
2. **Sequential Backward-Aware Selection (SBAS) & XGBoost Classifier**: Light-weight feature ranking and probabilistic spam inference.
3. **Real-Time Context Awareness**: Matches active deliveries and approved services to prevent false blocks.
4. **Intelligent 4-State Call Decision Engine**: Outputs `ALLOW`, `WARN`, `REVIEW`, or `BLOCK` rather than blind auto-blocking.
5. **Local Privacy Guarantee**: Phone numbers are hashed using SHA-256; zero plaintext call log scraping and zero call audio recording.

---

## 2. High-Level Architecture Diagram

```
+-----------------------------------------------------------------------------------+
|                              Incoming Call Event                                  |
|                 (Android Telecom CallScreeningService Intercept)                  |
+-----------------------------------------+-----------------------------------------+
                                          |
                                          v
                    +-------------------------------------+
                    |   Telephony Feature Extraction      |
                    |   - Calls in 1 min, 5 min, 1 hour   |
                    |   - Short-call ratio (<10s)         |
                    |   - Repeat-call & Burst Score       |
                    +------------------+------------------+
                                       |
                                       v
                    +-------------------------------------+
                    |   SpamPredictionService (ML Layer)  |
                    |   - SBAS Feature Reduction          |
                    |   - XGBoost Probability Engine      |
                    |   -> Risk Score (0-100%) & Level    |
                    +------------------+------------------+
                                       |
                                       v
+-----------------------------------------------------------------------------------+
|                         CallDecisionEngine (Domain Layer)                         |
|                                                                                   |
|   Inputs:                                                                         |
|     [ ML Risk Score ]   [ Trusted safelists ]   [ Active Delivery Context ]       |
|                                                                                   |
|   Rule Evaluation:                                                                |
|     * Trusted Caller / Verified Org?               ==> ALLOW                      |
|     * High Risk BUT Active Delivery Context?      ==> REVIEW (Neutral Safety)    |
|     * Low Risk (<40%)?                             ==> ALLOW                      |
|     * Medium Risk (40-69%)?                        ==> WARN                       |
|     * High Risk (>=70%) AND No Context?            ==> BLOCK / SCREEN             |
+-----------------------------------------+-----------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                                  UI / UX Layer                                    |
|                                                                                   |
|  [ Call Screening Simulator ]  [ Home Dashboard ]  [ Call History & Detail ]     |
|  [ Trusted Callers Directory ] [ Delivery Contexts ] [ Analytics & Feedback ]     |
+-----------------------------------------------------------------------------------+
```

---

## 3. Technology Stack

- **Language**: Kotlin 2.0.21
- **UI Toolkit**: Jetpack Compose with Material 3 (M3)
- **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern + Clean Architecture
- **Navigation**: Jetpack Navigation Compose
- **Concurrency & Reactivity**: Kotlin Coroutines & `StateFlow`
- **Local Persistence**: Android Room Database (`AppDatabase`, TypeConverters, DAOs)
- **System Integration**: Android Telecom `CallScreeningService` (`CallGuardCallScreeningService`)
- **Compatibility**: Android 10+ (API level 29 to 35)

---

## 4. Key Features & Screens

1. **Splash Screen**: Branded cybersecurity AI shield visual with animated transition.
2. **Onboarding**: 3-step walkthrough explaining behavioral detection, delivery protection, and user control.
3. **Home Dashboard**:
   - Master Protection Toggle (`ON` / `OFF`).
   - 4-Card Telemetry Grid (Calls Analyzed, Suspicious, Blocked, Allowed).
   - Quick Action Shortcuts (Trusted, History, Deliveries, Analytics).
   - "Today's Activity" with real-time risk scores and context badges.
4. **Incoming Call Risk Screen (Simulator)**:
   - Realistic incoming call overlay with caller ID and SHA-256 hash.
   - Dynamic Risk Score Gauge (0-100%) and reusable `RiskLevelBadge` (LOW, MEDIUM, HIGH).
   - Active delivery warning: *"Active delivery context detected. Review before blocking."*
   - Interactive Allow, Warn, Block decision buttons and *"Trust this caller"* action.
   - Interactive scenario switcher for project presentation (Amazon courier vs Robocall vs Promo vs Verified).
5. **Call History Screen**:
   - Filter by `All`, `Allowed`, `Warned`, `Blocked`, and `Trusted`.
   - Displays caller, timestamp, risk score badge, and decision status.
6. **Call Detail Screen**:
   - Granular breakdown of SBAS behavioral metrics (calls in 1m/5m/1h, short-call ratio, repeat ratio, burst score).
   - Model version (`v1.0-SBAS-XGBoost`) and Decision Source info.
   - User feedback prompt: *"Was this call actually spam?"* -> *"Trust Caller"* / *"Confirmed Spam"*.
7. **Trusted Callers Screen**:
   - Safelist for individuals and organizations (Amazon Logistics, College, EMI Finance, Family).
   - Expiry support (Permanent, 30 Days, 7 Days, Today Only).
   - Enable/disable toggle switches and delete actions.
8. **Add Trusted Caller**:
   - Input validation for name, phone/identifier, category, reason for trust, and notes.
9. **Delivery & Service Context Screen**:
   - Cards for active Amazon shipments, Flipkart deliveries, and financial services.
   - Add active delivery context dialog (e.g., Swiggy, Zomato, BlueDart).
10. **Analytics Screen**:
    - Telemetry metrics: Analyzed, Suspicious, Blocked, Allowed, False Positives, Delivery Saves.
    - Weekly call volume bar chart.
    - Stacked risk level distribution progress bar.
    - Allowed vs Blocked ratio indicator.
11. **Settings Screen**:
    - Toggle core protection, automatic blocking, warn before blocking, trusted overrides, and delivery safeguards.
    - Sensitivity threshold selector: Low (>80), Medium (>60), High (>40).
12. **About Project Screen**:
    - Academic project overview, ML architecture breakdown, and privacy assurances.

---

## 5. Folder Structure

```
app/src/main/java/com/callguard/ai/
│
├── CallGuardApplication.kt            # Application root & Dependency Injection locator
├── MainActivity.kt                    # Single Activity container
│
├── data/
│   ├── model/
│   │   ├── RiskLevel.kt               # Central RiskLevel, CallDecision, RiskThreshold enums
│   │   ├── Caller.kt                  # Caller model with SHA-256 hashing
│   │   ├── BehaviorFeatures.kt        # Telephony telemetry feature set
│   │   ├── SpamPrediction.kt          # ML risk output & explanation reasons
│   │   ├── TrustedCaller.kt           # Whitelist model for numbers & orgs
│   │   ├── ServiceContext.kt          # Active delivery & courier context
│   │   ├── UserFeedback.kt            # Feedback model for active retraining
│   │   ├── CallEvent.kt               # Comprehensive call record model
│   │   ├── AppSettings.kt             # Security preferences
│   │   └── AnalyticsSummary.kt        # Analytics and distribution data classes
│   │
│   ├── local/
│   │   ├── Converters.kt              # Room TypeConverters for enums and lists
│   │   ├── Entities.kt                # Room Entity definitions (6 tables)
│   │   ├── Daos.kt                    # Room DAOs with reactive Flow queries
│   │   └── AppDatabase.kt             # Room Database configuration
│   │
│   ├── repository/
│   │   ├── MockDataGenerator.kt       # Rich initial dataset (10 calls, 5 trusted, 3 contexts)
│   │   └── Repositories.kt            # Repository contracts & CallGuardRepository
│   │
│   └── remote/
│       └── CallGuardApiService.kt     # Clean REST API contract for cloud sync
│
├── domain/
│   └── decision/
│       └── CallDecisionEngine.kt      # Intelligent context-aware decision engine
│
├── ml/
│   ├── SpamPredictionService.kt       # Machine learning service interface
│   └── MockSpamPredictionService.kt   # Deterministic behavioral heuristic scoring
│
├── screening/
│   └── CallGuardCallScreeningService.kt # Android Telecom CallScreeningService integration
│
└── ui/
    ├── theme/
    │   ├── Color.kt                   # Cybersecurity dark & light color palette
    │   ├── Type.kt                    # Typography system
    │   ├── Shape.kt                   # Rounded corner design system
    │   └── Theme.kt                   # Theme composable
    │
    ├── components/
    │   ├── RiskComponents.kt          # Reusable RiskLevelBadge & RiskScoreGauge
    │   ├── CommonCards.kt             # StatCard, DecisionBadge, CallItemRow
    │   ├── AnalyticsCharts.kt         # DailyActivityBarChart, RiskDistributionStackedBar
    │   └── FeedbackDialog.kt          # Interactive user feedback modal
    │
    ├── navigation/
    │   ├── Screen.kt                  # Routes and destination metadata
    │   ├── BottomNavBar.kt            # Material 3 Navigation Bar
    │   └── CallGuardNavGraph.kt       # NavHost configuration for all 12 screens
    │
    └── screens/
        ├── splash/SplashScreen.kt
        ├── onboarding/OnboardingScreen.kt
        ├── home/HomeScreen.kt & HomeViewModel.kt
        ├── incoming/IncomingCallScreen.kt & IncomingCallViewModel.kt
        ├── history/CallHistoryScreen.kt & HistoryViewModel.kt
        ├── detail/CallDetailScreen.kt
        ├── trusted/TrustedCallersScreen.kt & AddTrustedCallerScreen.kt & TrustedViewModel.kt
        ├── context/ServiceContextScreen.kt & ContextViewModel.kt
        ├── analytics/AnalyticsScreen.kt & AnalyticsViewModel.kt
        ├── settings/SettingsScreen.kt & SettingsViewModel.kt
        └── about/AboutScreen.kt
```

---

## 6. How to Build and Run

### Prerequisites
- **Android Studio** (Koala / Ladybug or newer)
- **JDK 17** or **JDK 21** (Configured via `gradle.properties` / Android Studio JDK)
- **Android SDK** with Platform `android-35` installed

### Steps
1. Open the project in Android Studio:
   `File` -> `Open...` -> Select directory `d:\Final year project`.
2. Allow Gradle to sync dependencies automatically.
3. Build the project:
   - In terminal: `./gradlew assembleDebug`
   - Or in Android Studio: `Build` -> `Make Project` (Ctrl + F9).
4. Run on an Android Emulator (API 29+) or a physical Android device:
   - Click the green **Run 'app'** button (Shift + F10).

---

## 7. Mock Data Explanation

The application initializes with populated, realistic mock data:
- **10 Call History Records**:
  - `+91 98765 43210`: Unknown Robocall (Risk: 87%, Decision: Blocked).
  - `Amazon Delivery` (`+91 91234 56789`): Courier call with elevated frequency (Risk: 76%), **Allowed** due to active Amazon delivery context.
  - `+91 87654 32109`: Unregistered Promo (Risk: 54%, Decision: Warned).
  - `College Admin Office` (`+91 98450 11223`): Academic exam alerts (Risk: 12%, Decision: Allowed).
  - `EMI Finance Company` (`+91 80491 55667`): Verified financial institution (Risk: 28%, Decision: Allowed).
  - Additional entries for Robocalls, Flipkart delivery, and home personal contacts.
- **5 Trusted Callers**: Amazon Delivery, College, EMI Finance, Family, and Service Technician.
- **3 Active Service Contexts**: Amazon (*Out for Delivery*), Flipkart (*Arriving Today*), EMI Finance (*Active Account*).
- **Weekly Analytics**: Mon–Sun call telemetry and 3-tier risk distributions.

---

## 8. Future ML Integration Plan

The machine learning interface is isolated in `com.callguard.ai.ml.SpamPredictionService`:
```kotlin
interface SpamPredictionService {
    suspend fun predict(features: BehaviorFeatures, caller: Caller): SpamPrediction
}
```
### Next Steps for ML Team:
1. Train the XGBoost model on behavioral call datasets (e.g., Kaggle Telecom Spam / synthetic burst call logs).
2. Apply **Sequential Backward-Aware Selection (SBAS)** to identify the optimal 8–10 discriminatory features.
3. Convert the trained XGBoost model to **ONNX** (`.onnx`) or **TensorFlow Lite** (`.tflite`).
4. Place the model file in `app/src/main/assets/` and implement `XGBoostPredictionService : SpamPredictionService` using the ONNX Runtime for Android.

---

## 9. Future Backend Integration

The REST API contract is specified in `com.callguard.ai.data.remote.CallGuardApiService`:
- `POST /api/predictions`: Remote XGBoost cloud inference fallback.
- `GET /api/call-history`: Cloud backup and device sync.
- `GET /api/trusted-callers`: Organizational whitelist synchronization.
- `POST /api/feedback`: Ingest confirmed spam reports to continuously retrain the server-side model.
- `GET /api/model/version`: Check for updated feature weights and model files.

---

## 10. Privacy & Limitations

### Privacy
- Call audio is never accessed, recorded, or streamed.
- Phone numbers are SHA-256 hashed locally before writing to disk or logs.
- Only authorized metadata exposed by Android Telecom is processed.

### Current Limitations
- Real-time telecom intercept requires granting the `Default Caller ID & Spam App` role on Android 10+.
- Delivery context is currently populated locally or manually; future iterations can parse SMS delivery confirmations with user consent.
