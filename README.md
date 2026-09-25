# Virtual Lottery — Firebase Firestore Ready

यह project केवल virtual/entertainment lottery के लिए है। इसमें real money, deposits, withdrawals या cash prizes नहीं हैं।

## Firebase project
- Project ID: `gk-lottery-virtual`
- Android package: `com.example.virtuallottery`
- Android `google-services.json` पहले से `android/app/` में रखा गया है.
- Backend: Cloud Firestore
- Authentication: Email/Password for admin; Android customer uses anonymous auth.

## Firestore collections
- `settings/app` → title, announcement
- `lotteries/{lotteryId}` → name, points, tickets, status, winner
- `users/{uid}` → role, active, name, phone
- `tickets/{ticketId}` → lotteryId, ticketNo, ownerUid, ownerName, ownerPhone
- `activity/{id}` → adminUid, action, timestamp, details

## Admin panel
`admin/index.html` mobile-friendly panel है। यह Firebase Authentication REST API से admin login करता है और Firestore REST API से data पढ़ता/लिखता है। इसलिए Web App config की जरूरत नहीं है।

Admin login के लिए वही Firebase Authentication email/password इस्तेमाल करें जो आपने Firebase Console में बनाया है।

## Android app
Android customer app Firestore से live updates सुनता है और anonymous authentication शुरू करता है। Admin के changes Firestore में आते ही app में दिखाई देंगे।

## Security
Firestore में पहले `users/{ADMIN_UID}` document बनाया गया है:
- `role` = `admin` (String)
- `active` = `true` (Boolean)

फिर Firestore Rules publish करें। Admin UID को rules में hard-code करने की जरूरत नहीं है क्योंकि role document से admin check होता है।

## Build
Android Studio में `android/` folder खोलें। Gradle sync करके APK build करें।
