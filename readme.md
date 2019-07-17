# Android group chat using CometChat

This demo app is displaying how to easily implement group chat in your Android app using CometChat pro SDK

![The screenshot](https://github.com/MilanVucic/CometChatDemoApp/blob/master/screenshots/cometchat_screenshot.png "Screenshot of the app in action")

## Technology
- CometChat pro 1.3
- Kotlin

## Running the demo locally:
1. Install the latest Android studio
2. Clone the project
3. Open the project in Android studio

    3.1. Possibly install missing libraries
4. Launch the project on the emulator or on the actual device
5. [Head over to CometChat Pro and create an account](https://www.cometchat.com/pro?utm_source=github&utm_medium=example-code-readme)
6. From the [dashboard](https://app.cometchat.com/?utm_source=github&utm_medium=example-code-readme), create a new app called "Kotlin Group Chat"
7. Once created, click Explore
8. Go to the API Keys tab and click Create API Key
9. Create an API key called "Kotlin Group Chat Key" with Full Access
10. Update `API_KEY` and `APP_ID` in `Constants` file with the new values

## Testing the functionality of the app:
- Look at this line in MainActivity.kt ```val UID = "SUPERHERO5"```, that's where currently you can change the user, there's no UI for it for now
- Install on 1 emulator as some user e.g. "SUPERHERO1" (which is the test user for CometChat added by default
- Create a group by tapping on the button
- Install the app again on the second emulator using some other user ("SUPERHERO2")
- You should see the group from user2 that user1 has created
- Tap on it to join the group
- Tap again to enter the group chat
- Start chatting

## Useful links
- [Android docs](https://prodocs.cometchat.com/docs/android-quick-start)
- [Android groups docs](https://prodocs.cometchat.com/docs/android-groups)
- [Android tutorial](https://www.cometchat.com/tutorials/building-a-real-time-android-group-chat-app/)
